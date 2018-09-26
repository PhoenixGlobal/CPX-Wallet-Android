package chinapex.com.wallet.executor.runnable;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateNeoTxStateCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/13 0013 13:10.
 * E-Mail：liuyi_61@163.com
 */

public class CheckIsUpdateNeoTxState implements Runnable {

    private static final String TAG = CheckIsUpdateNeoTxState.class.getSimpleName();

    private ICheckIsUpdateNeoTxStateCallback mICheckIsUpdateNeoTxStateCallback;

    public CheckIsUpdateNeoTxState(ICheckIsUpdateNeoTxStateCallback ICheckIsUpdateNeoTxStateCallback) {
        mICheckIsUpdateNeoTxStateCallback = ICheckIsUpdateNeoTxStateCallback;
    }

    @Override
    public void run() {
        if (null == mICheckIsUpdateNeoTxStateCallback) {
            CpLog.e(TAG, "mICheckIsUpdateNeoTxStateCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<TransactionRecord> needUpdateStateTxs = new ArrayList<>();

        List<TransactionRecord> packagingTxs = apexWalletDbDao.queryTxByState(Constant
                .TABLE_NEO_TX_CACHE, Constant.TRANSACTION_STATE_PACKAGING);
        if (null != packagingTxs && !packagingTxs.isEmpty()) {
            needUpdateStateTxs.addAll(packagingTxs);
        }

        List<TransactionRecord> confirmingTxs = apexWalletDbDao.queryTxByState(Constant
                .TABLE_NEO_TRANSACTION_RECORD, Constant.TRANSACTION_STATE_CONFIRMING);
        if (null != confirmingTxs && !confirmingTxs.isEmpty()) {
            needUpdateStateTxs.addAll(confirmingTxs);
        }

        mICheckIsUpdateNeoTxStateCallback.checkIsUpdateNeoTxState(needUpdateStateTxs);
    }
}
