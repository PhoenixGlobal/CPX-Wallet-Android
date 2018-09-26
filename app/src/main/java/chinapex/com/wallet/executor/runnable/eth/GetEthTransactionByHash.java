package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthRpc;
import chinapex.com.wallet.bean.response.ResponseEthTxByHash;
import chinapex.com.wallet.bean.response.ResponseEthTxReceipt;
import chinapex.com.wallet.executor.callback.eth.IGetEthTransactionByHashCallback;
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
public class GetEthTransactionByHash implements Runnable, INetCallback {
    private static final String TAG = GetEthTransactionByHash.class.getSimpleName();

    private String mTxId;
    private String mWalletAddress;
    private IGetEthTransactionByHashCallback mIGetEthTransactionByHashCallback;

    public GetEthTransactionByHash(String txId, String walletAddress, IGetEthTransactionByHashCallback
            IGetEthTransactionByHashCallback) {
        mTxId = txId;
        mWalletAddress = walletAddress;
        mIGetEthTransactionByHashCallback = IGetEthTransactionByHashCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mTxId) || TextUtils.isEmpty(mWalletAddress) || null == mIGetEthTransactionByHashCallback) {
            CpLog.e(TAG, "mTxId or mTxId or mIGetEthTransactionByHashCallback is null!");
            return;
        }

        RequestGetEthRpc requestGetEthRpc = new RequestGetEthRpc();
        requestGetEthRpc.setJsonrpc("2.0");
        requestGetEthRpc.setMethod("eth_getTransactionByHash");
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
            mIGetEthTransactionByHashCallback.getEthTransactionByHash(mWalletAddress, null);
            return;
        }

        ResponseEthTxByHash responseEthTxByHash = GsonUtils.json2Bean(result, ResponseEthTxByHash.class);
        if (null == responseEthTxByHash) {
            CpLog.e(TAG, "responseEthTxByHash is null!");
            mIGetEthTransactionByHashCallback.getEthTransactionByHash(mWalletAddress, null);
            return;
        }

        ResponseEthTxByHash.ResultBean resultBean = responseEthTxByHash.getResult();
        if (null == resultBean) {
            CpLog.e(TAG, "resultBean is null!");
            mIGetEthTransactionByHashCallback.getEthTransactionByHash(mWalletAddress, null);
            return;
        }


        String nonce = WalletUtils.toDecString(resultBean.getNonce(), "0");
        mIGetEthTransactionByHashCallback.getEthTransactionByHash(mWalletAddress, nonce);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.i(TAG, "onFailed,msg:" + msg);
        mIGetEthTransactionByHashCallback.getEthTransactionByHash(mWalletAddress, null);
    }
}
