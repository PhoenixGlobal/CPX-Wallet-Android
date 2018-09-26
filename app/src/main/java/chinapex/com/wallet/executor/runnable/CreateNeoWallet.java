package chinapex.com.wallet.executor.runnable;


import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.callback.ICreateWalletCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/1 0001.
 */

public class CreateNeoWallet implements Runnable {

    private static final String TAG = CreateNeoWallet.class.getSimpleName();

    private String mWalletName;
    private String mPwd;
    private ICreateWalletCallback mICreateWalletCallback;

    public CreateNeoWallet(String walletName, String pwd, ICreateWalletCallback
            iCreateWalletCallback) {
        mWalletName = walletName;
        mPwd = pwd;
        mICreateWalletCallback = iCreateWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mWalletName)
                || TextUtils.isEmpty(mPwd)
                || null == mICreateWalletCallback) {
            CpLog.e(TAG, "neo mWalletName or mPwd or mICreateWalletCallback is null!");
            return;
        }

        Wallet walletFirst = null;
        try {
            walletFirst = Neomobile.new_();
        } catch (Exception e) {
            CpLog.e(TAG, "neo new_() exception:" + e.getMessage());
        }

        if (null == walletFirst) {
            CpLog.e(TAG, "neo walletFirst is null!");
            return;
        }

        Wallet walletChecked = checkMnemonic(walletFirst);
        if (null == walletChecked) {
            CpLog.e(TAG, "neo walletChecked is null!");
            return;
        }

        String toKeyStore = null;
        try {
            toKeyStore = walletChecked.toKeyStore(mPwd);
        } catch (Exception e) {
            CpLog.e(TAG, "neo toKeyStore exception:" + e.getMessage());
        }

        if (TextUtils.isEmpty(toKeyStore)) {
            CpLog.e(TAG, "neo toKeyStore is null！");
            return;
        }

        ArrayList<String> assets = new ArrayList<>();
        assets.add(Constant.ASSETS_NEO_GAS);
        assets.add(Constant.ASSETS_NEO);

        ArrayList<String> assetsNep5 = new ArrayList<>();
        assetsNep5.add(Constant.ASSETS_CPX);

        NeoWallet neoWallet = new NeoWallet();
        neoWallet.setWalletType(Constant.WALLET_TYPE_NEO);
        neoWallet.setName(mWalletName);
        neoWallet.setAddress(walletChecked.address());
        neoWallet.setBackupState(Constant.BACKUP_UNFINISHED);
        neoWallet.setKeyStore(toKeyStore);
        neoWallet.setAssetJson(GsonUtils.toJsonStr(assets));
        neoWallet.setColorAssetJson(GsonUtils.toJsonStr(assetsNep5));

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        apexWalletDbDao.insert(Constant.TABLE_NEO_WALLET, neoWallet);
        ApexListeners.getInstance().notifyWalletAdd(neoWallet);
        mICreateWalletCallback.newWallet(walletChecked);
    }

    private Wallet checkMnemonic(Wallet wallet) {
        Wallet walletFinal = null;
        try {
            String mnemonic = wallet.mnemonic("en_US");
            walletFinal = Neomobile.fromMnemonic(mnemonic, "en_US");
        } catch (Exception e) {
            CpLog.e(TAG, "neo checkMnemonic fromMnemonic Exception:" + e.getMessage());
            try {
                walletFinal = checkMnemonic(Neomobile.new_());
            } catch (Exception e1) {
                CpLog.e(TAG, "neo checkMnemonic new_() Exception:" + e.getMessage());
            }
        }

        return walletFinal;
    }
}
