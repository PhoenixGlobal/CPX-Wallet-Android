package chinapex.com.wallet.view.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;

public class GatheringActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = GatheringActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private TextView mTv_gathering_wallet_name;
    private TextView mTv_gathering_wallet_addr;
    private Button mBt_gathering_copy_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering);

        initView();
        initData();
    }

    private void initView() {
        mTv_gathering_wallet_name = (TextView) findViewById(R.id.tv_gathering_wallet_name);
        mTv_gathering_wallet_addr = (TextView) findViewById(R.id.tv_gathering_wallet_addr);
        mBt_gathering_copy_addr = (Button) findViewById(R.id.bt_gathering_copy_addr);

        mBt_gathering_copy_addr.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = (WalletBean) intent.getParcelableExtra(Constant
                .PARCELABLE_WALLET_BEAN_GATHERING);
        if (null == mWalletBean) {
            CpLog.e(TAG, "mWalletBean is null!");
            return;
        }
        mTv_gathering_wallet_name.setText(String.valueOf(Constant.WALLET_NAME + mWalletBean
                .getWalletName()));
        mTv_gathering_wallet_addr.setText(mWalletBean.getWalletAddr());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_gathering_copy_addr:
                CpLog.i(TAG, "bt_gathering_copy_addr is click！");
                PhoneUtils.copy2Clipboard(this, "bt_gathering_copy_addr is click！");
                break;
            default:
                break;
        }
    }

}
