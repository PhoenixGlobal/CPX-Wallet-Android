package chinapex.com.wallet.view.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.AssetsRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SwitchTransactionRecyclerViewAdapter;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class SwitchWalletDialog extends DialogFragment implements View.OnClickListener,
        SwitchTransactionRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = SwitchWalletDialog.class.getSimpleName();
    private ImageButton mIb_switch_wallet_close;
    private RecyclerView mRv_me_switch_wallet;
    private List<WalletBean> mWalletBeans;
    private WalletBean mCurrentWalletBean;
    private int mPreIndex;
    private int mCurrentIndex;
    private SwitchTransactionRecyclerViewAdapter mSwitchTransactionRecyclerViewAdapter;
    private onItemSelectedListener mOnItemSelectedListener;

    public static SwitchWalletDialog newInstance() {
        return new SwitchWalletDialog();
    }

    public void setCurrentWalletBean(WalletBean currentWalletBean) {
        mCurrentWalletBean = currentWalletBean;
    }

    public interface onItemSelectedListener {
        void onItemSelected(WalletBean walletBean);
    }

    public void setOnItemSelectedListener(onItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        //去掉边框
        Window window = getDialog().getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        return inflater.inflate(R.layout.dialog_switch_wallet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dip2px(getActivity(), 264), DensityUtil
                .dip2px(getActivity(), 436));
    }

    private void initData() {
        if (null == mCurrentWalletBean) {
            CpLog.e(TAG, "mCurrentWalletBean is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }


        mWalletBeans = apexWalletDbDao.queryWalletBeans(Constant.TABLE_APEX_WALLET);
        for (WalletBean walletBean : mWalletBeans) {
            if (null == walletBean) {
                CpLog.e(TAG, "walletBean is null!");
                continue;
            }

            if (mCurrentWalletBean.equals(walletBean)) {
                walletBean.setSelected(true);
                mCurrentIndex = mWalletBeans.indexOf(walletBean);
            }
        }
    }

    private void initView(View view) {
        mIb_switch_wallet_close = view.findViewById(R.id.ib_switch_wallet_close);
        mIb_switch_wallet_close.setOnClickListener(this);
        mRv_me_switch_wallet = view.findViewById(R.id.rv_me_switch_wallet);


        mRv_me_switch_wallet.setLayoutManager(new LinearLayoutManager(ApexWalletApplication
                .getInstance(), LinearLayoutManager.VERTICAL, false));
        mSwitchTransactionRecyclerViewAdapter = new SwitchTransactionRecyclerViewAdapter
                (mWalletBeans);
        mSwitchTransactionRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_me_switch_wallet.setAdapter(mSwitchTransactionRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_switch_wallet_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        WalletBean walletBean = mWalletBeans.get(position);
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        mOnItemSelectedListener.onItemSelected(walletBean);
        walletBean.setSelected(true);

        mPreIndex = mCurrentIndex;
        mCurrentIndex = mWalletBeans.indexOf(walletBean);

        //当前所选与上次不同
        if (!walletBean.equals(mWalletBeans.get(mPreIndex))) {
            mWalletBeans.get(mPreIndex).setSelected(false);
        }

        mSwitchTransactionRecyclerViewAdapter.notifyDataSetChanged();
    }
}
