package chinapex.com.wallet.executor.runnable.eth;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.response.ResponseGetEthTransactionHistory;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.executor.callback.eth.IGetEthTransactionHistoryCallback;
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

public class GetEthTransactionHistory implements Runnable, INetCallback {

    private static final String TAG = GetEthTransactionHistory.class.getSimpleName();

    private String mAddress;
    private IGetEthTransactionHistoryCallback mIGetEthTransactionHistoryCallback;

    public GetEthTransactionHistory(String address, IGetEthTransactionHistoryCallback IGetEthTransactionHistoryCallback) {
        mAddress = address;
        mIGetEthTransactionHistoryCallback = IGetEthTransactionHistoryCallback;
    }

    @Override
    public void run() {
        if (null == mIGetEthTransactionHistoryCallback || TextUtils.isEmpty(mAddress)) {
            CpLog.e(TAG, "mIGetEthTransactionHistoryCallback or mAddress is null!");
            return;
        }

        long startBlock;
        try {
            String startBlockSP = (String) SharedPreferencesUtils.getParam(ApexWalletApplication.getInstance(), mAddress, "0");
            startBlock = Long.valueOf(startBlockSP);
        } catch (NumberFormatException e) {
            CpLog.e(TAG, "GetEthTransactionHistory NumberFormatException:" + e.getMessage());
            return;
        }

        CpLog.i(TAG, "startBlock:" + startBlock);

        String url = Constant.URL_ETH_TRANSACTION_HISTORY
                + mAddress
                + "&startblock=" + (startBlock + 1);
        OkHttpClientManager.getInstance().get(url, this);
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        if (TextUtils.isEmpty(result)) {
            CpLog.e(TAG, "result is null!");
            mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
            return;
        }

        ResponseGetEthTransactionHistory responseGetEthTransactionHistory = GsonUtils.json2Bean(result,
                ResponseGetEthTransactionHistory.class);
        if (null == responseGetEthTransactionHistory) {
            CpLog.e(TAG, "responseGetEthTransactionHistory is null!");
            mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
            return;
        }

        List<ResponseGetEthTransactionHistory.DataBean> dataBeans = responseGetEthTransactionHistory.getData();
        if (null == dataBeans || dataBeans.isEmpty()) {
            CpLog.w(TAG, "dataBeans is null or empty!");
            mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
            return;
        }

        HashMap<String, TransactionRecord> txCacheByAddress = apexWalletDbDao.queryTxCacheByAddress(Constant
                .TABLE_ETH_TX_CACHE, mAddress);

        if (null == txCacheByAddress) {
            CpLog.e(TAG, "txCacheByAddress is null!");
            mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
            return;
        }

        // 记录该地址的最新交易所在区块
        SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), mAddress, dataBeans.get(dataBeans.size() - 1)
                .getBlock_number());

        List<TransactionRecord> transactionRecords = new ArrayList<>();

        for (ResponseGetEthTransactionHistory.DataBean dataBean : dataBeans) {
            if (null == dataBean) {
                CpLog.e(TAG, "dataBean is null!");
                continue;
            }

            TransactionRecord transactionRecord = new TransactionRecord();

            // 如果缓存中包含相同txid，删除缓存中该地址对应的该条txid，并写入正式表，状态为确认中
            String txID = dataBean.getTxid();
            String txType = dataBean.getType();
            long txTime = dataBean.getTime();
            String assetId = dataBean.getAssetId();
            if (txCacheByAddress.containsKey(txID)) {
                transactionRecord.setTxState(Constant.TRANSACTION_STATE_CONFIRMING);
                ApexListeners.getInstance().notifyTxStateUpdate(txID, Constant.TRANSACTION_STATE_CONFIRMING, txTime);
                apexWalletDbDao.delCacheByTxIDAndAddr(Constant.TABLE_ETH_TX_CACHE, txID, mAddress);
            } else {
                String vmstate = dataBean.getVmstate();
                if (TextUtils.isEmpty(vmstate)) {
                    transactionRecord.setTxState(Constant.TRANSACTION_STATE_SUCCESS);
                } else {
                    switch (vmstate) {
                        case "0":
                            transactionRecord.setTxState(Constant.TRANSACTION_STATE_SUCCESS);
                            break;
                        case "1":
                            transactionRecord.setTxState(Constant.TRANSACTION_STATE_FAIL);
                            break;
                        default:
                            break;
                    }
                }
            }
            transactionRecord.setWalletAddress(mAddress);
            transactionRecord.setTxType(txType);
            transactionRecord.setTxID(txID);
            transactionRecord.setTxAmount(dataBean.getValue());
            transactionRecord.setTxFrom(dataBean.getFrom());
            transactionRecord.setTxTo(dataBean.getTo());
            transactionRecord.setGasConsumed(null == dataBean.getGas_consumed() ? "0" : dataBean.getGas_consumed());
            transactionRecord.setAssetID(assetId);
            transactionRecord.setAssetSymbol(dataBean.getSymbol());
            transactionRecord.setAssetLogoUrl(dataBean.getImageURL());
            transactionRecord.setAssetDecimal(TextUtils.isEmpty(dataBean.getDecimal()) ?
                    0 : Integer.valueOf(dataBean.getDecimal()));
            transactionRecord.setGasPrice(dataBean.getGas_price());
            transactionRecord.setBlockNumber(dataBean.getBlock_number());
            transactionRecord.setGasFee(dataBean.getGas_fee());
            transactionRecord.setTxTime(txTime);

            List<TransactionRecord> txsByTxIdAndAddress = apexWalletDbDao.queryTxByTxIdAndAddress
                    (Constant.TABLE_ETH_TRANSACTION_RECORD, txID, mAddress);
            if (null == txsByTxIdAndAddress || txsByTxIdAndAddress.isEmpty()) {
                apexWalletDbDao.insertTxRecord(Constant.TABLE_ETH_TRANSACTION_RECORD, transactionRecord);
                transactionRecords.add(transactionRecord);
            }
        }

        mIGetEthTransactionHistoryCallback.getEthTransactionHistory(transactionRecords);
    }

    @Override
    public void onFailed(int failedCode, String msg) {
        CpLog.e(TAG, "GetEthTransactionHistory net onFailed!");
        mIGetEthTransactionHistoryCallback.getEthTransactionHistory(null);
    }
}
