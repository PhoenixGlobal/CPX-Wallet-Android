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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.eth.EthWallet;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IFromMnemonicToNeoWalletCallback;
import chinapex.com.wallet.executor.callback.eth.IFromMnemonicToEthWalletCallback;
import chinapex.com.wallet.executor.runnable.FromMnemonicToNeoWallet;
import chinapex.com.wallet.executor.runnable.eth.FromMnemonicToEthWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.MainActivity;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/10 22:31
 * E-Mail：liuyi_61@163.com
 */
public class ImportMnemonicFragment extends BaseFragment implements View.OnClickListener,
        IFromMnemonicToNeoWalletCallback, IFromMnemonicToEthWalletCallback {

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
    private TextView mTv_import_wallet_mnemonic_type;
    private ImageView mIv_import_wallet_mnemonic_arrows;

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
        mTv_import_wallet_mnemonic_type = view.findViewById(R.id.tv_import_wallet_mnemonic_type);
        mIv_import_wallet_mnemonic_arrows = view.findViewById(R.id.iv_import_wallet_mnemonic_arrows);
        mEt_import_wallet_mnemonic = view.findViewById(R.id.et_import_wallet_mnemonic);
        mEt_import_wallet_pwd = view.findViewById(R.id.et_import_wallet_pwd);
        mEt_import_wallet_repeat_pwd = view.findViewById(R.id.et_import_wallet_repeat_pwd);
        mIb_import_wallet_privacy_point = view.findViewById(R.id.ib_import_wallet_privacy_point);
        mTv_import_wallet_privacy = view.findViewById(R.id.tv_import_wallet_privacy);
        mBt_import_wallet_mnemonic = view.findViewById(R.id.bt_import_wallet_mnemonic);
        mTl_import_wallet_pwd = view.findViewById(R.id.tl_import_wallet_pwd);
        mTl_import_wallet_repeat_pwd = view.findViewById(R.id.tl_import_wallet_repeat_pwd);

        mTv_import_wallet_mnemonic_type.setOnClickListener(this);
        mIv_import_wallet_mnemonic_arrows.setOnClickListener(this);
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
            case R.id.tv_import_wallet_mnemonic_type:
            case R.id.iv_import_wallet_mnemonic_arrows:
                showOptionPicker();
                break;
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
                    mBt_import_wallet_mnemonic.setBackgroundResource(R.drawable.shape_new_visitor_bt_bg);
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
                String walletType = mTv_import_wallet_mnemonic_type.getText().toString().trim();
                if (TextUtils.isEmpty(walletType)) {
                    CpLog.e(TAG, "walletType is null!");
                    ToastUtils.getInstance().showToast(getString(R.string.select_wallet_type));
                    return;
                }

                switch (walletType) {
                    case "NEO":
                        TaskController.getInstance().submit(new FromMnemonicToNeoWallet(mnemonic, "en_US", this));
                        break;
                    case "ETH":
                        TaskController.getInstance().submit(new FromMnemonicToEthWallet(mnemonic, "en_US", this));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private boolean checkInput() {
        String mnemonic = mEt_import_wallet_mnemonic.getText().toString().trim();
        String wallet_pwd = mEt_import_wallet_pwd.getText().toString().trim();
        String repeat_pwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.getInstance().showToast(getString(R.string.mnemonic_can_not_be_empty));
            CpLog.w(TAG, "mnemonic is null!");
            return false;
        }

        if (TextUtils.isEmpty(wallet_pwd) || TextUtils.isEmpty(repeat_pwd)) {
            ToastUtils.getInstance().showToast(getString(R.string.pwd_can_not_be_empty));
            CpLog.w(TAG, "wallet_pwd or repeat_pwd is null!");
            return false;
        }

        if (!wallet_pwd.equals(repeat_pwd)) {
            ToastUtils.getInstance().showToast(getString(R.string.inconsistent_password));
            CpLog.w(TAG, "wallet_pwd and repeat_pwd is not same!");
            return false;
        }

        if (repeat_pwd.length() < 6) {
            ToastUtils.getInstance().showToast(getString(R.string.pwd_must_not_be_less_than_6_bits));
            CpLog.w(TAG, "repeat_pwd.length() < 6!");
            return false;
        }

        if (!mIsAgreePrivacy) {
            ToastUtils.getInstance().showToast(getString(R.string.read_privacy_policy_first));
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
    public void fromMnemonicToNeoWallet(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "fromMnemonicToNeoWallet() -> wallet is null!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(getString(R.string.mnemonic_import_failed));
                }
            });
            return;
        }

        String pwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();
        try {
            String keyStore = wallet.toKeyStore(pwd);
            mnemonicToWallet(wallet.address(), keyStore, Constant.WALLET_TYPE_NEO);
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
        }
    }

    @Override
    public void fromMnemonicToEthWallet(ethmobile.Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "fromMnemonicToEthWallet() -> wallet is null!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(getString(R.string.mnemonic_import_failed));
                }
            });
            return;
        }

        String pwd = mEt_import_wallet_repeat_pwd.getText().toString().trim();
        try {
            String keyStore = wallet.toKeyStore(pwd);
            mnemonicToWallet(wallet.address(), keyStore, Constant.WALLET_TYPE_ETH);
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
        }
    }

    private void mnemonicToWallet(String walletAddress, String keystore, int walletType) {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        String tableName = null;
        switch (walletType) {
            case Constant.WALLET_TYPE_NEO:
                tableName = Constant.TABLE_NEO_WALLET;
                break;
            case Constant.WALLET_TYPE_ETH:
                tableName = Constant.TABLE_ETH_WALLET;
                break;
            case Constant.WALLET_TYPE_CPX:
                break;
            default:
                break;
        }

        WalletBean queryByWalletAddress = apexWalletDbDao.queryByWalletAddress(tableName, walletAddress);
        if (null != queryByWalletAddress) {
            CpLog.e(TAG, "this wallet from mnemonic has existed!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources().getString(R.string
                            .wallet_exist));
                }
            });
            return;
        }

        WalletBean walletBean = null;
        ArrayList<String> assets = new ArrayList<>();
        ArrayList<String> colorAsset = new ArrayList<>();
        switch (walletType) {
            case Constant.WALLET_TYPE_NEO:
                walletBean = new NeoWallet();
                assets.add(Constant.ASSETS_NEO);
                assets.add(Constant.ASSETS_NEO_GAS);
                colorAsset.add(Constant.ASSETS_CPX);
                walletBean.setWalletType(Constant.WALLET_TYPE_NEO);
                break;
            case Constant.WALLET_TYPE_ETH:
                walletBean = new EthWallet();
                assets.add(Constant.ASSETS_ETH);
                colorAsset.add(Constant.ASSETS_ERC20_NMB);
                walletBean.setWalletType(Constant.WALLET_TYPE_ETH);
                break;
            case Constant.WALLET_TYPE_CPX:
                break;
            default:
                break;
        }

        if (null == walletBean) {
            CpLog.e(TAG, "mnemonicToWallet() -> walletBean is null!");
            return;
        }

        walletBean.setName(Constant.WALLET_NAME_IMPORT_DEFAULT);
        walletBean.setAddress(walletAddress);
        walletBean.setBackupState(Constant.BACKUP_UNFINISHED);
        walletBean.setKeyStore(keystore);
        walletBean.setAssetJson(GsonUtils.toJsonStr(assets));
        walletBean.setColorAssetJson(GsonUtils.toJsonStr(colorAsset));

        apexWalletDbDao.insert(tableName, walletBean);
        ApexListeners.getInstance().notifyWalletAdd(walletBean);

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

    private void showOptionPicker() {
        OptionPicker picker = new OptionPicker(this.getActivity(), getWalletTypes());
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
        picker.setCancelText(ApexWalletApplication.getInstance().getResources().getString(R.string.cancel));
        picker.setCancelTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_1253BF));
        picker.setCancelTextSize(14);

        // set confirm
        picker.setSubmitText(ApexWalletApplication.getInstance().getResources().getString(R.string.confirm));
        picker.setSubmitTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_1253BF));
        picker.setSubmitTextSize(14);

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                CpLog.i(TAG, "index:" + index + ",item:" + item);
                mTv_import_wallet_mnemonic_type.setText(item);
            }
        });

        picker.show();
    }

    private List<String> getWalletTypes() {
        String[] menuTexts = getResources().getStringArray(R.array.create_wallet_type);
        return Arrays.asList(menuTexts);
    }

}
