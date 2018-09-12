package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import chinapex.com.wallet.executor.callback.eth.IFromMnemonicToEthWalletCallback;
import chinapex.com.wallet.utils.CpLog;
import ethmobile.Ethmobile;
import ethmobile.Wallet;


/**
 * Created by SteelCabbage on 2018/6/11 0011.
 */

public class FromMnemonicToEthWallet implements Runnable {

    private static final String TAG = FromMnemonicToEthWallet.class.getSimpleName();

    private String mMnemonic;
    private String mMnemonicType;
    private IFromMnemonicToEthWalletCallback mIFromMnemonicToEthWalletCallback;

    public FromMnemonicToEthWallet(String mnemonic, String mnemonicType, IFromMnemonicToEthWalletCallback
            iFromMnemonicToEthWalletCallback) {
        mMnemonic = mnemonic;
        mMnemonicType = mnemonicType;
        mIFromMnemonicToEthWalletCallback = iFromMnemonicToEthWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mMnemonic) || null == mIFromMnemonicToEthWalletCallback) {
            CpLog.e(TAG, "mMnemonic or mPwd or mIFromMnemonicToEthWalletCallback is null!");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Ethmobile.fromMnemonic(mMnemonic, mMnemonicType);
        } catch (Exception e) {
            CpLog.e(TAG, "fromMnemonic exception:" + e.getMessage());
        }

        mIFromMnemonicToEthWalletCallback.fromMnemonicToEthWallet(wallet);
    }
}
