package chinapex.com.wallet.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.MeSkipActivity;
import chinapex.com.wallet.view.dialog.InputPwdDelDialog;
import chinapex.com.wallet.view.wallet.BackupWalletActivity;
import chinapex.com.wallet.view.wallet.ExportKeystoreActivity;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class MeManageDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MeManageDetailFragment.class.getSimpleName();
    private TextView mTv_me_manager_detail_title;
    private TextView mTv_me_manager_detail_address;
    private TextView mTv_me_manager_detail_bottom_wallet_name;
    private Button mBt_me_manager_detail_backup;
    private Button mBt_me_manager_detail_delete;
    private WalletBean mCurrentClickedWalletBean;
    private ImageButton mIb_manage_detail_export;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_manage_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        mTv_me_manager_detail_title = view.findViewById(R.id.tv_me_manager_detail_title);
        mTv_me_manager_detail_address = view.findViewById(R.id.tv_me_manager_detail_address);
        mTv_me_manager_detail_bottom_wallet_name = view.findViewById(R.id
                .tv_me_manager_detail_bottom_wallet_name);
        mBt_me_manager_detail_backup = view.findViewById(R.id.bt_me_manager_detail_backup);
        mBt_me_manager_detail_delete = view.findViewById(R.id.bt_me_manager_detail_delete);
        mIb_manage_detail_export = view.findViewById(R.id.ib_manage_detail_export);

        mBt_me_manager_detail_backup.setOnClickListener(this);
        mBt_me_manager_detail_delete.setOnClickListener(this);
        mIb_manage_detail_export.setOnClickListener(this);
    }

    private void initData() {
        MeSkipActivity meSkipActivity = (MeSkipActivity) getActivity();
        mCurrentClickedWalletBean = meSkipActivity.getWalletBean();
        if (null == mCurrentClickedWalletBean) {
            CpLog.e(TAG, "currentClickedWalletBean is null!");
            return;
        }

        mTv_me_manager_detail_title.setText(String.valueOf(Constant.WALLET_NAME +
                mCurrentClickedWalletBean
                        .getWalletName()));
        mTv_me_manager_detail_address.setText(mCurrentClickedWalletBean.getWalletAddr());
        mTv_me_manager_detail_bottom_wallet_name.setText(String.valueOf(Constant.WALLET_NAME +
                mCurrentClickedWalletBean.getWalletName()));

        int backupState = mCurrentClickedWalletBean.getBackupState();
        switch (backupState) {
            //未备份
            case Constant.BACKUP_UNFINISHED:
                mBt_me_manager_detail_backup.setVisibility(View.VISIBLE);
                break;
            //已备份
            case Constant.BACKUP_FINISH:
                mBt_me_manager_detail_backup.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_manage_detail_export:
                startActivity(ExportKeystoreActivity.class, false);
                break;
            case R.id.bt_me_manager_detail_backup:
                startActivity(BackupWalletActivity.class, false);
                break;
            case R.id.bt_me_manager_detail_delete:
                showInputPwdDialog();
                break;
            default:
                break;
        }
    }

    public void showInputPwdDialog() {
        InputPwdDelDialog inputPwdDelDialog = InputPwdDelDialog.newInstance();
        inputPwdDelDialog.setCurrentWalletBean(mCurrentClickedWalletBean);
        inputPwdDelDialog.show(getFragmentManager(), "InputPwdDelDialog");
    }

}
