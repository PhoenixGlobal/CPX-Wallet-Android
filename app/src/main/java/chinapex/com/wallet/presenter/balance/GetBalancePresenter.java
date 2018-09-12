package chinapex.com.wallet.presenter.balance;

import java.util.HashMap;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.balance.GetEthBalanceModel;
import chinapex.com.wallet.model.balance.GetNeoBalanceModel;
import chinapex.com.wallet.model.balance.IGetBalanceModel;
import chinapex.com.wallet.model.balance.IGetBalanceModelCallback;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.assets.IGetBalanceView;

/**
 * Created by SteelCabbage on 2018/8/17 0017 10:55.
 * E-Mail：liuyi_61@163.com
 */

public class GetBalancePresenter implements IGetBalancePresenter, IGetBalanceModelCallback {

    private static final String TAG = GetBalancePresenter.class.getSimpleName();

    private IGetBalanceView mIGetBalanceView;
    private IGetBalanceModel mIGetBalanceModel;

    public GetBalancePresenter(IGetBalanceView IGetBalanceView) {
        mIGetBalanceView = IGetBalanceView;
    }

    @Override
    public void init(int walletType) {
        switch (walletType) {
            case Constant.WALLET_TYPE_NEO:
                mIGetBalanceModel = new GetNeoBalanceModel(this);
                break;
            case Constant.WALLET_TYPE_ETH:
                mIGetBalanceModel = new GetEthBalanceModel(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void getGlobalAssetBalance(WalletBean walletBean) {
        mIGetBalanceModel.init();
        if (null == mIGetBalanceModel) {
            CpLog.e(TAG, "getColorAssetBalance() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceModel.getGlobalAssetBalance(walletBean);
    }

    @Override
    public synchronized void getGlobalBalanceModel(HashMap<String, BalanceBean> balanceBeans) {
        if (null == mIGetBalanceView) {
            CpLog.e(TAG, "getBalanceModel() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceView.getGlobalAssetBalance(balanceBeans);
    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {
        mIGetBalanceModel.init();
        if (null == mIGetBalanceModel) {
            CpLog.e(TAG, "getColorAssetBalance() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceModel.getColorAssetBalance(walletBean);
    }

    @Override
    public synchronized void getColorBalanceModel(HashMap<String, BalanceBean> balanceBeans) {
        if (null == mIGetBalanceView) {
            CpLog.e(TAG, "getBalanceModel() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceView.getColorAssetBalance(balanceBeans);
    }

}
