package chinapex.com.wallet.view.wallet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

public class BackupWalletActivity extends BaseActivity {

    private static final String TAG = BackupWalletActivity.class.getSimpleName();
    private TextView mTv_backup_title;
    private Toolbar mTb_backup;
    private String[] mBackupTitles;
    private String mBackupMnemonic;
    private String mWhereFromActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_wallet);

        initData();
        initView();
        initToolBar();
        initFragment();
    }

    private void initData() {
        mBackupTitles = getResources().getStringArray(R.array.backup_item_title);

        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mBackupMnemonic = intent.getStringExtra(Constant.BACKUP_MNEMONIC);
        if (TextUtils.isEmpty(mBackupMnemonic)) {
            CpLog.e(TAG, "mBackupMnemonic is null!");
            return;
        }

        mWhereFromActivity = intent.getStringExtra(Constant.WHERE_FROM_ACTIVITY);
        if (TextUtils.isEmpty(mWhereFromActivity)) {
            CpLog.e(TAG, "mWhereFromActivity is null!");
        }
    }

    private void initView() {
        mTb_backup = (Toolbar) findViewById(R.id.tb_backup);
        mTv_backup_title = (TextView) findViewById(R.id.tv_backup_title);
    }

    private void initToolBar() {
        mTb_backup.setTitle("");
        setSupportActionBar(mTb_backup);
        mTv_backup_title.setText(mBackupTitles[0]);

    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 7; i < mBackupTitles.length + 7; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(i + "");
            if (null != fragment) {
                fragmentTransaction.remove(fragment);
            }
        }
        fragmentTransaction.add(R.id.fl_backup, FragmentFactory.getFragment(7), "7");
        fragmentTransaction.commit();

        mTv_backup_title.setText(mBackupTitles[0]);
    }

    public String[] getBackupTitles() {
        return mBackupTitles;
    }

    public String getBackupMnemonic() {
        return mBackupMnemonic;
    }

    public String getWhereFromActivity() {
        return mWhereFromActivity;
    }
}
