package chinapex.com.wallet.model.balance;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.IGetErc20BalanceCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthBalanceCallback;
import chinapex.com.wallet.executor.runnable.eth.GetErc20Balance;
import chinapex.com.wallet.executor.runnable.eth.GetEthBalance;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;

/**
 * Created by SteelCabbage on 2018/8/17 0017 11:21.
 * E-Mail：liuyi_61@163.com
 */

public class GetEthBalanceModel implements IGetBalanceModel, IGetEthBalanceCallback, IGetErc20BalanceCallback {

    private static final String TAG = GetEthBalanceModel.class.getSimpleName();

    private IGetBalanceModelCallback mIGetBalanceModelCallback;
    private List<String> mGlobalAssets;
    private List<String> mColorAssets;
    private int mColorAssetNum;
    private int mColorAssetCounter;

    public GetEthBalanceModel(IGetBalanceModelCallback IGetBalanceModelCallback) {
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

        TaskController.getInstance().submit(new GetEthBalance(walletBean.getAddress(), this));
    }

    @Override
    public void getEthBalance(Map<String, BalanceBean> balanceBeans) {
        if (null == mIGetBalanceModelCallback) {
            CpLog.e(TAG, "mIGetBalanceModelCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        HashMap<String, BalanceBean> globalBalanceBeans = new HashMap<>();

        if (null == balanceBeans || balanceBeans.isEmpty()) {
            for (String globalAsset : mGlobalAssets) {
                AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_ETH_ASSETS, globalAsset);
                if (null == assetBean) {
                    CpLog.e(TAG, "assetBean is null!");
                    continue;
                }

                BalanceBean balanceBean = new BalanceBean();
                balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
                balanceBean.setWalletType(Constant.WALLET_TYPE_ETH);
                balanceBean.setAssetsID(globalAsset);
                balanceBean.setAssetSymbol(assetBean.getSymbol());
                balanceBean.setAssetType(Constant.ASSET_TYPE_ETH);
                balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
                balanceBean.setAssetsValue("0");
                globalBalanceBeans.put(globalAsset, balanceBean);
            }

            mIGetBalanceModelCallback.getGlobalBalanceModel(globalBalanceBeans);
            return;
        }

        for (String globalAsset : mGlobalAssets) {
            AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_ETH_ASSETS, globalAsset);
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            BalanceBean balanceBean = new BalanceBean();
            balanceBean.setMapState(Constant.MAP_STATE_UNFINISHED);
            balanceBean.setWalletType(Constant.WALLET_TYPE_ETH);
            balanceBean.setAssetsID(globalAsset);
            balanceBean.setAssetSymbol(assetBean.getSymbol());
            balanceBean.setAssetType(Constant.ASSET_TYPE_ETH);
            balanceBean.setAssetDecimal(Integer.valueOf(assetBean.getPrecision()));
            if (balanceBeans.containsKey(globalAsset)) {
                balanceBean.setAssetsValue(balanceBeans.get(globalAsset).getAssetsValue());
            } else {
                balanceBean.setAssetsValue("0");
            }
            globalBalanceBeans.put(globalAsset, balanceBean);
        }
        mIGetBalanceModelCallback.getGlobalBalanceModel(globalBalanceBeans);
    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {
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
        for (String colorAsset : mColorAssets) {
            if (TextUtils.isEmpty(colorAsset)) {
                CpLog.e(TAG, "colorAsset is null or empty!");
                continue;
            }

            TaskController.getInstance().submit(new GetErc20Balance(colorAsset, walletBean.getAddress(), this));
        }
    }

    @Override
    public void getErc20Balance(Map<String, BalanceBean> balanceBeans) {
        HashMap<String, BalanceBean> balanceBeansFinal = new HashMap<>();
        mColorAssetCounter++;
        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "balanceBeans is null!");
            if (mColorAssetCounter >= mColorAssetNum) {
                mIGetBalanceModelCallback.getColorBalanceModel(balanceBeansFinal);
            }
            return;
        }

        for (Map.Entry<String, BalanceBean> balance : balanceBeans.entrySet()) {
            if (null == balance) {
                CpLog.e(TAG, "balance is null!");
                continue;
            }

            balanceBeansFinal.put(balance.getKey(), balance.getValue());
        }

        if (mColorAssetCounter >= mColorAssetNum) {
            mIGetBalanceModelCallback.getColorBalanceModel(balanceBeansFinal);
        }
    }
}
