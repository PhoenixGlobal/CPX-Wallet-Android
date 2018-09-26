package chinapex.com.wallet.executor.runnable;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.executor.callback.ILoadTransactionRecordCallback;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/6/28 0028 10:21.
 * E-Mail：liuyi_61@163.com
 */

public class LoadTransactionRecord implements Runnable {

    private static final String TAG = LoadTransactionRecord.class.getSimpleName();

    private int mWalletType;
    private String mAddress;
    private ILoadTransactionRecordCallback mILoadTransactionRecordCallback;

    public LoadTransactionRecord(int walletType, String address, ILoadTransactionRecordCallback ILoadTransactionRecordCallback) {
        mWalletType = walletType;
        mAddress = address;
        mILoadTransactionRecordCallback = ILoadTransactionRecordCallback;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(mAddress) || null == mILoadTransactionRecordCallback) {
            CpLog.e(TAG, "mAddress or mILoadTransactionRecordCallback is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mILoadTransactionRecordCallback.loadTransactionRecord(null);
            return;
        }

        String txCacheTableName = null;
        String txTableName = null;
        switch (mWalletType) {
            case Constant.WALLET_TYPE_NEO:
                txCacheTableName = Constant.TABLE_NEO_TX_CACHE;
                txTableName = Constant.TABLE_NEO_TRANSACTION_RECORD;
                break;
            case Constant.WALLET_TYPE_ETH:
                txCacheTableName = Constant.TABLE_ETH_TX_CACHE;
                txTableName = Constant.TABLE_ETH_TRANSACTION_RECORD;
                break;
            case Constant.WALLET_TYPE_CPX:

                break;
            default:
                CpLog.e(TAG, "Illegal wallet type!");
                break;
        }

        List<TransactionRecord> finalTxs = new ArrayList<>();

        List<TransactionRecord> txCacheRecords = apexWalletDbDao.queryTxByAddress(txCacheTableName, mAddress);
        if (null != txCacheRecords && !txCacheRecords.isEmpty()) {
            Collections.reverse(txCacheRecords);
            finalTxs.addAll(txCacheRecords);
        }

        List<TransactionRecord> txRecords = apexWalletDbDao.queryTxByAddress(txTableName, mAddress);
        if (null != txRecords && !txRecords.isEmpty()) {
            Collections.reverse(txRecords);
            finalTxs.addAll(txRecords);
        }

        mILoadTransactionRecordCallback.loadTransactionRecord(finalTxs);
    }
}
