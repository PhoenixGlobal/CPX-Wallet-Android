package chinapex.com.wallet.executor.callback;

import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;

/**
 * Created by SteelCabbage on 2018/6/7 23:44
 * E-Mail：liuyi_61@163.com
 */
public interface IGetAccountStateCallback {
    void assetsBalance(List<BalanceBean> balanceBeans);
}
