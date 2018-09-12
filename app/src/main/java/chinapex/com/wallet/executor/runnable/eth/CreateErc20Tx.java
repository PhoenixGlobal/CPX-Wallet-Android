package chinapex.com.wallet.executor.runnable.eth;

import chinapex.com.wallet.bean.tx.EthTxBean;
import chinapex.com.wallet.executor.callback.eth.ICreateErc20TxCallback;
import chinapex.com.wallet.executor.callback.eth.ICreateEthTxCallback;
import chinapex.com.wallet.utils.CpLog;
import ethmobile.Wallet;

/**
 * Created by SteelCabbage on 2018/9/7 0007 14:16.
 * E-Mail：liuyi_61@163.com
 */
public class CreateErc20Tx implements Runnable {
    private static final String TAG = CreateErc20Tx.class.getSimpleName();
    private EthTxBean mEthTxBean;
    private ICreateErc20TxCallback mICreateErc20TxCallback;

    public CreateErc20Tx(EthTxBean ethTxBean, ICreateErc20TxCallback ICreateErc20TxCallback) {
        mEthTxBean = ethTxBean;
        mICreateErc20TxCallback = ICreateErc20TxCallback;
    }

    @Override
    public void run() {
        if (null == mICreateErc20TxCallback) {
            CpLog.e(TAG, "mICreateErc20TxCallback is null！");
            return;
        }

        if (null == mEthTxBean) {
            CpLog.e(TAG, "mEthTxBean is null！");
            mICreateErc20TxCallback.createErc20Tx(null);
            return;
        }

        Wallet ethWallet = mEthTxBean.getWallet();
        if (null == ethWallet) {
            CpLog.e(TAG, "ethWallet is null!");
            mICreateErc20TxCallback.createErc20Tx(null);
            return;
        }

        try {
            String data = ethWallet.transferERC20(mEthTxBean.getAssetID(),
                    mEthTxBean.getNonce(),
                    mEthTxBean.getToAddress(),
                    mEthTxBean.getAmount(),
                    mEthTxBean.getGasPrice(),
                    mEthTxBean.getGasLimit());
            mICreateErc20TxCallback.createErc20Tx("0x" + data);
        } catch (Exception e) {
            CpLog.e(TAG, "ethWallet.transferERC20 exception:" + e.getMessage());
            mICreateErc20TxCallback.createErc20Tx(null);
        }
    }
}
