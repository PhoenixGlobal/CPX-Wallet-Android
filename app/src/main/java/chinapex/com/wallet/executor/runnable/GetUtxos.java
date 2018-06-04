package chinapex.com.wallet.executor.runnable;

import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;

/**
 * Created by SteelCabbage on 2018/5/30 0030.
 */

public class GetUtxos implements Runnable {

    private static final String TAG = GetUtxos.class.getSimpleName();
    private String mAddress;
    private INetCallback mINetCallback;

    public GetUtxos(String address, INetCallback INetCallback) {
        mAddress = address;
        mINetCallback = INetCallback;
    }

    @Override
    public void run() {
        String url = Constant.URL_UTXOS + mAddress;
        OkHttpClientManager.getInstance().get(url, mINetCallback);
    }
}
