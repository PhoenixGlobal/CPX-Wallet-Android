package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.request.RequestErc20Params;
import chinapex.com.wallet.bean.request.RequestGetErc20Balance;
import chinapex.com.wallet.bean.response.ResponseGetEthBalance;
import chinapex.com.wallet.executor.callback.eth.IGetErc20BalanceCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import ethmobile.EthCall;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class GetErc20Balance implements Runnable, INetCallback {

    private static final String TAG = GetErc20Balance.class.getSimpleName();
    private String mAssetID;
    private String mAddress;
    private IGetErc20BalanceCallback mIGetErc20BalanceCallback;

    public GetErc20Balance(String assetID, String address, IGetErc20BalanceCallback IGetErc20BalanceCallback) {
        mAssetID = assetID;
        mAddress = address;
        mIGetErc20BalanceCallback = IGetErc20BalanceCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAssetID) || TextUtils.isEmpty(mAddress) || null == mIGetErc20BalanceCallback) {
            CpLog.e(TAG, "mAssetID or mAddress or mIGetErc20BalanceCallback is null!");
            return;
        }

        EthCall ethCall = new EthCall();
        String data = null;
        try {
            data = ethCall.balanceOf(mAssetID, mAddress);
        } catch (Exception e) {
            CpLog.e(TAG, "ethCall.balanceOf exception:" + e.getMessage());
        }

        if (TextUtils.isEmpty(data)) {
            CpLog.e(TAG, "ethCall.balanceOf data is null!");
            mIGetErc20BalanceCallback.getErc20Balance(null);
            return;
        }

        RequestGetErc20Balance requestGetErc20Balance = new RequestGetErc20Balance();
        requestGetErc20Balance.setJsonrpc("2.0");
        requestGetErc20Balance.setMethod("eth_call");
        requestGetErc20Balance.setId(1);
        ArrayList<Object> arrayList = new ArrayList<>();
        RequestErc20Params requestErc20Params = GsonUtils.json2Bean(data, RequestErc20Params.class);
        arrayList.add(requestErc20Params);
        arrayList.add("latest");
        requestGetErc20Balance.setParams(arrayList);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetErc20Balance), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "result:" + result);
        ResponseGetEthBalance responseGetErc20Balance = GsonUtils.json2Bean(result, ResponseGetEthBalance.class);
        if (null == responseGetErc20Balance) {
            CpLog.e(TAG, "responseGetErc20Balance is null!");
            mIGetErc20BalanceCallback.getErc20Balance(null);
            return;
        }

        String erc20BalanceValue = responseGetErc20Balance.getResult();
        if (TextUtils.isEmpty(erc20BalanceValue)) {
            CpLog.e(TAG, "erc20BalanceValue is null!");
            mIGetErc20BalanceCallback.getErc20Balance(null);
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mIGetErc20BalanceCallback.getErc20Balance(null);
            return;
        }

        AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_ETH_ASSETS, mAssetID);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            mIGetErc20BalanceCallback.getErc20Balance(null);
            return;
        }

        HashMap<String, BalanceBean> erc20Balance = new HashMap<>();

        BalanceBean balanceBean = new BalanceBean();
        balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
        balanceBean.setWalletType(Constant.WALLET_TYPE_ETH);
        balanceBean.setAssetsID(mAssetID);
        balanceBean.setAssetSymbol(assetBean.getSymbol());
        balanceBean.setAssetType(Constant.ASSET_TYPE_ERC20);
        balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
        balanceBean.setAssetsValue(erc20BalanceValue);

        erc20Balance.put(mAssetID, balanceBean);
        mIGetErc20BalanceCallback.getErc20Balance(erc20Balance);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetErc20BalanceCallback.getErc20Balance(null);
    }
}
