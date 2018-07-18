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

    private List<OnTxStateUpdateListener> mOnTxStateUpdateListeners;

    private List<OnAssetsUpdateListener> mOnAssetsUpdateListeners;

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
        mOnTxStateUpdateListeners = new ArrayList<>();
        mOnAssetsUpdateListeners = new ArrayList<>();
    }

    public void onDestroy() {
        mOnItemDeleteListeners.clear();
        mOnItemAddListeners.clear();
        mOnItemStateUpdateListeners.clear();
        mOnItemNameUpdateListeners.clear();
        mOnTxStateUpdateListeners.clear();
        mOnAssetsUpdateListeners.clear();

        mOnItemDeleteListeners = null;
        mOnItemAddListeners = null;
        mOnItemStateUpdateListeners = null;
        mOnItemNameUpdateListeners = null;
        mOnTxStateUpdateListeners = null;
        mOnAssetsUpdateListeners = null;
    }


    public void addOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        if (null == mOnItemDeleteListeners || null == onItemDeleteListener) {
            CpLog.e(TAG, "1:mOnItemDeleteListeners or OnItemDeleteListener is null!");
            return;
        }

        mOnItemDeleteListeners.add(onItemDeleteListener);
    }

    public void removeOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        if (null == mOnItemDeleteListeners || null == onItemDeleteListener) {
            CpLog.e(TAG, "0:mOnItemDeleteListeners or OnItemDeleteListener is null!");
            return;
        }

        mOnItemDeleteListeners.remove(onItemDeleteListener);
    }

    public void addOnItemAddListener(OnItemAddListener onItemAddListener) {
        if (null == mOnItemAddListeners || null == onItemAddListener) {
            CpLog.e(TAG, "1:mOnItemAddListeners or onItemAddListener is null!");
            return;
        }

        mOnItemAddListeners.add(onItemAddListener);
    }

    public void removeOnItemAddListener(OnItemAddListener onItemAddListener) {
        if (null == mOnItemAddListeners || null == onItemAddListener) {
            CpLog.e(TAG, "0:mOnItemAddListeners or onItemAddListener is null!");
            return;
        }

        mOnItemAddListeners.remove(onItemAddListener);
    }

    public void addOnItemStateUpdateListener(OnItemStateUpdateListener onItemStateUpdateListener) {
        if (null == mOnItemStateUpdateListeners || null == onItemStateUpdateListener) {
            CpLog.e(TAG, "1:mOnItemStateUpdateListeners or onItemStateUpdateListener is null!");
            return;
        }

        mOnItemStateUpdateListeners.add(onItemStateUpdateListener);
    }

    public void removeOnItemStateUpdateListener(OnItemStateUpdateListener
                                                        onItemStateUpdateListener) {
        if (null == mOnItemStateUpdateListeners || null == onItemStateUpdateListener) {
            CpLog.e(TAG, "0:mOnItemStateUpdateListeners or onItemStateUpdateListener is null!");
            return;
        }

        mOnItemStateUpdateListeners.remove(onItemStateUpdateListener);
    }

    public void addOnItemNameUpdateListener(OnItemNameUpdateListener onItemNameUpdateListener) {
        if (null == mOnItemNameUpdateListeners || null == onItemNameUpdateListener) {
            CpLog.e(TAG, "1:mOnItemNameUpdateListeners or onItemNameUpdateListener is null!");
            return;
        }

        mOnItemNameUpdateListeners.add(onItemNameUpdateListener);
    }

    public void removeOnItemNameUpdateListener(OnItemNameUpdateListener onItemNameUpdateListener) {
        if (null == mOnItemNameUpdateListeners || null == onItemNameUpdateListener) {
            CpLog.e(TAG, "0:mOnItemNameUpdateListeners or onItemNameUpdateListener is null!");
            return;
        }

        mOnItemNameUpdateListeners.remove(onItemNameUpdateListener);
    }

    public void addOnTxStateUpdateListener(OnTxStateUpdateListener onTxStateUpdateListener) {
        if (null == mOnTxStateUpdateListeners || null == onTxStateUpdateListener) {
            CpLog.e(TAG, "1:mOnTxStateUpdateListeners or onTxStateUpdateListener is null!");
            return;
        }

        mOnTxStateUpdateListeners.add(onTxStateUpdateListener);
    }

    public void removeOnTxStateUpdateListener(OnTxStateUpdateListener onTxStateUpdateListener) {
        if (null == mOnTxStateUpdateListeners || null == onTxStateUpdateListener) {
            CpLog.e(TAG, "0:mOnTxStateUpdateListeners or onTxStateUpdateListener is null!");
            return;
        }

        mOnTxStateUpdateListeners.remove(onTxStateUpdateListener);
    }

    public void addOnAssetsUpdateListener(OnAssetsUpdateListener onAssetsUpdateListener) {
        if (null == mOnAssetsUpdateListeners || null == onAssetsUpdateListener) {
            CpLog.e(TAG, "1:mOnAssetsUpdateListeners or onAssetsUpdateListener is null!");
            return;
        }

        mOnAssetsUpdateListeners.add(onAssetsUpdateListener);
    }

    public void removeOnAssetsUpdateListener(OnAssetsUpdateListener onAssetsUpdateListener) {
        if (null == mOnAssetsUpdateListeners || null == onAssetsUpdateListener) {
            CpLog.e(TAG, "0:mOnAssetsUpdateListeners or onAssetsUpdateListener is null!");
            return;
        }

        mOnAssetsUpdateListeners.remove(onAssetsUpdateListener);
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

    public void notifyTxStateUpdate(String txID, int state, long txTime) {
        if (null == mOnTxStateUpdateListeners) {
            CpLog.e(TAG, "mOnTxStateUpdateListeners is null!");
            return;
        }

        for (OnTxStateUpdateListener onTxStateUpdateListener : mOnTxStateUpdateListeners) {
            if (null == onTxStateUpdateListener) {
                CpLog.e(TAG, "onTxStateUpdateListener is null!");
                continue;
            }

            onTxStateUpdateListener.onTxStateUpdate(txID, state, txTime);
        }
    }

    public void notifyAssetsUpdate(WalletBean walletBean) {
        if (null == mOnAssetsUpdateListeners) {
            CpLog.e(TAG, "mOnAssetsUpdateListeners is null!");
            return;
        }

        for (OnAssetsUpdateListener onAssetsUpdateListener : mOnAssetsUpdateListeners) {
            if (null == onAssetsUpdateListener) {
                CpLog.e(TAG, "onAssetsUpdateListener is null!");
                continue;
            }

            onAssetsUpdateListener.onAssetsUpdate(walletBean);
        }
    }
}
