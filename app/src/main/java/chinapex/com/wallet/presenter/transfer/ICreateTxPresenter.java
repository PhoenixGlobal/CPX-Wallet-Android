package chinapex.com.wallet.presenter.transfer;

import chinapex.com.wallet.bean.tx.ITxBean;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:15.
 * E-Mail：liuyi_61@163.com
 */
public interface ICreateTxPresenter {
    void init(int walletType);

    void createGlobalTx(ITxBean iTxBean);

    void createColorTx(ITxBean iTxBean);
}
