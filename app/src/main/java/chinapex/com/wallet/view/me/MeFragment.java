package chinapex.com.wallet.view.me;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.MeRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.changelistener.OnItemAddListener;
import chinapex.com.wallet.changelistener.OnItemDeleteListener;
import chinapex.com.wallet.changelistener.OnItemNameUpdateListener;
import chinapex.com.wallet.changelistener.OnItemStateUpdateListener;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.view.MeSkipActivity;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class MeFragment extends BaseFragment implements MeRecyclerViewAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener,
        OnItemDeleteListener, OnItemAddListener, OnItemStateUpdateListener,
        OnItemNameUpdateListener {

    private static final String TAG = MeFragment.class.getSimpleName();
    private RecyclerView mRv_me;
    private MeRecyclerViewAdapter mMeRecyclerViewAdapter;
    private List<WalletBean> mWalletBeans;
    private SwipeRefreshLayout mSl_me;
    private Button mBt_me_manage_wallet;
    private Button mBt_me_transaction_record;
    private boolean mIsTransactionRecordState;
    private WalletBean mCurrentClickedWalletBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mRv_me = view.findViewById(R.id.rv_me);
        mSl_me = view.findViewById(R.id.sl_me);
        mBt_me_manage_wallet = view.findViewById(R.id.bt_me_manage_wallet);
        mBt_me_transaction_record = view.findViewById(R.id.bt_me_transaction_record);

        mRv_me.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(),
                LinearLayoutManager.VERTICAL, false));
        mWalletBeans = initWalletBeans();
        mMeRecyclerViewAdapter = new MeRecyclerViewAdapter(mWalletBeans);
        mMeRecyclerViewAdapter.setOnItemClickListener(this);

        int space = DensityUtil.dip2px(getActivity(), 5);
        mRv_me.addItemDecoration(new SpacesItemDecoration(space));

        mRv_me.setAdapter(mMeRecyclerViewAdapter);

        mSl_me.setColorSchemeColors(this.getActivity().getResources().getColor(R.color
                .colorPrimary));
        mSl_me.setOnRefreshListener(this);

        mBt_me_manage_wallet.setOnClickListener(this);
        mBt_me_transaction_record.setOnClickListener(this);
    }

    private void initData() {
        ApexListeners.getInstance().addOnItemDeleteListener(this);
        ApexListeners.getInstance().addOnItemAddListener(this);
        ApexListeners.getInstance().addOnItemStateUpdateListener(this);
        ApexListeners.getInstance().addOnItemNameUpdateListener(this);
    }

    @Override
    public void onItemClick(int position) {
        mCurrentClickedWalletBean = mWalletBeans.get(position);
        if (null == mCurrentClickedWalletBean) {
            CpLog.e(TAG, "mCurrentClickedWalletBean is null!");
            return;
        }

        if (mIsTransactionRecordState) {
            toMeTransactionRecordFragment();
        } else {
            toMeManagerDetailFragment();
        }

    }

    @Override
    public void onRefresh() {
        // 预留后续刷新功能
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSl_me.setRefreshing(false);
            }
        });
    }

    private List<WalletBean> initWalletBeans() {
        List<WalletBean> walletBeans = new ArrayList<>();
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return walletBeans;
        }

        walletBeans.addAll(apexWalletDbDao.queryWalletBeans(Constant.TABLE_APEX_WALLET));
        return walletBeans;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 管理钱包
            case R.id.bt_me_manage_wallet:
                manageWalletIsSelected();
                break;
            // 交易记录
            case R.id.bt_me_transaction_record:
                transactionRecordIsSelected();
                break;
        }
    }

    private void manageWalletIsSelected() {
        mBt_me_manage_wallet.setBackgroundResource(R.drawable.shape_white_bt_bg);
        mBt_me_manage_wallet.setTextColor(ApexWalletApplication.getInstance().getResources()
                .getColor(R.color
                        .colorPrimary));

//                mBt_me_transaction_record.setBackgroundResource(0);
        // 为适配小米4，否则button会有边框背景
        mBt_me_transaction_record.setBackground(new ColorDrawable(0));
        mBt_me_transaction_record.setTextColor(Color.WHITE);

        mIsTransactionRecordState = false;

        for (WalletBean walletBean : mWalletBeans) {
            walletBean.setSelectedTag(Constant.SELECTED_TAG_MANAGER_WALLET);
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void transactionRecordIsSelected() {
        mBt_me_transaction_record.setBackgroundResource(R.drawable.shape_white_bt_bg);
        mBt_me_transaction_record.setTextColor(ApexWalletApplication.getInstance().getResources()
                .getColor(R.color
                        .colorPrimary));

//                mBt_me_manage_wallet.setBackgroundResource(0);
        // 为适配小米4，否则button会有边框背景
        mBt_me_manage_wallet.setBackground(new ColorDrawable(0));
        mBt_me_manage_wallet.setTextColor(Color.WHITE);

        mIsTransactionRecordState = true;

        for (WalletBean walletBean : mWalletBeans) {
            walletBean.setSelectedTag(Constant.SELECTED_TAG_TRANSACTION_RECORED);
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void toMeManagerDetailFragment() {
        startActivityBundle(MeSkipActivity.class, false, Constant.ME_MANAGER_DETAIL_BUNDLE, Constant
                .ME_SKIP_ACTIVITY_FRAGMENT_TAG, Constant.FRAGMENT_TAG_ME_MANAGE_DETAIL, Constant
                .PARCELABLE_WALLET_BEAN_MANAGE_DETAIL, mCurrentClickedWalletBean);
    }

    private void toMeTransactionRecordFragment() {
        startActivityBundle(MeSkipActivity.class, false, Constant.ME_MANAGER_DETAIL_BUNDLE, Constant
                        .ME_SKIP_ACTIVITY_FRAGMENT_TAG, Constant.FRAGMENT_TAG_ME_TRANSACTION_RECORD,
                Constant
                        .PARCELABLE_WALLET_BEAN_MANAGE_DETAIL, mCurrentClickedWalletBean);
    }

    // 删除钱包时回调
    @Override
    public void onItemDelete(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "onItemDelete() -> walletBean is null!");
            return;
        }

        mWalletBeans.remove(walletBean);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    // 新增钱包时回调
    @Override
    public void onItemAdd(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "onItemAdd() -> walletBean is null!");
            return;
        }

        mWalletBeans.add(walletBean);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    // 备份钱包后回调
    @Override
    public void OnItemStateUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        for (WalletBean walletBeanTmp : mWalletBeans) {
            if (null == walletBeanTmp) {
                CpLog.e(TAG, "walletBeanTmp is null!");
                continue;
            }

            if (walletBeanTmp.equals(walletBean)) {
                walletBeanTmp.setBackupState(walletBean.getBackupState());
            }
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }

    // 修改钱包名称回调
    @Override
    public void OnItemNameUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        for (WalletBean walletBeanTmp : mWalletBeans) {
            if (null == walletBeanTmp) {
                CpLog.e(TAG, "walletBeanTmp is null!");
                continue;
            }

            if (walletBeanTmp.equals(walletBean)) {
                walletBeanTmp.setWalletName(walletBean.getWalletName());
            }
        }

        mMeRecyclerViewAdapter.notifyDataSetChanged();
    }
}
