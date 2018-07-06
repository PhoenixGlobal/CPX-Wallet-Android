package chinapex.com.wallet.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SteelCabbage on 2018/5/24 0024.
 */

public class PhoneUtils {

    private static final String TAG = PhoneUtils.class.getSimpleName();
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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


    public static String getFormatTime(long time) {
        String formatTime = "";
        if (time == 0) {
            return formatTime;
        }

        try {
            formatTime = sSimpleDateFormat.format(new Date(time * 1000));
        } catch (Exception e) {
            CpLog.e(TAG, "getFormatTime exception:" + e.getMessage());
        }
        return formatTime;
    }

    public static byte[] reverseArray(String string) {
        if ("0".equals(string)) {
            byte[] zero = new byte[1];
            return zero;
        }
        byte[] array = hexStringToBytes(string);
        byte[] array_list = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            array_list[i] = (array[array.length - i - 1]);
        }
        return array_list;
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
