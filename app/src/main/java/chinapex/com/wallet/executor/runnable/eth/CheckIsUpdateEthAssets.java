package chinapex.com.wallet.executor.runnable.eth;

import java.util.List;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.executor.callback.eth.ICheckIsUpdateEthAssetsCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/8 14:49
 * E-Mail：liuyi_61@163.com
 */
public class CheckIsUpdateEthAssets implements Runnable {

    private static final String TAG = CheckIsUpdateEthAssets.class.getSimpleName();

    private ICheckIsUpdateEthAssetsCallback mICheckIsUpdateEthAssetsCallback;

    public CheckIsUpdateEthAssets(ICheckIsUpdateEthAssetsCallback ICheckIsUpdateEthAssetsCallback) {
        mICheckIsUpdateEthAssetsCallback = ICheckIsUpdateEthAssetsCallback;
    }

    @Override
    public void run() {
        if (null == mICheckIsUpdateEthAssetsCallback) {
            CpLog.e(TAG, "mICheckIsUpdateEthAssetsCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<AssetBean> assetBeans = apexWalletDbDao.queryAssetsByType(Constant.TABLE_ETH_ASSETS, Constant.ASSET_TYPE_ERC20);
        if (null == assetBeans || assetBeans.isEmpty()) {
            mICheckIsUpdateEthAssetsCallback.checkIsUpdateEthAssets(true);
            return;
        }

        CpLog.i(TAG, "no need to update eth assets!");
        mICheckIsUpdateEthAssetsCallback.checkIsUpdateEthAssets(false);
    }
}
