package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthRpc;
import chinapex.com.wallet.bean.response.ResponseGetEthRpcResult;
import chinapex.com.wallet.executor.callback.eth.IGetEthNonceCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/9/6 0006 17:30.
 * E-Mail：liuyi_61@163.com
 */
public class GetEthNonce implements Runnable, INetCallback {

    private static final String TAG = GetEthNonce.class.getSimpleName();

    private String mAddress;
    private IGetEthNonceCallback mIGetEthNonceCallback;

    public GetEthNonce(String address, IGetEthNonceCallback IGetEthNonceCallback) {
        mAddress = address;
        mIGetEthNonceCallback = IGetEthNonceCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mIGetEthNonceCallback) {
            CpLog.e(TAG, "mAddress or mIGetEthNonceCallback is null!");
            return;
        }

        RequestGetEthRpc requestGetEthRpc = new RequestGetEthRpc();
        requestGetEthRpc.setJsonrpc("2.0");
        requestGetEthRpc.setMethod("eth_getTransactionCount");
        requestGetEthRpc.setId(1);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mAddress);
        arrayList.add("latest");
        requestGetEthRpc.setParams(arrayList);

        OkHttpClientManager.getInstance().postJsonByAuth(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthRpc), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetEthRpcResult responseGetEthRpcResult = GsonUtils.json2Bean(result, ResponseGetEthRpcResult.class);
        if (null == responseGetEthRpcResult) {
            CpLog.e(TAG, "responseGetEthRpcResult is null!");
            mIGetEthNonceCallback.getEthNonce(null);
            return;
        }

        String nonce = responseGetEthRpcResult.getResult();
        if (TextUtils.isEmpty(nonce)) {
            CpLog.e(TAG, "nonce is null!");
            mIGetEthNonceCallback.getEthNonce(null);
            return;
        }

        CpLog.i(TAG, "nonce is:" + nonce);
        mIGetEthNonceCallback.getEthNonce(nonce);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetEthNonceCallback.getEthNonce(null);
    }
}
