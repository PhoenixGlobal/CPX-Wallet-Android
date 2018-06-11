package chinapex.com.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by SteelCabbage on 2018/3/23.
 */

public class GsonUtils {

    private static final String TAG = GsonUtils.class.getSimpleName();

    private static Gson sGson;

    private GsonUtils() {

    }

    static {
        if (null == sGson) {
            sGson = new Gson();
        }
    }

    public static String toJsonStr(Object object) {
        String jsonStr = null;
        if (null != sGson) {
            try {
                jsonStr = sGson.toJson(object);
            } catch (Exception e) {
                CpLog.e(TAG, "toJsonStr() is exception:" + e.getMessage());
            }
        }
        return jsonStr;
    }

    public static <T> T json2Bean(String jsonStr, Class<T> cls) {
        T t = null;
        if (null != sGson) {
            try {
                t = sGson.fromJson(jsonStr, cls);
            } catch (JsonSyntaxException e) {
                CpLog.e(TAG, "json2Bean() is exception:" + e.getMessage());
            }
        }
        return t;
    }

//    public static List<WalletKeyStore> json2List(String listJson) {
//        if (TextUtils.isEmpty(listJson)) {
//            CpLog.e(TAG, "listJson is null!");
//            return null;
//        }
//
//        List<WalletKeyStore> list = null;
//        try {
//            list = sGson.fromJson(listJson, new TypeToken<List<WalletKeyStore>>() {
//            }.getType());
//        } catch (JsonSyntaxException e) {
//            CpLog.e(TAG, "json2List exception:" + e.getMessage());
//        }
//
//        return list;
//    }


}
