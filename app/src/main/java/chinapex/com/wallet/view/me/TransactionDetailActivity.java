package chinapex.com.wallet.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;

public class TransactionDetailActivity extends BaseActivity {

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
        mTv_transaction_detail_unit.setText(String.valueOf("转账金额 (" + mTransactionRecord
                .getSymbol() + ")"));
        mTv_transaction_detail_from.setText(mTransactionRecord.getFrom());
        mTv_transaction_detail_to.setText(mTransactionRecord.getTo());
        mTv_transaction_detail_time.setText(PhoneUtils.getFormatTime(mTransactionRecord.getTime()));
        mTv_transaction_detail_tx_id.setText(mTransactionRecord.getTxID());
    }
}
