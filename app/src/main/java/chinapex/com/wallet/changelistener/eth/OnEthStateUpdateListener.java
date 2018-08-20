package chinapex.com.wallet.changelistener.eth;

import chinapex.com.wallet.bean.eth.EthWallet;

/**
 * Created by SteelCabbage on 2018/6/13 0013 16:37.
 * E-Mail：liuyi_61@163.com
 */

public interface OnEthStateUpdateListener {
    void onEthStateUpdate(EthWallet ethWallet);
}
