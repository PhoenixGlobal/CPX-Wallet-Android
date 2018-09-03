package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;

import java.math.BigDecimal;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.tx.EthTxBean;
import chinapex.com.wallet.bean.tx.ITxBean;
import chinapex.com.wallet.bean.tx.NeoTxBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.presenter.transfer.CreateTxPresenter;
import chinapex.com.wallet.presenter.transfer.ICreateTxPresenter;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.dialog.TransferPwdDialog;
import neomobile.Wallet;

public class TransferActivity extends BaseActivity implements View.OnClickListener,
        TransferPwdDialog.OnCheckPwdListener,
        SeekBar.OnSeekBarChangeListener, ICreateTxView {

    private static final String TAG = TransferActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private BalanceBean mBalanceBean;
    private Button mBt_transfer_send;
    private EditText mEt_transfer_amount;
    private EditText mEt_transfer_to_wallet_addr;
    private TextView mTv_transfer_unit;
    private ImageButton mIb_transfer_scan;
    private final static int REQ_CODE = 1029;
    private TextView mTv_available_amount;
    private TextView mTv_amount_all;
    private SeekBar mSb_transfer;
    private TextView mTv_transfer_gas;
    private ICreateTxPresenter mICreateTxPresenter;
    private RelativeLayout mRl_seek_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initView();
        initData();

    }

    private void initView() {
        mEt_transfer_to_wallet_addr = (EditText) findViewById(R.id.et_transfer_to_wallet_addr);
        mEt_transfer_amount = (EditText) findViewById(R.id.et_transfer_amount);
        mTv_transfer_unit = findViewById(R.id.tv_transfer_unit);
        mIb_transfer_scan = (ImageButton) findViewById(R.id.ib_transfer_scan);
        mTv_available_amount = (TextView) findViewById(R.id.tv_available_amount);
        mTv_amount_all = (TextView) findViewById(R.id.tv_amount_all);
        mSb_transfer = (SeekBar) findViewById(R.id.sb_transfer);
        mTv_transfer_gas = (TextView) findViewById(R.id.tv_transfer_gas);
        mRl_seek_bar = (RelativeLayout) findViewById(R.id.rl_seek_bar);

        mBt_transfer_send = (Button) findViewById(R.id.bt_transfer_send);
        mBt_transfer_send.setOnClickListener(this);
        mIb_transfer_scan.setOnClickListener(this);
        mTv_amount_all.setOnClickListener(this);
        mSb_transfer.setOnSeekBarChangeListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = intent.getParcelableExtra(Constant.PARCELABLE_WALLET_BEAN_TRANSFER);
        if (null == mWalletBean) {
            CpLog.e(TAG, "mWalletBean is null!");
            return;
        }

        int walletType = mWalletBean.getWalletType();
        switch (walletType) {
            case Constant.WALLET_TYPE_NEO:
                mRl_seek_bar.setVisibility(View.INVISIBLE);
                break;
            case Constant.WALLET_TYPE_ETH:
                mRl_seek_bar.setVisibility(View.VISIBLE);
                break;
            case Constant.WALLET_TYPE_CPX:
                break;
            default:
                CpLog.e(TAG, "unknown wallet type!");
                break;
        }

        mBalanceBean = intent.getParcelableExtra(Constant.PARCELABLE_BALANCE_BEAN_TRANSFER);
        if (null == mBalanceBean) {
            CpLog.e(TAG, "mBalanceBean is null!");
            return;
        }

        mTv_transfer_unit.setText(mBalanceBean.getAssetSymbol().toUpperCase());
        mTv_available_amount.setText(mBalanceBean.getAssetsValue());
        mTv_transfer_gas.setText(String.valueOf(mSb_transfer.getProgress() / 100.0 + " ether"));

        mICreateTxPresenter = new CreateTxPresenter(this);
        mICreateTxPresenter.init(walletType);

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
                String addressFrom = mWalletBean.getAddress();
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
                        ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                                .getResources().getString(R.string.insufficient_balance));
                        return;
                    }
                } catch (NumberFormatException e) {
                    CpLog.e(TAG, "NumberFormatException: " + e.getMessage());
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.illegal_input));
                    return;
                }

                showTransferPwdDialog();
                break;
            case R.id.ib_transfer_scan:
                Intent intent = new Intent(TransferActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.tv_amount_all:
                mEt_transfer_amount.setText(mBalanceBean.getAssetsValue());
                mEt_transfer_amount.setSelection(mEt_transfer_amount.getText().length());
                break;
            default:
                break;
        }
    }

    public void showTransferPwdDialog() {
        TransferPwdDialog transferPwdDialog = TransferPwdDialog.newInstance();
        transferPwdDialog.setCurrentWallet(mWalletBean);
        transferPwdDialog.setOnCheckPwdListener(this);
        transferPwdDialog.setTransferAmount(mEt_transfer_amount.getText().toString().trim());
        transferPwdDialog.setTransferUnit(mBalanceBean.getAssetSymbol().toUpperCase());
        transferPwdDialog.show(getFragmentManager(), "TransferPwdDialog");
    }

    @Override
    public void onCheckPwd(Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "onCheckPwd() -> wallet is null!");
            return;
        }

        ITxBean iTxBean = null;
        switch (mWalletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                NeoTxBean neoTxBean = new NeoTxBean();
                neoTxBean.setWallet(wallet);
                neoTxBean.setAssetID(mBalanceBean.getAssetsID());
                neoTxBean.setAssetDecimal(mBalanceBean.getAssetDecimal());
                neoTxBean.setFromAddress(wallet.address());
                neoTxBean.setToAddress(mEt_transfer_to_wallet_addr.getText().toString().trim());
                neoTxBean.setAmount(mEt_transfer_amount.getText().toString().trim());
                iTxBean = neoTxBean;
                break;
            case Constant.WALLET_TYPE_ETH:
                iTxBean = new EthTxBean();
                break;
            case Constant.WALLET_TYPE_CPX:
                break;
            default:
                break;
        }

        if (null == iTxBean) {
            CpLog.e(TAG, "iTxBean is null!");
            return;
        }

        String assetType = mBalanceBean.getAssetType();
        if (TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "assetType is null or empty!");
            return;
        }

        switch (assetType) {
            case Constant.ASSET_TYPE_GLOBAL:
                mICreateTxPresenter.createGlobalTx(iTxBean);
                break;
            case Constant.ASSET_TYPE_NEP5:
                mICreateTxPresenter.createColorTx(iTxBean);
                break;
            default:
                CpLog.w(TAG, "illegal asset");
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

        CpLog.i(TAG, "qrCode:" + qrCode);
        mEt_transfer_to_wallet_addr.setText(qrCode);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTv_transfer_gas.setText(String.valueOf(progress / 100.0 + " ether"));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void createTxMsg(final String toastMsg, final boolean isFinish) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.getInstance().showToast(toastMsg);
                if (isFinish) {
                    finish();
                }
            }
        });
    }
}
