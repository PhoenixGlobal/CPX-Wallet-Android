package chinapex.com.wallet.executor.runnable;

import java.math.BigDecimal;

import chinapex.com.wallet.bean.Nep5TxBean;
import chinapex.com.wallet.executor.callback.ICreateNep5TxCallback;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Neomobile;
import neomobile.Tx;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/6/27 0027 14:25.
 * E-Mail：liuyi_61@163.com
 */

public class CreateNep5Tx implements Runnable {

    private static final String TAG = CreateNep5Tx.class.getSimpleName();
    private Wallet mWallet;
    private Nep5TxBean mNep5TxBean;
    private ICreateNep5TxCallback mICreateNep5TxCallback;

    public CreateNep5Tx(Wallet wallet, Nep5TxBean nep5TxBean, ICreateNep5TxCallback
            ICreateNep5TxCallback) {
        mWallet = wallet;
        mNep5TxBean = nep5TxBean;
        mICreateNep5TxCallback = ICreateNep5TxCallback;
    }

    @Override
    public void run() {
        if (null == mWallet
                || null == mNep5TxBean
                || null == mICreateNep5TxCallback) {
            CpLog.e(TAG, "mWallet or mNep5TxBean or mICreateNep5TxCallback is null!");
            return;
        }

        Tx nep5Tx = null;
        try {
            nep5Tx = mWallet.createNep5Tx(
                    mNep5TxBean.getAssetID(),
                    Neomobile.decodeAddress(mNep5TxBean.getAddrFrom()),
                    Neomobile.decodeAddress(mNep5TxBean.getAddrTo()),
                    new BigDecimal(mNep5TxBean.getTransferAmount()).multiply(new BigDecimal(10)
                            .pow(mNep5TxBean.getAssetDecimal())).longValue(),
                    mNep5TxBean.getUtxos()
            );
        } catch (Exception e) {
            CpLog.e(TAG, "createNep5Tx exception:" + e.getMessage());
        }
        mICreateNep5TxCallback.createNep5Tx(nep5Tx);
    }
}
