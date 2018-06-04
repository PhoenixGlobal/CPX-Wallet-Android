package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;

import chinapex.com.wallet.bean.request.RequestGetAccountState;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class GetAccountState implements Runnable {

    private static final String TAG = GetAccountState.class.getSimpleName();
    private String mAddress;
    private INetCallback mINetCallback;

    public GetAccountState(String account, INetCallback iNetCallback) {
        mAddress = account;
        mINetCallback = iNetCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mINetCallback) {
            CpLog.e(TAG, "run() -> mAccount or mIResultCallback is null！");
            return;
        }

        RequestGetAccountState requestGetAccountState = new RequestGetAccountState();
        requestGetAccountState.setJsonrpc("2.0");
        requestGetAccountState.setMethod("getaccountstate");
        requestGetAccountState.setId(1);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mAddress);
        requestGetAccountState.setParams(arrayList);

        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                (requestGetAccountState), mINetCallback);
    }

}
