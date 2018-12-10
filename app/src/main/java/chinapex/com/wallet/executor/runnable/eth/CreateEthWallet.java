package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.eth.EthWallet;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.callback.eth.ICreateEthWalletCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import ethmobile.Ethmobile;
import ethmobile.Wallet;


/**
 * Created by SteelCabbage on 2018/8/13 0013 18:03.
 * E-Mail：liuyi_61@163.com
 */

public class CreateEthWallet implements Runnable {

    private static final String TAG = CreateEthWallet.class.getSimpleName();

    private String mName;
    private String mPwd;
    private ICreateEthWalletCallback mICreateEthWalletCallback;

    public CreateEthWallet(String name, String pwd, ICreateEthWalletCallback
            ICreateEthWalletCallback) {
        mName = name;
        mPwd = pwd;
        mICreateEthWalletCallback = ICreateEthWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mName)
                || TextUtils.isEmpty(mPwd)
                || null == mICreateEthWalletCallback) {
            CpLog.e(TAG, "eth mName or mPwd or mICreateEthWalletCallback is null!");
            return;
        }

        Wallet walletFirst = null;
        try {
            walletFirst = Ethmobile.new_();
        } catch (Exception e) {
            CpLog.e(TAG, "eth new_() exception:" + e.getMessage());
        }

        if (null == walletFirst) {
            CpLog.e(TAG, "eth walletFirst is null!");
            return;
        }

        Wallet walletChecked = checkMnemonic(walletFirst);
        if (null == walletChecked) {
            CpLog.e(TAG, "eth walletChecked is null!");
            return;
        }

        String toKeyStore = null;
        try {
            toKeyStore = walletChecked.toKeyStore(mPwd);
        } catch (Exception e) {
            CpLog.e(TAG, "eth toKeyStore exception:" + e.getMessage());
        }

        if (TextUtils.isEmpty(toKeyStore)) {
            CpLog.e(TAG, "eth toKeyStore is null！");
            return;
        }

        ArrayList<String> assets = new ArrayList<>();
        assets.add(Constant.ASSETS_ETH);

        ArrayList<String> colorAssets = new ArrayList<>();
//        colorAssets.add(Constant.ASSETS_ERC20_NMB);

        EthWallet ethWallet = new EthWallet();
        ethWallet.setWalletType(Constant.WALLET_TYPE_ETH);
        ethWallet.setName(mName);
        ethWallet.setAddress(walletChecked.address());
        ethWallet.setBackupState(Constant.BACKUP_UNFINISHED);
        ethWallet.setKeyStore(toKeyStore);
        ethWallet.setAssetJson(GsonUtils.toJsonStr(assets));
        ethWallet.setColorAssetJson(GsonUtils.toJsonStr(colorAssets));

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        apexWalletDbDao.insert(Constant.TABLE_ETH_WALLET, ethWallet);
        ApexListeners.getInstance().notifyWalletAdd(ethWallet);
        mICreateEthWalletCallback.createEthWallet(walletChecked);
    }

    private Wallet checkMnemonic(Wallet wallet) {
        Wallet walletFinal = null;
        try {
            String mnemonic = wallet.mnemonic("en_US");
            walletFinal = Ethmobile.fromMnemonic(mnemonic, "en_US");
        } catch (Exception e) {
            CpLog.e(TAG, "eth checkMnemonic fromMnemonic Exception:" + e.getMessage());
            try {
                walletFinal = checkMnemonic(Ethmobile.new_());
            } catch (Exception e1) {
                CpLog.e(TAG, "eth checkMnemonic new_() Exception:" + e.getMessage());
            }
        }

        return walletFinal;
    }
}
