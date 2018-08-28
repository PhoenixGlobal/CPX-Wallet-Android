package chinapex.com.wallet.model.transfer;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.tx.ITxBean;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:56.
 * E-Mail：liuyi_61@163.com
 */
public class CreateEthTxModel implements ICreateTxModel {
    private static final String TAG = CreateEthTxModel.class.getSimpleName();
    private ICreateTxModelCallback mICreateTxModelCallback;

    public CreateEthTxModel(ICreateTxModelCallback ICreateTxModelCallback) {
        mICreateTxModelCallback = ICreateTxModelCallback;
    }

    @Override
    public void createGlobalTx(ITxBean iTxBean) {

    }

    @Override
    public void createColorTx(ITxBean iTxBean) {

    }
}
