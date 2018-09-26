package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthRpc;
import chinapex.com.wallet.bean.response.ResponseGetEthRpcResult;
import chinapex.com.wallet.executor.callback.eth.IGetEthBlockNumberCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.WalletUtils;

/**
 * Created by SteelCabbage on 2018/9/20 0020 13:29.
 * E-Mail：liuyi_61@163.com
 */
public class GetEthBlockNumber implements Runnable, INetCallback {
    private static final String TAG = GetEthBlockNumber.class.getSimpleName();
    private IGetEthBlockNumberCallback mIGetEthBlockNumberCallback;

    public GetEthBlockNumber(IGetEthBlockNumberCallback IGetEthBlockNumberCallback) {
        mIGetEthBlockNumberCallback = IGetEthBlockNumberCallback;
    }

    @Override
    public void run() {
        if (null == mIGetEthBlockNumberCallback) {
            CpLog.e(TAG, "mIGetEthBlockNumberCallback is null!");
            return;
        }

        RequestGetEthRpc requestGetEthRpc = new RequestGetEthRpc();
        requestGetEthRpc.setJsonrpc("2.0");
        requestGetEthRpc.setMethod("eth_blockNumber");
        requestGetEthRpc.setId(83);
        requestGetEthRpc.setParams(new ArrayList<String>());

        OkHttpClientManager.getInstance().postJsonByAuth(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthRpc), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "onSuccess,result:" + result);
        if (TextUtils.isEmpty(result)) {
            CpLog.e(TAG, "result is null!");
            mIGetEthBlockNumberCallback.getEthBlockNumber(null);
            return;
        }

        ResponseGetEthRpcResult responseGetEthRpcResult = GsonUtils.json2Bean(result, ResponseGetEthRpcResult.class);
        if (null == responseGetEthRpcResult) {
            CpLog.e(TAG, "responseGetEthRpcResult is null!");
            mIGetEthBlockNumberCallback.getEthBlockNumber(null);
            return;
        }

        String blockNumber = WalletUtils.toDecString(responseGetEthRpcResult.getResult(), "0");
        CpLog.i(TAG, "blockNumber:" + blockNumber);
        mIGetEthBlockNumberCallback.getEthBlockNumber(blockNumber);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.i(TAG, "onFailed,msg:" + msg);
        mIGetEthBlockNumberCallback.getEthBlockNumber(null);
    }
}
