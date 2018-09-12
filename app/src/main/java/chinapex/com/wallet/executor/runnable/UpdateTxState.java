package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetRawTransaction;
import chinapex.com.wallet.bean.response.ResponseGetRawTransaction;
import chinapex.com.wallet.executor.callback.IUpdateTxStateCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/6/28 0028 16:58.
 * E-Mail：liuyi_61@163.com
 */

public class UpdateTxState implements Runnable, INetCallback {

    private static final String TAG = UpdateTxState.class.getSimpleName();

    private String mTxId;
    private String mWalletAddress;
    private IUpdateTxStateCallback mIUpdateTxStateCallback;

    public UpdateTxState(String txId, String walletAddress, IUpdateTxStateCallback iUpdateTxStateCallback) {
        mTxId = txId;
        mWalletAddress = walletAddress;
        mIUpdateTxStateCallback = iUpdateTxStateCallback;
    }

    @Override
    public void run() {
        if (null == mIUpdateTxStateCallback
                || TextUtils.isEmpty(mTxId)
                || TextUtils.isEmpty(mWalletAddress)) {
            CpLog.e(TAG, "mIUpdateTxStateCallback or mTxId or mWalletAddress is null!");
            return;
        }

        RequestGetRawTransaction requestGetRawTransaction = new RequestGetRawTransaction();
        requestGetRawTransaction.setJsonrpc("2.0");
        requestGetRawTransaction.setMethod("getrawtransaction");
        ArrayList<String> params = new ArrayList<>();
        params.add(mTxId);
        params.add("1");
        requestGetRawTransaction.setParams(params);
        requestGetRawTransaction.setId(1);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI_NEO, GsonUtils.toJsonStr(requestGetRawTransaction), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetRawTransaction responseGetRawTransaction = GsonUtils.json2Bean(result, ResponseGetRawTransaction.class);
        if (null == responseGetRawTransaction) {
            CpLog.e(TAG, "responseGetRawTransaction is null!");
            mIUpdateTxStateCallback.updateTxState(mTxId, mWalletAddress, Constant.TX_CONFIRM_EXCEPTION);
            return;
        }

        ResponseGetRawTransaction.ResultBean resultBean = responseGetRawTransaction.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean is null!");
            mIUpdateTxStateCallback.updateTxState(mTxId, mWalletAddress, Constant.TX_CONFIRM_EXCEPTION);
            return;
        }

        long confirmations = resultBean.getConfirmations();
        CpLog.w(TAG, "confirmations:" + confirmations);
        mIUpdateTxStateCallback.updateTxState(mTxId, mWalletAddress, confirmations);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIUpdateTxStateCallback.updateTxState(mTxId, mWalletAddress, Constant.TX_CONFIRM_EXCEPTION);
    }
}
