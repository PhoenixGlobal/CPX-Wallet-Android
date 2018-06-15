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

    private List<OnItemStateUpdateListener> mOnItemStateUpdateListeners;

    private List<OnItemNameUpdateListener> mOnItemNameUpdateListeners;

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
        mOnItemStateUpdateListeners = new ArrayList<>();
        mOnItemNameUpdateListeners = new ArrayList<>();
    }

    public void onDestroy() {
        if (null == mOnItemDeleteListeners) {
            CpLog.e(TAG, "onDestroy() -> mOnItemDeleteListeners is null!");
            return;
        }

        mOnItemDeleteListeners.clear();
        mOnItemAddListeners.clear();
        mOnItemStateUpdateListeners.clear();
        mOnItemNameUpdateListeners.clear();
        mOnItemDeleteListeners = null;
        mOnItemAddListeners = null;
        mOnItemStateUpdateListeners = null;
        mOnItemNameUpdateListeners = null;
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

    public void addOnItemStateUpdateListener(OnItemStateUpdateListener onItemStateUpdateListener) {
        if (null == mOnItemStateUpdateListeners || null == onItemStateUpdateListener) {
            CpLog.e(TAG, "mOnItemStateUpdateListeners or onItemStateUpdateListener is null!");
            return;
        }

        mOnItemStateUpdateListeners.add(onItemStateUpdateListener);
    }

    public void addOnItemNameUpdateListener(OnItemNameUpdateListener onItemNameUpdateListener) {
        if (null == mOnItemNameUpdateListeners || null == onItemNameUpdateListener) {
            CpLog.e(TAG, "mOnItemNameUpdateListeners or onItemNameUpdateListener is null!");
            return;
        }

        mOnItemNameUpdateListeners.add(onItemNameUpdateListener);
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

    public void notifyItemStateUpdate(WalletBean walletBean) {
        if (null == mOnItemStateUpdateListeners) {
            CpLog.e(TAG, "mOnItemStateUpdateListeners is null!");
            return;
        }

        for (OnItemStateUpdateListener onItemStateUpdateListener : mOnItemStateUpdateListeners) {
            if (null == onItemStateUpdateListener) {
                CpLog.e(TAG, "onItemStateUpdateListener is null!");
                continue;
            }

            onItemStateUpdateListener.OnItemStateUpdate(walletBean);
        }
    }

    public void notifyItemNameUpdate(WalletBean walletBean) {
        if (null == mOnItemNameUpdateListeners) {
            CpLog.e(TAG, "mOnItemNameUpdateListeners is null!");
            return;
        }

        for (OnItemNameUpdateListener onItemNameUpdateListener : mOnItemNameUpdateListeners) {
            if (null == onItemNameUpdateListener) {
                CpLog.e(TAG, "onItemNameUpdateListener is null!");
                continue;
            }

            onItemNameUpdateListener.OnItemNameUpdate(walletBean);
        }
    }
}
