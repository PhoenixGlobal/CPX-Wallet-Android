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
    private String mPwd;
    private IFromMnemonicToWalletCallback mIFromMnemonicToWalletCallback;

    public FromMnemonicToWallet(String mnemonic, String mnemonicType, String pwd,
                                IFromMnemonicToWalletCallback IFromMnemonicToWalletCallback) {
        mMnemonic = mnemonic;
        mMnemonicType = mnemonicType;
        mPwd = pwd;
        mIFromMnemonicToWalletCallback = IFromMnemonicToWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mMnemonic)
                || TextUtils.isEmpty(mPwd)
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

        if (null == wallet) {
            CpLog.e(TAG, "from mnemonic wallet is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        String walletAddress = wallet.address();
        CpLog.i(TAG, "wallet address:" + walletAddress);
        WalletBean queryByWalletAddress = apexWalletDbDao.queryByWalletAddress(Constant
                .TABLE_APEX_WALLET, walletAddress);
        if (null != queryByWalletAddress) {
            CpLog.e(TAG, "this wallet from mnemonic has existed!");
            return;
        }

        ArrayList<String> assetses = new ArrayList<>();
        assetses.add(Constant.ASSETS_CPX);
        assetses.add(Constant.ASSETS_NEO);
        assetses.add(Constant.ASSETS_NEO_GAS);

        WalletBean walletBean = new WalletBean();
        walletBean.setWalletName(Constant.WALLET_NAME_IMPORT_DEFAULT);
        walletBean.setWalletAddr(walletAddress);
        walletBean.setBackupState(Constant.BACKUP_UNFINISHED);
        walletBean.setAssetsJson(GsonUtils.toJsonStr(assetses));
        try {
            walletBean.setKeyStore(wallet.toKeyStore(mPwd));
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
            return;
        }

        apexWalletDbDao.insert(Constant.TABLE_APEX_WALLET, walletBean);

        mIFromMnemonicToWalletCallback.fromMnemonicToWallet(wallet);
    }
}
