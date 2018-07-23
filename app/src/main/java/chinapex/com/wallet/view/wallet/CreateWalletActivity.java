package chinapex.com.wallet.view.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICreateWalletCallback;
import chinapex.com.wallet.executor.runnable.CreateWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.ToastUtils;
import neomobile.Wallet;

public class CreateWalletActivity extends BaseActivity implements View.OnClickListener,
        ICreateWalletCallback {

    private static final String TAG = CreateWalletActivity.class.getSimpleName();
    private Button mBt_create_wallet_confirm;
    private EditText mEt_create_wallet_name;
    private EditText mEt_create_wallet_pwd;
    private EditText mEt_create_wallet_repeat_pwd;
    private boolean mIsSelectedPrivacy;
    private boolean mIsAgreePrivacy;
    private ImageButton mIb_create_wallet_privacy_point;
    private TextInputLayout mTl_create_wallet_name;
    private TextInputLayout mTl_create_wallet_pwd;
    private TextInputLayout mTl_create_wallet_repeat_pwd;
    private TextView mTv_create_wallet_privacy_have_read;
    private Button mBt_create_wallet_import;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        initView();
        initData();
    }

    private void initView() {
        mEt_create_wallet_name = (EditText) findViewById(R.id.et_create_wallet_name);
        mEt_create_wallet_pwd = (EditText) findViewById(R.id.et_create_wallet_pwd);
        mEt_create_wallet_repeat_pwd = (EditText) findViewById(R.id.et_create_wallet_repeat_pwd);
        mBt_create_wallet_confirm = (Button) findViewById(R.id.bt_create_wallet_confirm);
        mBt_create_wallet_import = (Button) findViewById(R.id.bt_create_wallet_import);
        mIb_create_wallet_privacy_point = findViewById(R.id.ib_create_wallet_privacy_point);
        mTv_create_wallet_privacy_have_read = (TextView) findViewById(R.id
                .tv_create_wallet_privacy_have_read);
        mTl_create_wallet_name = (TextInputLayout) findViewById(R.id.tl_create_wallet_name);
        mTl_create_wallet_pwd = (TextInputLayout) findViewById(R.id.tl_create_wallet_pwd);
        mTl_create_wallet_repeat_pwd = (TextInputLayout) findViewById(R.id
                .tl_create_wallet_repeat_pwd);

        mBt_create_wallet_confirm.setOnClickListener(this);
        mIb_create_wallet_privacy_point.setOnClickListener(this);
        mTv_create_wallet_privacy_have_read.setOnClickListener(this);
        mBt_create_wallet_import.setOnClickListener(this);

        mEt_create_wallet_name.addTextChangedListener(new MyTextWatcher(mEt_create_wallet_name) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    //设置错误提示信息
                    showError(mTl_create_wallet_name, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.wallet_name_can_not_be_empty));
                } else {
                    //关闭错误提示
                    mTl_create_wallet_name.setErrorEnabled(false);
                }
            }
        });

        mEt_create_wallet_pwd.addTextChangedListener(new MyTextWatcher(mEt_create_wallet_pwd) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    //设置错误提示信息
                    showError(mTl_create_wallet_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.pwd_must_not_be_less_than_6_bits));
                } else {
                    //关闭错误提示
                    mTl_create_wallet_pwd.setErrorEnabled(false);
                }
            }
        });

        mEt_create_wallet_repeat_pwd.addTextChangedListener(new MyTextWatcher
                (mEt_create_wallet_repeat_pwd) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = mEt_create_wallet_pwd.getText().toString().trim();
                String repeatPwd = mEt_create_wallet_repeat_pwd.getText().toString().trim();

                if (s.length() < 6) {
                    //设置错误提示信息
                    showError(mTl_create_wallet_repeat_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.pwd_must_not_be_less_than_6_bits));
                } else if (TextUtils.isEmpty(repeatPwd) || !repeatPwd.equals(pwd)) {
                    showError(mTl_create_wallet_repeat_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.inconsistent_password));
                } else {
                    //关闭错误提示
                    mTl_create_wallet_repeat_pwd.setErrorEnabled(false);
                }

            }
        });
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_create_wallet_confirm:
                if (!checkInput()) {
                    CpLog.w(TAG, "checkInput is false!");
                    return;
                }

                String walletName = mEt_create_wallet_name.getText().toString().trim();
                String walletPwd = mEt_create_wallet_pwd.getText().toString().trim();
                TaskController.getInstance().submit(new CreateWallet(walletName, walletPwd, this));
                break;
            case R.id.bt_create_wallet_import:
                startActivity(ImportWalletActivity.class, true);
                break;
            case R.id.tv_create_wallet_privacy_have_read:
            case R.id.ib_create_wallet_privacy_point:
                if (mIsSelectedPrivacy) {
                    mIsSelectedPrivacy = false;
                    mIb_create_wallet_privacy_point.setImageResource(R.drawable
                            .shape_privacy_point);
                    mBt_create_wallet_confirm.setBackgroundResource(R.drawable
                            .shape_new_visitor_bt_bg);
                    mBt_create_wallet_confirm.setTextColor(Color.WHITE);
                    mIsAgreePrivacy = true;
                } else {
                    mIsSelectedPrivacy = true;
                    mIb_create_wallet_privacy_point.setImageResource(R.drawable
                            .shape_privacy_point_def);
                    mBt_create_wallet_confirm.setBackgroundResource(R.drawable.shape_gray_bt_bg);
                    mBt_create_wallet_confirm.setTextColor(getResources().getColor(R.color
                            .colorAccent));
                    mIsAgreePrivacy = false;
                }
                break;
            default:
                break;
        }
    }

    private boolean checkInput() {
        String wallet_name = mEt_create_wallet_name.getText().toString().trim();
        String wallet_pwd = mEt_create_wallet_pwd.getText().toString().trim();
        String repeat_pwd = mEt_create_wallet_repeat_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(wallet_name)) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.wallet_name_can_not_be_empty));
            CpLog.w(TAG, "wallet_name is null!");
            return false;
        }

        if (TextUtils.isEmpty(wallet_pwd) || TextUtils.isEmpty(repeat_pwd)) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.pwd_can_not_be_empty));
            CpLog.w(TAG, "wallet_pwd or repeat_pwd is null!");
            return false;
        }

        if (!wallet_pwd.equals(repeat_pwd)) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.inconsistent_password));
            CpLog.w(TAG, "wallet_pwd and repeat_pwd is not same!");
            return false;
        }

        if (repeat_pwd.length() < 6) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.pwd_must_not_be_less_than_6_bits));
            CpLog.w(TAG, "repeat_pwd.length() < 6!");
            return false;
        }

        if (!mIsAgreePrivacy) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.read_privacy_policy_first));
            return false;
        }

        return true;
    }

    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        EditText editText = textInputLayout.getEditText();
        if (null == editText) {
            CpLog.e(TAG, "editText is null!");
            return;
        }

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void newWallet(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "wallet is null！");
            return;
        }

        String mnemonicEnUs = null;
        try {
            mnemonicEnUs = wallet.mnemonic("en_US");
        } catch (Exception e) {
            CpLog.e(TAG, "mnemonicEnUs exception:" + e.getMessage());
        }

        if (TextUtils.isEmpty(mnemonicEnUs)) {
            CpLog.e(TAG, "mnemonicEnUs is null！");
            return;
        }

        WalletBean walletBean = new WalletBean();
        walletBean.setWalletAddr(wallet.address());
        walletBean.setBackupState(Constant.BACKUP_UNFINISHED);
        Intent intent = new Intent(this, BackupWalletActivity.class);
        intent.putExtra(Constant.BACKUP_MNEMONIC, mnemonicEnUs);
        intent.putExtra(Constant.WALLET_BEAN, walletBean);
        startActivity(intent);
        finish();
    }
}
