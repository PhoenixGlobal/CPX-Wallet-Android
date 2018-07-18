package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;

import java.math.BigDecimal;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.AssertTxBean;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.Nep5TxBean;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICreateAssertTxCallback;
import chinapex.com.wallet.executor.callback.ICreateNep5TxCallback;
import chinapex.com.wallet.executor.callback.IGetUtxosCallback;
import chinapex.com.wallet.executor.callback.ISendRawTransactionCallback;
import chinapex.com.wallet.executor.runnable.CreateAssertTx;
import chinapex.com.wallet.executor.runnable.CreateNep5Tx;
import chinapex.com.wallet.executor.runnable.GetUtxos;
import chinapex.com.wallet.executor.runnable.SendRawTransaction;
import chinapex.com.wallet.global.ApexGlobalTask;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.dialog.TransferPwdDialog;
import neomobile.Tx;
import neomobile.Wallet;

public class TransferActivity extends BaseActivity implements View.OnClickListener,
        IGetUtxosCallback, ISendRawTransactionCallback, ICreateAssertTxCallback,
        TransferPwdDialog.OnCheckPwdListener, ICreateNep5TxCallback {

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
    private String mOrder;
    private ImageButton mIb_transfer_scan;
    private final static int REQ_CODE = 1029;

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
        mIb_transfer_scan = (ImageButton) findViewById(R.id.ib_transfer_scan);

        mBt_transfer_send = (Button) findViewById(R.id.bt_transfer_send);
        mBt_transfer_send.setOnClickListener(this);
        mIb_transfer_scan.setOnClickListener(this);

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

        mTv_transfer_unit.setText(mBalanceBean.getAssetSymbol());

        String qrCode = intent.getStringExtra(Constant.PARCELABLE_QR_CODE_TRANSFER);
        if (TextUtils.isEmpty(qrCode)) {
            CpLog.e(TAG, "qrCode is null or empty!");
            return;
        }

        mEt_transfer_to_wallet_addr.setText(qrCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_transfer_send:
                String addressFrom = mTv_transfer_from_wallet_addr.getText().toString().trim();
                String addressTo = mEt_transfer_to_wallet_addr.getText().toString().trim();
                if (TextUtils.isEmpty(addressFrom) || TextUtils.isEmpty(addressTo)) {
                    CpLog.e(TAG, "addressFrom or addressTo is null or empty!");
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.address_cannot_be_empty));
                    return;
                }

                if (addressFrom.equals(addressTo)) {
                    CpLog.e(TAG, "addressFrom equals addressTo!");
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.address_cannot_be_same));
                    return;
                }

                String balance = mBalanceBean.getAssetsValue();
                String amount = mEt_transfer_amount.getText().toString().trim();
                try {
                    BigDecimal balanceBigDecimal = new BigDecimal(balance);
                    BigDecimal amountBigDecimal = new BigDecimal(amount);
                    if (amountBigDecimal.compareTo(balanceBigDecimal) == 1) {
                        Toast.makeText(TransferActivity.this, "余额不足！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    CpLog.e(TAG, "NumberFormatException: " + e.getMessage());
                    Toast.makeText(TransferActivity.this, "非法输入！", Toast.LENGTH_SHORT).show();
                    return;
                }

                showTransferPwdDialog();
                break;
            case R.id.ib_transfer_scan:
                Intent intent = new Intent(TransferActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            default:
                break;
        }
    }

    public void showTransferPwdDialog() {
        TransferPwdDialog transferPwdDialog = TransferPwdDialog.newInstance();
        transferPwdDialog.setCurrentWalletBean(mWalletBean);
        transferPwdDialog.setOnCheckPwdListener(this);
        transferPwdDialog.show(getFragmentManager(), "TransferPwdDialog");
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
        switch (mBalanceBean.getAssetType()) {
            case Constant.ASSET_TYPE_GLOBAL:
                startAssertTx(utxos);
                break;
            case Constant.ASSET_TYPE_NEP5:
                startNep5Tx();
                break;
            default:
                CpLog.w(TAG, "illegal asset");
                break;
        }
    }

    private void startAssertTx(String utxos) {
        if (TextUtils.isEmpty(utxos) || "[]".equals(utxos)) {
            CpLog.e(TAG, "utxos is null or []!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast("utxos is empty!");
                }
            });
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

    private void startNep5Tx() {
        Nep5TxBean nep5TxBean = new Nep5TxBean();
        nep5TxBean.setAssetID(mBalanceBean.getAssetsID());
        nep5TxBean.setAssetDecimal(mBalanceBean.getAssetDecimal());
        nep5TxBean.setAddrFrom(mWalletFrom.address());
        nep5TxBean.setAddrTo(mEt_transfer_to_wallet_addr.getText().toString().trim());
        nep5TxBean.setTransferAmount(mEt_transfer_amount.getText().toString().trim());
        nep5TxBean.setUtxos("[]");

        TaskController.getInstance().submit(new CreateNep5Tx(mWalletFrom, nep5TxBean, this));
    }

    @Override
    public void createAssertTx(Tx tx) {
        if (null == tx) {
            CpLog.e(TAG, "createAssertTx() -> tx is null！");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TransferActivity.this, "交易创建失败，请校验输入参数！", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            return;
        }

        mOrder = "0x" + tx.getID();
        CpLog.i(TAG, "createAssertTx order:" + mOrder);

//        String data = tx.getData();
//        CpLog.i(TAG, "createAssertTx data:" + data);

        TaskController.getInstance().submit(new SendRawTransaction(tx.getData(), this));
    }

    @Override
    public void createNep5Tx(Tx tx) {
        if (null == tx) {
            CpLog.e(TAG, "createNep5Tx() -> tx is null！");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TransferActivity.this, "交易创建失败，请校验输入参数！", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            return;
        }

        mOrder = "0x" + tx.getID();
        CpLog.i(TAG, "createNep5Tx order:" + mOrder);

        String data = tx.getData();
        CpLog.i(TAG, "createNep5Tx data:" + data);

        TaskController.getInstance().submit(new SendRawTransaction(tx.getData(), this));
    }

    @Override
    public void sendTxData(final Boolean isSuccess) {
        // write db
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance
                (ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TransferActivity.this, "数据库异常！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            return;
        }

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setWalletAddress(mWalletFrom.address());
        transactionRecord.setTxAmount(String.valueOf("-" + mEt_transfer_amount.getText().toString
                ().trim()));
        transactionRecord.setTxFrom(mWalletFrom.address());
        transactionRecord.setTxTo(mEt_transfer_to_wallet_addr.getText().toString().trim());
        transactionRecord.setTxTime(0);
        transactionRecord.setTxID(mOrder);

        AssetBean assetBean = apexWalletDbDao.queryAssetByHash(mBalanceBean.getAssetsID());
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        transactionRecord.setAssetID(mBalanceBean.getAssetsID());
        transactionRecord.setAssetLogoUrl(assetBean.getImageUrl());
        transactionRecord.setAssetSymbol(assetBean.getSymbol());

        if (isSuccess) {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_PACKAGING);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_TX_CACHE, transactionRecord);
        } else {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_FAIL);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_TRANSACTION_RECORD, transactionRecord);
        }

        // prompt the user
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    ToastUtils.getInstance().showToast("交易广播成功!");
                } else {
                    ToastUtils.getInstance().showToast("交易广播失败!");
                }
            }
        });

        // start polling
        ApexGlobalTask.getInstance().startPolling(mOrder, mWalletFrom.address());
        finish();
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

        CpLog.i(TAG, "qrCode:" + qrCode);
        mEt_transfer_to_wallet_addr.setText(qrCode);
    }
}
