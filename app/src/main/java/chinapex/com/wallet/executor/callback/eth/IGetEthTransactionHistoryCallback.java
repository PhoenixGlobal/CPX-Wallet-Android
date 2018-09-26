package chinapex.com.wallet.executor.callback.eth;

import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;

/**
 * Created by SteelCabbage on 2018/9/19 0019 15:26.
 * E-Mail：liuyi_61@163.com
 */
public interface IGetEthTransactionHistoryCallback {
    void getEthTransactionHistory(List<TransactionRecord> transactionRecords);
}
