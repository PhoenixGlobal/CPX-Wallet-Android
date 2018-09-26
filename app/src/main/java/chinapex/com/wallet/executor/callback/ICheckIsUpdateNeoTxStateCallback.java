package chinapex.com.wallet.executor.callback;

import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;

/**
 * Created by SteelCabbage on 2018/7/13 0013 13:11.
 * E-Mail：liuyi_61@163.com
 */

public interface ICheckIsUpdateNeoTxStateCallback {
    void checkIsUpdateNeoTxState(List<TransactionRecord> transactionRecords);
}
