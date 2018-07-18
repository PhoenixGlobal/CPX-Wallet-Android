package chinapex.com.wallet.view.dialog;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IFromKeystoreToWalletCallback;
import chinapex.com.wallet.executor.runnable.FromKeystoreToWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class DeleteWalletPwdDialog extends DialogFragment implements View.OnClickListener,
        IFromKeystoreToWalletCallback {

    private static final String TAG = DeleteWalletPwdDialog.class.getSimpleName();
    private WalletBean mCurrentWalletBean;
    private Button mBt_dialog_pwd_del_cancel;
    private Button mBt_dialog_pwd_del_confirm;
    private EditText mEt_dialog_pwd_del;

    public static DeleteWalletPwdDialog newInstance() {
        return new DeleteWalletPwdDialog();
    }

    public void setCurrentWalletBean(WalletBean currentWalletBean) {
        mCurrentWalletBean = currentWalletBean;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        // 去掉边框
        Window window = getDialog().getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }

        // 点击空白区域不可取消
        setCancelable(false);

        return inflater.inflate(R.layout.dialog_delete_wallet_pwd, container, false);
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
        getDialog().getWindow().setLayout(DensityUtil.dip2px(getActivity(), 257), DensityUtil
                .dip2px(getActivity(), 159));
    }

    private void initData() {

    }

    private void initView(View view) {
        mBt_dialog_pwd_del_cancel = view.findViewById(R.id.bt_dialog_pwd_del_cancel);
        mBt_dialog_pwd_del_confirm = view.findViewById(R.id.bt_dialog_pwd_del_confirm);
        mEt_dialog_pwd_del = view.findViewById(R.id.et_dialog_pwd_del);

        mBt_dialog_pwd_del_cancel.setOnClickListener(this);
        mBt_dialog_pwd_del_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialog_pwd_del_cancel:
                dismiss();
                break;
            case R.id.bt_dialog_pwd_del_confirm:
                String pwd = mEt_dialog_pwd_del.getText().toString().trim();
                TaskController.getInstance().submit(new FromKeystoreToWallet(mCurrentWalletBean
                        .getKeyStore(), pwd, this));
                break;
        }
    }

    @Override
    public void fromKeystoreWallet(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "pwd is not match keystore");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "密码输入有误！", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        deleteWalletByNameAndAddr();
    }

    private void deleteWalletByNameAndAddr() {
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

        String walletAddress = mCurrentWalletBean.getWalletAddr();
        apexWalletDbDao.deleteByWalletNameAndAddr(Constant.TABLE_APEX_WALLET, mCurrentWalletBean
                .getWalletName(), walletAddress);
        apexWalletDbDao.delTxsByAddress(Constant.TABLE_TRANSACTION_RECORD, walletAddress);
        apexWalletDbDao.delTxsByAddress(Constant.TABLE_TX_CACHE, walletAddress);
        SharedPreferencesUtils.remove(ApexWalletApplication.getInstance(), walletAddress);

        ApexListeners.getInstance().notifyItemDelete(mCurrentWalletBean);
        dismiss();
    }
}
