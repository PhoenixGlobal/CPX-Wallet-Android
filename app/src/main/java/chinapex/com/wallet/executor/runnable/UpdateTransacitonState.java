package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetRawTransaction;
import chinapex.com.wallet.bean.response.ResponseGetRawTransaction;
import chinapex.com.wallet.executor.callback.IUpdateTransacitonStateCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/6/28 0028 16:58.
 * E-Mail：liuyi_61@163.com
 */

public class UpdateTransacitonState implements Runnable, INetCallback {

    private static final String TAG = UpdateTransacitonState.class.getSimpleName();
    private String mTxId;
    private IUpdateTransacitonStateCallback mIUpdateTransacitonStateCallback;

    public UpdateTransacitonState(String txId, IUpdateTransacitonStateCallback
            IUpdateTransacitonStateCallback) {
        mTxId = txId;
        mIUpdateTransacitonStateCallback = IUpdateTransacitonStateCallback;
    }

    @Override
    public void run() {
        if (null == mIUpdateTransacitonStateCallback || TextUtils.isEmpty(mTxId)) {
            CpLog.e(TAG, "mIUpdateTransacitonStateCallback or mTxId is null!");
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

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                (requestGetRawTransaction), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetRawTransaction responseGetRawTransaction = GsonUtils.json2Bean(result,
                ResponseGetRawTransaction.class);
        if (null == responseGetRawTransaction) {
            CpLog.e(TAG, "responseGetRawTransaction is null!");
            mIUpdateTransacitonStateCallback.updateTransacitonState(Constant.TX_CONFIRM_EXCEPTION);
            return;
        }

        ResponseGetRawTransaction.ResultBean resultBean = responseGetRawTransaction.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean");
            mIUpdateTransacitonStateCallback.updateTransacitonState(Constant.TX_CONFIRM_EXCEPTION);
            return;
        }

        long confirmations = resultBean.getConfirmations();
        CpLog.w(TAG, "confirmations:" + confirmations);
        mIUpdateTransacitonStateCallback.updateTransacitonState(confirmations);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIUpdateTransacitonStateCallback.updateTransacitonState(Constant.TX_CONFIRM_EXCEPTION);
    }
}
