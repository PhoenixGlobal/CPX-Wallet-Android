package chinapex.com.wallet.executor.runnable;

import chinapex.com.wallet.bean.response.ResponseGetUtxos;
import chinapex.com.wallet.executor.callback.IGetUtxosCallback;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/5/30 0030.
 */

public class GetUtxos implements Runnable, INetCallback {

    private static final String TAG = GetUtxos.class.getSimpleName();
    private String mAddress;
    private IGetUtxosCallback mIGetUtxosCallback;

    public GetUtxos(String address, IGetUtxosCallback iGetUtxosCallback) {
        mAddress = address;
        mIGetUtxosCallback = iGetUtxosCallback;
    }

    @Override
    public void run() {
        String url = Constant.URL_UTXOS + mAddress;
        if (null == mIGetUtxosCallback) {
            CpLog.e(TAG, "mIGetUtxosCallback is null！");
            return;
        }

        OkHttpClientManager.getInstance().get(url, this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetUtxos responseGetUtxos = GsonUtils.json2Bean(result, ResponseGetUtxos.class);
        if (null == responseGetUtxos) {
            CpLog.e(TAG, "responseGetUtxos is null!");
            mIGetUtxosCallback.getUtxos(null);
            return;
        }

        String utxos = GsonUtils.toJsonStr(responseGetUtxos.getResult());
        mIGetUtxosCallback.getUtxos(utxos);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetUtxosCallback.getUtxos(null);
    }
}
