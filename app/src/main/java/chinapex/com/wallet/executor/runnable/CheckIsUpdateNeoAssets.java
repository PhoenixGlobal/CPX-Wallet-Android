package chinapex.com.wallet.executor.runnable;

import java.util.List;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.executor.callback.ICheckIsUpdateNeoAssetsCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/8 14:49
 * E-Mail：liuyi_61@163.com
 */
public class CheckIsUpdateNeoAssets implements Runnable {

    private static final String TAG = CheckIsUpdateNeoAssets.class.getSimpleName();

    private ICheckIsUpdateNeoAssetsCallback mICheckIsUpdateNeoAssetsCallback;

    public CheckIsUpdateNeoAssets(ICheckIsUpdateNeoAssetsCallback ICheckIsUpdateNeoAssetsCallback) {
        mICheckIsUpdateNeoAssetsCallback = ICheckIsUpdateNeoAssetsCallback;
    }

    @Override
    public void run() {
        if (null == mICheckIsUpdateNeoAssetsCallback) {
            CpLog.e(TAG, "mICheckIsUpdateNeoAssetsCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<AssetBean> assetBeans = apexWalletDbDao.queryAssetsByType(Constant.TABLE_NEO_ASSETS, Constant.ASSET_TYPE_NEP5);
        if (null == assetBeans || assetBeans.isEmpty()) {
            mICheckIsUpdateNeoAssetsCallback.checkIsUpdateNeoAssets(true);
            return;
        }

        CpLog.i(TAG, "no need to update neo assets!");
        mICheckIsUpdateNeoAssetsCallback.checkIsUpdateNeoAssets(false);
    }
}
