package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import chinapex.com.wallet.executor.callback.eth.IFromKeystoreToEthWalletCallback;
import chinapex.com.wallet.utils.CpLog;
import ethmobile.Ethmobile;
import ethmobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/8 00:34
 * E-Mail：liuyi_61@163.com
 */
public class FromKeystoreToEthWallet implements Runnable {

    private static final String TAG = FromKeystoreToEthWallet.class.getSimpleName();

    private String mKeystore;
    private String mPwd;
    private IFromKeystoreToEthWalletCallback mIFromKeystoreToEthWalletCallback;

    public FromKeystoreToEthWallet(String keystore, String pwd, IFromKeystoreToEthWalletCallback
            iFromKeystoreToEthWalletCallback) {
        mKeystore = keystore;
        mPwd = pwd;
        mIFromKeystoreToEthWalletCallback = iFromKeystoreToEthWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mKeystore)
                || TextUtils.isEmpty(mPwd)
                || null == mIFromKeystoreToEthWalletCallback) {
            CpLog.e(TAG, "mKeystore or mPwd or mIFromKeystoreToEthWalletCallback is null！");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Ethmobile.fromKeyStore(mKeystore, mPwd);
        } catch (Exception e) {
            CpLog.e(TAG, "fromKeyStore exception:" + e.getMessage());
        }
        mIFromKeystoreToEthWalletCallback.fromKeystoreToEthWallet(wallet);
    }
}
