package chinapex.com.wallet.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    /***是否显示状态栏*/
    private boolean mIsShowStatusBar = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式状态栏
        if (!mIsShowStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);
                } catch (IllegalAccessException e) {
                    CpLog.e(TAG, "onCreate() -> IllegalAccessException:" + e.getMessage());
                } catch (NoSuchFieldException e) {
                    CpLog.e(TAG, "onCreate() -> NoSuchFieldException:" + e.getMessage());
                } catch (ClassNotFoundException e) {
                    CpLog.e(TAG, "onCreate() -> ClassNotFoundException:" + e.getMessage());
                }
            }
            getWindow().getDecorView().setSystemUiVisibility(View
                    .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    public void startActivity(Class cls, boolean isFinish) {
        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void startActivityParcelable(Class cls, boolean isFinish, String parcelableKey, Parcelable parcelable) {
        if (null == parcelable || TextUtils.isEmpty(parcelableKey)) {
            CpLog.e(TAG, "parcelable or parcelableKey is null!");
            return;
        }

        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);
        intent.putExtra(parcelableKey, parcelable);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    public void startActivityParcelables(Class cls, boolean isFinish, HashMap<String, Parcelable> parcelables) {
        if (null == parcelables || parcelables.isEmpty()) {
            CpLog.e(TAG, "parcelables is null!");
            return;
        }

        Intent intent = new Intent(ApexWalletApplication.getInstance(), cls);

        for (Map.Entry<String, Parcelable> parcelableEntry : parcelables.entrySet()) {
            if (null == parcelableEntry) {
                CpLog.e(TAG, "parcelableEntry is null!");
                continue;
            }

            intent.putExtra(parcelableEntry.getKey(), parcelableEntry.getValue());
        }

        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

}
