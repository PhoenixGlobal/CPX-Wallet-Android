package chinapex.com.wallet.view;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.utils.CpLog;

public class NewVisitorActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NewVisitorActivity.class.getSimpleName();
    private Button mCreate_wallet;
    private Button mImport_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visitor);

        initView();
        testScreen();
    }

    private void initView() {
        mCreate_wallet = (Button) findViewById(R.id.bt_new_visitor_create_wallet);
        mImport_wallet = (Button) findViewById(R.id.bt_new_visitor_import_wallet);

        mCreate_wallet.setOnClickListener(this);
        mImport_wallet.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_new_visitor_create_wallet:
                startActivity(CreateWalletActivity.class, true);
                break;
            case R.id.bt_new_visitor_import_wallet:
                break;
        }
    }

    private void testScreen() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;
        float xdpi = metric.xdpi;
        float ydpi = metric.ydpi;
        CpLog.i(TAG, "width:" + width);
        CpLog.i(TAG, "height:" + height);
        CpLog.i(TAG, "density:" + density);
        CpLog.i(TAG, "densityDpi:" + densityDpi);
        CpLog.i(TAG, "xdpi:" + xdpi);
        CpLog.i(TAG, "ydpi:" + ydpi);

        LinearLayout ll_new_visitor = (LinearLayout) findViewById(R.id.ll_new_visitor);
        int lll_new_visitorWidth = ll_new_visitor.getWidth();
        int ll_new_visitorHeight = ll_new_visitor.getHeight();
        CpLog.i(TAG, "lll_new_visitorWidth:" + lll_new_visitorWidth);
        CpLog.i(TAG, "ll_new_visitorHeight:" + ll_new_visitorHeight);


        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        CpLog.d(TAG, "状态栏-方法1:" + statusBarHeight1);

    }
}
