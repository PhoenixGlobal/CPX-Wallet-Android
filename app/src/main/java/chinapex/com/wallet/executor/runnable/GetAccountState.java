package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.request.RequestGetAccountState;
import chinapex.com.wallet.bean.response.ResponseGetAccountState;
import chinapex.com.wallet.executor.callback.IGetAccountStateCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class GetAccountState implements Runnable, INetCallback {

    private static final String TAG = GetAccountState.class.getSimpleName();
    private String mAddress;
    private IGetAccountStateCallback mIGetAccountStateCallback;

    public GetAccountState(String account, IGetAccountStateCallback iGetAccountStateCallback) {
        mAddress = account;
        mIGetAccountStateCallback = iGetAccountStateCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mIGetAccountStateCallback) {
            CpLog.e(TAG, "run() -> mAccount or mIGetAccountStateCallback is null！");
            return;
        }

        RequestGetAccountState requestGetAccountState = new RequestGetAccountState();
        requestGetAccountState.setJsonrpc("2.0");
        requestGetAccountState.setMethod("getaccountstate");
        requestGetAccountState.setId(1);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mAddress);
        requestGetAccountState.setParams(arrayList);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                (requestGetAccountState), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetAccountState responseGetAccountState = GsonUtils.json2Bean(result,
                ResponseGetAccountState.class);
        if (null == responseGetAccountState) {
            CpLog.e(TAG, "responseGetAccountState is null!");
            mIGetAccountStateCallback.assetsBalance(null);
            return;
        }

        ResponseGetAccountState.ResultBean resultBean = responseGetAccountState.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean is null!");
            mIGetAccountStateCallback.assetsBalance(null);
            return;
        }

        List<ResponseGetAccountState.ResultBean.BalancesBean> balances = resultBean.getBalances();
        if (null == balances || balances.isEmpty()) {
            CpLog.w(TAG, "balances is null or empty!");
            mIGetAccountStateCallback.assetsBalance(null);
            return;
        }

        HashMap<String, BalanceBean> balanceBeans = new HashMap<>();
        for (ResponseGetAccountState.ResultBean.BalancesBean balance : balances) {
            if (null == balance) {
                CpLog.e(TAG, "balance is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setAssetsID(balance.getAsset());
            balanceBean.setAssetType(Constant.ASSET_TYPE_GLOBAL);
            balanceBean.setAssetDecimal(8);
            balanceBean.setAssetsValue(balance.getValue());
            balanceBeans.put(balance.getAsset(), balanceBean);
        }

        mIGetAccountStateCallback.assetsBalance(balanceBeans);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetAccountStateCallback.assetsBalance(null);
    }
}
