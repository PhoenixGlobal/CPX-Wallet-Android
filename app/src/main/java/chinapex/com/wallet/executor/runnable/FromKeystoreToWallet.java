package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.callback.IFromKeystoreGenerateWalletCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Neomobile;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/8 00:34
 * E-Mail：liuyi_61@163.com
 */
public class FromKeystoreToWallet implements Runnable {

    private static final String TAG = FromKeystoreToWallet.class.getSimpleName();
    private WalletBean mWalletBean;
    private String mPwd;
    private IFromKeystoreGenerateWalletCallback mIFromKeystoreGenerateWalletCallback;

    public FromKeystoreToWallet(WalletBean walletBean, String pwd, IFromKeystoreGenerateWalletCallback IFromKeystoreGenerateWalletCallback) {
        mWalletBean = walletBean;
        mPwd = pwd;
        mIFromKeystoreGenerateWalletCallback = IFromKeystoreGenerateWalletCallback;
    }

    @Override
    public void run() {
        if (null == mWalletBean
                || TextUtils.isEmpty(mPwd)
                || null == mIFromKeystoreGenerateWalletCallback) {
            CpLog.e(TAG, "mWalletBean or mPwd or mIFromKeystoreGenerateWalletCallback is null！");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null！");
            return;
        }

        WalletBean walletBean = apexWalletDbDao.queryByWalletNaAddress(Constant.TABLE_APEX_WALLET,
                mWalletBean.getWalletAddr());
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        Wallet wallet = null;
        try {
            wallet = Neomobile.fromKeyStore(walletBean.getKeyStore(), mPwd);
            CpLog.i(TAG, "wallet address:" + wallet.address());
        } catch (Exception e) {
            CpLog.e(TAG, "fromKeyStore exception:" + e.getMessage());
        }
        mIFromKeystoreGenerateWalletCallback.fromKeystoreWallet(wallet);
    }
}
