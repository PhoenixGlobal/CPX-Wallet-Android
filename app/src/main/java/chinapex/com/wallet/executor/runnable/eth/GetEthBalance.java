package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.request.RequestGetEthBalance;
import chinapex.com.wallet.bean.response.ResponseGetAccountState;
import chinapex.com.wallet.bean.response.ResponseGetEthBalance;
import chinapex.com.wallet.executor.callback.eth.IGetEthBalanceCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class GetEthBalance implements Runnable, INetCallback {

    private static final String TAG = GetEthBalance.class.getSimpleName();
    private String mAddress;
    private IGetEthBalanceCallback mIGetEthBalanceCallback;

    public GetEthBalance(String address, IGetEthBalanceCallback IGetEthBalanceCallback) {
        mAddress = address;
        mIGetEthBalanceCallback = IGetEthBalanceCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mIGetEthBalanceCallback) {
            CpLog.e(TAG, "run() -> mAddress or mIGetEthBalanceCallback is null！");
            return;
        }

        RequestGetEthBalance requestGetEthBalance = new RequestGetEthBalance();
        requestGetEthBalance.setJsonrpc("2.0");
        requestGetEthBalance.setMethod("eth_getBalance");
        requestGetEthBalance.setId(1);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mAddress);
        arrayList.add("latest");
        requestGetEthBalance.setParams(arrayList);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthBalance), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "result:" + result);
        ResponseGetEthBalance responseGetEthBalance = GsonUtils.json2Bean(result, ResponseGetEthBalance.class);
        if (null == responseGetEthBalance) {
            CpLog.e(TAG, "responseGetEthBalance is null!");
            mIGetEthBalanceCallback.getEthBalance(null);
            return;
        }

        String ethBalanceResult = responseGetEthBalance.getResult();
        if (TextUtils.isEmpty(ethBalanceResult)) {
            CpLog.e(TAG, "ethBalanceResult is null!");
            mIGetEthBalanceCallback.getEthBalance(null);
            return;
        }

        HashMap<String, BalanceBean> balanceBeans = new HashMap<>();
        BalanceBean balanceBean = new BalanceBean();
        balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
        balanceBean.setWalletType(Constant.WALLET_TYPE_ETH);
        balanceBean.setAssetsID(Constant.ASSETS_ETH);
        balanceBean.setAssetType(Constant.ASSET_TYPE_ETH);
        balanceBean.setAssetDecimal(18);
        balanceBean.setAssetsValue(ethBalanceResult);
        balanceBeans.put(Constant.ASSETS_ETH, balanceBean);

        mIGetEthBalanceCallback.getEthBalance(balanceBeans);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetEthBalanceCallback.getEthBalance(null);
    }
}
