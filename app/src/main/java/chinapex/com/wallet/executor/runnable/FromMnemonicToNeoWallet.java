package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import chinapex.com.wallet.executor.callback.IFromMnemonicToNeoWalletCallback;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/11 0011.
 */

public class FromMnemonicToNeoWallet implements java.lang.Runnable {

    private static final String TAG = FromMnemonicToNeoWallet.class.getSimpleName();

    private String mMnemonic;
    private String mMnemonicType;
    private IFromMnemonicToNeoWalletCallback mIFromMnemonicToNeoWalletCallback;

    public FromMnemonicToNeoWallet(String mnemonic, String mnemonicType,
                                   IFromMnemonicToNeoWalletCallback IFromMnemonicToNeoWalletCallback) {
        mMnemonic = mnemonic;
        mMnemonicType = mnemonicType;
        mIFromMnemonicToNeoWalletCallback = IFromMnemonicToNeoWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mMnemonic)
                || null == mIFromMnemonicToNeoWalletCallback) {
            CpLog.e(TAG, "mMnemonic or mPwd or mIFromMnemonicToNeoWalletCallback is null!");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Neomobile.fromMnemonic(mMnemonic, mMnemonicType);
        } catch (Exception e) {
            CpLog.e(TAG, "fromMnemonic exception:" + e.getMessage());
        }

        mIFromMnemonicToNeoWalletCallback.fromMnemonicToNeoWallet(wallet);
    }
}
