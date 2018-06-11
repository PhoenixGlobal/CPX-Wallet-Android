package chinapex.com.wallet.global;

/**
 * Created by SteelCabbage on 2018/6/10 15:23
 * E-Mail：liuyi_61@163.com
 */
public class ApexCache {

    private static final String TAG = ApexCache.class.getSimpleName();
    // 确认助记词后是否跳转到MainActivity
    private boolean isStartMainActivity = true;

    private ApexCache() {

    }

    private static class ApexCacheHolder {
        private static final ApexCache sApexCache = new ApexCache();
    }

    public static ApexCache getInstance() {
        return ApexCacheHolder.sApexCache;
    }

    public boolean isStartMainActivity() {
        return isStartMainActivity;
    }

    public void setStartMainActivity(boolean startMainActivity) {
        isStartMainActivity = startMainActivity;
    }
}
