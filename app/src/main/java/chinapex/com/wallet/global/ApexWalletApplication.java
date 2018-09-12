package chinapex.com.wallet.global;

import android.app.Application;
import android.content.res.Configuration;

import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class ApexWalletApplication extends Application {

    private static final String TAG = ApexWalletApplication.class.getSimpleName();

    private static ApexWalletApplication sApexWalletApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        CpLog.i(TAG, "ApexWalletApplication start!");
        sApexWalletApplication = this;
        TaskController.getInstance().doInit();
        ApexListeners.getInstance().doInit();
        ApexGlobalTask.getInstance().doInit();
        PhoneUtils.setLanguage();
    }

    public static ApexWalletApplication getInstance() {
        return sApexWalletApplication;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PhoneUtils.setLanguage();
    }
}