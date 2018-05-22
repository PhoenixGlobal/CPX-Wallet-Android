package chinapex.com.wallet.view;

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

public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = WalletDetailActivity.class.getSimpleName();
    private Button mBt_wallet_detail_transfer;
    private Button mBt_wallet_detail_gathering;
    private TextView mTv_wallet_detail_wallet_name;
    private TextView mTv_wallet_detail_balance;
    private TextView mTv_wallet_detail_addr;
    private WalletBean mWalletBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = (WalletBean) intent.getParcelableExtra(Constant.WALLET_BEAN);
        CpLog.i(TAG, "walletBean:" + mWalletBean.toString());
        mTv_wallet_detail_wallet_name.setText(mWalletBean.getWalletName());
        mTv_wallet_detail_balance.setText(String.valueOf(mWalletBean.getBalance()));
        mTv_wallet_detail_addr.setText(mWalletBean.getWalletAddr());

    }

    private void initView() {
        mBt_wallet_detail_transfer = (Button) findViewById(R.id.bt_wallet_detail_transfer);
        mBt_wallet_detail_gathering = (Button) findViewById(R.id.bt_wallet_detail_gathering);
        mTv_wallet_detail_wallet_name = (TextView) findViewById(R.id.tv_wallet_detail_wallet_name);
        mTv_wallet_detail_balance = (TextView) findViewById(R.id.tv_wallet_detail_balance);
        mTv_wallet_detail_addr = (TextView) findViewById(R.id.tv_wallet_detail_addr);

        mBt_wallet_detail_transfer.setOnClickListener(this);
        mBt_wallet_detail_gathering.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_wallet_detail_transfer:
                startActivityParcelable(TransferActivity.class, true, Constant
                        .PARCELABLE_WALLET_BEAN, mWalletBean);
                break;
            case R.id.bt_wallet_detail_gathering:
                break;
            default:
                break;
        }
    }
}
