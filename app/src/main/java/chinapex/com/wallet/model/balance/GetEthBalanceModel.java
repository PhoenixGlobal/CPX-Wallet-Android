package chinapex.com.wallet.model.balance;

import chinapex.com.wallet.bean.WalletBean;

/**
 * Created by SteelCabbage on 2018/8/17 0017 11:21.
 * E-Mail：liuyi_61@163.com
 */

public class GetEthBalanceModel implements IGetBalanceModel {
    private static final String TAG = GetEthBalanceModel.class.getSimpleName();
    private IGetBalanceModelCallback mIGetBalanceModelCallback;

    public GetEthBalanceModel(IGetBalanceModelCallback IGetBalanceModelCallback) {
        mIGetBalanceModelCallback = IGetBalanceModelCallback;
    }

    @Override
    public void init() {

    }

    @Override
    public void getGlobalAssetBalance(WalletBean walletBean) {

    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {

    }
}
