package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import chinapex.com.wallet.executor.callback.IFromKeystoreToWalletCallback;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/8 00:34
 * E-Mail：liuyi_61@163.com
 */
public class FromKeystoreToWallet implements Runnable {

    private static final String TAG = FromKeystoreToWallet.class.getSimpleName();
    private String mKeystore;
    private String mPwd;
    private IFromKeystoreToWalletCallback mIFromKeystoreToWalletCallback;

    public FromKeystoreToWallet(String keystore, String pwd, IFromKeystoreToWalletCallback
            IFromKeystoreToWalletCallback) {
        mKeystore = keystore;
        mPwd = pwd;
        mIFromKeystoreToWalletCallback = IFromKeystoreToWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mKeystore)
                || TextUtils.isEmpty(mPwd)
                || null == mIFromKeystoreToWalletCallback) {
            CpLog.e(TAG, "mKeystore or mPwd or mIFromKeystoreToWalletCallback is null！");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Neomobile.fromKeyStore(mKeystore, mPwd);
            CpLog.i(TAG, "wallet address:" + wallet.address());
        } catch (Exception e) {
            CpLog.e(TAG, "fromKeyStore exception:" + e.getMessage());
        }
        mIFromKeystoreToWalletCallback.fromKeystoreWallet(wallet);
    }
}
