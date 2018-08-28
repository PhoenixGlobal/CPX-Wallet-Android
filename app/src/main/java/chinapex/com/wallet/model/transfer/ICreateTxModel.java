package chinapex.com.wallet.model.transfer;

import chinapex.com.wallet.bean.tx.ITxBean;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:48.
 * E-Mail：liuyi_61@163.com
 */
public interface ICreateTxModel {
    void createGlobalTx(ITxBean iTxBean);

    void createColorTx(ITxBean iTxBean);
}
