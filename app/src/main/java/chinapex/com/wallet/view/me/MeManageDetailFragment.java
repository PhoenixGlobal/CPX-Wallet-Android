package chinapex.com.wallet.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.changelistener.OnWalletBackupStateUpdateListener;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.dialog.BackupWalletPwdDialog;
import chinapex.com.wallet.view.dialog.DeleteWalletPwdDialog;
import chinapex.com.wallet.view.dialog.ExportKeystorePwdDialog;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class MeManageDetailFragment extends BaseFragment implements View.OnClickListener,
        OnWalletBackupStateUpdateListener {

    private static final String TAG = MeManageDetailFragment.class.getSimpleName();

    private TextView mTv_me_manager_detail_title;
    private TextView mTv_me_manager_detail_address;
    private Button mBt_me_manager_detail_backup;
    private Button mBt_me_manager_detail_delete;
    private ImageButton mIb_manage_detail_export;
    private EditText mEt_me_manager_detail_bottom_wallet_name;
    private Button mBt_me_manager_detail_save;

    private WalletBean mCurrentWalletBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_manage_detail, container, false);
    }

    @Override
    protected void init(View view) {
        super.init(view);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mTv_me_manager_detail_title = view.findViewById(R.id.tv_me_manager_detail_title);
        mTv_me_manager_detail_address = view.findViewById(R.id.tv_me_manager_detail_address);
        mEt_me_manager_detail_bottom_wallet_name = view.findViewById(R.id.et_me_manager_detail_bottom_wallet_name);
        mBt_me_manager_detail_backup = view.findViewById(R.id.bt_me_manager_detail_backup);
        mBt_me_manager_detail_delete = view.findViewById(R.id.bt_me_manager_detail_delete);
        mBt_me_manager_detail_save = view.findViewById(R.id.bt_me_manager_detail_save);
        mIb_manage_detail_export = view.findViewById(R.id.ib_manage_detail_export);

        mBt_me_manager_detail_backup.setOnClickListener(this);
        mBt_me_manager_detail_delete.setOnClickListener(this);
        mBt_me_manager_detail_save.setOnClickListener(this);
        mIb_manage_detail_export.setOnClickListener(this);
        ApexListeners.getInstance().addOnItemStateUpdateListener(this);

        // 复制地址
        mTv_me_manager_detail_address.setOnClickListener(this);
    }

    private void initData() {
        Me3Activity me3Activity = (Me3Activity) getActivity();
        mCurrentWalletBean = me3Activity.getWalletBean();
        if (null == mCurrentWalletBean) {
            CpLog.e(TAG, "mCurrentWalletBean is null!");
            return;
        }

        mTv_me_manager_detail_title.setText(mCurrentWalletBean.getName());
        mTv_me_manager_detail_address.setText(mCurrentWalletBean.getAddress());
        mEt_me_manager_detail_bottom_wallet_name.setText(String.valueOf(mCurrentWalletBean.getName()));

        setIsShowBackupKey(mCurrentWalletBean.getBackupState());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_manage_detail_export:
                showExportKeystorePwdDialog();
                break;
            case R.id.bt_me_manager_detail_backup:
                showBackupWalletPwdDialog();
                break;
            case R.id.bt_me_manager_detail_delete:
                showDeleteWalletPwdDialog();
                break;
            case R.id.bt_me_manager_detail_save:
                modifyWalletName();
                break;
            case R.id.tv_me_manager_detail_address:
                String copyAddr = mTv_me_manager_detail_address.getText().toString().trim();
                PhoneUtils.copy2Clipboard(ApexWalletApplication.getInstance(), copyAddr);
                ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                        .getResources().getString(R.string.wallet_address_copied));
                break;
            default:
                break;
        }
    }

    private void modifyWalletName() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        String newWalletName = mEt_me_manager_detail_bottom_wallet_name.getText().toString().trim();
        switch (mCurrentWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                apexWalletDbDao.updateWalletName(Constant.TABLE_NEO_WALLET, mCurrentWalletBean.getAddress(), newWalletName);
                break;
            case Constant.WALLET_TYPE_ETH:
                apexWalletDbDao.updateWalletName(Constant.TABLE_ETH_WALLET, mCurrentWalletBean.getAddress(), newWalletName);
                break;
            case Constant.WALLET_TYPE_CPX:
                // TODO: 2018/8/21 0021
                break;
            default:
                break;

        }

        mTv_me_manager_detail_title.setText(newWalletName);
        mCurrentWalletBean.setName(newWalletName);
        ApexListeners.getInstance().notifyItemNameUpdate(mCurrentWalletBean);
        ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                .getString(R.string.wallet_name_save_success));
    }

    public void showDeleteWalletPwdDialog() {
        DeleteWalletPwdDialog deleteWalletPwdDialog = DeleteWalletPwdDialog.newInstance();
        deleteWalletPwdDialog.setCurrentWalletBean(mCurrentWalletBean);
        deleteWalletPwdDialog.show(getFragmentManager(), "DeleteWalletPwdDialog");
    }

    public void showBackupWalletPwdDialog() {
        BackupWalletPwdDialog backupWalletPwdDialog = BackupWalletPwdDialog.newInstance();
        backupWalletPwdDialog.setCurrentWalletBean(mCurrentWalletBean);
        backupWalletPwdDialog.show(getFragmentManager(), "BackupWalletPwdDialog");
    }

    public void showExportKeystorePwdDialog() {
        ExportKeystorePwdDialog exportKeystorePwdDialog = ExportKeystorePwdDialog.newInstance();
        exportKeystorePwdDialog.setCurrentWalletBean(mCurrentWalletBean);
        exportKeystorePwdDialog.show(getFragmentManager(), "ExportKeystorePwdDialog");
    }

    @Override
    public void onWalletBackupStateUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        setIsShowBackupKey(walletBean.getBackupState());
    }

    private void setIsShowBackupKey(int backupState) {
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
}
