package chinapex.com.wallet.global;

import android.text.TextUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.IGetEthBlockNumberCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthNonceCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthTransactionHistoryCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthTransactionReceiptCallback;
import chinapex.com.wallet.executor.runnable.eth.GetEthBlockNumber;
import chinapex.com.wallet.executor.runnable.eth.GetEthNonce;
import chinapex.com.wallet.executor.runnable.eth.GetEthTransactionHistory;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.utils.WalletUtils;

/**
 * Created by SteelCabbage on 2018/7/13 0013 11:55.
 * E-Mail：liuyi_61@163.com
 */

public class UpdateEthTxState implements IGetEthTransactionReceiptCallback, IGetEthTransactionHistoryCallback,
        IGetEthBlockNumberCallback, IGetEthNonceCallback {

    private static final String TAG = UpdateEthTxState.class.getSimpleName();

    private String mTxId;
    private ScheduledFuture mGetTxReceiptSF;
    private ScheduledFuture mUpdateTxRecordsSF;
    private ScheduledFuture mGetBlockNumberSF;
    private long mTxOfBlockNum;
    private long mCurrentBlockNum;
    private long mStartPollingTime;
    private String mHexNonce;
    private String mWalletAddress;
    private String mBlockNumber;

    public UpdateEthTxState(String txId) {
        mTxId = txId;
        mStartPollingTime = (long) SharedPreferencesUtils.getParam(ApexWalletApplication.getInstance(), mTxId, 0L);
        CpLog.i(TAG, "startPollingTime:" + mStartPollingTime);
    }

    public void setGetTxReceiptSF(ScheduledFuture getTxReceiptSF) {
        mGetTxReceiptSF = getTxReceiptSF;
    }

    @Override
    public void getEthTransactionReceipt(String walletAddress, String blockNumber, boolean isSuccess) {
        if (TextUtils.isEmpty(mTxId) || TextUtils.isEmpty(walletAddress) || null == mGetTxReceiptSF) {
            CpLog.e(TAG, "getEthTransactionReceipt() -> mTxId or walletAddress or mGetTxReceiptSF is null!");
            return;
        }

        mWalletAddress = walletAddress;
        mBlockNumber = blockNumber;

        if (System.currentTimeMillis() - mStartPollingTime > Constant.TX_ETH_EXCEPTION_TIME) {
            CpLog.w(TAG, "over 5 minutes,handle failed tx");
            mHexNonce = (String) SharedPreferencesUtils.getParam(ApexWalletApplication.getInstance(), Constant.TX_ETH_NONCE
                    + mTxId, "");
            TaskController.getInstance().submit(new GetEthNonce(walletAddress, this));
            return;
        }

        handleBlockNum(walletAddress, blockNumber);
    }

    private void handleBlockNum(String walletAddress, String blockNumber) {
        if (TextUtils.isEmpty(blockNumber)) {
            CpLog.w(TAG, "handleBlockNum()-> blockNumber is null,this tx is not in block!");
            return;
        }

        try {
            mTxOfBlockNum = Long.valueOf(blockNumber);
        } catch (NumberFormatException e) {
            CpLog.e(TAG, "getEthTransactionReceipt NumberFormatException:" + e.getMessage());
            return;
        }

        mGetTxReceiptSF.cancel(true);
        mGetBlockNumberSF = TaskController.getInstance().schedule(new GetEthBlockNumber(this), 0, Constant.TX_ETH_POLLING_TIME);
        mUpdateTxRecordsSF = TaskController.getInstance().schedule(new GetEthTransactionHistory(walletAddress, this), 0,
                Constant.TX_ETH_POLLING_TIME);
    }

    @Override
    public void getEthBlockNumber(String blockNumber) {
        if (TextUtils.isEmpty(blockNumber)) {
            CpLog.e(TAG, "getEthBlockNumber() -> blockNumber is null!");
            return;
        }

        try {
            mCurrentBlockNum = Long.valueOf(blockNumber);
        } catch (NumberFormatException e) {
            CpLog.e(TAG, "getEthBlockNumber NumberFormatException:" + e.getMessage());
            return;
        }

        if (mCurrentBlockNum - mTxOfBlockNum >= Constant.TX_ETH_CONFIRM_OK) {
            CpLog.i(TAG, "TX_ETH_CONFIRM_OK");
            mUpdateTxRecordsSF.cancel(false);
            mGetBlockNumberSF.cancel(false);
            SharedPreferencesUtils.remove(ApexWalletApplication.getInstance(), mTxId);
            SharedPreferencesUtils.remove(ApexWalletApplication.getInstance(), Constant.TX_ETH_NONCE + mTxId);
        }
    }

    @Override
    public void getEthTransactionHistory(List<TransactionRecord> transactionRecords) {
        if (Constant.TX_ETH_CONFIRM_OK > mCurrentBlockNum - mTxOfBlockNum) {
            return;
        }

        handleTx();
    }

    private void handleTx() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<TransactionRecord> cacheTxs = apexWalletDbDao.queryTxCacheByTxId(Constant.TABLE_ETH_TX_CACHE, mTxId);
        if (null == cacheTxs || cacheTxs.isEmpty()) {
            apexWalletDbDao.updateTxState(Constant.TABLE_ETH_TRANSACTION_RECORD, mTxId, Constant.TRANSACTION_STATE_SUCCESS);
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
            apexWalletDbDao.insertTxRecord(Constant.TABLE_ETH_TRANSACTION_RECORD, cacheTx);
        }
        ApexListeners.getInstance().notifyTxStateUpdate(mTxId, Constant.TRANSACTION_STATE_FAIL, Constant.NO_NEED_MODIFY_TX_TIME);
        apexWalletDbDao.delCacheByTxId(Constant.TABLE_ETH_TX_CACHE, mTxId);
    }

    @Override
    public void getEthNonce(String nonce) {
        if (TextUtils.isEmpty(nonce)) {
            CpLog.e(TAG, "nonce is null!");
            return;
        }

        try {
            String currentNonce = WalletUtils.toDecString(nonce, "0");
            String txNonce = WalletUtils.toDecString(mHexNonce, "0");

            BigInteger subtract = new BigInteger(currentNonce).subtract(new BigInteger(txNonce));
            if (BigInteger.ZERO.compareTo(subtract) == 0 || BigInteger.ZERO.compareTo(subtract) == 1) {
                CpLog.w(TAG, "this txNonce is valid!");
                handleBlockNum(mWalletAddress, mBlockNumber);
                return;
            }
        } catch (Exception e) {
            CpLog.e(TAG, "UpdateEthTxState getEthNonce Exception:" + e.getMessage());
            handleBlockNum(mWalletAddress, mBlockNumber);
            return;
        }

        mGetTxReceiptSF.cancel(true);
        handleTx();
        SharedPreferencesUtils.remove(ApexWalletApplication.getInstance(), mTxId);
        SharedPreferencesUtils.remove(ApexWalletApplication.getInstance(), Constant.TX_ETH_NONCE + mTxId);
    }
}
