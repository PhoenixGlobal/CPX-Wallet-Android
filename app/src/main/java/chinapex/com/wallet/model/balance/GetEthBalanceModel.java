package chinapex.com.wallet.model.balance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.IGetEthBalanceCallback;
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

public class GetEthBalanceModel implements IGetBalanceModel, IGetEthBalanceCallback {
    private static final String TAG = GetEthBalanceModel.class.getSimpleName();
    private IGetBalanceModelCallback mIGetBalanceModelCallback;
    private List<String> mGlobalAssets;
    private List<String> mColorAssets;

    public GetEthBalanceModel(IGetBalanceModelCallback IGetBalanceModelCallback) {
        mIGetBalanceModelCallback = IGetBalanceModelCallback;
    }

    @Override
    public void init() {

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

        List<BalanceBean> globalBalanceBeans = new ArrayList<>();

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
                globalBalanceBeans.add(balanceBean);
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
            globalBalanceBeans.add(balanceBean);
        }
        mIGetBalanceModelCallback.getGlobalBalanceModel(globalBalanceBeans);
    }

    @Override
    public void getColorAssetBalance(WalletBean walletBean) {
        // TODO: 2018/8/28 0028  erc20
        mIGetBalanceModelCallback.getColorBalanceModel(new ArrayList<BalanceBean>());
    }


}
