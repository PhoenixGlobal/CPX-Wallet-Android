package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.AssetsOverviewRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.presenter.balance.GetBalancePresenter;
import chinapex.com.wallet.presenter.balance.IGetBalancePresenter;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.dialog.AddAssetsDialog;

public class AssetsOverviewActivity extends BaseActivity implements AssetsOverviewRecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AddAssetsDialog.onCheckedAssetsListener, IGetBalanceView {

    private static final String TAG = AssetsOverviewActivity.class.getSimpleName();
    private TextView mTv_assets_overview_wallet_name;
    private TextView mTv_assets_overview_wallet_address;
    private WalletBean mWalletBean;
    private RecyclerView mRv_assets_overview;
    private List<BalanceBean> mBalanceBeans;
    private AssetsOverviewRecyclerViewAdapter mAssetsOverviewRecyclerViewAdapter;
    private SwipeRefreshLayout mSl_assets_overview_rv;
    private ImageButton mIb_assets_overview_ellipsis;
    private List<String> mCurrentAssets;
    private int mCurrentWalletType;
    private IGetBalancePresenter mIGetBalancePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_overview);

        initView();
        initData();
        getBalance();
    }

    private void initView() {
        mTv_assets_overview_wallet_name = (TextView) findViewById(R.id.tv_assets_overview_wallet_name);
        mTv_assets_overview_wallet_address = (TextView) findViewById(R.id.tv_assets_overview_wallet_address);
        mIb_assets_overview_ellipsis = (ImageButton) findViewById(R.id.ib_assets_overview_ellipsis);

        mRv_assets_overview = (RecyclerView) findViewById(R.id.rv_assets_overview);
        mSl_assets_overview_rv = (SwipeRefreshLayout) findViewById(R.id.sl_assets_overview_rv);

        mIb_assets_overview_ellipsis.setOnClickListener(this);
        mSl_assets_overview_rv.setColorSchemeColors(this.getResources().getColor(R.color.c_1253BF));
        mSl_assets_overview_rv.setOnRefreshListener(this);

        // 复制地址
        mTv_assets_overview_wallet_address.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        // 确定钱包类型 NEO/ETH/CPX
        mCurrentWalletType = intent.getIntExtra(Constant.PARCELABLE_WALLET_TYPE, Constant.WALLET_TYPE_NEO);
        mWalletBean = intent.getParcelableExtra(Constant.WALLET_BEAN);

        mTv_assets_overview_wallet_name.setText(mWalletBean.getName());
        mTv_assets_overview_wallet_address.setText(mWalletBean.getAddress());

        mRv_assets_overview.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(), LinearLayoutManager
                .VERTICAL, false));
        mCurrentAssets = new ArrayList<>();
        mBalanceBeans = new ArrayList<>();
        getDefaultAssets();
        mAssetsOverviewRecyclerViewAdapter = new AssetsOverviewRecyclerViewAdapter(mBalanceBeans);
        mAssetsOverviewRecyclerViewAdapter.setOnItemClickListener(this);

        int space = DensityUtil.dip2px(this, 8);
        mRv_assets_overview.addItemDecoration(new SpacesItemDecoration(space));
        mRv_assets_overview.setAdapter(mAssetsOverviewRecyclerViewAdapter);
    }

    private void getBalance() {
        mIGetBalancePresenter = new GetBalancePresenter(this);
        mIGetBalancePresenter.init(mCurrentWalletType);
        mIGetBalancePresenter.getAssetBalance(mWalletBean);
    }

    @Override
    public void getGlobalAssetBalance(List<BalanceBean> balanceBeans) {
        mBalanceBeans.addAll(balanceBeans);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSl_assets_overview_rv.isRefreshing()) {
                    mSl_assets_overview_rv.setRefreshing(false);
                }
                mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getColorAssetBalance(List<BalanceBean> balanceBeans) {
        mBalanceBeans.addAll(balanceBeans);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSl_assets_overview_rv.isRefreshing()) {
                    mSl_assets_overview_rv.setRefreshing(false);
                }
                mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        BalanceBean balanceBean = mBalanceBeans.get(position);
        if (null == balanceBean) {
            CpLog.e(TAG, "balanceBean is null!");
            return;
        }

        Intent intent = new Intent(ApexWalletApplication.getInstance(), BalanceDetailActivity.class);
        intent.putExtra(Constant.WALLET_BEAN, mWalletBean);
        intent.putExtra(Constant.BALANCE_BEAN, balanceBean);
        intent.putExtra(Constant.PARCELABLE_WALLET_TYPE, mCurrentWalletType);
        startActivity(intent);
    }

    // 设置默认添加的资产
    private void getDefaultAssets() {
        if (null == mBalanceBeans) {
            CpLog.e(TAG, "mBalanceBeans is null!");
            return;
        }

        List<BalanceBean> colorAssets = getColorAssets();
        if (null != colorAssets && !colorAssets.isEmpty()) {
            mBalanceBeans.addAll(colorAssets);
        }

        List<BalanceBean> globalAssets = getGlobalAssets();
        if (null != globalAssets && !globalAssets.isEmpty()) {
            mBalanceBeans.addAll(globalAssets);
        }
    }

    private List<BalanceBean> getColorAssets() {
        if (null == mWalletBean) {
            CpLog.e(TAG, "getColorAssets() -> mWalletBean is null!");
            return null;
        }

        String colorAssetJson = mWalletBean.getColorAssetJson();
        List<String> colorAssets = GsonUtils.json2List(colorAssetJson, String.class);
        if (null == colorAssets || colorAssets.isEmpty()) {
            CpLog.e(TAG, "colorAssets is null or empty!");
            return null;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null");
            return null;
        }

        ArrayList<BalanceBean> balanceBeans = new ArrayList<>();
        for (String colorAsset : colorAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(colorAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setAssetsID(colorAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(Constant.ASSET_TYPE_NEP5);
            balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
            balanceBean.setAssetsValue("0");
            balanceBeans.add(balanceBean);
            mCurrentAssets.add(colorAsset);
        }
        return balanceBeans;
    }

    private List<BalanceBean> getGlobalAssets() {
        if (null == mWalletBean) {
            CpLog.e(TAG, "getGlobalAssets() -> mWalletBean is null!");
            return null;
        }

        String assetJson = mWalletBean.getAssetJson();
        List<String> globalAssets = GsonUtils.json2List(assetJson, String.class);
        if (null == globalAssets || globalAssets.isEmpty()) {
            CpLog.e(TAG, "globalAssets is null or empty!");
            return null;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null");
            return null;
        }

        ArrayList<BalanceBean> balanceBeans = new ArrayList<>();
        for (String globalAsset : globalAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(globalAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setAssetsID(globalAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(Constant.ASSET_TYPE_GLOBAL);
            balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
            balanceBean.setAssetsValue("0");
            balanceBeans.add(balanceBean);
            if (mCurrentAssets.size() >= 1) {
                mCurrentAssets.add(1, globalAsset);
            } else {
                mCurrentAssets.add(globalAsset);
            }
        }
        return balanceBeans;
    }

    @Override
    public void onRefresh() {
        mIGetBalancePresenter.getAssetBalance(mWalletBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_assets_overview_ellipsis:
                showAddAssetsDialog();
                break;
            case R.id.tv_assets_overview_wallet_address:
                String copyAddr = mTv_assets_overview_wallet_address.getText().toString().trim();
                PhoneUtils.copy2Clipboard(ApexWalletApplication.getInstance(), copyAddr);
                ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                        .getResources().getString(R.string.wallet_address_copied));
            default:
                break;
        }
    }

    public void showAddAssetsDialog() {
        AddAssetsDialog addAssetsDialog = AddAssetsDialog.newInstance();
        addAssetsDialog.setOnCheckedAssetsListener(this);
        addAssetsDialog.setCurrentAssets(mCurrentAssets);
        addAssetsDialog.show(getFragmentManager(), "AddAssetsDialog");
    }

    @Override
    public void onCheckedAssets(List<String> checkedAssets) {
        if (null == checkedAssets || checkedAssets.isEmpty()) {
            CpLog.w(TAG, "checkedAssets is null or empty!");
            return;
        }

        mCurrentAssets.clear();
        mCurrentAssets.addAll(checkedAssets);

        List<String> colorAssets = new ArrayList<>();
        List<String> globalAssets = new ArrayList<>();

        for (String checkedAsset : checkedAssets) {
            if (TextUtils.isEmpty(checkedAsset)) {
                CpLog.e(TAG, "checkedAsset is null!");
                continue;
            }

            if (Constant.ASSETS_NEO.equals(checkedAsset)
                    || Constant.ASSETS_NEO_GAS.equals(checkedAsset)) {
                globalAssets.add(checkedAsset);
            } else {
                colorAssets.add(checkedAsset);
            }
        }

        mWalletBean.setAssetJson(GsonUtils.toJsonStr(globalAssets));
        mWalletBean.setColorAssetJson(GsonUtils.toJsonStr(colorAssets));
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        switch (mCurrentWalletType) {
            case Constant.WALLET_TYPE_NEO:
                apexWalletDbDao.updateCheckedAssets(Constant.TABLE_NEO_WALLET, mWalletBean);
                break;
            case Constant.WALLET_TYPE_ETH:
                apexWalletDbDao.updateCheckedAssets(Constant.TABLE_ETH_WALLET, mWalletBean);
                break;
        }

        ApexListeners.getInstance().notifyAssetJsonUpdate(mWalletBean);

        mIGetBalancePresenter.getAssetBalance(mWalletBean);
    }

    @Override
    public void getAssetBalance(final List<BalanceBean> balanceBeans) {
        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "balanceBeans is null or emtpy!");
            return;
        }

        mBalanceBeans.clear();
        mBalanceBeans.addAll(balanceBeans);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSl_assets_overview_rv.isRefreshing()) {
                    mSl_assets_overview_rv.setRefreshing(false);
                }

                mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

}
