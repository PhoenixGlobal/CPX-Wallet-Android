package chinapex.com.wallet.global;

import android.app.Application;

import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class ApexWalletApplication extends Application {
    private static final String TAG = ApexWalletApplication.class.getSimpleName();
    private static ApexWalletApplication sApexWalletApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        CpLog.i(TAG, "onCreate() -> start!");
        sApexWalletApplication = this;
        TaskController.getInstance().doInit();
        ApexListeners.getInstance().doInit();
    }

    public static ApexWalletApplication getInstance() {
        return sApexWalletApplication;
    }

}
