package chinapex.com.wallet.executor.runnable.eth;

import java.util.List;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.response.ResponseGetAssets;
import chinapex.com.wallet.executor.callback.IGetNeoAssetsCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthAssetsCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/7/8 13:57
 * E-Mail：liuyi_61@163.com
 */
public class GetEthAssets implements Runnable/*, INetCallback*/ {
    private static final String TAG = GetEthAssets.class.getSimpleName();
    private IGetEthAssetsCallback mIGetEthAssetsCallback;

    public GetEthAssets(IGetEthAssetsCallback IGetEthAssetsCallback) {
        mIGetEthAssetsCallback = IGetEthAssetsCallback;
    }

    @Override
    public void run() {
        if (null == mIGetEthAssetsCallback) {
            CpLog.e(TAG, "mIGetEthAssetsCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        AssetBean assetBean = new AssetBean();
        assetBean.setType(Constant.ASSET_TYPE_ETH);
        assetBean.setSymbol("ETH");
        assetBean.setPrecision(18 + "");
        assetBean.setName("ETH");
        assetBean.setImageUrl("");
        assetBean.setHexHash(Constant.ASSETS_ETH);
        assetBean.setHash(Constant.ASSETS_ETH);

        apexWalletDbDao.insertAsset(Constant.TABLE_ETH_ASSETS, assetBean);

        mIGetEthAssetsCallback.getEthAssets(Constant.UPDATE_ASSETS_OK);

//        OkHttpClientManager.getInstance().get(Constant.URL_ASSETS, this);
    }

//    @Override
//    public void onSuccess(int statusCode, String msg, String result) {
//        ResponseGetAssets responseGetAssets = GsonUtils.json2Bean(result, ResponseGetAssets.class);
//        if (null == responseGetAssets) {
//            CpLog.e(TAG, "responseGetAssets is null!");
//            return;
//        }
//
//        List<ResponseGetAssets.ResultBean> resultBeans = responseGetAssets.getResult();
//        if (null == resultBeans || resultBeans.isEmpty()) {
//            CpLog.e(TAG, "resultBeans is null or empty!");
//            return;
//        }
//
//        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
//                .getInstance());
//        if (null == apexWalletDbDao) {
//            CpLog.e(TAG, "apexWalletDbDao is null!");
//            return;
//        }
//
//        for (ResponseGetAssets.ResultBean resultBean : resultBeans) {
//            if (null == resultBean) {
//                CpLog.e(TAG, "resultBean is null!");
//                continue;
//            }
//
//            AssetBean assetBean = new AssetBean();
//            assetBean.setType(resultBean.getType());
//            assetBean.setSymbol(resultBean.getSymbol());
//            assetBean.setPrecision(resultBean.getPrecision());
//            assetBean.setName(resultBean.getName());
//            assetBean.setImageUrl(resultBean.getImage_url());
//            assetBean.setHexHash(resultBean.getHex_hash());
//            assetBean.setHash(resultBean.getHash());
//
//            apexWalletDbDao.insertAsset(assetBean);
//        }
//
//        mIGetNeoAssetsCallback.getNeoAssets(Constant.UPDATE_ASSETS_OK);
//    }
//
//    @Override
//    public void onFailed(int failedCode, String msg) {
//        CpLog.e(TAG, "onFailed() -> msg:" + msg);
//    }
}
