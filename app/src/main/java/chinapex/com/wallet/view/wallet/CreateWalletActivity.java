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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.eth.EthWallet;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICreateWalletCallback;
import chinapex.com.wallet.executor.callback.eth.ICreateEthWalletCallback;
import chinapex.com.wallet.executor.runnable.CreateNeoWallet;
import chinapex.com.wallet.executor.runnable.eth.CreateEthWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.ToastUtils;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import neomobile.Wallet;

public class CreateWalletActivity extends BaseActivity implements View.OnClickListener,
        ICreateWalletCallback, ICreateEthWalletCallback {

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
    private Button mBt_create_wallet_import;
    private TextView mTv_create_wallet_privacy;
    private TextView mTv_create_wallet_type;
    private ImageView mIv_create_wallet_arrows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        initView();
    }

    private void initView() {
        mTv_create_wallet_type = (TextView) findViewById(R.id.tv_create_wallet_type);
        mIv_create_wallet_arrows = (ImageView) findViewById(R.id.iv_create_wallet_arrows);
        mEt_create_wallet_name = (EditText) findViewById(R.id.et_create_wallet_name);
        mEt_create_wallet_pwd = (EditText) findViewById(R.id.et_create_wallet_pwd);
        mEt_create_wallet_repeat_pwd = (EditText) findViewById(R.id.et_create_wallet_repeat_pwd);
        mBt_create_wallet_confirm = (Button) findViewById(R.id.bt_create_wallet_confirm);
        mBt_create_wallet_import = (Button) findViewById(R.id.bt_create_wallet_import);
        mIb_create_wallet_privacy_point = findViewById(R.id.ib_create_wallet_privacy_point);
        mTv_create_wallet_privacy = (TextView) findViewById(R.id.tv_create_wallet_privacy);
        mTl_create_wallet_name = (TextInputLayout) findViewById(R.id.tl_create_wallet_name);
        mTl_create_wallet_pwd = (TextInputLayout) findViewById(R.id.tl_create_wallet_pwd);
        mTl_create_wallet_repeat_pwd = (TextInputLayout) findViewById(R.id.tl_create_wallet_repeat_pwd);

        mTv_create_wallet_type.setOnClickListener(this);
        mIv_create_wallet_arrows.setOnClickListener(this);
        mBt_create_wallet_confirm.setOnClickListener(this);
        mIb_create_wallet_privacy_point.setOnClickListener(this);
        mBt_create_wallet_import.setOnClickListener(this);
        mTv_create_wallet_privacy.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_create_wallet_arrows:
            case R.id.tv_create_wallet_type:
                showOptionPicker();
                break;
            case R.id.bt_create_wallet_confirm:
                if (!checkInput()) {
                    CpLog.w(TAG, "checkInput is false!");
                    return;
                }

                String walletName = mEt_create_wallet_name.getText().toString().trim();
                String walletPwd = mEt_create_wallet_pwd.getText().toString().trim();
                String walletType = mTv_create_wallet_type.getText().toString().trim();
                if (TextUtils.isEmpty(walletType)) {
                    CpLog.e(TAG, "walletType is null!");
                    ToastUtils.getInstance().showToast(getString(R.string.select_wallet_type));
                    return;
                }

                switch (walletType) {
                    case "NEO":
                        TaskController.getInstance().submit(new CreateNeoWallet(walletName, walletPwd, this));
                        break;
                    case "ETH":
                        TaskController.getInstance().submit(new CreateEthWallet(walletName, walletPwd, this));
                        break;
                    default:
                        break;

                }
                break;
            case R.id.bt_create_wallet_import:
                startActivity(ImportWalletActivity.class, true);
                break;
            case R.id.ib_create_wallet_privacy_point:
                if (mIsSelectedPrivacy) {
                    mIsSelectedPrivacy = false;
                    mIb_create_wallet_privacy_point.setImageResource(R.drawable.icon_privacy_def);
                    mBt_create_wallet_confirm.setBackgroundResource(R.drawable.shape_gray_bt_bg);
                    mBt_create_wallet_confirm.setTextColor(getResources().getColor(R.color.c_979797));
                    mIsAgreePrivacy = false;
                } else {
                    mIsSelectedPrivacy = true;
                    mIb_create_wallet_privacy_point.setImageResource(R.drawable.icon_privacy);
                    mBt_create_wallet_confirm.setBackgroundResource(R.drawable.shape_new_visitor_bt_bg);
                    mBt_create_wallet_confirm.setTextColor(Color.WHITE);
                    mIsAgreePrivacy = true;
                }
                break;
            case R.id.tv_create_wallet_privacy:
                startActivity(PrivacyActivity.class, false);
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

        NeoWallet neoWallet = new NeoWallet();
        neoWallet.setAddress(wallet.address());
        neoWallet.setBackupState(Constant.BACKUP_UNFINISHED);
        Intent intent = new Intent(this, BackupWalletActivity.class);
        intent.putExtra(Constant.PARCELABLE_WALLET_TYPE, Constant.WALLET_TYPE_NEO);
        intent.putExtra(Constant.BACKUP_MNEMONIC, mnemonicEnUs);
        intent.putExtra(Constant.WALLET_BEAN, neoWallet);
        startActivity(intent);
        finish();
    }

    @Override
    public void createEthWallet(ethmobile.Wallet wallet) {
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

        EthWallet ethWallet = new EthWallet();
        ethWallet.setAddress(wallet.address());
        ethWallet.setBackupState(Constant.BACKUP_UNFINISHED);
        Intent intent = new Intent(this, BackupWalletActivity.class);
        intent.putExtra(Constant.PARCELABLE_WALLET_TYPE, Constant.WALLET_TYPE_ETH);
        intent.putExtra(Constant.BACKUP_MNEMONIC, mnemonicEnUs);
        intent.putExtra(Constant.WALLET_BEAN, ethWallet);
        startActivity(intent);
        finish();
    }

    private List<String> getWalletTypes() {
        String[] menuTexts = getResources().getStringArray(R.array.create_wallet_type);
        return Arrays.asList(menuTexts);
    }

    private void showOptionPicker() {
        OptionPicker picker = new OptionPicker(this, getWalletTypes());
        picker.setOffset(2);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setHeight(DensityUtil.dip2px(ApexWalletApplication.getInstance(), 200));
        picker.setTopHeight(40);
        picker.setDividerColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_DDDDDD));
        picker.setTopLineColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_DDDDDD));
        picker.setTextColor(Color.BLACK, ApexWalletApplication.getInstance().getResources().getColor(R.color.c_999999));
        picker.setSelectedIndex(1);
        picker.setTextSize(16);

        // set cancel
        picker.setCancelText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.cancel));
        picker.setCancelTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_1253BF));
        picker.setCancelTextSize(14);

        // set confirm
        picker.setSubmitText(ApexWalletApplication.getInstance().getResources().getString(R
                .string.confirm));
        picker.setSubmitTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_1253BF));
        picker.setSubmitTextSize(14);

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                CpLog.i(TAG, "index:" + index + ",item:" + item);
                mTv_create_wallet_type.setText(item);
            }
        });

        picker.show();
    }

}
