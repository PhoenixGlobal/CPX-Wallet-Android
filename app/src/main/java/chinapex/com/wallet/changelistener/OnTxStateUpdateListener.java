package chinapex.com.wallet.changelistener;

/**
 * Created by SteelCabbage on 2018/6/29 0029 10:57.
 * E-Mail：liuyi_61@163.com
 */

public interface OnTxStateUpdateListener {
    void onTxStateUpdate(String txID, int state, long txTime);
}
