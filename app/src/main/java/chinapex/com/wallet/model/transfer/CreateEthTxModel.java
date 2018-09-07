package chinapex.com.wallet.model.transfer;

import android.text.TextUtils;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.tx.EthTxBean;
import chinapex.com.wallet.bean.tx.ITxBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.ICreateEthTxCallback;
import chinapex.com.wallet.executor.callback.eth.IEthSendRawTransactionCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthNonceCallback;
import chinapex.com.wallet.executor.runnable.eth.CreateEthTx;
import chinapex.com.wallet.executor.runnable.eth.EthSendRawTransaction;
import chinapex.com.wallet.executor.runnable.eth.GetEthNonce;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:56.
 * E-Mail：liuyi_61@163.com
 */
public class CreateEthTxModel implements ICreateTxModel, ICreateEthTxCallback, IEthSendRawTransactionCallback,
        IGetEthNonceCallback {
    private static final String TAG = CreateEthTxModel.class.getSimpleName();
    private ICreateTxModelCallback mICreateTxModelCallback;
    private EthTxBean mEthTxBean;

    public CreateEthTxModel(ICreateTxModelCallback ICreateTxModelCallback) {
        mICreateTxModelCallback = ICreateTxModelCallback;
    }

    @Override
    public void createGlobalTx(ITxBean iTxBean) {
        if (null == mICreateTxModelCallback) {
            CpLog.e(TAG, "mICreateTxModelCallback is null!");
            return;
        }

        if (null == iTxBean) {
            CpLog.e(TAG, "iTxBean is null!");
            return;
        }

        if (iTxBean instanceof EthTxBean) {
            mEthTxBean = (EthTxBean) iTxBean;
        }

        if (null == mEthTxBean) {
            CpLog.e(TAG, "mEthTxBean is null!");
            return;
        }

        TaskController.getInstance().submit(new GetEthNonce(mEthTxBean.getFromAddress(), this));
    }

    @Override
    public void getEthNonce(String nonce) {
        if (TextUtils.isEmpty(nonce)) {
            CpLog.e(TAG, "nonce is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .eth_nonce_null), false);
            return;
        }

        mEthTxBean.setNonce(nonce);
        TaskController.getInstance().submit(new CreateEthTx(mEthTxBean, this));
    }

    @Override
    public void createEthTx(String data) {
        if (TextUtils.isEmpty(data)) {
            CpLog.e(TAG, "createEthTx() -> data is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .eth_nonce_null), false);
            return;
        }

        CpLog.i(TAG, "createEthTx() -> data:" + data);
        TaskController.getInstance().submit(new EthSendRawTransaction(data, this));
    }

    @Override
    public void ethSendRawTransaction(Boolean isSendSuccess, String txId) {
        if (!isSendSuccess) {
            CpLog.e(TAG, "ethSendRawTransaction is false");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_broadcast_failed), false);
            return;
        }

        if (TextUtils.isEmpty(txId)) {
            CpLog.e(TAG, "txId is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .server_err_txId_null), false);
            return;
        }

        mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                .transaction_broadcast_successful), true);
    }

    @Override
    public void createColorTx(ITxBean iTxBean) {

    }


}
