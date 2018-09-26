package chinapex.com.wallet.executor.callback;

/**
 * Created by SteelCabbage on 2018/6/28 0028 17:01.
 * E-Mail：liuyi_61@163.com
 */

public interface IGetRawTransactionCallback {
    void getRawTransaction(String txId, String walletAddress, long confirmations);
}
