package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.AssertTxBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICreateAssertTxCallback;
import chinapex.com.wallet.executor.callback.IGetUtxosCallback;
import chinapex.com.wallet.executor.callback.ISendRawTransactionCallback;
import chinapex.com.wallet.executor.runnable.CreateAssertTx;
import chinapex.com.wallet.executor.runnable.GetUtxos;
import chinapex.com.wallet.executor.runnable.SendRawTransaction;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.dialog.TransferPwdDialog;
import neomobile.Tx;
import neomobile.Wallet;

public class TransferActivity extends BaseActivity implements View.OnClickListener,
        IGetUtxosCallback, ISendRawTransactionCallback, ICreateAssertTxCallback,
        TransferPwdDialog.OnCheckPwdListener {

    private static final String TAG = TransferActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private BalanceBean mBalanceBean;
    private TextView mTv_transfer_from_wallet_name;
    private TextView mTv_transfer_from_wallet_addr;
    private Button mBt_transfer_send;
    private Wallet mWalletFrom;
    private EditText mEt_transfer_amount;
    private EditText mEt_transfer_to_wallet_addr;
    private TextView mTv_transfer_unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initView();
        initData();

    }

    private void initView() {
        mTv_transfer_from_wallet_name = (TextView) findViewById(R.id.tv_transfer_from_wallet_name);
        mTv_transfer_from_wallet_addr = (TextView) findViewById(R.id.tv_transfer_from_wallet_addr);
        mEt_transfer_to_wallet_addr = (EditText) findViewById(R.id.et_transfer_to_wallet_addr);
        mEt_transfer_amount = (EditText) findViewById(R.id.et_transfer_amount);
        mTv_transfer_unit = findViewById(R.id.tv_transfer_unit);

        mBt_transfer_send = (Button) findViewById(R.id.bt_transfer_send);
        mBt_transfer_send.setOnClickListener(this);

    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = (WalletBean) intent.getParcelableExtra(Constant
                .PARCELABLE_WALLET_BEAN_TRANSFER);
        if (null == mWalletBean) {
            CpLog.e(TAG, "mWalletBean is null!");
            return;
        }

        mTv_transfer_from_wallet_name.setText(mWalletBean.getWalletName());
        mTv_transfer_from_wallet_addr.setText(mWalletBean.getWalletAddr());

        mBalanceBean = intent.getParcelableExtra(Constant.PARCELABLE_BALANCE_BEAN_TRANSFER);
        if (null == mBalanceBean) {
            CpLog.e(TAG, "mBalanceBean is null!");
            return;
        }

        switch (mBalanceBean.getAssetsID()) {
            case Constant.ASSETS_NEO:
                mTv_transfer_unit.setText(Constant.UNIT_NEO);
                break;
            case Constant.ASSETS_NEO_GAS:
                mTv_transfer_unit.setText(Constant.UNIT_NEO_GAS);
                break;
            case Constant.ASSETS_CPX:
                mTv_transfer_unit.setText(Constant.UNIT_CPX);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_transfer_send:
                showDeleteWalletPwdDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckPwd(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "onCheckPwd() -> wallet is null!");
            return;
        }

        mWalletFrom = wallet;
        TaskController.getInstance().submit(new GetUtxos(mWalletFrom.address(), this));
    }

    @Override
    public void getUtxos(String utxos) {
        if (TextUtils.isEmpty(utxos)) {
            CpLog.e(TAG, "utxos is null!");
            return;
        }

        AssertTxBean assertTxBean = new AssertTxBean();
        assertTxBean.setAssetsID(mBalanceBean.getAssetsID());
        assertTxBean.setAddrFrom(mWalletFrom.address());
        assertTxBean.setAddrTo(mEt_transfer_to_wallet_addr.getText().toString().trim());
        assertTxBean.setTransferAmount(Double.valueOf(mEt_transfer_amount.getText().toString()
                .trim()));
        assertTxBean.setUtxos(utxos);

        TaskController.getInstance().submit(new CreateAssertTx(mWalletFrom, assertTxBean, this));
    }

    @Override
    public void createAssertTx(Tx tx) {
        if (null == tx) {
            CpLog.e(TAG, "createAssertTx() -> tx is null！");
            return;
        }

        String order = "0x" + tx.getID();
        CpLog.i(TAG, "createAssertTx order:" + order);

        String data = tx.getData();
        CpLog.i(TAG, "createAssertTx data:" + data);

        TaskController.getInstance().submit(new SendRawTransaction(tx.getData(), this));
    }

    @Override
    public void sendTxData(final Boolean isSuccess) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    Toast.makeText(TransferActivity.this, "广播交易,成功!请等待区块确认", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(TransferActivity.this, "广播交易,失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        finish();
    }

    public void showDeleteWalletPwdDialog() {
        TransferPwdDialog transferPwdDialog = TransferPwdDialog.newInstance();
        transferPwdDialog.setCurrentWalletBean(mWalletBean);
        transferPwdDialog.setOnCheckPwdListener(this);
        transferPwdDialog.show(getFragmentManager(), "TransferPwdDialog");
    }

}
