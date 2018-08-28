package chinapex.com.wallet.executor.callback.eth;

import java.util.Map;

import chinapex.com.wallet.bean.BalanceBean;

/**
 * Created by SteelCabbage on 2018/8/28 0028 16:33.
 * E-Mail：liuyi_61@163.com
 */
public interface IGetEthBalanceCallback {
    void getEthBalance(Map<String, BalanceBean> balanceBeans);
}
