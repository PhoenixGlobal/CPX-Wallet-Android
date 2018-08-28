package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;

import java.util.HashMap;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class BalanceDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = BalanceDetailActivity.class.getSimpleName();
    private Button mBt_balance_detail_transfer;
    private Button mBt_balance_detail_gathering;
    private TextView mTv_balance_detail_assets_name;
    private TextView mTv_balance_detail_assets_value;
    private WalletBean mWalletBean;
    private BalanceBean mBalanceBean;
    private LinearLayout mLl_balance_detail_map;
    // QR_CODE activity请求码
    private final static int REQ_CODE = 1028;
    private ImageButton mIb_balance_detail_scan;
    private TextView mTv_balance_detail_wallet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_detail);

        initView();
        initData();
    }

    private void initView() {
        mBt_balance_detail_transfer = (Button) findViewById(R.id.bt_balance_detail_transfer);
        mBt_balance_detail_gathering = (Button) findViewById(R.id.bt_balance_detail_gathering);
        mTv_balance_detail_assets_name = (TextView) findViewById(R.id.tv_balance_detail_assets_name);
        mTv_balance_detail_assets_value = (TextView) findViewById(R.id.tv_balance_detail_assets_value);
        mLl_balance_detail_map = (LinearLayout) findViewById(R.id.ll_balance_detail_map);
        mIb_balance_detail_scan = (ImageButton) findViewById(R.id.ib_balance_detail_scan);
        mTv_balance_detail_wallet_name = (TextView) findViewById(R.id.tv_balance_detail_wallet_name);

        mBt_balance_detail_transfer.setOnClickListener(this);
        mBt_balance_detail_gathering.setOnClickListener(this);
        mIb_balance_detail_scan.setOnClickListener(this);

    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = intent.getParcelableExtra(Constant.WALLET_BEAN);
        mBalanceBean = intent.getParcelableExtra(Constant.BALANCE_BEAN);

        if (null == mWalletBean || null == mBalanceBean) {
            CpLog.e(TAG, "mNeoWallet or mBalanceBean is null!");
            return;
        }

        mLl_balance_detail_map.setVisibility(View.INVISIBLE);
        mTv_balance_detail_assets_name.setText(mBalanceBean.getAssetSymbol());
        mTv_balance_detail_assets_value.setText(mBalanceBean.getAssetsValue());
        mTv_balance_detail_wallet_name.setText(mWalletBean.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_balance_detail_transfer:
                Intent intentTransfer = new Intent(ApexWalletApplication.getInstance(), TransferActivity.class);
                intentTransfer.putExtra(Constant.PARCELABLE_WALLET_BEAN_TRANSFER, mWalletBean);
                intentTransfer.putExtra(Constant.PARCELABLE_BALANCE_BEAN_TRANSFER, mBalanceBean);
                startActivity(intentTransfer);
                break;
            case R.id.bt_balance_detail_gathering:
                startActivityParcelable(GatheringActivity.class, false, Constant.PARCELABLE_WALLET_BEAN_GATHERING, mWalletBean);
                break;
            case R.id.ib_balance_detail_scan:
                CpLog.i(TAG, "扫一扫");
                Intent intent = new Intent(BalanceDetailActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            default:
                break;
        }
    }

    // QR_CODE Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQ_CODE) {
            CpLog.e(TAG, "requestCode != REQ_CODE");
            return;
        }

        if (null == data) {
            CpLog.e(TAG, "onActivityResult() -> data is null!");
            return;
        }

        String qrCode = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
        if (TextUtils.isEmpty(qrCode)) {
            CpLog.e(TAG, "qrCode is null or empty!");
            return;
        }

        Intent intent = new Intent(BalanceDetailActivity.this, TransferActivity.class);
        intent.putExtra(Constant.PARCELABLE_WALLET_BEAN_TRANSFER, mWalletBean);
        intent.putExtra(Constant.PARCELABLE_BALANCE_BEAN_TRANSFER, mBalanceBean);
        intent.putExtra(Constant.PARCELABLE_QR_CODE_TRANSFER, qrCode);
        startActivity(intent);
    }
}
