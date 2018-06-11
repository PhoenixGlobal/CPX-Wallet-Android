package chinapex.com.wallet.changelistener;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/4/9 0009.
 */

public class ApexListeners {

    private static final String TAG = ApexListeners.class.getSimpleName();

    private List<OnItemDeleteListener> mOnItemDeleteListeners;

    private List<OnItemAddListener> mOnItemAddListeners;

    private ApexListeners() {

    }

    private static class ApexListenersHolder {
        private static final ApexListeners sApexListeners = new ApexListeners();
    }

    public static ApexListeners getInstance() {
        return ApexListenersHolder.sApexListeners;
    }

    public void doInit() {
        mOnItemDeleteListeners = new ArrayList<>();
        mOnItemAddListeners = new ArrayList<>();
    }

    public void onDestroy() {
        if (null == mOnItemDeleteListeners) {
            CpLog.e(TAG, "onDestroy() -> mOnItemDeleteListeners is null!");
            return;
        }

        mOnItemDeleteListeners.clear();
        mOnItemAddListeners.clear();
        mOnItemDeleteListeners = null;
        mOnItemAddListeners = null;
    }


    public void addOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        if (null == mOnItemDeleteListeners || null == onItemDeleteListener) {
            CpLog.e(TAG, "mOnItemDeleteListeners or OnItemDeleteListener is null!");
            return;
        }

        mOnItemDeleteListeners.add(onItemDeleteListener);
    }

    public void addOnItemAddListener(OnItemAddListener onItemAddListener) {
        if (null == mOnItemAddListeners || null == onItemAddListener) {
            CpLog.e(TAG, "mOnItemAddListeners or onItemAddListener is null!");
            return;
        }

        mOnItemAddListeners.add(onItemAddListener);
    }

    public void notifyItemDelete(WalletBean walletBean) {
        if (null == mOnItemDeleteListeners) {
            CpLog.e(TAG, "mOnItemDeleteListeners is null!");
            return;
        }

        for (OnItemDeleteListener onItemDeleteListener : mOnItemDeleteListeners) {
            if (null == onItemDeleteListener) {
                CpLog.e(TAG, "OnItemDeleteListener is null!");
                continue;
            }

            onItemDeleteListener.onItemDelete(walletBean);
        }
    }

    public void notifyItemAdd(WalletBean walletBean) {
        if (null == mOnItemAddListeners) {
            CpLog.e(TAG, "mOnItemAddListeners is null!");
            return;
        }

        for (OnItemAddListener onItemAddListener : mOnItemAddListeners) {
            if (null == onItemAddListener) {
                CpLog.e(TAG, "onItemAddListener is null!");
                continue;
            }

            onItemAddListener.onItemAdd(walletBean);
        }
    }
}
