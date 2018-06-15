package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.callback.IFromMnemonicToWalletCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/11 0011.
 */

public class FromMnemonicToWallet implements java.lang.Runnable {

    private static final String TAG = FromMnemonicToWallet.class.getSimpleName();
    private String mMnemonic;
    private String mMnemonicType;
    private IFromMnemonicToWalletCallback mIFromMnemonicToWalletCallback;

    public FromMnemonicToWallet(String mnemonic, String mnemonicType,
                                IFromMnemonicToWalletCallback IFromMnemonicToWalletCallback) {
        mMnemonic = mnemonic;
        mMnemonicType = mnemonicType;
        mIFromMnemonicToWalletCallback = IFromMnemonicToWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mMnemonic)
                || null == mIFromMnemonicToWalletCallback) {
            CpLog.e(TAG, "mMnemonic or mPwd or mIFromMnemonicToWalletCallback is null!");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Neomobile.fromMnemonic(mMnemonic, mMnemonicType);
        } catch (Exception e) {
            CpLog.e(TAG, "fromMnemonic exception:" + e.getMessage());
        }

        mIFromMnemonicToWalletCallback.fromMnemonicToWallet(wallet);
    }
}
