package chinapex.com.wallet.view.excitation.detail;

import android.text.TextUtils;

import chinapex.com.wallet.bean.AddressResultCode;
import chinapex.com.wallet.bean.request.RequestSubmitExcitation;
import chinapex.com.wallet.bean.response.ResponseExcitationDetailCode;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

public class GetDetailCode implements Runnable, INetCallback {

    private static final String TAG = GetDetailCode.class.getSimpleName();
    private String mAddress;
    private RequestSubmitExcitation mRequestSubmitExcitation;
    private IGetDetailCodeCallback mIGetDetailCodeCallback;

    public GetDetailCode(String address, RequestSubmitExcitation requestSubmitExcitation, IGetDetailCodeCallback
            iGetDetailCodeCallback) {
        mAddress = address;
        mRequestSubmitExcitation = requestSubmitExcitation;
        mIGetDetailCodeCallback = iGetDetailCodeCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mIGetDetailCodeCallback || null == mRequestSubmitExcitation) {
            CpLog.e(TAG, "run() -> mAddress or mIGetExcitationCallback or mRequestSubmitExcitation is null！");
            return;
        }

        OkHttpClientManager.getInstance().postJson(mAddress, GsonUtils.toJsonStr(mRequestSubmitExcitation), this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        if (TextUtils.isEmpty(result)) {
            CpLog.i(TAG, "result == null ");
            mIGetDetailCodeCallback.getDetailCode(null);
            return;
        }

        ResponseExcitationDetailCode responseExcitationDetailCode = GsonUtils.json2Bean(result,
                ResponseExcitationDetailCode.class);
        if (null == responseExcitationDetailCode) {
            CpLog.e(TAG, "responseExcitationDetailCode is null ");
            mIGetDetailCodeCallback.getDetailCode(null);
            return;
        }

        ResponseExcitationDetailCode.DataBean dataBeans = responseExcitationDetailCode.getData();
        if (null == dataBeans) {
            CpLog.e(TAG, "DataBean is null ");
            mIGetDetailCodeCallback.getDetailCode(null);
            return;
        }

        AddressResultCode addressResultCode = new AddressResultCode();
        addressResultCode.setCpxCode(dataBeans.getCPX());
        addressResultCode.setEthCode(dataBeans.getETH());

        mIGetDetailCodeCallback.getDetailCode(addressResultCode);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetDetailCodeCallback.getDetailCode(null);
    }

}
