package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import chinapex.com.wallet.bean.tx.NeoTxBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.IGetEthGasPriceCallback;
import chinapex.com.wallet.executor.runnable.eth.GetEthGasPrice;
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
        SeekBar.OnSeekBarChangeListener, ICreateTxView, IGetEthGasPriceCallback {

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
    private TextView mTv_transfer_user_set_gas_price;
    private ICreateTxPresenter mICreateTxPresenter;
    private RelativeLayout mRl_seek_bar;
    private LinearLayout mLl_transfer_gas_price;
    private RelativeLayout mRl_transfer_gas_fee;
    private TextView mTv_transfer_gas_price;
    private TextView mTv_transfer_gas_fee;

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

        mLl_transfer_gas_price = (LinearLayout) findViewById(R.id.ll_transfer_gas_price);
        mTv_transfer_gas_price = (TextView) findViewById(R.id.tv_transfer_gas_price);

        mRl_seek_bar = (RelativeLayout) findViewById(R.id.rl_seek_bar);
        mTv_transfer_user_set_gas_price = (TextView) findViewById(R.id.tv_transfer_user_set_gas_price);

        mRl_transfer_gas_fee = (RelativeLayout) findViewById(R.id.rl_transfer_gas_fee);
        mTv_transfer_gas_fee = (TextView) findViewById(R.id.tv_transfer_gas_fee);

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
                mLl_transfer_gas_price.setVisibility(View.INVISIBLE);
                mRl_seek_bar.setVisibility(View.INVISIBLE);
                mRl_transfer_gas_fee.setVisibility(View.INVISIBLE);
                break;
            case Constant.WALLET_TYPE_ETH:
                mLl_transfer_gas_price.setVisibility(View.VISIBLE);
                mRl_seek_bar.setVisibility(View.VISIBLE);
                mRl_transfer_gas_fee.setVisibility(View.VISIBLE);
                TaskController.getInstance().submit(new GetEthGasPrice(this));
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
    public void getEthGasPrice(final String gasPrice) {
        if (TextUtils.isEmpty(gasPrice)) {
            CpLog.e(TAG, "gasPrice is null!");
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTv_transfer_gas_price.setText(gasPrice);
                mSb_transfer.setProgress(Integer.valueOf(gasPrice) * 10);
                String gasFee = "0";
                try {
                    gasFee = new BigDecimal(gasPrice).divide(new BigDecimal(10).pow(5)).multiply(new BigDecimal(9))
                            .setScale(8, BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
                } catch (Exception e) {
                    CpLog.e(TAG, "gasFee Exception:" + e.getMessage());
                }
                mTv_transfer_gas_fee.setText(gasFee);
            }
        });
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

        NeoTxBean neoTxBean = new NeoTxBean();
        neoTxBean.setWallet(wallet);
        neoTxBean.setAssetID(mBalanceBean.getAssetsID());
        neoTxBean.setAssetDecimal(mBalanceBean.getAssetDecimal());
        neoTxBean.setFromAddress(wallet.address());
        neoTxBean.setToAddress(mEt_transfer_to_wallet_addr.getText().toString().trim());
        neoTxBean.setAmount(mEt_transfer_amount.getText().toString().trim());

        String assetType = mBalanceBean.getAssetType();
        if (TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "assetType is null or empty!");
            return;
        }

        switch (assetType) {
            case Constant.ASSET_TYPE_GLOBAL:
                mICreateTxPresenter.createGlobalTx(neoTxBean);
                break;
            case Constant.ASSET_TYPE_NEP5:
                mICreateTxPresenter.createColorTx(neoTxBean);
                break;
            default:
                CpLog.w(TAG, "illegal asset");
                break;
        }
    }

    @Override
    public void onCheckEthPwd(ethmobile.Wallet wallet) {
        if (null == wallet) {
            CpLog.e(TAG, "onCheckPwd() -> wallet is null!");
            return;
        }


        EthTxBean ethTxBean = new EthTxBean();
        ethTxBean.setWallet(wallet);
        ethTxBean.setAssetID(mBalanceBean.getAssetsID());
        ethTxBean.setAssetDecimal(mBalanceBean.getAssetDecimal());
        ethTxBean.setFromAddress(wallet.address());
        ethTxBean.setToAddress(mEt_transfer_to_wallet_addr.getText().toString().trim());
        // TODO: 2018/9/7 0007  amount,price,limit
//        ethTxBean.setAmount(mEt_transfer_amount.getText().toString().trim());
        ethTxBean.setAmount("0x16345785d8a0000");
        ethTxBean.setGasPrice("0x3b9aca00");
        ethTxBean.setGasLimit("0x15f90");

        String assetType = mBalanceBean.getAssetType();
        if (TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "assetType is null or empty!");
            return;
        }

        switch (assetType) {
            case Constant.ASSET_TYPE_ETH:
                mICreateTxPresenter.createGlobalTx(ethTxBean);
                break;
            case Constant.ASSET_TYPE_ERC20:
                mICreateTxPresenter.createColorTx(ethTxBean);
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
        mTv_transfer_user_set_gas_price.setText(String.valueOf(progress / 10.0 + " Gwei"));
        String gasFee = "0";
        try {
            gasFee = new BigDecimal(progress / 10.0).divide(new BigDecimal(10).pow(5)).multiply(new BigDecimal(9))
                    .setScale(8, BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            CpLog.e(TAG, "gasFee Exception:" + e.getMessage());
        }
        mTv_transfer_gas_fee.setText(gasFee);
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
