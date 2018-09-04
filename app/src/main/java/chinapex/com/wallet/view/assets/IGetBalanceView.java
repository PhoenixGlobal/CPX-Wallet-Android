package chinapex.com.wallet.view.assets;

import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;

/**
 * Created by SteelCabbage on 2018/8/17 0017 10:57.
 * E-Mail：liuyi_61@163.com
 */

public interface IGetBalanceView {
    void getGlobalAssetBalance(HashMap<String, BalanceBean> balanceBeans);

    void getColorAssetBalance(HashMap<String, BalanceBean> balanceBeans);
}
