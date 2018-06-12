package chinapex.com.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by SteelCabbage on 2018/3/23.
 */

public class GsonUtils {

    private static final String TAG = GsonUtils.class.getSimpleName();

    private static Gson sGson;

    private GsonUtils() {

    }

    static {
        sGson = new Gson();
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

    public static <T> List<T> json2List(String listJson, Class<T> cls) {
        Type type = new ParameterizedTypeImpl(cls);
        List<T> list = null;
        try {
            list = sGson.fromJson(listJson, type);
        } catch (JsonSyntaxException e) {
            CpLog.e(TAG, "json2List() is exception:" + e.getMessage());
        }
        return list;
    }


    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class mCls;

        ParameterizedTypeImpl(Class cls) {
            mCls = cls;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{mCls};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
