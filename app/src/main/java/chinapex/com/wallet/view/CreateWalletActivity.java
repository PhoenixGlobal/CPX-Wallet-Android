package chinapex.com.wallet.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletKeyStore;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import neomobile.Neomobile;
import neomobile.Wallet;

public class CreateWalletActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CreateWalletActivity.class.getSimpleName();
    private Button mBt_create_wallet_confirm;
    private EditText mEt_create_wallet_name;
    private EditText mEt_create_wallet_pwd;
    private EditText mEt_create_wallet_repeat_pwd;
    private Wallet mWalletNew;
    private boolean mIsSelectedPrivacy;
    private boolean mIsAgreePrivacy;
    private ImageButton mIb_create_wallet_privacy_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        initView();
    }

    private void initView() {
        mEt_create_wallet_name = (EditText) findViewById(R.id.et_create_wallet_name);
        mEt_create_wallet_pwd = (EditText) findViewById(R.id.et_create_wallet_pwd);
        mEt_create_wallet_repeat_pwd = (EditText) findViewById(R.id.et_create_wallet_repeat_pwd);
        mBt_create_wallet_confirm = (Button) findViewById(R.id.bt_create_wallet_confirm);
        mIb_create_wallet_privacy_point = findViewById(R.id.ib_create_wallet_privacy_point);

        mBt_create_wallet_confirm.setOnClickListener(this);
        mIb_create_wallet_privacy_point.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_create_wallet_name:
                break;
            case R.id.et_create_wallet_pwd:
                break;
            case R.id.et_create_wallet_repeat_pwd:
                break;
            case R.id.bt_create_wallet_confirm:
                if (!checkInput()) {
                    CpLog.w(TAG, "checkInput is false!");
                    return;
                }
                newNeoAddr(mEt_create_wallet_pwd.getText().toString().trim());
                startActivity(MainActivity.class, true);
                break;
            case R.id.ib_create_wallet_privacy_point:
                if (mIsSelectedPrivacy) {
                    mIsSelectedPrivacy = false;
                    mIb_create_wallet_privacy_point.setImageResource(R.drawable
                            .shape_privacy_point);
                    mBt_create_wallet_confirm.setBackgroundResource(R.drawable
                            .shape_new_visitor_bt_bg);
                    mBt_create_wallet_confirm.setTextColor(getResources().getColor(R.color
                            .text_color_white));
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

        if (TextUtils.isEmpty(wallet_name)
                || TextUtils.isEmpty(wallet_pwd)
                || TextUtils.isEmpty(repeat_pwd)) {
            Toast.makeText(this, "不能为空！", Toast.LENGTH_LONG).show();
            CpLog.w(TAG, "wallet_name or wallet_pwd or repeat_pwd is null!");
            return false;
        }

        if (!wallet_pwd.equals(repeat_pwd)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_LONG).show();
            CpLog.w(TAG, "wallet_pwd and repeat_pwd is not same!");
            return false;
        }

        if (!mIsAgreePrivacy) {
            Toast.makeText(this, "请先仔细阅读隐私条款", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void newNeoAddr(String pwd) {
        try {
            mWalletNew = Neomobile.new_();
        } catch (Exception e) {
            CpLog.e(TAG, "new_() exception:" + e.getMessage());
        }

        try {
            String keyStore = mWalletNew.toKeyStore(pwd);
            CpLog.i(TAG, "keyStore:" + keyStore);

            ArrayList<WalletKeyStore> walletKeyStores = new ArrayList<>();
            WalletKeyStore walletKeyStore = new WalletKeyStore();
            walletKeyStore.setWalletName(mEt_create_wallet_name.getText().toString().trim());
            walletKeyStore.setWalletAddr(mWalletNew.address());
            walletKeyStore.setWalletKeyStore(keyStore);
            walletKeyStores.add(walletKeyStore);

            String keyStoresJson = (String) SharedPreferencesUtils.getParam(this, Constant
                    .SP_WALLET_KEYSTORE, "");
            if (TextUtils.isEmpty(keyStoresJson)) {
                CpLog.w(TAG, "keyStoresJson is null!");
                SharedPreferencesUtils.putParam(this, Constant.SP_WALLET_KEYSTORE, GsonUtils
                        .toJsonStr(walletKeyStores));
                return;
            }

            List<WalletKeyStore> walletKeyStoresSaved = GsonUtils.json2List(keyStoresJson);
            walletKeyStoresSaved.add(walletKeyStore);
            SharedPreferencesUtils.putParam(this, Constant.SP_WALLET_KEYSTORE, GsonUtils
                    .toJsonStr(walletKeyStoresSaved));
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
        }

    }

}
