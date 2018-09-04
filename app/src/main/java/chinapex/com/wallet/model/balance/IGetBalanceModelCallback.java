package chinapex.com.wallet.model.balance;

import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;

/**
 * Created by SteelCabbage on 2018/8/17 0017 11:42.
 * E-Mail：liuyi_61@163.com
 */

public interface IGetBalanceModelCallback {
    void getGlobalBalanceModel(HashMap<String, BalanceBean> balanceBeans);

    void getColorBalanceModel(HashMap<String, BalanceBean> balanceBeans);
}
