package chinapex.com.wallet.executor.runnable.eth;

import chinapex.com.wallet.bean.tx.EthTxBean;
import chinapex.com.wallet.executor.callback.eth.ICreateEthTxCallback;
import chinapex.com.wallet.utils.CpLog;
import ethmobile.Wallet;

/**
 * Created by SteelCabbage on 2018/9/7 0007 14:16.
 * E-Mail：liuyi_61@163.com
 */
public class CreateEthTx implements Runnable {
    private static final String TAG = CreateEthTx.class.getSimpleName();
    private EthTxBean mEthTxBean;
    private ICreateEthTxCallback mICreateEthTxCallback;

    public CreateEthTx(EthTxBean ethTxBean, ICreateEthTxCallback ICreateEthTxCallback) {
        mEthTxBean = ethTxBean;
        mICreateEthTxCallback = ICreateEthTxCallback;
    }

    @Override
    public void run() {
        if (null == mICreateEthTxCallback) {
            CpLog.e(TAG, "mICreateEthTxCallback is null！");
            return;
        }

        if (null == mEthTxBean) {
            CpLog.e(TAG, "mEthTxBean is null！");
            mICreateEthTxCallback.createEthTx(null);
            return;
        }

        Wallet ethWallet = mEthTxBean.getWallet();
        if (null == ethWallet) {
            CpLog.e(TAG, "ethWallet is null!");
            mICreateEthTxCallback.createEthTx(null);
            return;
        }

        try {
            String data = ethWallet.transfer(mEthTxBean.getNonce(),
                    mEthTxBean.getToAddress(),
                    mEthTxBean.getAmount(),
                    mEthTxBean.getGasPrice(),
                    mEthTxBean.getGasLimit());
            mICreateEthTxCallback.createEthTx("0x" + data);
        } catch (Exception e) {
            CpLog.e(TAG, "ethWallet.transfer exception:" + e.getMessage());
            mICreateEthTxCallback.createEthTx(null);
        }
    }
}
