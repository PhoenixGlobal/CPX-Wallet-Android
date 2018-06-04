package chinapex.com.wallet.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class MeManagerDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MeManagerDetailFragment.class.getSimpleName();
    private TextView mTv_me_manager_detail_title;
    private TextView mTv_me_manager_detail_balance;
    private TextView mTv_me_manager_detail_address;
    private TextView mTv_me_manager_detail_bottom_wallet_name;
    private Button mBt_me_manager_detail_backup;
    private Button mBt_me_manager_detail_delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_manager_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        mTv_me_manager_detail_title = view.findViewById(R.id.tv_me_manager_detail_title);
        mTv_me_manager_detail_balance = view.findViewById(R.id.tv_me_manager_detail_balance);
        mTv_me_manager_detail_address = view.findViewById(R.id.tv_me_manager_detail_address);
        mTv_me_manager_detail_bottom_wallet_name = view.findViewById(R.id
                .tv_me_manager_detail_bottom_wallet_name);
        mBt_me_manager_detail_backup = view.findViewById(R.id.bt_me_manager_detail_backup);
        mBt_me_manager_detail_delete = view.findViewById(R.id.bt_me_manager_detail_delete);

        mBt_me_manager_detail_backup.setOnClickListener(this);
        mBt_me_manager_detail_delete.setOnClickListener(this);
    }

    private void initData() {
        MeFragment meFragment = (MeFragment) getActivity().getFragmentManager().findFragmentByTag
                (2 + "");
        WalletBean currentClickedWalletBean = meFragment.getCurrentClickedWalletBean();
        if (null == currentClickedWalletBean) {
            CpLog.e(TAG, "currentClickedWalletBean is null!");
            return;
        }

        mTv_me_manager_detail_title.setText(String.valueOf(Constant.WALLET_NAME +
                currentClickedWalletBean.getWalletName()));
        mTv_me_manager_detail_balance.setText(String.valueOf(currentClickedWalletBean.getBalance
                ()));
        mTv_me_manager_detail_address.setText(currentClickedWalletBean.getWalletAddr());
        mTv_me_manager_detail_bottom_wallet_name.setText(String.valueOf(Constant.WALLET_NAME +
                currentClickedWalletBean.getWalletName()));

        int backupState = currentClickedWalletBean.getBackupState();
        switch (backupState) {
            //未备份
            case 0:
                break;
            //已备份
            case 1:
                mBt_me_manager_detail_backup.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_me_manager_detail_backup:
                break;
            case R.id.bt_me_manager_detail_delete:
                break;
            default:
                break;
        }
    }
}
