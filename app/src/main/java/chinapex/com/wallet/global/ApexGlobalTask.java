package chinapex.com.wallet.global;

import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateNeoAssetsCallback;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateTxStateCallback;
import chinapex.com.wallet.executor.callback.IGetNeoAssetsCallback;
import chinapex.com.wallet.executor.callback.eth.ICheckIsUpdateEthAssetsCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthAssetsCallback;
import chinapex.com.wallet.executor.runnable.CheckIsUpdateNeoAssets;
import chinapex.com.wallet.executor.runnable.CheckIsUpdateTxState;
import chinapex.com.wallet.executor.runnable.GetNeoAssets;
import chinapex.com.wallet.executor.runnable.UpdateTxState;
import chinapex.com.wallet.executor.runnable.eth.CheckIsUpdateEthAssets;
import chinapex.com.wallet.executor.runnable.eth.GetEthAssets;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/6/10 15:23
 * E-Mail：liuyi_61@163.com
 */
public class ApexGlobalTask implements ICheckIsUpdateNeoAssetsCallback, IGetNeoAssetsCallback,
        ICheckIsUpdateTxStateCallback, ICheckIsUpdateEthAssetsCallback, IGetEthAssetsCallback {

    private static final String TAG = ApexGlobalTask.class.getSimpleName();

    private ScheduledFuture mCheckIsUpdateNeoAssetsSF;
    private ScheduledFuture mCheckIsUpdateEthAssetsSF;

    private ApexGlobalTask() {

    }

    private static class ApexCacheHolder {
        private static final ApexGlobalTask sApexGlobalTask = new ApexGlobalTask();
    }

    public static ApexGlobalTask getInstance() {
        return ApexCacheHolder.sApexGlobalTask;
    }

    public void doInit() {
        // check assets
        TaskController.getInstance().submit(new CheckIsUpdateNeoAssets(this));
        TaskController.getInstance().submit(new CheckIsUpdateEthAssets(this));

        // check tx state
        TaskController.getInstance().submit(new CheckIsUpdateTxState(this));
    }

    @Override
    public void checkIsUpdateNeoAssets(boolean isUpdate) {
        if (isUpdate) {
            CpLog.i(TAG, "need to update neo assets!");
            mCheckIsUpdateEthAssetsSF = TaskController.getInstance().schedule(new GetNeoAssets(this), 0, Constant
                    .ASSETS_POLLING_TIME);
        }
    }

    @Override
    public void checkIsUpdateEthAssets(boolean isUpdate) {
        if (isUpdate) {
            CpLog.i(TAG, "need to update eth assets!");
            mCheckIsUpdateNeoAssetsSF = TaskController.getInstance().schedule(new GetEthAssets(this), 0, Constant
                    .ASSETS_POLLING_TIME);
        }
    }

    @Override
    public void getNeoAssets(String msg) {
        if (TextUtils.isEmpty(msg)) {
            CpLog.e(TAG, "getNeoAssets() -> msg is null!");
            return;
        }

        if (Constant.UPDATE_ASSETS_OK.equals(msg)) {
            CpLog.i(TAG, "update neo assets ok!");
            mCheckIsUpdateNeoAssetsSF.cancel(true);
        }
    }

    @Override
    public void getEthAssets(String msg) {
        if (TextUtils.isEmpty(msg)) {
            CpLog.e(TAG, "getEthAssets() -> msg is null!");
            return;
        }

        if (Constant.UPDATE_ASSETS_OK.equals(msg)) {
            CpLog.i(TAG, "update eth assets ok!");
            mCheckIsUpdateEthAssetsSF.cancel(true);
        }
    }

    @Override
    public void checkIsUpdateTxState(List<TransactionRecord> transactionRecords) {
        if (null == transactionRecords || transactionRecords.isEmpty()) {
            CpLog.i(TAG, "no need to update tx state!");
            return;
        }

        for (TransactionRecord transactionRecord : transactionRecords) {
            if (null == transactionRecord) {
                CpLog.e(TAG, "transactionRecord is null!");
                continue;
            }

            startPolling(transactionRecord.getTxID(), transactionRecord.getWalletAddress());
            CpLog.i(TAG, "restart polling for txId:" + transactionRecord.getTxID());
        }
    }

    public void startPolling(String txId, String walletAddress) {
        if (TextUtils.isEmpty(txId) || TextUtils.isEmpty(walletAddress)) {
            CpLog.e(TAG, "txId or walletAddress is null!");
            return;
        }

        ImpUpdateTxStateCallback impUpdateTxStateCallback = new ImpUpdateTxStateCallback(txId);
        ScheduledFuture updateTxStateSF = TaskController.getInstance().schedule(new UpdateTxState
                (txId, walletAddress, impUpdateTxStateCallback), 0, Constant.TX_POLLING_TIME);
        impUpdateTxStateCallback.setScheduledFuture(updateTxStateSF);
    }

}
