package chinapex.com.wallet.executor.runnable;


import android.text.TextUtils;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.callback.ICreateWalletCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/1 0001.
 */

public class CreateWallet implements Runnable {

    private static final String TAG = CreateWallet.class.getSimpleName();
    private String mWalletName;
    private String mPwd;
    private ICreateWalletCallback mICreateWalletCallback;

    public CreateWallet(String walletName, String pwd, ICreateWalletCallback
            ICreateWalletCallback) {
        mWalletName = walletName;
        mPwd = pwd;
        mICreateWalletCallback = ICreateWalletCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mWalletName)
                || TextUtils.isEmpty(mPwd)
                || null == mICreateWalletCallback) {
            CpLog.e(TAG, "mWalletName or mPwd or mICreateWalletCallback is null!");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Neomobile.new_();
        } catch (Exception e) {
            CpLog.e(TAG, "new_() exception:" + e.getMessage());
        }

        if (null == wallet) {
            CpLog.e(TAG, "wallet is null!");
            return;
        }

        String toKeyStore = null;
        try {
            toKeyStore = wallet.toKeyStore(mPwd);
        } catch (Exception e) {
            CpLog.e(TAG, "toKeyStore exception:" + e.getMessage());
        }

        if (TextUtils.isEmpty(toKeyStore)) {
            CpLog.e(TAG, "toKeyStore is null！");
            return;
        }

        WalletBean walletBean = new WalletBean();
        walletBean.setWalletName(mWalletName);
        walletBean.setWalletAddr(wallet.address());
        //todo: 0 or 1 待确认
        walletBean.setBackupState(0);
        walletBean.setKeyStore(toKeyStore);

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        apexWalletDbDao.insert(Constant.TABLE_APEX_WALLET, walletBean);
        mICreateWalletCallback.newWallet(wallet);
    }
}