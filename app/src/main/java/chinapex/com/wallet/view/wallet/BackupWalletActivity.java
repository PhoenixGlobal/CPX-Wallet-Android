package chinapex.com.wallet.view.wallet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

public class BackupWalletActivity extends BaseActivity {

    private static final String TAG = BackupWalletActivity.class.getSimpleName();
    private TextView mTv_backup_title;
    private Toolbar mTb_backup;
    private String[] mBackupTitles;
    private String mBackupMnemonic;
    private WalletBean mWalletBean;


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
        mWalletBean = intent.getParcelableExtra(Constant.WALLET_BEAN);

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
        fragmentTransaction.add(R.id.fl_backup, FragmentFactory.getFragment(Constant.FRAGMENT_TAG_BACKUP), Constant
                .FRAGMENT_TAG_BACKUP);
        fragmentTransaction.commit();

        mTv_backup_title.setText(mBackupTitles[0]);
    }

    public String[] getBackupTitles() {
        return mBackupTitles;
    }

    public String getBackupMnemonic() {
        return mBackupMnemonic;
    }

    public WalletBean getWalletBean() {
        return mWalletBean;
    }
}
