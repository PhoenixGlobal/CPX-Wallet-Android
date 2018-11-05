package chinapex.com.wallet.view.excitation;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import chinapex.com.wallet.bean.ExcitationBean;
import chinapex.com.wallet.bean.response.ResponseExcitation;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.PhoneUtils;

public class GetExcitation implements Runnable, INetCallback {

    private static final String TAG = GetExcitation.class.getSimpleName();

    private String mAddress;
    private IGetExcitationCallback mIGetExcitationCallback;

    public GetExcitation(String mAddress, IGetExcitationCallback iGetExcitationCallback) {
        this.mAddress = mAddress;
        this.mIGetExcitationCallback = iGetExcitationCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mIGetExcitationCallback) {
            CpLog.e(TAG, "run() -> mAddress or mIGetExcitationCallback is null！");
            return;
        }

        OkHttpClientManager.getInstance().get(mAddress, this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        CpLog.i(TAG, "result:" + result);
        List<ExcitationBean> excitationBeans = new ArrayList<>();

        if (TextUtils.isEmpty(result)) {
            CpLog.i(TAG, "result == null ");
            mIGetExcitationCallback.getExcitation(null);
            return;
        }


        ResponseExcitation responseExcitation = GsonUtils.json2Bean(result, ResponseExcitation.class);
        if (null == responseExcitation) {
            CpLog.i(TAG, "responseExcitation is null ");
            mIGetExcitationCallback.getExcitation(null);
            return;
        }

        List<ResponseExcitation.DataBean> datas = responseExcitation.getData();
        if (null == datas || datas.isEmpty()) {
            CpLog.i(TAG, "ResponseExcitation.DataBean is null ");
            mIGetExcitationCallback.getExcitation(null);
            return;
        }

        for (ResponseExcitation.DataBean data : datas) {
            ExcitationBean excitationBean = new ExcitationBean();

            excitationBean.setEventNew(data.getNew_flag() == 1);
            excitationBean.setNewEventPic(data.getImagesurl());
            if (PhoneUtils.getAppLanguage().contains(Locale.CHINA.toString())) {
                excitationBean.setNewEventText(data.getTitle_cn());
            } else {
                excitationBean.setNewEventText(data.getTitle_en());
            }
            excitationBean.setNewEventStatus(data.getStatus());
            excitationBean.setGasLimit(data.getGas_limit());
            excitationBean.setActivityId(data.getId());

            excitationBeans.add(excitationBean);
        }

        mIGetExcitationCallback.getExcitation(excitationBeans);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "onFailed() -> msg:" + msg);
        mIGetExcitationCallback.getExcitation(null);
    }

}
