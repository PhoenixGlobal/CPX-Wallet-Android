package chinapex.com.wallet.executor.runnable.eth;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.executor.callback.eth.ICheckIsUpdateEthTxStateCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/13 0013 13:10.
 * E-Mail：liuyi_61@163.com
 */

public class CheckIsUpdateEthTxState implements Runnable {

    private static final String TAG = CheckIsUpdateEthTxState.class.getSimpleName();

    private ICheckIsUpdateEthTxStateCallback mICheckIsUpdateEthTxStateCallback;

    public CheckIsUpdateEthTxState(ICheckIsUpdateEthTxStateCallback ICheckIsUpdateEthTxStateCallback) {
        mICheckIsUpdateEthTxStateCallback = ICheckIsUpdateEthTxStateCallback;
    }

    @Override
    public void run() {
        if (null == mICheckIsUpdateEthTxStateCallback) {
            CpLog.e(TAG, "mICheckIsUpdateEthTxStateCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<TransactionRecord> needUpdateStateTxs = new ArrayList<>();

        List<TransactionRecord> packagingTxs = apexWalletDbDao.queryTxByState(Constant.TABLE_ETH_TX_CACHE,
                Constant.TRANSACTION_STATE_PACKAGING);
        if (null != packagingTxs && !packagingTxs.isEmpty()) {
            needUpdateStateTxs.addAll(packagingTxs);
        }

        List<TransactionRecord> confirmingTxs = apexWalletDbDao.queryTxByState(Constant.TABLE_ETH_TRANSACTION_RECORD,
                Constant.TRANSACTION_STATE_CONFIRMING);
        if (null != confirmingTxs && !confirmingTxs.isEmpty()) {
            needUpdateStateTxs.addAll(confirmingTxs);
        }

        mICheckIsUpdateEthTxStateCallback.checkIsUpdateEthTxState(needUpdateStateTxs);
    }
}
