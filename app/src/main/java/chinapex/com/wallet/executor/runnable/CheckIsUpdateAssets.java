package chinapex.com.wallet.executor.runnable;

import java.util.List;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateAssetsCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/8 14:49
 * E-Mail：liuyi_61@163.com
 */
public class CheckIsUpdateAssets implements Runnable {
    private static final String TAG = CheckIsUpdateAssets.class.getSimpleName();
    private ICheckIsUpdateAssetsCallback mICheckIsUpdateAssetsCallback;

    public CheckIsUpdateAssets(ICheckIsUpdateAssetsCallback ICheckIsUpdateAssetsCallback) {
        mICheckIsUpdateAssetsCallback = ICheckIsUpdateAssetsCallback;
    }

    @Override
    public void run() {
        if (null == mICheckIsUpdateAssetsCallback) {
            CpLog.e(TAG, "mICheckIsUpdateAssetsCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<AssetBean> assetBeans = apexWalletDbDao.queryAssetsByType(Constant.ASSET_TYPE_NEP5);
        if (null == assetBeans || assetBeans.isEmpty()) {
            mICheckIsUpdateAssetsCallback.checkIsUpdateAssets(true);
            return;
        }

        CpLog.i(TAG, "no need to update assets!");
        mICheckIsUpdateAssetsCallback.checkIsUpdateAssets(false);
    }
}
