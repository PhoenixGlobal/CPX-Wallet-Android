package chinapex.com.wallet.executor.runnable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IGetLocalCpxSumCallback;
import chinapex.com.wallet.executor.callback.IGetNep5BalanceCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

public class GetLocalCpxSum implements Runnable, IGetNep5BalanceCallback {

    private static final String TAG = GetLocalCpxSum.class.getSimpleName();

    private IGetLocalCpxSumCallback iGetLocalCpxSumCallback;
    private int cpxWalletNum;
    private int cpxWalletCounter;
    private BigDecimal cpxSum;

    public GetLocalCpxSum(IGetLocalCpxSumCallback iGetLocalCpxSumCallback) {
        this.iGetLocalCpxSumCallback = iGetLocalCpxSumCallback;
    }

    @Override
    public void run() {
        if (null == iGetLocalCpxSumCallback) {
            CpLog.e(TAG, "iGetLocalCpxSumCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        cpxSum = new BigDecimal(0);
        List<WalletBean> walletBeans = apexWalletDbDao.queryWallets(Constant.TABLE_NEO_WALLET);
        if (null == walletBeans || walletBeans.isEmpty()) {
            CpLog.w(TAG, "walletBeans is null or empty!");
            iGetLocalCpxSumCallback.getLocalCpxSum(cpxSum.toPlainString());
            return;
        }

        cpxWalletNum = walletBeans.size();
        for (WalletBean walletBean : walletBeans) {
            if (null == walletBean) {
                CpLog.e(TAG, "walletBean is null!");
                continue;
            }

            TaskController.getInstance().submit(new GetNep5Balance(Constant.ASSETS_CPX,
                    walletBean.getAddress(), this));
        }
    }

    @Override
    public void getNep5Balance(Map<String, BalanceBean> balanceBeans) {
        cpxWalletCounter++;

        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "balanceBeans is null or empty!");
            return;
        }

        BalanceBean balanceBean = balanceBeans.get(Constant.ASSETS_CPX);
        if (null == balanceBean) {
            CpLog.w(TAG, "balanceBean is null!");
        } else {
            cpxSum = cpxSum.add(new BigDecimal(balanceBean.getAssetsValue()));
        }

        if (cpxWalletCounter == cpxWalletNum) {
            iGetLocalCpxSumCallback.getLocalCpxSum(cpxSum.toPlainString());
        }

    }
}
