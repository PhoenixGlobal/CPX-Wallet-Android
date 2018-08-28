package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.request.RequestGetNep5Balance;
import chinapex.com.wallet.bean.request.RequestGetNep5BalanceSub;
import chinapex.com.wallet.bean.response.ResponseGetNep5Balance;
import chinapex.com.wallet.executor.callback.IGetNep5BalanceCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.PhoneUtils;
import neomobile.Neomobile;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class GetNep5Balance implements Runnable, INetCallback {

    private static final String TAG = GetNep5Balance.class.getSimpleName();
    private String mAssetID;
    private String mAddress;
    private IGetNep5BalanceCallback mIGetNep5BalanceCallback;

    public GetNep5Balance(String assetID, String address, IGetNep5BalanceCallback
            IGetNep5BalanceCallback) {
        mAssetID = assetID;
        mAddress = address;
        mIGetNep5BalanceCallback = IGetNep5BalanceCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAssetID)
                || TextUtils.isEmpty(mAddress)
                || null == mIGetNep5BalanceCallback) {
            CpLog.e(TAG, "mAssetID or mAddress or mIGetNep5BalanceCallback is null！");
            return;
        }

        String decodeAddress = null;
        try {
            decodeAddress = Neomobile.decodeAddress(mAddress);
        } catch (Exception e) {
            CpLog.e(TAG, "decodeAddress exception: " + e.getMessage());
        }

        if (TextUtils.isEmpty(decodeAddress)) {
            CpLog.e(TAG, "decodeAddress is null or empty!");
            return;
        }

        RequestGetNep5Balance requestGetNep5Balance = new RequestGetNep5Balance();
        requestGetNep5Balance.setJsonrpc("2.0");
        requestGetNep5Balance.setMethod("invokefunction");
        requestGetNep5Balance.setId(3);

        ArrayList<Object> params = new ArrayList<>();
        params.add(mAssetID);
        params.add("balanceOf");
        ArrayList<RequestGetNep5BalanceSub> paramsSub = new ArrayList<>();
        RequestGetNep5BalanceSub requestGetNep5BalanceSub = new RequestGetNep5BalanceSub();
        requestGetNep5BalanceSub.setType("Hash160");
        requestGetNep5BalanceSub.setValue(decodeAddress);
        paramsSub.add(requestGetNep5BalanceSub);
        params.add(paramsSub);

        requestGetNep5Balance.setParams(params);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI_NEO, GsonUtils.toJsonStr(requestGetNep5Balance), this);

    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        HashMap<String, BalanceBean> balanceBeans = new HashMap<>();

        ResponseGetNep5Balance responseGetNep5Balance = GsonUtils.json2Bean(result,
                ResponseGetNep5Balance.class);
        if (null == responseGetNep5Balance) {
            CpLog.e(TAG, "responseGetNep5Balance is null!");
            balanceBeans.put(mAssetID, null);
            mIGetNep5BalanceCallback.getNep5Balance(balanceBeans);
            return;
        }

        ResponseGetNep5Balance.ResultBean resultBean = responseGetNep5Balance.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean is null!");
            balanceBeans.put(mAssetID, null);
            mIGetNep5BalanceCallback.getNep5Balance(balanceBeans);
            return;
        }

        List<ResponseGetNep5Balance.ResultBean.StackBean> stackBeans = resultBean.getStack();
        if (null == stackBeans || stackBeans.isEmpty()) {
            CpLog.e(TAG, "stackBeans is null or empty!");
            balanceBeans.put(mAssetID, null);
            mIGetNep5BalanceCallback.getNep5Balance(balanceBeans);
            return;
        }

        for (ResponseGetNep5Balance.ResultBean.StackBean stackBean : stackBeans) {
            if (null == stackBean) {
                CpLog.e(TAG, "stackBean is null!");
                continue;
            }

            String stackBeanValue = stackBean.getValue();
            if (TextUtils.isEmpty(stackBeanValue)) {
                CpLog.w(TAG, "stackBeanValue is null!");
                stackBeanValue = "0";
            }

            BigInteger reverseArray = new BigInteger(PhoneUtils.reverseArray(stackBeanValue));
            BigDecimal pow = new BigDecimal(10).pow(Integer.parseInt("8"));
            BigDecimal value = null;
            try {
                value = new BigDecimal(reverseArray).divide(pow);
            } catch (Exception e) {
                CpLog.e(TAG, e.getMessage());
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setWalletType(Constant.WALLET_TYPE_NEO);
            balanceBean.setAssetsID(mAssetID);
            balanceBean.setAssetType(Constant.ASSET_TYPE_NEP5);
            balanceBean.setAssetDecimal(8);
            balanceBean.setAssetsValue(null == value ? "0" : value.toPlainString());
            balanceBeans.put(mAssetID, balanceBean);
        }

        mIGetNep5BalanceCallback.getNep5Balance(balanceBeans);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        HashMap<String, BalanceBean> balanceBeans = new HashMap<>();
        balanceBeans.put(mAssetID, null);
        mIGetNep5BalanceCallback.getNep5Balance(balanceBeans);
    }
}
