package chinapex.com.wallet.model.balance;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IGetAccountStateCallback;
import chinapex.com.wallet.executor.callback.IGetNep5BalanceCallback;
import chinapex.com.wallet.executor.runnable.GetAccountState;
import chinapex.com.wallet.executor.runnable.GetNep5Balance;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/8/17 0017 11:21.
 * E-Mail：liuyi_61@163.com
 */

public class GetNeoBalanceModel implements IGetBalanceModel, IGetAccountStateCallback, IGetNep5BalanceCallback {
    private static final String TAG = GetNeoBalanceModel.class.getSimpleName();
    private IGetBalanceModelCallback mIGetBalanceModelCallback;
    private List<String> mGlobalAssets;
    private List<String> mColorAssets;
    private List<BalanceBean> mColorAssetBalanceBeans;
    private int mColorAssetNum;
    private int mColorAssetCounter;

    public GetNeoBalanceModel(IGetBalanceModelCallback IGetBalanceModelCallback) {
        mIGetBalanceModelCallback = IGetBalanceModelCallback;
    }

    @Override
    public void init() {
        mColorAssetCounter = 0;
    }

    @Override
    public void getGlobalAssetBalance(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "getGlobalAssetBalance() -> walletBean is null!");
            return;
        }

        String assetJson = walletBean.getAssetJson();
        mGlobalAssets = GsonUtils.json2List(assetJson, String.class);
        if (null == mGlobalAssets || mGlobalAssets.isEmpty()) {
            CpLog.e(TAG, "getGlobalAssetBalance() -> mGlobalAssets is null or empty!");
            return;
        }

        TaskController.getInstance().submit(new GetAccountState(walletBean.getAddress(), this));
    }

    @Override
    public void getNeoGlobalAssetBalance(Map<String, BalanceBean> balanceBeans) {
        if (null == mIGetBalanceModelCallback) {
            CpLog.e(TAG, "mIGetBalanceModelCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        List<BalanceBean> globalBalanceBeans = new ArrayList<>();

        if (null == balanceBeans || balanceBeans.isEmpty()) {
            for (String globalAsset : mGlobalAssets) {
                AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_NEO_ASSETS, globalAsset);
                if (null == assetBean) {
                    CpLog.e(TAG, "assetBean is null!");
                    continue;
                }

                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
                balanceBean.setWalletType(Constant.WALLET_TYPE_NEO);
                balanceBean.setAssetsID(globalAsset);
                balanceBean.setAssetSymbol(assetBean.getSymbol());
                balanceBean.setAssetType(Constant.ASSET_TYPE_GLOBAL);
                balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
                balanceBean.setAssetsValue("0");
                globalBalanceBeans.add(balanceBean);
            }

            mIGetBalanceModelCallback.getGlobalBalanceModel(globalBalanceBeans);
            return;
        }

        for (String globalAsset : mGlobalAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_NEO_ASSETS, globalAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setWalletType(Constant.WALLET_TYPE_NEO);
            balanceBean.setAssetsID(globalAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(Constant.ASSET_TYPE_GLOBAL);
            balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
            if (balanceBeans.containsKey(globalAsset)) {
                balanceBean.setAssetsValue(balanceBeans.get(globalAsset).getAssetsValue());
            } else {
                balanceBean.setAssetsValue("0");
            }
            globalBalanceBeans.add(balanceBean);
        }
        mIGetBalanceModelCallback.getGlobalBalanceModel(globalBalanceBeans);
    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {
        if (null == mIGetBalanceModelCallback) {
            CpLog.e(TAG, "getColorAssetBalance() -> mIGetBalanceModelCallback is null!");
            return;
        }

        if (null == walletBean) {
            CpLog.e(TAG, "getColorAssetBalance() -> walletBean is null!");
            return;
        }

        String colorAssetJson = walletBean.getColorAssetJson();
        mColorAssets = GsonUtils.json2List(colorAssetJson, String.class);
        if (null == mColorAssets || mColorAssets.isEmpty()) {
            CpLog.e(TAG, "getColorAssetBalance() -> mColorAssets is null or empty!");
            return;
        }

        mColorAssetNum = mColorAssets.size();
        mColorAssetBalanceBeans = new ArrayList<>();
        for (String colorAsset : mColorAssets) {
            if (TextUtils.isEmpty(colorAsset)) {
                CpLog.e(TAG, "colorAsset is null or empty!");
                continue;
            }

            TaskController.getInstance().submit(new GetNep5Balance(colorAsset, walletBean.getAddress(), this));
        }
    }

    @Override
    public synchronized void getNep5Balance(Map<String, BalanceBean> balanceBeans) {
        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "getNep5Balance() -> balanceBeans is null!");
            return;
        }

        mColorAssetCounter++;

        for (Map.Entry<String, BalanceBean> balanceBeanEntry : balanceBeans.entrySet()) {
            if (null == balanceBeanEntry) {
                CpLog.e(TAG, "balanceBeanEntry is null!");
                continue;
            }

            BalanceBean balanceBeanTmp = balanceBeanEntry.getValue();
            if (null == balanceBeanTmp) {
                CpLog.e(TAG, "balanceBeanTmp is null!");
                continue;
            }

            if (Constant.ASSETS_CPX.equals(balanceBeanEntry.getKey())) {
                mColorAssetBalanceBeans.add(0, balanceBeanTmp);
            } else {
                mColorAssetBalanceBeans.add(balanceBeanTmp);
            }
        }

        if (mColorAssetCounter >= mColorAssetNum) {
            mIGetBalanceModelCallback.getColorBalanceModel(mColorAssetBalanceBeans);
        }
    }
}
