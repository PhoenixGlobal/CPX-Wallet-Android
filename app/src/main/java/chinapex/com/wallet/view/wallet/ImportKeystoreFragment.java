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
import chinapex.com.wallet.executor.callback.IFromKeystoreToWalletCallback;
import chinapex.com.wallet.executor.runnable.FromKeystoreToWallet;
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
public class ImportKeystoreFragment extends BaseFragment implements View.OnClickListener,
        IFromKeystoreToWalletCallback {

    private static final String TAG = ImportKeystoreFragment.class.getSimpleName();
    private EditText mEt_import_wallet_keystore;
    private ImageButton mIb_import_wallet_keystore_privacy_point;
    private Button mBt_import_wallet_keystore;
    private boolean mIsSelectedPrivacy;
    private boolean mIsAgreePrivacy;
    private EditText mEt_import_wallet_keystore_pwd;
    private TextInputLayout mTl_import_wallet_keystore;
    private TextView mTv_import_wallet_keystore_privacy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import_keystore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mEt_import_wallet_keystore = view.findViewById(R.id.et_import_wallet_keystore);
        mEt_import_wallet_keystore_pwd = view.findViewById(R.id.et_import_wallet_keystore_pwd);
        mIb_import_wallet_keystore_privacy_point = view.findViewById(R.id
                .ib_import_wallet_keystore_privacy_point);
        mTv_import_wallet_keystore_privacy = view.findViewById(R.id
                .tv_import_wallet_keystore_privacy);
        mBt_import_wallet_keystore = view.findViewById(R.id.bt_import_wallet_keystore);
        mTl_import_wallet_keystore = view.findViewById(R.id.tl_import_wallet_keystore);

        mIb_import_wallet_keystore_privacy_point.setOnClickListener(this);
        mBt_import_wallet_keystore.setOnClickListener(this);
        mTv_import_wallet_keystore_privacy.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_import_wallet_keystore_privacy_point:
                if (mIsSelectedPrivacy) {
                    mIsSelectedPrivacy = false;
                    mIb_import_wallet_keystore_privacy_point.setImageResource(R.drawable
                            .icon_privacy_def);
                    mBt_import_wallet_keystore.setBackgroundResource(R.drawable
                            .shape_import_wallet_bt_bg_def);
                    mBt_import_wallet_keystore.setTextColor(getResources().getColor(R.color
                            .c_666666));
                    mIsAgreePrivacy = false;
                } else {
                    mIsSelectedPrivacy = true;
                    mIb_import_wallet_keystore_privacy_point.setImageResource(R.drawable
                            .icon_privacy);
                    mBt_import_wallet_keystore.setBackgroundResource(R.drawable
                            .shape_new_visitor_bt_bg);
                    mBt_import_wallet_keystore.setTextColor(Color.WHITE);
                    mIsAgreePrivacy = true;
                }
                break;
            case R.id.tv_import_wallet_keystore_privacy:
                startActivity(PrivacyActivity.class, false);
                break;
            case R.id.bt_import_wallet_keystore:
                if (!checkInput()) {
                    CpLog.w(TAG, "checkInput is false!");
                    return;
                }

                String keystore = mEt_import_wallet_keystore.getText().toString().trim();
                String keystorePwd = mEt_import_wallet_keystore_pwd.getText().toString().trim();
                TaskController.getInstance().submit(new FromKeystoreToWallet(keystore,
                        keystorePwd, this));
                break;

        }
    }

    private boolean checkInput() {
        String pwd = mEt_import_wallet_keystore_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.pwd_can_not_be_empty));
            CpLog.w(TAG, "pwd is null!");
            return false;
        }

        if (!mIsAgreePrivacy) {
            ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance().getResources()
                    .getString(R.string.read_privacy_policy_first));
            return false;
        }

        return true;
    }

    @Override
    public void fromKeystoreWallet(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "wallet is null!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.keystore_import_failed));
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
        WalletBean walletBeanTmp = apexWalletDbDao.queryByWalletAddress(Constant.TABLE_APEX_WALLET,
                walletAddress);
        if (null != walletBeanTmp) {
            CpLog.e(TAG, "this walletBeanTmp from keystore has existed!");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.wallet_exist));
                }
            });
            return;
        }

        String keystore = mEt_import_wallet_keystore.getText().toString().trim();
        ArrayList<String> assets = new ArrayList<>();
        assets.add(Constant.ASSETS_NEO);
        assets.add(Constant.ASSETS_NEO_GAS);

        ArrayList<String> assetsNep5 = new ArrayList<>();
        assetsNep5.add(Constant.ASSETS_CPX);

        WalletBean walletBean = new WalletBean();
        walletBean.setWalletName(Constant.WALLET_NAME_IMPORT_DEFAULT);
        walletBean.setWalletAddr(walletAddress);
        walletBean.setBackupState(Constant.BACKUP_UNFINISHED);
        walletBean.setKeyStore(keystore);
        walletBean.setAssetsJson(GsonUtils.toJsonStr(assets));
        walletBean.setAssetsNep5Json(GsonUtils.toJsonStr(assetsNep5));

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
