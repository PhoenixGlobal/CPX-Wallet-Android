package chinapex.com.wallet.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by SteelCabbage on 2018/5/24 0024.
 */

public class PhoneUtils {

    private static final String TAG = PhoneUtils.class.getSimpleName();

    /**
     * 获取顶部状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);

        }
        CpLog.v(TAG, "getStatusBarHeight:" + statusBarHeight);
        return statusBarHeight;
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = -1;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        CpLog.v(TAG, "getNavigationBarHeight:" + navigationBarHeight);
        return navigationBarHeight;
    }

    public static void logDisplayMetrics(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int widthPixels = metric.widthPixels;
        int heightPixels = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;
        float xdpi = metric.xdpi;
        float ydpi = metric.ydpi;
        CpLog.i(TAG, "widthPixels:" + widthPixels);
        CpLog.i(TAG, "heightPixels:" + heightPixels);
        CpLog.i(TAG, "density:" + density);
        CpLog.i(TAG, "densityDpi:" + densityDpi);
        CpLog.i(TAG, "xdpi:" + xdpi);
        CpLog.i(TAG, "ydpi:" + ydpi);
    }

    /**
     * 复制内容到剪切板
     *
     * @param copyContent
     */
    public static void copy2Clipboard(Context context, String copyContent) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        if (null == clipboardManager) {
            CpLog.e(TAG, "clipboardManager is null!");
            return;
        }

        ClipData clipData = ClipData.newPlainText("text", copyContent);
        clipboardManager.setPrimaryClip(clipData);
    }
}
