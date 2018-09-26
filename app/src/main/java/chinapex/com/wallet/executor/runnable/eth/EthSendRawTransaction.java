package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthRpc;
import chinapex.com.wallet.bean.response.ResponseGetEthRpcResult;
import chinapex.com.wallet.executor.callback.eth.IEthSendRawTransactionCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/9/7 0007 16:26.
 * E-Mail：liuyi_61@163.com
 */
public class EthSendRawTransaction implements Runnable, INetCallback {

    private static final String TAG = EthSendRawTransaction.class.getSimpleName();

    private String mEthTxData;
    private IEthSendRawTransactionCallback mIEthSendRawTransactionCallback;

    public EthSendRawTransaction(String ethTxData, IEthSendRawTransactionCallback IEthSendRawTransactionCallback) {
        mEthTxData = ethTxData;
        mIEthSendRawTransactionCallback = IEthSendRawTransactionCallback;
    }

    @Override
    public void run() {
        if (null == mIEthSendRawTransactionCallback) {
            CpLog.e(TAG, "mIEthSendRawTransactionCallback is null!");
            return;
        }

        if (TextUtils.isEmpty(mEthTxData)) {
            CpLog.e(TAG, "mEthTxData is null!");
            mIEthSendRawTransactionCallback.ethSendRawTransaction(false, null);
            return;
        }

        RequestGetEthRpc requestGetEthRpc = new RequestGetEthRpc();
        requestGetEthRpc.setJsonrpc("2.0");
        requestGetEthRpc.setMethod("eth_sendRawTransaction");
        requestGetEthRpc.setId(1);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mEthTxData);
        requestGetEthRpc.setParams(arrayList);

        OkHttpClientManager.getInstance().postJsonByAuth(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthRpc), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "EthSendRawTransaction onSuccess:" + result);
        ResponseGetEthRpcResult responseGetEthRpcResult = GsonUtils.json2Bean(result, ResponseGetEthRpcResult.class);
        if (null == responseGetEthRpcResult) {
            CpLog.e(TAG, "responseGetEthRpcResult is null!");
            mIEthSendRawTransactionCallback.ethSendRawTransaction(true, null);
            return;
        }

        String txId = responseGetEthRpcResult.getResult();
        if (TextUtils.isEmpty(txId)) {
            CpLog.e(TAG, "txId is null!");
            mIEthSendRawTransactionCallback.ethSendRawTransaction(true, null);
            return;
        }

        CpLog.i(TAG, "txId is:" + txId);
        mIEthSendRawTransactionCallback.ethSendRawTransaction(true, txId);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIEthSendRawTransactionCallback.ethSendRawTransaction(false, null);
    }
}
