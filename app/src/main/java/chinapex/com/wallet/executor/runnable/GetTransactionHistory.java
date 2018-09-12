package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.response.ResponseGetTransactionHistory;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.callback.IGetTransactionHistoryCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;

/**
 * Created by SteelCabbage on 2018/6/22 0022 11:42.
 * E-Mail：liuyi_61@163.com
 */

public class GetTransactionHistory implements Runnable, INetCallback {

    private static final String TAG = GetTransactionHistory.class.getSimpleName();

    private String mAddress;
    private IGetTransactionHistoryCallback mIGetTransactionHistoryCallback;
    private long mRecentTime;

    public GetTransactionHistory(String address, IGetTransactionHistoryCallback
            IGetTransactionHistoryCallback) {
        mAddress = address;
        mIGetTransactionHistoryCallback = IGetTransactionHistoryCallback;
    }

    @Override
    public void run() {
        if (null == mIGetTransactionHistoryCallback || TextUtils.isEmpty(mAddress)) {
            CpLog.e(TAG, "mIGetTransactionHistoryCallback or mAddress is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        mRecentTime = (long) SharedPreferencesUtils.getParam(ApexWalletApplication.getInstance(),
                mAddress, 0L);
        CpLog.i(TAG, "mRecentTime:" + mRecentTime);

        String url = Constant.URL_TRANSACTION_HISTORY + mAddress + "?beginTime=" + mRecentTime;
        OkHttpClientManager.getInstance().get(url, this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        if (TextUtils.isEmpty(result)) {
            CpLog.e(TAG, "result is null!");
            mIGetTransactionHistoryCallback.getTransactionHistory(null);
            return;
        }

        ResponseGetTransactionHistory responseGetTransactionHistory = GsonUtils.json2Bean(result,
                ResponseGetTransactionHistory.class);
        if (null == responseGetTransactionHistory) {
            CpLog.e(TAG, "responseGetTransactionHistory is null!");
            mIGetTransactionHistoryCallback.getTransactionHistory(null);
            return;
        }

        List<ResponseGetTransactionHistory.ResultBean> resultBeans = responseGetTransactionHistory
                .getResult();
        if (null == resultBeans || resultBeans.isEmpty()) {
            CpLog.w(TAG, "resultBeans is null or empty!");
            mIGetTransactionHistoryCallback.getTransactionHistory(null);
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mIGetTransactionHistoryCallback.getTransactionHistory(null);
            return;
        }

        HashMap<String, TransactionRecord> txCacheByAddress = apexWalletDbDao
                .queryTxCacheByAddress(Constant.TABLE_TX_CACHE, mAddress);

        if (null == txCacheByAddress) {
            CpLog.e(TAG, "txCacheByAddress is null!");
            mIGetTransactionHistoryCallback.getTransactionHistory(null);
            return;
        }

        // 记录该地址的最近更新时间
        SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), mAddress,
                resultBeans.get(resultBeans.size() - 1).getTime());

        List<TransactionRecord> transactionRecords = new ArrayList<>();

        for (ResponseGetTransactionHistory.ResultBean resultBean : resultBeans) {
            if (null == resultBean) {
                CpLog.e(TAG, "resultBean is null!");
                continue;
            }

            TransactionRecord transactionRecord = new TransactionRecord();

            // 如果缓存中包含相同txid，删除缓存中该地址对应的该条txid，并写入正式表，状态为确认中
            String txID = resultBean.getTxid();
            String txType = resultBean.getType();
            long txTime = resultBean.getTime();
            String assetId = resultBean.getAssetId();
            if (txCacheByAddress.containsKey(txID)) {
                transactionRecord.setTxState(Constant.TRANSACTION_STATE_CONFIRMING);
                ApexListeners.getInstance().notifyTxStateUpdate(txID, Constant
                        .TRANSACTION_STATE_CONFIRMING, txTime);
                apexWalletDbDao.delCacheByTxIDAndAddr(Constant.TABLE_TX_CACHE, txID, mAddress);
            } else {
                switch (txType) {
                    case Constant.ASSET_TYPE_NEP5:
                        String vmstate = (String) resultBean.getVmstate();
                        if (!TextUtils.isEmpty(vmstate) && !vmstate.contains("FAULT")) {
                            transactionRecord.setTxState(Constant.TRANSACTION_STATE_SUCCESS);
                        } else {
                            transactionRecord.setTxState(Constant.TRANSACTION_STATE_FAIL);
                        }
                        break;
                    default:
                        transactionRecord.setTxState(Constant.TRANSACTION_STATE_SUCCESS);
                        break;
                }

            }
            transactionRecord.setWalletAddress(mAddress);
            transactionRecord.setTxType(txType);
            transactionRecord.setTxID(txID);
            transactionRecord.setTxAmount(resultBean.getValue());
            transactionRecord.setTxFrom(resultBean.getFrom());
            transactionRecord.setTxTo(resultBean.getTo());
            transactionRecord.setGasConsumed(null == resultBean.getGas_consumed() ? "0" : (String)
                    resultBean.getGas_consumed());
            transactionRecord.setAssetID(assetId);
            switch (assetId) {
                case Constant.ASSETS_NEO_GAS:
                    transactionRecord.setAssetSymbol(Constant.SYMBOL_NEO_GAS);
                    break;
                default:
                    transactionRecord.setAssetSymbol(resultBean.getSymbol());
                    break;

            }
            transactionRecord.setAssetLogoUrl(resultBean.getImageURL());
            transactionRecord.setAssetDecimal(null == resultBean.getDecimal() ? 0 : Integer
                    .valueOf((String) resultBean.getDecimal()));
            transactionRecord.setTxTime(txTime);

            List<TransactionRecord> txsByTxIdAndAddress = apexWalletDbDao.queryTxByTxIdAndAddress
                    (Constant.TABLE_TRANSACTION_RECORD, txID, mAddress);
            if (null == txsByTxIdAndAddress || txsByTxIdAndAddress.isEmpty()) {
                apexWalletDbDao.insertTxRecord(Constant.TABLE_TRANSACTION_RECORD, transactionRecord);
                transactionRecords.add(transactionRecord);
            }
        }

        mIGetTransactionHistoryCallback.getTransactionHistory(transactionRecords);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "getTransactionHistory net onFailed!");
        mIGetTransactionHistoryCallback.getTransactionHistory(null);
    }
}
