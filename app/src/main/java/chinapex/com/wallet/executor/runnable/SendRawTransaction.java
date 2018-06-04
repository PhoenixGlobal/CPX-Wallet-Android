package chinapex.com.wallet.executor.runnable;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestSendRawTransaction;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/5/30 0030.
 */

public class SendRawTransaction implements Runnable {

    private static final String TAG = SendRawTransaction.class.getSimpleName();
    private String mTxData;
    private INetCallback mINetCallback;

    public SendRawTransaction(String txData, INetCallback INetCallback) {
        mTxData = txData;
        mINetCallback = INetCallback;
    }

    @Override
    public void run() {
        if (null == mTxData) {
            CpLog.e(TAG, "mTxData is null!");
            return;
        }

        if (null == mINetCallback) {
            CpLog.e(TAG, "mINetCallback is null!");
            return;
        }

        RequestSendRawTransaction requestSendRawTransaction = new RequestSendRawTransaction();
        requestSendRawTransaction.setJsonrpc("2.0");
        requestSendRawTransaction.setMethod("sendrawtransaction");
        ArrayList<String> sendDatas = new ArrayList<>();
        sendDatas.add(mTxData);
        requestSendRawTransaction.setParams(sendDatas);
        requestSendRawTransaction.setId(1);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                (requestSendRawTransaction), mINetCallback);
    }
}
