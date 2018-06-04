package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.request.RequestUtxo;
import chinapex.com.wallet.bean.response.ResponseGetUtxos;
import chinapex.com.wallet.bean.response.ResponseSendRawTransaction;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.runnable.GetUtxos;
import chinapex.com.wallet.executor.runnable.SendRawTransaction;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import neomobile.Neomobile;
import neomobile.Tx;
import neomobile.Wallet;

public class TransferActivity extends BaseActivity implements View.OnClickListener, INetCallback {

    private static final String TAG = TransferActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private TextView mTv_transfer_from_wallet_name;
    private TextView mTv_transfer_from_wallet_addr;
    private Button mBt_transfer_send;
    private Wallet mWalletFrom;
    private EditText mEt_transfer_pwd;
    private EditText mEt_transfer_amount;
    private EditText mEt_transfer_to_wallet_addr;
    private RequestUtxo.VoutBean mVoutBean;
    private RequestUtxo mRequestUtxo;

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
        mEt_transfer_pwd = (EditText) findViewById(R.id.et_transfer_pwd);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_transfer_send:
                fromKeyStore();
                if (null == mWalletFrom) {
                    CpLog.e(TAG, "mWalletFrom is null!");
                    return;
                }

                startTx(mWalletFrom.address());
                break;
            default:
                break;
        }
    }

    private void fromKeyStore() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null！");
            return;
        }

        WalletBean walletBean = apexWalletDbDao.queryByWalletName(Constant.TABLE_APEX_WALLET,
                mWalletBean.getWalletName());
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        try {
            mWalletFrom = Neomobile.fromKeyStore(walletBean.getKeyStore(), mEt_transfer_pwd
                    .getText().toString().trim());
            CpLog.i(TAG, "mWalletFrom address:" + mWalletFrom.address());
        } catch (Exception e) {
            CpLog.e(TAG, "fromKeyStore exception:" + e.getMessage());
        }

    }

    private void startTx(String addressFrom) {
        TaskController.getInstance().submit(new GetUtxos(addressFrom, this));
    }

    private Tx createAssertTx(String assetsID, String addrFrom, String addrTo, double
            transferAmount, String utxos) {
        if (TextUtils.isEmpty(assetsID)
                || TextUtils.isEmpty(addrFrom)
                || TextUtils.isEmpty(addrTo)) {
            CpLog.e(TAG, "createAssertTx() -> assetsID or addrFrom or addrTo is null!");
            return null;
        }

        Tx tx = null;
        try {
            tx = mWalletFrom.createAssertTx(assetsID, addrFrom, addrTo, transferAmount, utxos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == tx) {
            CpLog.e(TAG, "tx is null！");
            return tx;
        }

        String order = "0x" + tx.getID();
        CpLog.i(TAG, "createAssertTx order:" + order);


        String data = tx.getData();
        CpLog.i(TAG, "createAssertTx data:" + data);
        return tx;
    }

    @Override
    public void onSuccess(int statusCode, String msg, String result) {
        ResponseGetUtxos responseGetUtxos = GsonUtils.json2Bean(result, ResponseGetUtxos.class);
        if (null == responseGetUtxos) {
            CpLog.e(TAG, "responseGetUtxos is null!");
            return;
        }

        String utxos = GsonUtils.toJsonStr(responseGetUtxos.getResult());
        CpLog.i(TAG, "utxos:" + utxos);
        String toAddress = mEt_transfer_to_wallet_addr.getText().toString().trim();
        String transfer_neo_amount = mEt_transfer_amount.getText().toString().trim();
        Tx tx = createAssertTx(Constant.ASSETS_NEO, mWalletFrom.address(), toAddress,
                Double.valueOf(transfer_neo_amount), utxos);
        if (null == tx) {
            CpLog.e(TAG, "tx is null!");
            return;
        }

        SendRawTransaction sendRawTransaction = new SendRawTransaction(tx.getData(), new
                INetCallback() {
                    @Override
                    public void onSuccess(int statusCode, String msg, String result) {
                        ResponseSendRawTransaction responseSendRawTransaction = GsonUtils
                                .json2Bean(result, ResponseSendRawTransaction.class);
                        CpLog.i(TAG, "broadcast:" + responseSendRawTransaction.isResult());
                        finish();
                    }

                    @Override
                    public void onFailed(int failedCode, String msg) {
                        CpLog.e(TAG, "onFailed");
                    }
                });

        TaskController.getInstance().submit(sendRawTransaction);

    }

    @Override
    public void onFailed(int failedCode, String msg) {

    }
}
