package chinapex.com.wallet.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.MeRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.changelistener.OnItemNameUpdateListener;
import chinapex.com.wallet.changelistener.OnWalletBackupStateUpdateListener;
import chinapex.com.wallet.changelistener.OnWalletDeleteListener;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;

public class Me2Activity extends BaseActivity implements MeRecyclerViewAdapter
        .OnItemClickListener, OnItemNameUpdateListener, OnWalletBackupStateUpdateListener,
        OnWalletDeleteListener {
    private static final String TAG = Me2Activity.class.getSimpleName();
    private List<WalletBean> mWalletBeans;
    private RecyclerView mRv_me2;
    private MeRecyclerViewAdapter mMeRecyclerViewAdapter;
    private TextView mTv_me2_title;
    private WalletBean mCurrentClickedWallet;
    private String mShowTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me2);

        initView();
        initData();
    }

    private void initView() {
        mRv_me2 = (RecyclerView) findViewById(R.id.rv_me2);
        mTv_me2_title = (TextView) findViewById(R.id.tv_me2_title);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mShowTag = intent.getStringExtra(Constant.ME_2_SHOULD_BE_SHOW);
        if (TextUtils.isEmpty(mShowTag)) {
            CpLog.e(TAG, "showTag is null!");
            return;
        }

        switch (mShowTag) {
            case Constant.ME_2_SHOULD_BE_SHOW_MANAGE_WALLET:
                mTv_me2_title.setText(ApexWalletApplication.getInstance().getResources()
                        .getString(R.string.manage_wallets));
                break;
            case Constant.ME_2_SHOULD_BE_SHOW_TX_RECORDS:
                mTv_me2_title.setText(ApexWalletApplication.getInstance().getResources()
                        .getString(R.string.transaction_records));
                break;
            default:
                break;
        }

        mWalletBeans = initWalletBeans();
        mRv_me2.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(),
                LinearLayoutManager.VERTICAL, false));
        int space = DensityUtil.dip2px(ApexWalletApplication.getInstance(), 8);
        mRv_me2.addItemDecoration(new SpacesItemDecoration(space));
        mMeRecyclerViewAdapter = new MeRecyclerViewAdapter(mWalletBeans);
        mMeRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_me2.setAdapter(mMeRecyclerViewAdapter);

        ApexListeners.getInstance().addOnItemStateUpdateListener(this);
        ApexListeners.getInstance().addOnItemDeleteListener(this);
        ApexListeners.getInstance().addOnItemNameUpdateListener(this);
    }

    private List<WalletBean> initWalletBeans() {
        List<WalletBean> walletBeans = new ArrayList<>();
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return walletBeans;
        }

        walletBeans.addAll(apexWalletDbDao.queryWallets(Constant.TABLE_NEO_WALLET));
        walletBeans.addAll(apexWalletDbDao.queryWallets(Constant.TABLE_ETH_WALLET));
        if (walletBeans.isEmpty()) {
            CpLog.w(TAG, "local have no wallet");
            return walletBeans;
        }

        for (WalletBean walletBean : walletBeans) {
            if (null == walletBean) {
                CpLog.e(TAG, "walletBean is null!");
                continue;
            }

            switch (mShowTag) {
                case Constant.ME_2_SHOULD_BE_SHOW_MANAGE_WALLET:
                    walletBean.setSelectedTag(Constant.SELECTED_TAG_MANAGER_WALLET);
                    break;
                case Constant.ME_2_SHOULD_BE_SHOW_TX_RECORDS:
                    walletBean.setSelectedTag(Constant.SELECTED_TAG_TRANSACTION_RECORED);
                    break;
                default:
                    break;
            }
        }

        return walletBeans;
    }

    @Override
    public void onItemClick(int position) {
        mCurrentClickedWallet = mWalletBeans.get(position);
        if (null == mCurrentClickedWallet) {
            CpLog.e(TAG, "mCurrentClickedWallet is null!");
            return;
        }

        switch (mShowTag) {
            case Constant.ME_2_SHOULD_BE_SHOW_MANAGE_WALLET:
                toMeManagerDetailFragment();
                break;
            case Constant.ME_2_SHOULD_BE_SHOW_TX_RECORDS:
                toMeTransactionRecordFragment();
                break;
            default:
                break;
        }
    }

    private void toMeManagerDetailFragment() {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), Me3Activity.class);
        intent.putExtra(Constant.ME_SKIP_ACTIVITY_FRAGMENT_TAG, Constant.FRAGMENT_TAG_ME_MANAGE_DETAIL);
        intent.putExtra(Constant.PARCELABLE_WALLET_BEAN_MANAGE_DETAIL, mCurrentClickedWallet);
        startActivity(intent);
    }

    private void toMeTransactionRecordFragment() {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), Me3Activity.class);
        intent.putExtra(Constant.ME_SKIP_ACTIVITY_FRAGMENT_TAG, Constant.FRAGMENT_TAG_ME_TRANSACTION_RECORD);
        intent.putExtra(Constant.PARCELABLE_WALLET_BEAN_MANAGE_DETAIL, mCurrentClickedWallet);
        startActivity(intent);
    }

    // 备份钱包后回调
    @Override
    public void onWalletBackupStateUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        for (WalletBean walletBeanTmp : mWalletBeans) {
            if (null == walletBeanTmp) {
                CpLog.e(TAG, "walletBeanTmp is null!");
                continue;
            }

            if (walletBeanTmp.getAddress().equals(walletBean.getAddress())) {
                walletBeanTmp.setBackupState(walletBean.getBackupState());
            }
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }

    // 删除钱包时回调
    @Override
    public void onWalletDelete(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "onWalletDelete() -> neoWallet is null!");
            return;
        }

        if (!mWalletBeans.contains(walletBean)) {
            CpLog.e(TAG, "onWalletDelete() -> this wallet not exist!");
            return;
        }

        mWalletBeans.remove(walletBean);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    // 修改钱包名称回调
    @Override
    public void OnItemNameUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "neoWallet is null!");
            return;
        }

        for (WalletBean walletBeanTmp : mWalletBeans) {
            if (null == walletBeanTmp) {
                CpLog.e(TAG, "walletBeanTmp is null!");
                continue;
            }

            if (walletBeanTmp.getAddress().equals(walletBean.getAddress())) {
                walletBeanTmp.setName(walletBean.getName());
            }
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApexListeners.getInstance().removeOnItemStateUpdateListener(this);
        ApexListeners.getInstance().removeOnItemDeleteListener(this);
        ApexListeners.getInstance().removeOnItemNameUpdateListener(this);
    }
}
