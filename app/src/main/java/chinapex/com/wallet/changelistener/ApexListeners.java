package chinapex.com.wallet.changelistener;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.bean.eth.EthWallet;
import chinapex.com.wallet.changelistener.eth.OnEthAddListener;
import chinapex.com.wallet.changelistener.eth.OnEthStateUpdateListener;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/4/9 0009.
 */

public class ApexListeners {

    private static final String TAG = ApexListeners.class.getSimpleName();

    private List<OnNeoDeleteListener> mOnNeoDeleteListeners;
    private List<OnNeoAddListener> mOnNeoAddListeners;
    private List<OnItemStateUpdateListener> mOnItemStateUpdateListeners;
    private List<OnItemNameUpdateListener> mOnItemNameUpdateListeners;
    private List<OnTxStateUpdateListener> mOnTxStateUpdateListeners;
    private List<OnAssetJsonUpdateListener> mOnAssetJsonUpdateListeners;
    // eth
    private List<OnEthAddListener> mOnEthAddListeners;
    private List<OnEthStateUpdateListener> mOnEthStateUpdateListeners;

    private ApexListeners() {

    }

    private static class ApexListenersHolder {
        private static final ApexListeners sApexListeners = new ApexListeners();
    }

    public static ApexListeners getInstance() {
        return ApexListenersHolder.sApexListeners;
    }

    public void doInit() {
        mOnNeoDeleteListeners = new ArrayList<>();
        mOnNeoAddListeners = new ArrayList<>();
        mOnItemStateUpdateListeners = new ArrayList<>();
        mOnItemNameUpdateListeners = new ArrayList<>();
        mOnTxStateUpdateListeners = new ArrayList<>();
        mOnAssetJsonUpdateListeners = new ArrayList<>();
        mOnEthAddListeners = new ArrayList<>();
        mOnEthStateUpdateListeners = new ArrayList<>();
    }

    public void onDestroy() {
        mOnNeoDeleteListeners.clear();
        mOnNeoAddListeners.clear();
        mOnItemStateUpdateListeners.clear();
        mOnItemNameUpdateListeners.clear();
        mOnTxStateUpdateListeners.clear();
        mOnAssetJsonUpdateListeners.clear();
        mOnEthAddListeners.clear();
        mOnEthStateUpdateListeners.clear();

        mOnNeoDeleteListeners = null;
        mOnNeoAddListeners = null;
        mOnItemStateUpdateListeners = null;
        mOnItemNameUpdateListeners = null;
        mOnTxStateUpdateListeners = null;
        mOnAssetJsonUpdateListeners = null;
        mOnEthAddListeners = null;
        mOnEthStateUpdateListeners = null;
    }

    public void addOnItemDeleteListener(OnNeoDeleteListener onNeoDeleteListener) {
        if (null == mOnNeoDeleteListeners || null == onNeoDeleteListener) {
            CpLog.e(TAG, "1:mOnNeoDeleteListeners or OnNeoDeleteListener is null!");
            return;
        }

        mOnNeoDeleteListeners.add(onNeoDeleteListener);
    }

    public void removeOnItemDeleteListener(OnNeoDeleteListener onNeoDeleteListener) {
        if (null == mOnNeoDeleteListeners || null == onNeoDeleteListener) {
            CpLog.e(TAG, "0:mOnNeoDeleteListeners or OnNeoDeleteListener is null!");
            return;
        }

        mOnNeoDeleteListeners.remove(onNeoDeleteListener);
    }

    public void addOnItemAddListener(OnNeoAddListener onNeoAddListener) {
        if (null == mOnNeoAddListeners || null == onNeoAddListener) {
            CpLog.e(TAG, "1:mOnNeoAddListeners or onNeoAddListener is null!");
            return;
        }

        mOnNeoAddListeners.add(onNeoAddListener);
    }

