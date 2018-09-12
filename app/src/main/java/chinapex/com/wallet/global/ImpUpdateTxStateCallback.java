package chinapex.com.wallet.global;

import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IGetTransactionHistoryCallback;
import chinapex.com.wallet.executor.callback.IUpdateTxStateCallback;
import chinapex.com.wallet.executor.runnable.GetTransactionHistory;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/13 0013 11:55.
 * E-Mail：liuyi_61@163.com
 */

public class ImpUpdateTxStateCallback implements IUpdateTxStateCallback, IGetTransactionHistoryCallback {

    private static final String TAG = ImpUpdateTxStateCallback.class.getSimpleName();

    private String mTxId;
    private ScheduledFuture mScheduledFuture;
    private long mConfirmations;

    public ImpUpdateTxStateCallback(String txId) {
        mTxId = txId;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        mScheduledFuture = scheduledFuture;
    }

    @Override
    public void updateTxState(String txId, String walletAddress, long confirmations) {
        if (null == mScheduledFuture
                || TextUtils.isEmpty(txId)
                || TextUtils.isEmpty(walletAddress)) {
            CpLog.e(TAG, "mUpdateTxStateSF or txId or walletAddress is null!");
            return;
        }

        if (Constant.TX_CONFIRM_EXCEPTION == confirmations) {
            CpLog.e(TAG, "TX_CONFIRM_EXCEPTION");
            return;
        }

        if (Constant.TX_UN_CONFIRM == confirmations) {
            CpLog.w(TAG, "TX_UN_CONFIRM");
            return;
        }

        if (Constant.TX_CONFIRM_OK <= confirmations) {
            CpLog.i(TAG, "TX_CONFIRM_OK");
            mScheduledFuture.cancel(false);
        }

        mConfirmations = confirmations;
        TaskController.getInstance().submit(new GetTransactionHistory(walletAddress, this));
    }

    @Override
    public void getTransactionHistory(List<TransactionRecord> transactionRecords) {
        if (Constant.TX_CONFIRM_OK > mConfirmations) {
            return;
        }

        handleFailedTx();
    }

    private void handleFailedTx() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<TransactionRecord> cacheTxs = apexWalletDbDao.queryTxCacheByTxId(mTxId);
        if (null == cacheTxs || cacheTxs.isEmpty()) {
            apexWalletDbDao.updateTxState(Constant.TABLE_TRANSACTION_RECORD, mTxId, Constant.TRANSACTION_STATE_SUCCESS);
            ApexListeners.getInstance().notifyTxStateUpdate(mTxId, Constant.TRANSACTION_STATE_SUCCESS, Constant
                    .NO_NEED_MODIFY_TX_TIME);
            return;
        }

        for (TransactionRecord cacheTx : cacheTxs) {
            if (null == cacheTx) {
                CpLog.e(TAG, "cacheTx is null!");
                continue;
            }

            cacheTx.setTxState(Constant.TRANSACTION_STATE_FAIL);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_TRANSACTION_RECORD, cacheTx);
        }
        ApexListeners.getInstance().notifyTxStateUpdate(mTxId, Constant.TRANSACTION_STATE_FAIL, Constant.NO_NEED_MODIFY_TX_TIME);
        apexWalletDbDao.delCacheByTxId(mTxId);
    }
}
