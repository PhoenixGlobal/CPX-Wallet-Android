package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthRpc;
import chinapex.com.wallet.bean.response.ResponseEthTxReceipt;
import chinapex.com.wallet.bean.response.ResponseGetEthRpcResult;
import chinapex.com.wallet.executor.callback.eth.IGetEthTransactionReceiptCallback;
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
public class GetEthTransactionReceipt implements Runnable, INetCallback {
    private static final String TAG = GetEthTransactionReceipt.class.getSimpleName();

    private String mTxId;
    private String mWalletAddress;
    private IGetEthTransactionReceiptCallback mIGetEthTransactionReceiptCallback;

    public GetEthTransactionReceipt(String txId, String walletAddress, IGetEthTransactionReceiptCallback
            IGetEthTransactionReceiptCallback) {
        mTxId = txId;
        mWalletAddress = walletAddress;
        mIGetEthTransactionReceiptCallback = IGetEthTransactionReceiptCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mTxId) || TextUtils.isEmpty(mWalletAddress) || null == mIGetEthTransactionReceiptCallback) {
            CpLog.e(TAG, "mTxId or mTxId or mIGetEthTransactionReceiptCallback is null!");
            return;
        }

        RequestGetEthRpc requestGetEthRpc = new RequestGetEthRpc();
        requestGetEthRpc.setJsonrpc("2.0");
        requestGetEthRpc.setMethod("eth_getTransactionReceipt");
        requestGetEthRpc.setId(1);
        ArrayList<String> txHash = new ArrayList<>();
        txHash.add(mTxId);
        requestGetEthRpc.setParams(txHash);

        OkHttpClientManager.getInstance().postJsonByAuth(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthRpc), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "onSuccess,result:" + result);
        if (TextUtils.isEmpty(result)) {
            CpLog.e(TAG, "result is null!");
            mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
            return;
        }

        ResponseEthTxReceipt responseEthTxReceipt = GsonUtils.json2Bean(result, ResponseEthTxReceipt.class);
        if (null == responseEthTxReceipt) {
            CpLog.e(TAG, "responseEthTxReceipt is null!");
            mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
            return;
        }

        ResponseEthTxReceipt.ResultBean resultBean = responseEthTxReceipt.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean is null!");
            mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
            return;
        }

        String blockNumber = WalletUtils.toDecString(resultBean.getBlockNumber(), "0");
        String status = WalletUtils.toDecString(resultBean.getStatus(), "0");

        if (TextUtils.isEmpty(blockNumber) || TextUtils.isEmpty(status)) {
            CpLog.e(TAG, "blockNumber or status is null!");
            mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
            return;
        }

        switch (status) {
            case "1":
                mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, blockNumber, true);
                break;
            case "0":
                mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, blockNumber, false);
                break;
            default:
                mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
                break;
        }
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.i(TAG, "onFailed,msg:" + msg);
        mIGetEthTransactionReceiptCallback.getEthTransactionReceipt(mWalletAddress, null, false);
    }
}
