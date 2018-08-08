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
import android.widget.TextView;
import android.widget.Toast;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IFromKeystoreToWalletCallback;
import chinapex.com.wallet.executor.runnable.FromKeystoreToWallet;
import chinapex.com.wallet.executor.runnable.GetUtxos;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.ToastUtils;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class TransferPwdDialog extends DialogFragment implements View.OnClickListener,
        IFromKeystoreToWalletCallback {

    private static final String TAG = TransferPwdDialog.class.getSimpleName();
    private WalletBean mCurrentWalletBean;

    private Button mBt_dialog_pwd_transfer_cancel;
    private Button mBt_dialog_pwd_transfer_confirm;
    private String mTransferAmount;
    private String mTransferUnit;
    private EditText mEt_dialog_pwd_transfer;
    private OnCheckPwdListener mOnCheckPwdListener;
    private TextView mTv_dialog_amount;
    private TextView mTv_dialog_unit;

    public static TransferPwdDialog newInstance() {
        return new TransferPwdDialog();
    }

    public interface OnCheckPwdListener {
        void onCheckPwd(Wallet wallet);
    }

    public void setOnCheckPwdListener(OnCheckPwdListener onCheckPwdListener) {
        mOnCheckPwdListener = onCheckPwdListener;
    }

    public void setCurrentWalletBean(WalletBean currentWalletBean) {
        mCurrentWalletBean = currentWalletBean;
    }

    public void setTransferAmount(String transferAmount) {
        mTransferAmount = transferAmount;
    }

    public void setTransferUnit(String transferUnit) {
        mTransferUnit = transferUnit;
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

        return inflater.inflate(R.layout.dialog_transfer_pwd, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dip2px(getActivity(), 257), DensityUtil
                .dip2px(getActivity(), 159));
    }

    private void initView(View view) {
        mBt_dialog_pwd_transfer_cancel = view.findViewById(R.id.bt_dialog_pwd_transfer_cancel);
        mBt_dialog_pwd_transfer_confirm = view.findViewById(R.id.bt_dialog_pwd_transfer_confirm);
        mEt_dialog_pwd_transfer = view.findViewById(R.id.et_dialog_pwd_transfer);
        mTv_dialog_amount = view.findViewById(R.id.tv_dialog_amount);
        mTv_dialog_unit = view.findViewById(R.id.tv_dialog_unit);

        mBt_dialog_pwd_transfer_cancel.setOnClickListener(this);
        mBt_dialog_pwd_transfer_confirm.setOnClickListener(this);
    }

    private void initData() {
        mTv_dialog_amount.setText(mTransferAmount);
        mTv_dialog_unit.setText(mTransferUnit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialog_pwd_transfer_cancel:
                dismiss();
                break;
            case R.id.bt_dialog_pwd_transfer_confirm:
                String pwd = mEt_dialog_pwd_transfer.getText().toString().trim();
                TaskController.getInstance().submit(new FromKeystoreToWallet(mCurrentWalletBean
                        .getKeyStore(), pwd, this));
                break;
        }
    }

    @Override
    public void fromKeystoreWallet(Wallet wallet) {
        if (null == mOnCheckPwdListener) {
            CpLog.e(TAG, "mOnCheckPwdListener is null!");
            return;
        }

        if (null == wallet) {
            CpLog.e(TAG, "pwd is not match keystore");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.password_incorrect));
                }
            });
            return;
        }

        mOnCheckPwdListener.onCheckPwd(wallet);
        dismiss();
    }

}
