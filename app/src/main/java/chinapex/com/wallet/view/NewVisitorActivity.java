package chinapex.com.wallet.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.model.ApexWalletDbHelper;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.view.wallet.CreateWalletActivity;

public class NewVisitorActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NewVisitorActivity.class.getSimpleName();
    private Button mCreate_wallet;
    private Button mImport_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置透明导航键
        setNavigationBarColorTransparent();
        setContentView(R.layout.activity_new_visitor);

        initView();


        //output screen info
        logScreenInfo();
//        testDb();
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
                startActivity(CreateWalletActivity.class, true, TAG);
                break;
            case R.id.bt_new_visitor_import_wallet:
                break;
        }
    }

    private void setNavigationBarColorTransparent() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    private void logScreenInfo() {
        PhoneUtils.logDisplayMetrics(this);
        PhoneUtils.getStatusBarHeight(this);
        PhoneUtils.getNavigationBarHeight(this);
    }

    private void testDb() {
        CpLog.i(TAG, "testDb start");
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        for (int i = 0; i < 5; i++) {
            WalletBean walletBean = new WalletBean();
            walletBean.setWalletName("testName" + i);
            walletBean.setWalletAddr("testAddr" + i);
            walletBean.setBackupState(0);
            walletBean.setKeyStore("{a:b}");
            CpLog.d(TAG, "test walletBean:" + walletBean.toString());
            CpLog.i(TAG, "start insert db!");
            apexWalletDbDao.insert(Constant
                    .TABLE_APEX_WALLET, walletBean);
        }


        CpLog.i(TAG, "start query db!");
        List<WalletBean> walletBeans = apexWalletDbDao.queryWalletBeans(Constant.TABLE_APEX_WALLET);
        if (null == walletBeans) {
            CpLog.e(TAG, "walletBeans is null!");
            return;
        }

        for (WalletBean bean : walletBeans) {
            CpLog.i(TAG, "query bean:" + bean.toString());
        }

        CpLog.i(TAG, "start update db!");
        apexWalletDbDao.updateWalletNameByWalletName(Constant.TABLE_APEX_WALLET, "testName4", "修改后的钱包名字");
        apexWalletDbDao.updateBackupStateByWalletName(Constant.TABLE_APEX_WALLET, "testName1", 111);

        CpLog.i(TAG, "start delete db!");
        apexWalletDbDao.deleteByWalletName(Constant.TABLE_APEX_WALLET, "testName3");


        CpLog.i(TAG, "start query again db!");
        List<WalletBean> walletBeans0 = apexWalletDbDao.queryWalletBeans(Constant
                .TABLE_APEX_WALLET);
        if (null == walletBeans0) {
            CpLog.e(TAG, "walletBeans is null!");
            return;
        }

        for (WalletBean bean : walletBeans0) {
            CpLog.i(TAG, "query agarin bean:" + bean.toString());
        }

    }


}
