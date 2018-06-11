package chinapex.com.wallet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by SteelCabbage on 2018/3/30 0030.
 */

public class SharedPreferencesUtils {

    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();

    private static final String SP_NAME = "sp_analytics";


    public static void putParam(Context context, String key, Object value) {
        if (null == context) {
            CpLog.e(TAG, "setParam() -> context is null!");
            return;
        }

        if (TextUtils.isEmpty(key) || null == value) {
            CpLog.e(TAG, "setParam() -> key or value is null!");
            return;
        }

        String clsName = value.getClass().getSimpleName();
        if (TextUtils.isEmpty(clsName)) {
            CpLog.e(TAG, "setParam() -> key's clsName is null!");
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch (clsName) {
            case "Long":
                editor.putLong(key, (Long) value);
                break;
            case "String":
                editor.putString(key, (String) value);
                break;
            case "Integer":
                editor.putInt(key, (Integer) value);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) value);
                break;
            case "Float":
                editor.putFloat(key, (Float) value);
                break;
            default:
                break;
        }

        editor.apply();
    }

    public static Object getParam(Context context, String key, Object defVal) {
        if (null == context) {
            CpLog.e(TAG, "getParam() -> context is null!");
            return null;
        }

        if (TextUtils.isEmpty(key) || null == defVal) {
            CpLog.e(TAG, "getParam() -> key or defVal is null!");
            return null;
        }

        String clsName = defVal.getClass().getSimpleName();
        if (TextUtils.isEmpty(clsName)) {
            CpLog.e(TAG, "getParam() -> key's clsName is null!");
            return null;
        }

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        switch (clsName) {
            case "Long":
                return sp.getLong(key, (Long) defVal);
            case "String":
                return sp.getString(key, (String) defVal);
            case "Integer":
                return sp.getInt(key, (Integer) defVal);
            case "Boolean":
                return sp.getBoolean(key, (Boolean) defVal);
            case "Float":
                return sp.getFloat(key, (Float) defVal);
            default:
                break;
        }

        return null;
    }

}
