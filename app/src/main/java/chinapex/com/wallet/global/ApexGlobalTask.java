package chinapex.com.wallet.global;

import android.os.SystemClock;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateAssetsCallback;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateTxStateCallback;
import chinapex.com.wallet.executor.callback.IGetAssetsCallback;
import chinapex.com.wallet.executor.runnable.CheckIsUpdateAssets;
import chinapex.com.wallet.executor.runnable.CheckIsUpdateTxState;
import chinapex.com.wallet.executor.runnable.GetAssets;
import chinapex.com.wallet.executor.runnable.UpdateTxState;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/6/10 15:23
 * E-Mail：liuyi_61@163.com
 */
public class ApexGlobalTask implements ICheckIsUpdateAssetsCallback, IGetAssetsCallback,
        ICheckIsUpdateTxStateCallback {

    private static final String TAG = ApexGlobalTask.class.getSimpleName();
    private ScheduledFuture mCheckIsUpdateAssetsSF;

    private ApexGlobalTask() {

    }

    private static class ApexCacheHolder {
        private static final ApexGlobalTask sApexGlobalTask = new ApexGlobalTask();
    }

    public static ApexGlobalTask getInstance() {
        return ApexCacheHolder.sApexGlobalTask;
    }

    public void doInit() {
        TaskController.getInstance().submit(new CheckIsUpdateAssets(this));
        TaskController.getInstance().submit(new CheckIsUpdateTxState(this));
    }

    @Override
    public void checkIsUpdateAssets(boolean isUpdate) {
        if (isUpdate) {
            CpLog.i(TAG, "need to update assets!");
            mCheckIsUpdateAssetsSF = TaskController.getInstance().schedule(new
                    GetAssets(this), 0, Constant.ASSETS_POLLING_TIME);
        }
    }

    @Override
    public void getAssets(String msg) {
        if (TextUtils.isEmpty(msg)) {
            CpLog.e(TAG, "getAssets() -> msg is null!");
            return;
        }

        if (Constant.UPDATE_ASSETS_OK.equals(msg)) {
            CpLog.i(TAG, "update assets ok!");
            mCheckIsUpdateAssetsSF.cancel(true);
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
