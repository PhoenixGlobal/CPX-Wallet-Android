package chinapex.com.wallet.presenter.transfer;

import chinapex.com.wallet.bean.gasfee.ITxFee;
import chinapex.com.wallet.bean.tx.ITxBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.transfer.CreateEthTxModel;
import chinapex.com.wallet.model.transfer.CreateNeoTxModel;
import chinapex.com.wallet.model.transfer.ICreateTxModel;
import chinapex.com.wallet.model.transfer.ICreateTxModelCallback;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.assets.ICreateTxView;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:15.
 * E-Mail：liuyi_61@163.com
 */
public class CreateTxPresenter implements ICreateTxPresenter, ICreateTxModelCallback {

    private static final String TAG = CreateTxPresenter.class.getSimpleName();

    private ICreateTxView mICreateTxView;
    private ICreateTxModel mICreateTxModel = new CreateEthTxModel(this);

    public CreateTxPresenter(ICreateTxView ICreateTxView) {
        mICreateTxView = ICreateTxView;
    }

    @Override
    public void init(int walletType) {
        switch (walletType) {
            case Constant.WALLET_TYPE_NEO:
                mICreateTxModel = new CreateNeoTxModel(this);
                break;
            case Constant.WALLET_TYPE_ETH:
                mICreateTxModel = new CreateEthTxModel(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void checkTxFee(ITxFee iTxFee) {
        mICreateTxModel.checkTxFee(iTxFee);
    }

    @Override
    public void createGlobalTx(ITxBean iTxBean) {
        mICreateTxModel.createGlobalTx(iTxBean);
    }

    @Override
    public void createColorTx(ITxBean iTxBean) {
        mICreateTxModel.createColorTx(iTxBean);
    }

    @Override
    public void checkTxFee(boolean isEnough, String msg) {
        mICreateTxView.checkTxFee(isEnough, msg);
    }

    @Override
    public void CreateTxModel(String toastMsg, boolean isFinish) {
        if (null == mICreateTxView) {
            CpLog.e(TAG, "mICreateTxView is null!");
            return;
        }

        mICreateTxView.createTxMsg(toastMsg, isFinish);
    }

}
