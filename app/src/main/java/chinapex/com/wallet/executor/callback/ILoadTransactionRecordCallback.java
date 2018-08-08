package chinapex.com.wallet.executor.callback;

import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;

/**
 * Created by SteelCabbage on 2018/6/28 0028 10:22.
 * E-Mail：liuyi_61@163.com
 */

public interface ILoadTransactionRecordCallback {
    void loadTransactionRecord(List<TransactionRecord> transactionRecords);
}
