package chinapex.com.wallet.executor.runnable;

import java.util.List;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.response.ResponseGetNeoAssets;
import chinapex.com.wallet.executor.callback.IGetNeoAssetsCallback;
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
public class GetNeoAssets implements Runnable, INetCallback {

    private static final String TAG = GetNeoAssets.class.getSimpleName();

    private IGetNeoAssetsCallback mIGetNeoAssetsCallback;

    public GetNeoAssets(IGetNeoAssetsCallback IGetNeoAssetsCallback) {
        mIGetNeoAssetsCallback = IGetNeoAssetsCallback;
    }

    @Override
    public void run() {
        if (null == mIGetNeoAssetsCallback) {
            CpLog.e(TAG, "mIGetNeoAssetsCallback is null!");
            return;
        }

        OkHttpClientManager.getInstance().get(Constant.URL_ASSETS_NEO, this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetNeoAssets responseGetNeoAssets = GsonUtils.json2Bean(result, ResponseGetNeoAssets.class);
        if (null == responseGetNeoAssets) {
            CpLog.e(TAG, "responseGetNeoAssets is null!");
            mIGetNeoAssetsCallback.getNeoAssets(null);
            return;
        }

        List<ResponseGetNeoAssets.DataBean> dataBeans = responseGetNeoAssets.getData();
        if (null == dataBeans || dataBeans.isEmpty()) {
            CpLog.e(TAG, "dataBeans is null or empty!");
            mIGetNeoAssetsCallback.getNeoAssets(null);
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mIGetNeoAssetsCallback.getNeoAssets(null);
            return;
        }

        for (ResponseGetNeoAssets.DataBean dataBean : dataBeans) {
            if (null == dataBean) {
                CpLog.e(TAG, "dataBean is null!");
                continue;
            }

            AssetBean assetBean = new AssetBean();
            String assetType = dataBean.getType();
            if (Constant.ASSET_TYPE_GOVERNING.equals(assetType) || Constant.ASSET_TYPE_UTILITY.equals(assetType)) {
                assetBean.setType(Constant.ASSET_TYPE_GLOBAL);
            } else {
                assetBean.setType(assetType);
            }

            assetBean.setSymbol(dataBean.getSymbol());
            assetBean.setPrecision(dataBean.getPrecision());
            assetBean.setName(dataBean.getName());
            assetBean.setImageUrl(dataBean.getImage_url());
            assetBean.setHexHash(dataBean.getHex_hash());
            assetBean.setHash(dataBean.getHash());

            apexWalletDbDao.insertAsset(Constant.TABLE_NEO_ASSETS, assetBean);
        }

        mIGetNeoAssetsCallback.getNeoAssets(Constant.UPDATE_ASSETS_OK);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetNeoAssetsCallback.getNeoAssets(null);
    }
}
