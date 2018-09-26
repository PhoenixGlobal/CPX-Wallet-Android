package chinapex.com.wallet.executor.callback;

import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;

/**
 * Created by SteelCabbage on 2018/6/22 0022 11:45.
 * E-Mail：liuyi_61@163.com
 */

public interface IGetNeoTransactionHistoryCallback {
    void getNeoTransactionHistory(List<TransactionRecord> transactionRecords);
}
