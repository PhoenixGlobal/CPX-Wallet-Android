package chinapex.com.wallet.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.ToastUtils;

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = TransactionDetailActivity.class.getSimpleName();
    private TransactionRecord mTransactionRecord;
    private TextView mTv_transaction_detail_amount;
    private TextView mTv_transaction_detail_unit;
    private TextView mTv_transaction_detail_from;
    private TextView mTv_transaction_detail_to;
    private TextView mTv_transaction_detail_time;
    private TextView mTv_transaction_detail_tx_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        initView();
        initData();
    }

    private void initView() {
        mTv_transaction_detail_amount = (TextView) findViewById(R.id.tv_transaction_detail_amount);
        mTv_transaction_detail_unit = (TextView) findViewById(R.id.tv_transaction_detail_unit);
        mTv_transaction_detail_from = (TextView) findViewById(R.id.tv_transaction_detail_from);
        mTv_transaction_detail_to = (TextView) findViewById(R.id.tv_transaction_detail_to);
        mTv_transaction_detail_time = (TextView) findViewById(R.id.tv_transaction_detail_time);
        mTv_transaction_detail_tx_id = (TextView) findViewById(R.id.tv_transaction_detail_tx_id);

        mTv_transaction_detail_from.setOnClickListener(this);
        mTv_transaction_detail_to.setOnClickListener(this);
        mTv_transaction_detail_tx_id.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mTransactionRecord = intent.getParcelableExtra(Constant.PARCELABLE_TRANSACTION_RECORD);
        if (null == mTransactionRecord) {
            CpLog.e(TAG, "transactionRecord is null!");
            return;
        }

        mTv_transaction_detail_amount.setText(mTransactionRecord.getTxAmount());
        mTv_transaction_detail_unit.setText(String.valueOf(ApexWalletApplication.getInstance()
                .getResources().getString(R.string.transfer_amount) + " (" + mTransactionRecord
                .getAssetSymbol() + ")"));
        mTv_transaction_detail_from.setText(mTransactionRecord.getTxFrom());
        mTv_transaction_detail_to.setText(mTransactionRecord.getTxTo());
        mTv_transaction_detail_time.setText(PhoneUtils.getFormatTime(mTransactionRecord.getTxTime
                ()));
        mTv_transaction_detail_tx_id.setText(mTransactionRecord.getTxID());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_transaction_detail_from:
                String from = mTv_transaction_detail_from.getText().toString().trim();
                copy2Clipboard(from, ApexWalletApplication.getInstance().getResources().getString
                        (R.string.payment_address_copied));
                break;
            case R.id.tv_transaction_detail_to:
                String to = mTv_transaction_detail_to.getText().toString().trim();
                copy2Clipboard(to, ApexWalletApplication.getInstance().getResources().getString
                        (R.string.payee_address_copied));
                break;
            case R.id.tv_transaction_detail_tx_id:
                String txId = mTv_transaction_detail_tx_id.getText().toString().trim();
                copy2Clipboard(txId, ApexWalletApplication.getInstance().getResources().getString
                        (R.string.tx_id_copied));
                break;
            default:
                break;
        }
    }

    private void copy2Clipboard(String content, String toastMsg) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(toastMsg)) {
            CpLog.e(TAG, "content or toastMsg is null!");
            return;
        }

        PhoneUtils.copy2Clipboard(ApexWalletApplication.getInstance(), content);
        ToastUtils.getInstance().showToast(toastMsg);
    }
}
