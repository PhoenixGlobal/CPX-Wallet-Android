package chinapex.com.wallet.executor.runnable.eth;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetEthBalance;
import chinapex.com.wallet.bean.response.ResponseGetEthBalance;
import chinapex.com.wallet.executor.callback.eth.IGetEthGasPriceCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/8/29 0029 15:07.
 * E-Mail：liuyi_61@163.com
 */
public class GetEthGasPrice implements Runnable, INetCallback {
    private static final String TAG = GetEthGasPrice.class.getSimpleName();
    private IGetEthGasPriceCallback mIGetEthGasPriceCallback;

    public GetEthGasPrice(IGetEthGasPriceCallback IGetEthGasPriceCallback) {
        mIGetEthGasPriceCallback = IGetEthGasPriceCallback;
    }

    @Override
    public void run() {
        if (null == mIGetEthGasPriceCallback) {
            CpLog.e(TAG, "mIGetEthGasPriceCallback is null!");
            return;
        }

        RequestGetEthBalance requestGetEthGasPrice = new RequestGetEthBalance();
        requestGetEthGasPrice.setJsonrpc("2.0");
        requestGetEthGasPrice.setMethod("eth_gasPrice");
        requestGetEthGasPrice.setId(73);
        ArrayList<String> arrayList = new ArrayList<>();
        requestGetEthGasPrice.setParams(arrayList);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI_ETH, GsonUtils.toJsonStr(requestGetEthGasPrice), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "result:" + result);
        ResponseGetEthBalance responseGetEthGasPrice = GsonUtils.json2Bean(result, ResponseGetEthBalance.class);
        if (null == responseGetEthGasPrice) {
            CpLog.e(TAG, "responseGetEthGasPrice is null!");
            mIGetEthGasPriceCallback.getEthGasPrice(null);
            return;
        }

        mIGetEthGasPriceCallback.getEthGasPrice(responseGetEthGasPrice.getResult());
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
    }
}
