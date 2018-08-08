package chinapex.com.wallet.view.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IFromMnemonicToWalletCallback;
import chinapex.com.wallet.executor.runnable.FromMnemonicToWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.MainActivity;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/10 22:31
 * E-Mail：liuyi_61@163.com
 */
public class ImportMnemonicFragment extends BaseFragment implements View.OnClickListener,
        IFromMnemonicToWalletCallback {

    private static final String TAG = ImportMnemonicFragment.class.getSimpleName();
    private EditText mEt_import_wallet_mnemonic;
    private EditText mEt_import_wallet_pwd;
    private EditText mEt_import_wallet_repeat_pwd;
    private ImageButton mIb_import_wallet_privacy_point;
    private Button mBt_import_wallet_mnemonic;
    private TextInputLayout mTl_import_wallet_pwd;
    private TextInputLayout mTl_import_wallet_repeat_pwd;
    private boolean mIsSelectedPrivacy;
    private boolean mIsAgreePrivacy;
    private TextView mTv_import_wallet_privacy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import_mnemonic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mEt_import_wallet_mnemonic = view.findViewById(R.id.et_import_wallet_mnemonic);
        mEt_import_wallet_pwd = view.findViewById(R.id.et_import_wallet_pwd);
        mEt_import_wallet_repeat_pwd = view.findViewById(R.id.et_import_wallet_repeat_pwd);
        mIb_import_wallet_privacy_point = view.findViewById(R.id.ib_import_wallet_privacy_point);
        mTv_import_wallet_privacy = view.findViewById(R.id.tv_import_wallet_privacy);
        mBt_import_wallet_mnemonic = view.findViewById(R.id.bt_import_wallet_mnemonic);
        mTl_import_wallet_pwd = view.findViewById(R.id.tl_import_wallet_pwd);
        mTl_import_wallet_repeat_pwd = view.findViewById(R.id.tl_import_wallet_repeat_pwd);

        mEt_import_wallet_mnemonic.setOnClickListener(this);
        mEt_import_wallet_pwd.setOnClickListener(this);
        mEt_import_wallet_repeat_pwd.setOnClickListener(this);
        mIb_import_wallet_privacy_point.setOnClickListener(this);
        mBt_import_wallet_mnemonic.setOnClickListener(this);
        mTv_import_wallet_privacy.setOnClickListener(this);

        mEt_import_wallet_pwd.addTextChangedListener(new MyTextWatcher(mEt_import_wallet_pwd) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    //设置错误提示信息
                    showError(mTl_import_wallet_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.pwd_must_not_be_less_than_6_bits));
                } else {
                    //关闭错误提示
                    mTl_import_wallet_pwd.setErrorEnabled(false);
                }
            }
        });

        mEt_import_wallet_repeat_pwd.addTextChangedListener(new MyTextWatcher
                (mEt_import_wallet_repeat_pwd) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = mEt_import_wallet_pwd.getText().toString().trim();
                String repeatPwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();

                if (s.length() < 6) {
                    //设置错误提示信息
                    showError(mTl_import_wallet_repeat_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.pwd_must_not_be_less_than_6_bits));
                } else if (TextUtils.isEmpty(repeatPwd) || !repeatPwd.equals(pwd)) {
                    showError(mTl_import_wallet_repeat_pwd, ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.inconsistent_password));
                } else {
                    //关闭错误提示
                    mTl_import_wallet_repeat_pwd.setErrorEnabled(false);
                }

            }
        });

    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_import_wallet_privacy_point:
                if (mIsSelectedPrivacy) {
                    mIsSelectedPrivacy = false;
                    mIb_import_wallet_privacy_point.setImageResource(R.drawable.icon_privacy_def);
                    mBt_import_wallet_mnemonic.setBackgroundResource(R.drawable
                            .shape_import_wallet_bt_bg_def);
                    mBt_import_wallet_mnemonic.setTextColor(getResources().getColor(R.color
                            .c_666666));
                    mIsAgreePrivacy = false;
                } else {
                    mIsSelectedPrivacy = true;
                    mIb_import_wallet_privacy_point.setImageResource(R.drawable.icon_privacy);
                    mBt_import_wallet_mnemonic.setBackgroundResource(R.drawable
                            .shape_new_visitor_bt_bg);
                    mBt_import_wallet_mnemonic.setTextColor(Color.WHITE);
                    mIsAgreePrivacy = true;
                }
                break;
            case R.id.tv_import_wallet_privacy:
                startActivity(PrivacyActivity.class, false);
                break;
            case R.id.bt_import_wallet_mnemonic:
                if (!checkInput()) {
                    CpLog.w(TAG, "checkInput is false!");
                    return;
                }

                String mnemonic = mEt_import_wallet_mnemonic.getText().toString().trim();
                TaskController.getInstance().submit(new FromMnemonicToWallet(mnemonic, "en_US",
                        this));
                break;
            default:
                break;
        }
    }

    private boolean checkInput() {
        String wallet_pwd = mEt_import_wallet_pwd.getText().toString().trim();
        String repeat_pwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(wallet_pwd) || TextUtils.isEmpty(repeat_pwd)) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.pwd_can_not_be_empty));
            CpLog.w(TAG, "wallet_name or wallet_pwd or repeat_pwd is null!");
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
    public void fromMnemonicToWallet(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "fromMnemonicToWallet() -> wallet is null!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.mnemonic_import_failed));
                }
            });
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        String walletAddress = wallet.address();
        WalletBean queryByWalletAddress = apexWalletDbDao.queryByWalletAddress(Constant
                .TABLE_APEX_WALLET, walletAddress);
        if (null != queryByWalletAddress) {
            CpLog.e(TAG, "this wallet from mnemonic has existed!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.wallet_exist));
                }
            });
            return;
        }

        ArrayList<String> assets = new ArrayList<>();
        assets.add(Constant.ASSETS_NEO);
        assets.add(Constant.ASSETS_NEO_GAS);

        ArrayList<String> assetsNep5 = new ArrayList<>();
        assetsNep5.add(Constant.ASSETS_CPX);

        WalletBean walletBean = new WalletBean();
        walletBean.setWalletName(Constant.WALLET_NAME_IMPORT_DEFAULT);
        walletBean.setWalletAddr(walletAddress);
        walletBean.setBackupState(Constant.BACKUP_UNFINISHED);
        walletBean.setAssetsJson(GsonUtils.toJsonStr(assets));
        walletBean.setAssetsNep5Json(GsonUtils.toJsonStr(assetsNep5));

        String pwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();
        try {
            walletBean.setKeyStore(wallet.toKeyStore(pwd));
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
            return;
        }

        apexWalletDbDao.insert(Constant.TABLE_APEX_WALLET, walletBean);
        CpLog.i(TAG, "ApexListeners.getInstance().notifyItemAdd");
        ApexListeners.getInstance().notifyItemAdd(walletBean);

        isFirstEnter();
    }

    private void isFirstEnter() {
        boolean isFirstExport = (boolean) SharedPreferencesUtils.getParam(ApexWalletApplication
                .getInstance(), Constant.IS_FIRST_ENTER_MAIN, true);
        if (isFirstExport) {
            SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), Constant
                    .IS_FIRST_ENTER_MAIN, false);
            startActivity(MainActivity.class, true);
        } else {
            getActivity().finish();
        }
    }
}
