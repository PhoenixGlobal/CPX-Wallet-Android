package chinapex.com.wallet.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/3/29 0029.
 */

public class ApexWalletDbDao {

    private static final String TAG = ApexWalletDbDao.class.getSimpleName();

//    private static final String DB_PWD = "";

    private static ApexWalletDbDao sApexWalletDbDao;

    private AtomicInteger mOpenCounter = new AtomicInteger();

    private ApexWalletDbHelper mApexWalletDbHelper;

    private SQLiteDatabase mDatabase;

    private ReentrantLock mReentrantLock;

    private Context mContext;

    private ApexWalletDbDao(Context context) {
        mContext = context;
        mApexWalletDbHelper = new ApexWalletDbHelper(context);
        mReentrantLock = new ReentrantLock();
    }

    public static ApexWalletDbDao getInstance(Context context) {
        if (null == context) {
            CpLog.e(TAG, "context is null!");
            return null;
        }

        if (null == sApexWalletDbDao) {
            synchronized (ApexWalletDbDao.class) {
                if (null == sApexWalletDbDao) {
                    sApexWalletDbDao = new ApexWalletDbDao(context);
                }
            }
        }

        return sApexWalletDbDao;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mApexWalletDbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }


    public synchronized void insert(String tableName, WalletBean walletBean) {
        if (TextUtils.isEmpty(tableName) || null == walletBean) {
            CpLog.e(TAG, "insert() -> tableName or walletBean is null!");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.FIELD_WALLET_NAME, walletBean.getWalletName());
        contentValues.put(Constant.FIELD_WALLET_ADDRESS, walletBean.getWalletAddr());
        contentValues.put(Constant.FIELD_BACKUP_STATE, walletBean.getBackupState());
        contentValues.put(Constant.FIELD_WALLET_KEYSTORE, walletBean.getKeyStore());
        contentValues.put(Constant.FIELD_CREATE_TIME, SystemClock.currentThreadTimeMillis());

        SQLiteDatabase db = openDatabase();
        try {
            db.beginTransaction();
            db.insertOrThrow(tableName, null, contentValues);
            db.setTransactionSuccessful();
            CpLog.i(TAG, "insert() -> insert " + walletBean.getWalletName() + " ok!");
        } catch (SQLException e) {
            CpLog.e(TAG, "insert exception:" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        closeDatabase();
    }

    private static final String WHERE_CLAUSE_WALLET_NAME_EQ = Constant.FIELD_WALLET_NAME + " = ?";

    public void deleteByWalletName(String tableName, String walletName) {
        if (TextUtils.isEmpty(walletName)) {
            CpLog.e(TAG, "deleteByWalletName() -> walletName is null!");
            return;
        }

        SQLiteDatabase db = openDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, WHERE_CLAUSE_WALLET_NAME_EQ, new String[]{walletName});
            db.setTransactionSuccessful();
            CpLog.i(TAG, "deleteByWalletName() -> delete " + walletName + " ok!");
        } catch (Exception e) {
            CpLog.e(TAG, "deleteByWalletName exception:" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        closeDatabase();
    }

    public List<WalletBean> queryWalletBeans(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            CpLog.e(TAG, "queryAll() -> tableName is null!");
            return null;
        }

        ArrayList<WalletBean> walletBeans = new ArrayList<>();

        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                int walletNameIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_NAME);
                int walletAddressIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_ADDRESS);
                int backupStateIndex = cursor.getColumnIndex(Constant.FIELD_BACKUP_STATE);
                int walletKeystoreIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_KEYSTORE);

                String walletName = cursor.getString(walletNameIndex);
                String walletAddress = cursor.getString(walletAddressIndex);
                int backupState = cursor.getInt(backupStateIndex);
                String walletKeystore = cursor.getString(walletKeystoreIndex);

                WalletBean walletBean = new WalletBean();
                walletBean.setWalletName(walletName);
                walletBean.setWalletAddr(walletAddress);
                walletBean.setBackupState(backupState);
                walletBean.setKeyStore(walletKeystore);

                walletBeans.add(walletBean);
            }
            cursor.close();
        }
        closeDatabase();
        return walletBeans;
    }

    public WalletBean queryByWalletName(String tableName, String walletName) {
        if (TextUtils.isEmpty(tableName)
                || TextUtils.isEmpty(walletName)) {
            CpLog.e(TAG, "queryByWalletName() -> tableName or walletName is null!");
            return null;
        }

        ArrayList<WalletBean> walletBeans = new ArrayList<>();

        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.query(tableName, null, WHERE_CLAUSE_WALLET_NAME_EQ, new
                String[]{walletName}, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                int walletNameIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_NAME);
                int walletAddressIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_ADDRESS);
                int backupStateIndex = cursor.getColumnIndex(Constant.FIELD_BACKUP_STATE);
                int walletKeystoreIndex = cursor.getColumnIndex(Constant.FIELD_WALLET_KEYSTORE);

                String walletNameTmp = cursor.getString(walletNameIndex);
                String walletAddress = cursor.getString(walletAddressIndex);
                int backupState = cursor.getInt(backupStateIndex);
                String walletKeystore = cursor.getString(walletKeystoreIndex);

                WalletBean walletBean = new WalletBean();
                walletBean.setWalletName(walletNameTmp);
                walletBean.setWalletAddr(walletAddress);
                walletBean.setBackupState(backupState);
                walletBean.setKeyStore(walletKeystore);

                walletBeans.add(walletBean);
            }
            cursor.close();
        }
        closeDatabase();
        return walletBeans.get(0);
    }

    public void updateBackupStateByWalletName(String tableName, String walletName, int
            backupState) {
        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(walletName)) {
            CpLog.e(TAG, "insert() -> tableName or walletName is null!");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.FIELD_BACKUP_STATE, backupState);

        SQLiteDatabase db = openDatabase();
        try {
            db.beginTransaction();
            db.update(tableName, contentValues, WHERE_CLAUSE_WALLET_NAME_EQ, new
                    String[]{walletName + ""});
            db.setTransactionSuccessful();
            CpLog.i(TAG, "updateBackupStateByWalletName() -> update" + backupState + " ok!");
        } catch (SQLException e) {
            CpLog.e(TAG, "updateBackupStateByWalletName exception:" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        closeDatabase();
    }

    public void updateWalletNameByWalletName(String tableName, String walletNameOld, String
            walletNameNew) {
        if (TextUtils.isEmpty(tableName)
                || TextUtils.isEmpty(walletNameOld)
                || TextUtils.isEmpty(walletNameNew)) {
            CpLog.e(TAG, "insert() -> tableName or walletName or walletNameNew is null!");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.FIELD_WALLET_NAME, walletNameNew);

        SQLiteDatabase db = openDatabase();
        try {
            db.beginTransaction();
            db.update(tableName, contentValues, WHERE_CLAUSE_WALLET_NAME_EQ, new
                    String[]{walletNameOld + ""});
            db.setTransactionSuccessful();
            CpLog.i(TAG, "updateBackupStateByWalletName() -> update " + walletNameOld + " ok!");
        } catch (SQLException e) {
            CpLog.e(TAG, "updateBackupStateByWalletName exception:" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        closeDatabase();
    }
}
