package chinapex.com.wallet.presenter.balance;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.balance.GetEthBalanceModel;
import chinapex.com.wallet.model.balance.IGetBalanceModel;
import chinapex.com.wallet.model.balance.IGetBalanceModelCallback;
import chinapex.com.wallet.model.balance.GetNeoBalanceModel;
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
    private List<BalanceBean> mBalanceBeans;
    private int mAssetOrder;
    private static final int GLOBAL_ASSET_FIRST = 1;
    private static final int COLOR_ASSET_FIRST = 2;


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
        mBalanceBeans = new ArrayList<>();
    }

    @Override
    public void getAssetBalance(WalletBean walletBean) {
        if (null == mIGetBalanceModel) {
            CpLog.e(TAG, "getAssetBalance() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceModel.init();
        mAssetOrder = 0;
        mBalanceBeans.clear();
        getGlobalAssetBalance(walletBean);
        getColorAssetBalance(walletBean);
    }

    @Override
    public void getGlobalAssetBalance(WalletBean walletBean) {
        if (null == mIGetBalanceModel) {
            CpLog.e(TAG, "getColorAssetBalance() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceModel.getGlobalAssetBalance(walletBean);
    }

    @Override
    public synchronized void getGlobalBalanceModel(List<BalanceBean> balanceBeans) {
        if (null == mIGetBalanceView) {
            CpLog.e(TAG, "getBalanceModel() -> mIGetBalanceModel is null!");
            return;
        }

        if (mAssetOrder == 0) {
            mAssetOrder = GLOBAL_ASSET_FIRST;
            mBalanceBeans.addAll(balanceBeans);
            return;
        }

        if (mBalanceBeans.size() >= 1) {
            mBalanceBeans.addAll(1, balanceBeans);
        } else {
            mBalanceBeans.addAll(balanceBeans);
        }
        mIGetBalanceView.getAssetBalance(mBalanceBeans);
    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {
        if (null == mIGetBalanceModel) {
            CpLog.e(TAG, "getColorAssetBalance() -> mIGetBalanceModel is null!");
            return;
        }

        mIGetBalanceModel.getColorAssetBalance(walletBean);
    }

    @Override
    public synchronized void getColorBalanceModel(List<BalanceBean> balanceBeans) {
        if (null == mIGetBalanceView) {
            CpLog.e(TAG, "getBalanceModel() -> mIGetBalanceModel is null!");
            return;
        }

        if (mAssetOrder == 0) {
            mAssetOrder = COLOR_ASSET_FIRST;
            mBalanceBeans.addAll(balanceBeans);
            return;
        }

        if (mBalanceBeans.isEmpty()) {
            mBalanceBeans.addAll(balanceBeans);
        } else {
            for (BalanceBean balanceBean : balanceBeans) {
                if (null == balanceBean) {
                    CpLog.e(TAG, "balanceBean is null!");
                    continue;
                }

                if (Constant.ASSETS_CPX.equals(balanceBean.getAssetsID())) {
                    mBalanceBeans.add(0, balanceBean);
                } else {
                    mBalanceBeans.add(balanceBean);
                }
            }
        }

        mIGetBalanceView.getAssetBalance(mBalanceBeans);
    }

}
