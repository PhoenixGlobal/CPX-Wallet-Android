package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        mTv_assets_overview_wallet_name = (TextView) findViewById(R.id
                .tv_assets_overview_wallet_name);
        mTv_assets_overview_wallet_address = (TextView) findViewById(R.id
                .tv_assets_overview_wallet_address);
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

        mWalletBean = intent.getParcelableExtra(Constant.WALLET_BEAN);
        if (null == mWalletBean) {
            CpLog.e(TAG, "initData() -> mWalletBean is null!");
            return;
        }

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
        mIGetBalancePresenter.init(mWalletBean.getWalletType());
        mIGetBalancePresenter.getGlobalAssetBalance(mWalletBean);
        mIGetBalancePresenter.getColorAssetBalance(mWalletBean);
    }

    @Override
    public void onItemClick(int position) {
        BalanceBean balanceBean = mBalanceBeans.get(position);
        if (null == balanceBean) {
            CpLog.e(TAG, "balanceBean is null!");
            return;
        }

        Intent intent = new Intent(ApexWalletApplication.getInstance(), BalanceDetailActivity
                .class);
        intent.putExtra(Constant.WALLET_BEAN, mWalletBean);
        intent.putExtra(Constant.BALANCE_BEAN, balanceBean);
        startActivity(intent);
    }

    // 设置默认添加的资产
    private void getDefaultAssets() {
        if (null == mBalanceBeans) {
            CpLog.e(TAG, "mBalanceBeans is null!");
            return;
        }

        List<BalanceBean> globalAssets = getGlobalAssets();
        if (null != globalAssets && !globalAssets.isEmpty()) {
            for (BalanceBean globalAssetBalanceBean : globalAssets) {
                if (null == globalAssetBalanceBean) {
                    CpLog.e(TAG, "globalAssetBalanceBean is null!");
                    continue;
                }

                if (Constant.ASSETS_NEO.equals(globalAssetBalanceBean.getAssetsID())
                        || Constant.ASSETS_NEO_GAS.equals(globalAssetBalanceBean.getAssetsID())) {
                    mBalanceBeans.add(0, globalAssetBalanceBean);
                } else {
                    mBalanceBeans.add(globalAssetBalanceBean);
                }
            }
        }

        List<BalanceBean> colorAssets = getColorAssets();
        if (null != colorAssets && !colorAssets.isEmpty()) {
            for (BalanceBean colorAssetBalanceBean : colorAssets) {
                if (null == colorAssetBalanceBean) {
                    CpLog.e(TAG, "colorAssetBalanceBean is null!");
                    continue;
                }

                if (Constant.ASSETS_CPX.equals(colorAssetBalanceBean.getAssetsID())) {
                    mBalanceBeans.add(0, colorAssetBalanceBean);
                } else {
                    mBalanceBeans.add(colorAssetBalanceBean);
                }
            }
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

        String tableName = null;
        String assetType = null;
        switch (mWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                tableName = Constant.TABLE_NEO_ASSETS;
                assetType = Constant.ASSET_TYPE_NEP5;
                break;
            case Constant.WALLET_TYPE_ETH:
                tableName = Constant.TABLE_ETH_ASSETS;
                assetType = Constant.ASSET_TYPE_ERC20;
                break;
            case Constant.WALLET_TYPE_CPX:
                tableName = Constant.TABLE_CPX_ASSETS;
                break;
            default:
                break;
        }

        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "getColorAssets() -> tableName or assetType is null!");
            return null;
        }

        ArrayList<BalanceBean> balanceBeans = new ArrayList<>();
        for (String colorAsset : colorAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(tableName, colorAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "getColorAssets() -> assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setWalletType(mWalletBean.getWalletType());
            balanceBean.setAssetsID(colorAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(assetType);
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

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null");
            return null;
        }

        String tableName = null;
        String assetType = null;
        switch (mWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                tableName = Constant.TABLE_NEO_ASSETS;
                assetType = Constant.ASSET_TYPE_GLOBAL;
                break;
            case Constant.WALLET_TYPE_ETH:
                tableName = Constant.TABLE_ETH_ASSETS;
                assetType = Constant.ASSET_TYPE_ETH;
                break;
            case Constant.WALLET_TYPE_CPX:
                tableName = Constant.TABLE_CPX_ASSETS;
                break;
            default:
                break;
        }

        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "getGlobalAssets() -> tableName or assetType is null!");
            return null;
        }

        ArrayList<BalanceBean> balanceBeans = new ArrayList<>();
        for (String globalAsset : globalAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(tableName, globalAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "getGlobalAssets() -> assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setWalletType(mWalletBean.getWalletType());
            balanceBean.setAssetsID(globalAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(assetType);
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
        mIGetBalancePresenter.getGlobalAssetBalance(mWalletBean);
        mIGetBalancePresenter.getColorAssetBalance(mWalletBean);
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

            if (Constant.ASSETS_NEO.equals(checkedAsset) || Constant.ASSETS_NEO_GAS.equals(checkedAsset)) {
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

        switch (mWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                apexWalletDbDao.updateCheckedAssets(Constant.TABLE_NEO_WALLET, mWalletBean);
                break;
            case Constant.WALLET_TYPE_ETH:
                apexWalletDbDao.updateCheckedAssets(Constant.TABLE_ETH_WALLET, mWalletBean);
                break;
        }

        ApexListeners.getInstance().notifyAssetJsonUpdate(mWalletBean);
        updateAssets();
    }

    private void updateAssets() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        if (null == mBalanceBeans) {
            CpLog.e(TAG, "mBalanceBeans is null!");
            return;
        }

        Iterator<BalanceBean> iterator = mBalanceBeans.iterator();
        while (iterator.hasNext()) {
            BalanceBean balanceBeanTmp = iterator.next();
            if (null == balanceBeanTmp) {
                CpLog.e(TAG, "balanceBeanTmp is null!");
                continue;
            }

            if (!mCurrentAssets.contains(balanceBeanTmp.getAssetsID())) {
                iterator.remove();
            }
        }

        String tableName = null;
        switch (mWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                tableName = Constant.TABLE_NEO_ASSETS;
                break;
            case Constant.WALLET_TYPE_ETH:
                tableName = Constant.TABLE_ETH_ASSETS;
                break;
            case Constant.WALLET_TYPE_CPX:
                tableName = Constant.TABLE_CPX_ASSETS;
                break;
        }

        a:
        for (String currentAsset : mCurrentAssets) {
            if (TextUtils.isEmpty(currentAsset)) {
                CpLog.e(TAG, "currentAsset is null!");
                continue;
            }

            for (BalanceBean balanceBean : mBalanceBeans) {
                if (null == balanceBean) {
                    CpLog.e(TAG, "balanceBean is null!");
                    continue;
                }

                if (currentAsset.equals(balanceBean.getAssetsID())) {
                    continue a;
                }
            }

            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(tableName, currentAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            BalanceBean balanceBeanNew = new BalanceBean();
            balanceBeanNew.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBeanNew.setWalletType(mWalletBean.getWalletType());
            balanceBeanNew.setAssetsID(currentAsset);
            balanceBeanNew.setAssetSymbol(assetBean.getSymbol());
            balanceBeanNew.setAssetType(assetBean.getType());
            balanceBeanNew.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
            balanceBeanNew.setAssetsValue("0");
            mBalanceBeans.add(balanceBeanNew);
        }

        mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();

        mIGetBalancePresenter.getGlobalAssetBalance(mWalletBean);
        mIGetBalancePresenter.getColorAssetBalance(mWalletBean);
    }

    @Override
    public void getGlobalAssetBalance(HashMap<String, BalanceBean> balanceBeans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSl_assets_overview_rv.isRefreshing()) {
                    mSl_assets_overview_rv.setRefreshing(false);
                }
            }
        });

        if (null == mBalanceBeans || mBalanceBeans.isEmpty()) {
            CpLog.e(TAG, "getGlobalAssetBalance() -> mBalanceBeans is null or empty!");
            return;
        }

        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "getGlobalAssetBalance() -> balanceBeans is null or empty!");
            return;
        }

        for (BalanceBean balanceBean : mBalanceBeans) {
            if (null == balanceBean) {
                CpLog.e(TAG, "getGlobalAssetBalance() -> balanceBean is null or empty!");
                continue;
            }

            String assetsID = balanceBean.getAssetsID();
            if (balanceBeans.containsKey(assetsID)) {
                BalanceBean balanceBeanTmp = balanceBeans.get(assetsID);
                if (null == balanceBeanTmp) {
                    CpLog.e(TAG, "getGlobalAssetBalance() -> balanceBeanTmp is null or empty!");
                    continue;
                }

                balanceBean.setAssetsValue(balanceBeanTmp.getAssetsValue());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getColorAssetBalance(HashMap<String, BalanceBean> balanceBeans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSl_assets_overview_rv.isRefreshing()) {
                    mSl_assets_overview_rv.setRefreshing(false);
                }
            }
        });

        if (null == mBalanceBeans || mBalanceBeans.isEmpty()) {
            CpLog.e(TAG, "getColorAssetBalance() -> mBalanceBeans is null or empty!");
            return;
        }

        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "getColorAssetBalance() -> balanceBeans is null or empty!");
            return;
        }

        for (BalanceBean balanceBean : mBalanceBeans) {
            if (null == balanceBean) {
                CpLog.e(TAG, "getColorAssetBalance() -> balanceBean is null or empty!");
                continue;
            }

            String assetsID = balanceBean.getAssetsID();
            if (balanceBeans.containsKey(assetsID)) {
                BalanceBean balanceBeanTmp = balanceBeans.get(assetsID);
                if (null == balanceBeanTmp) {
                    CpLog.e(TAG, "getColorAssetBalance() -> balanceBeanTmp is null or empty!");
                    continue;
                }

                balanceBean.setAssetsValue(balanceBeanTmp.getAssetsValue());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssetsOverviewRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
