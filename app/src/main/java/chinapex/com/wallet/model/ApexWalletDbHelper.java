package chinapex.com.wallet.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/3/28 0028.
 */

public class ApexWalletDbHelper extends SQLiteOpenHelper {

    private static final String TAG = ApexWalletDbHelper.class.getSimpleName();

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "apexData";

    public ApexWalletDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
//        SQLiteDatabase.loadLibs(context);
    }

    public ApexWalletDbHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.SQL_CREATE_APEX_WALLET);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
