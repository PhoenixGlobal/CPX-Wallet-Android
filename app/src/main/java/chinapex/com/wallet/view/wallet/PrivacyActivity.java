package chinapex.com.wallet.view.wallet;

import android.os.Bundle;
import android.webkit.WebView;

import java.util.Locale;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.utils.PhoneUtils;

public class PrivacyActivity extends BaseActivity {

    private static final String TAG = PrivacyActivity.class.getSimpleName();

    private WebView mWv_privacy;

    @Override
    protected void setContentView() {
        super.setContentView();

        setContentView(R.layout.activity_privacy);
    }

    @Override
    protected void init() {
        super.init();

        initView();
        initData();
    }

    private void initView() {
        mWv_privacy = (WebView) findViewById(R.id.wv_privacy);
    }

    private void initData() {
        String appLanguage = PhoneUtils.getAppLanguage();
        String url;
        if (appLanguage.contains(Locale.CHINA.toString())) {
            url = "file:///android_asset/web/userProtocol.html";
        } else if (appLanguage.contains(Locale.ENGLISH.toString())) {
            url = "file:///android_asset/web/userProtocol_en.html";
        } else {
            url = "file:///android_asset/web/userProtocol_en.html";
        }

        mWv_privacy.loadUrl(url);
    }
}
