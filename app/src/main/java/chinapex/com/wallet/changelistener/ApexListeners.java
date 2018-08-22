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

    private List<OnWalletDeleteListener> mOnWalletDeleteListeners;
    private List<OnWalletAddListener> mOnWalletAddListeners;
    private List<OnWalletBackupStateUpdateListener> mOnWalletBackupStateUpdateListeners;
    private List<OnWalletNameUpdateListener> mOnWalletNameUpdateListeners;
    private List<OnTxStateUpdateListener> mOnTxStateUpdateListeners;
    private List<OnAssetJsonUpdateListener> mOnAssetJsonUpdateListeners;

    private ApexListeners() {

    }

    private static class ApexListenersHolder {
        private static final ApexListeners sApexListeners = new ApexListeners();
    }

    public static ApexListeners getInstance() {
        return ApexListenersHolder.sApexListeners;
    }

    public void doInit() {
        mOnWalletDeleteListeners = new ArrayList<>();
        mOnWalletAddListeners = new ArrayList<>();
        mOnWalletBackupStateUpdateListeners = new ArrayList<>();
        mOnWalletNameUpdateListeners = new ArrayList<>();
        mOnTxStateUpdateListeners = new ArrayList<>();
        mOnAssetJsonUpdateListeners = new ArrayList<>();
    }

    public void onDestroy() {
        mOnWalletDeleteListeners.clear();
        mOnWalletAddListeners.clear();
        mOnWalletBackupStateUpdateListeners.clear();
        mOnWalletNameUpdateListeners.clear();
        mOnTxStateUpdateListeners.clear();
        mOnAssetJsonUpdateListeners.clear();

        mOnWalletDeleteListeners = null;
        mOnWalletAddListeners = null;
        mOnWalletBackupStateUpdateListeners = null;
        mOnWalletNameUpdateListeners = null;
        mOnTxStateUpdateListeners = null;
        mOnAssetJsonUpdateListeners = null;
    }

    public void addOnItemDeleteListener(OnWalletDeleteListener onWalletDeleteListener) {
        if (null == mOnWalletDeleteListeners || null == onWalletDeleteListener) {
            CpLog.e(TAG, "1:mOnWalletDeleteListeners or OnWalletDeleteListener is null!");
            return;
        }

        mOnWalletDeleteListeners.add(onWalletDeleteListener);
    }

    public void removeOnItemDeleteListener(OnWalletDeleteListener onWalletDeleteListener) {
        if (null == mOnWalletDeleteListeners || null == onWalletDeleteListener) {
            CpLog.e(TAG, "0:mOnWalletDeleteListeners or OnWalletDeleteListener is null!");
            return;
        }

        mOnWalletDeleteListeners.remove(onWalletDeleteListener);
    }

    public void addOnWalletAddListener(OnWalletAddListener onWalletAddListener) {
        if (null == mOnWalletAddListeners || null == onWalletAddListener) {
            CpLog.e(TAG, "1:mOnWalletAddListeners or onWalletAddListener is null!");
            return;
        }

        mOnWalletAddListeners.add(onWalletAddListener);
    }

    public void removeOnWalletAddListener(OnWalletAddListener onWalletAddListener) {
        if (null == mOnWalletAddListeners || null == onWalletAddListener) {
            CpLog.e(TAG, "0:mOnWalletAddListeners or onWalletAddListener is null!");
            return;
        }

        mOnWalletAddListeners.remove(onWalletAddListener);
    }

    public void addOnItemStateUpdateListener(OnWalletBackupStateUpdateListener onWalletBackupStateUpdateListener) {
        if (null == mOnWalletBackupStateUpdateListeners || null == onWalletBackupStateUpdateListener) {
            CpLog.e(TAG, "1:mOnWalletBackupStateUpdateListeners or onWalletBackupStateUpdateListener is null!");
            return;
        }

        mOnWalletBackupStateUpdateListeners.add(onWalletBackupStateUpdateListener);
    }

    public void removeOnItemStateUpdateListener(OnWalletBackupStateUpdateListener
                                                        onWalletBackupStateUpdateListener) {
        if (null == mOnWalletBackupStateUpdateListeners || null == onWalletBackupStateUpdateListener) {
            CpLog.e(TAG, "0:mOnWalletBackupStateUpdateListeners or onWalletBackupStateUpdateListener is null!");
            return;
        }

        mOnWalletBackupStateUpdateListeners.remove(onWalletBackupStateUpdateListener);
    }

    public void addOnItemNameUpdateListener(OnWalletNameUpdateListener onWalletNameUpdateListener) {
        if (null == mOnWalletNameUpdateListeners || null == onWalletNameUpdateListener) {
            CpLog.e(TAG, "1:mOnWalletNameUpdateListeners or onWalletNameUpdateListener is null!");
            return;
        }

        mOnWalletNameUpdateListeners.add(onWalletNameUpdateListener);
    }

    public void removeOnItemNameUpdateListener(OnWalletNameUpdateListener onWalletNameUpdateListener) {
        if (null == mOnWalletNameUpdateListeners || null == onWalletNameUpdateListener) {
            CpLog.e(TAG, "0:mOnWalletNameUpdateListeners or onWalletNameUpdateListener is null!");
            return;
        }

        mOnWalletNameUpdateListeners.remove(onWalletNameUpdateListener);
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

    public void addOnAssetsUpdateListener(OnAssetJsonUpdateListener onAssetJsonUpdateListener) {
        if (null == mOnAssetJsonUpdateListeners || null == onAssetJsonUpdateListener) {
            CpLog.e(TAG, "1:mOnAssetJsonUpdateListeners or onAssetJsonUpdateListener is null!");
            return;
        }

        mOnAssetJsonUpdateListeners.add(onAssetJsonUpdateListener);
    }

    public void removeOnAssetsUpdateListener(OnAssetJsonUpdateListener onAssetJsonUpdateListener) {
        if (null == mOnAssetJsonUpdateListeners || null == onAssetJsonUpdateListener) {
            CpLog.e(TAG, "0:mOnAssetJsonUpdateListeners or onAssetJsonUpdateListener is null!");
            return;
        }

        mOnAssetJsonUpdateListeners.remove(onAssetJsonUpdateListener);
    }

    public void notifyWalletDelete(WalletBean walletBean) {
        if (null == mOnWalletDeleteListeners) {
            CpLog.e(TAG, "mOnWalletDeleteListeners is null!");
            return;
        }

        for (OnWalletDeleteListener onWalletDeleteListener : mOnWalletDeleteListeners) {
            if (null == onWalletDeleteListener) {
                CpLog.e(TAG, "OnWalletDeleteListener is null!");
                continue;
            }

            onWalletDeleteListener.onWalletDelete(walletBean);
        }
    }

    public void notifyWalletAdd(WalletBean walletBean) {
        if (null == mOnWalletAddListeners) {
            CpLog.e(TAG, "mOnWalletAddListeners is null!");
            return;
        }

        for (OnWalletAddListener onWalletAddListener : mOnWalletAddListeners) {
            if (null == onWalletAddListener) {
                CpLog.e(TAG, "onWalletAddListener is null!");
                continue;
            }

            onWalletAddListener.onWalletAdd(walletBean);
        }
    }

    public void notifyWalletBackupStateUpdate(WalletBean walletBean) {
        if (null == mOnWalletBackupStateUpdateListeners) {
            CpLog.e(TAG, "mOnWalletBackupStateUpdateListeners is null!");
            return;
        }

        for (OnWalletBackupStateUpdateListener onWalletBackupStateUpdateListener : mOnWalletBackupStateUpdateListeners) {
            if (null == onWalletBackupStateUpdateListener) {
                CpLog.e(TAG, "onWalletBackupStateUpdateListener is null!");
                continue;
            }

            onWalletBackupStateUpdateListener.onWalletBackupStateUpdate(walletBean);
        }
    }

    public void notifyItemNameUpdate(WalletBean walletBean) {
        if (null == mOnWalletNameUpdateListeners) {
            CpLog.e(TAG, "mOnWalletNameUpdateListeners is null!");
            return;
        }

        for (OnWalletNameUpdateListener onWalletNameUpdateListener : mOnWalletNameUpdateListeners) {
            if (null == onWalletNameUpdateListener) {
                CpLog.e(TAG, "onWalletNameUpdateListener is null!");
                continue;
            }

            onWalletNameUpdateListener.OnWalletNameUpdate(walletBean);
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

    public void notifyAssetJsonUpdate(WalletBean walletBean) {
        if (null == mOnAssetJsonUpdateListeners) {
            CpLog.e(TAG, "mOnAssetJsonUpdateListeners is null!");
            return;
        }

        for (OnAssetJsonUpdateListener onAssetJsonUpdateListener : mOnAssetJsonUpdateListeners) {
            if (null == onAssetJsonUpdateListener) {
                CpLog.e(TAG, "onAssetJsonUpdateListener is null!");
                continue;
            }

            onAssetJsonUpdateListener.onAssetJsonUpdate(walletBean);
        }
    }

}
