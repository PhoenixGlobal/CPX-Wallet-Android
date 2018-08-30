package chinapex.com.wallet.executor.callback.eth;

import java.util.Map;

import chinapex.com.wallet.bean.BalanceBean;

/**
 * Created by SteelCabbage on 2018/7/2 0002 11:12.
 * E-Mail：liuyi_61@163.com
 */

public interface IGetErc20BalanceCallback {
    void getErc20Balance(Map<String, BalanceBean> balanceBeans);
}