    public void removeOnItemAddListener(OnNeoAddListener onNeoAddListener) {
        if (null == mOnNeoAddListeners || null == onNeoAddListener) {
            CpLog.e(TAG, "0:mOnNeoAddListeners or onNeoAddListener is null!");
            return;
        }

        mOnNeoAddListeners.remove(onNeoAddListener);
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

    public void addOnEthAddListener(OnEthAddListener onEthAddListener) {
        if (null == mOnEthAddListeners || null == onEthAddListener) {
            CpLog.e(TAG, "1:mOnEthAddListeners or onEthAddListener is null!");
            return;
        }

        mOnEthAddListeners.add(onEthAddListener);
    }

    public void removeOnEthAddListener(OnEthAddListener onEthAddListener) {
        if (null == mOnEthAddListeners || null == onEthAddListener) {
            CpLog.e(TAG, "0:mOnEthAddListeners or onEthAddListener is null!");
            return;
        }

        mOnEthAddListeners.remove(onEthAddListener);
    }

    public void addOnEthStateUpdateListener(OnEthStateUpdateListener onEthStateUpdateListener) {
        if (null == mOnEthStateUpdateListeners || null == onEthStateUpdateListener) {
            CpLog.e(TAG, "1:mOnEthStateUpdateListeners or onEthStateUpdateListener is null!");
            return;
        }

        mOnEthStateUpdateListeners.add(onEthStateUpdateListener);
    }

    public void removeOnEthStateUpdateListener(OnEthStateUpdateListener onEthStateUpdateListener) {
        if (null == mOnEthStateUpdateListeners || null == onEthStateUpdateListener) {
            CpLog.e(TAG, "0:mOnEthStateUpdateListeners or onEthStateUpdateListener is null!");
            return;
        }

        mOnEthStateUpdateListeners.remove(onEthStateUpdateListener);
    }

    public void notifyItemDelete(NeoWallet neoWallet) {
        if (null == mOnNeoDeleteListeners) {
            CpLog.e(TAG, "mOnNeoDeleteListeners is null!");
            return;
        }

        for (OnNeoDeleteListener onNeoDeleteListener : mOnNeoDeleteListeners) {
            if (null == onNeoDeleteListener) {
                CpLog.e(TAG, "OnNeoDeleteListener is null!");
                continue;
            }

            onNeoDeleteListener.onNeoDelete(neoWallet);
        }
    }

    public void notifyNeoAdd(NeoWallet neoWallet) {
        if (null == mOnNeoAddListeners) {
            CpLog.e(TAG, "mOnNeoAddListeners is null!");
            return;
        }

        for (OnNeoAddListener onNeoAddListener : mOnNeoAddListeners) {
            if (null == onNeoAddListener) {
                CpLog.e(TAG, "onNeoAddListener is null!");
                continue;
            }

            onNeoAddListener.onNeoAdd(neoWallet);
        }
    }

    public void notifyItemStateUpdate(NeoWallet neoWallet) {
        if (null == mOnItemStateUpdateListeners) {
            CpLog.e(TAG, "mOnItemStateUpdateListeners is null!");
            return;
        }

        for (OnItemStateUpdateListener onItemStateUpdateListener : mOnItemStateUpdateListeners) {
            if (null == onItemStateUpdateListener) {
                CpLog.e(TAG, "onItemStateUpdateListener is null!");
                continue;
            }

            onItemStateUpdateListener.OnItemStateUpdate(neoWallet);
        }
    }

    public void notifyItemNameUpdate(NeoWallet neoWallet) {
        if (null == mOnItemNameUpdateListeners) {
            CpLog.e(TAG, "mOnItemNameUpdateListeners is null!");
            return;
        }

        for (OnItemNameUpdateListener onItemNameUpdateListener : mOnItemNameUpdateListeners) {
            if (null == onItemNameUpdateListener) {
                CpLog.e(TAG, "onItemNameUpdateListener is null!");
                continue;
            }

            onItemNameUpdateListener.OnItemNameUpdate(neoWallet);
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

    public void notifyEthAdd(EthWallet ethWallet) {
        if (null == mOnEthAddListeners) {
            CpLog.e(TAG, "mOnEthAddListeners is null!");
            return;
        }

        for (OnEthAddListener onEthAddListener : mOnEthAddListeners) {
            if (null == onEthAddListener) {
                CpLog.e(TAG, "onEthAddListener is null!");
                continue;
            }

            onEthAddListener.onEthAdd(ethWallet);
        }
    }

    public void notifyEthStateUpdate(EthWallet ethWallet) {
        if (null == mOnEthStateUpdateListeners) {
            CpLog.e(TAG, "mOnEthStateUpdateListeners is null!");
            return;
        }

        for (OnEthStateUpdateListener onEthStateUpdateListener : mOnEthStateUpdateListeners) {
            if (null == onEthStateUpdateListener) {
                CpLog.e(TAG, "onEthStateUpdateListener is null!");
                continue;
            }

            onEthStateUpdateListener.onEthStateUpdate(ethWallet);
        }
    }
}
