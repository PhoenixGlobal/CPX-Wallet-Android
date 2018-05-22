package chinapex.com.wallet.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.WalletKeyStore;
import chinapex.com.wallet.bean.request.RequestGetBlock;
import chinapex.com.wallet.bean.request.RequestGetRawTransaction;
import chinapex.com.wallet.bean.request.RequestSendRawTransaction;
import chinapex.com.wallet.bean.request.RequestUtxo;
import chinapex.com.wallet.bean.response.ResponseGetBlock;
import chinapex.com.wallet.bean.response.ResponseGetRawTransaction;
import chinapex.com.wallet.bean.response.ResponseSendRawTransaction;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import neomobile.Neomobile;
import neomobile.Tx;
import neomobile.Wallet;

public class TransferActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = TransferActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private TextView mTv_transfer_from_wallet_name;
    private TextView mTv_transfer_from_wallet_addr;
    private Button mBt_transfer_send;
    private Wallet mWalletFrom;
    private EditText mEt_transfer_pwd;
    private EditText mEt_transfer_amount;
    private EditText mEt_transfer_to_wallet_addr;
    private EditText mEt_transfer_tx_id;
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
        mEt_transfer_tx_id = (EditText) findViewById(R.id.et_transfer_tx_id);

        mBt_transfer_send = (Button) findViewById(R.id.bt_transfer_send);
        mBt_transfer_send.setOnClickListener(this);

    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = (WalletBean) intent.getParcelableExtra(Constant.PARCELABLE_WALLET_BEAN);
        CpLog.i(TAG, "walletBean:" + mWalletBean.toString());

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

                mRequestUtxo = new RequestUtxo();
                mVoutBean = new RequestUtxo.VoutBean();
                sendTxData(mEt_transfer_tx_id.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void fromKeyStore() {
        String keyStores = (String) SharedPreferencesUtils.getParam(this, Constant
                .SP_WALLET_KEYSTORE, "");

        if (TextUtils.isEmpty(keyStores)) {
            CpLog.e(TAG, "keyStores is null!");
            return;
        }

        List<WalletKeyStore> walletKeyStores = GsonUtils.json2List(keyStores);
        if (null == walletKeyStores) {
            CpLog.e(TAG, "jsonListObject is null!");
            return;
        }

        for (WalletKeyStore walletKeyStore : walletKeyStores) {
            if (!mWalletBean.getWalletName().equals(walletKeyStore.getWalletName())) {
                continue;
            }

            try {
                mWalletFrom = Neomobile.fromKeyStore(walletKeyStore.getWalletKeyStore(),
                        mEt_transfer_pwd.getText().toString().trim());
                CpLog.i(TAG, "mWalletFrom address:" + mWalletFrom.address());
            } catch (Exception e) {
                CpLog.e(TAG, "fromKeyStore exception:" + e.getMessage());
            }
        }
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

    private void sendTxData(String txid) {
        final RequestGetRawTransaction requestGetRawTransaction = new RequestGetRawTransaction();
        requestGetRawTransaction.setJsonrpc("2.0");
        requestGetRawTransaction.setMethod("getrawtransaction");
        ArrayList<String> params = new ArrayList<>();
        params.add(txid);
        params.add("1");
        requestGetRawTransaction.setParams(params);
        requestGetRawTransaction.setId(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                        (requestGetRawTransaction), new INetCallback() {


                    @Override
                    public void onSuccess(int statusCode, String msg, String result) {
                        CpLog.i(TAG, "getrawtransaction onSuccess");
                        ResponseGetRawTransaction responseGetRawTransaction = GsonUtils.json2Bean
                                (result, ResponseGetRawTransaction.class);
                        List<ResponseGetRawTransaction.ResultBean.VoutBean> voutBeans =
                                responseGetRawTransaction.getResult().getVout();
                        for (ResponseGetRawTransaction.ResultBean.VoutBean voutBean : voutBeans) {
                            if (mWalletFrom.address().equals(voutBean.getAddress()) && Constant
                                    .ASSETS_NEO.equals(voutBean.getAsset())) {
                                mVoutBean.setAddress(voutBean.getAddress());
                                mVoutBean.setAsset(voutBean.getAsset());
                                mVoutBean.setValue(voutBean.getValue());
                                mVoutBean.setN(voutBean.getN());


                            }
                        }

                        RequestGetBlock requestGetBlock = new RequestGetBlock();
                        requestGetBlock.setJsonrpc("2.0");
                        requestGetBlock.setMethod("getblock");
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(responseGetRawTransaction.getResult().getBlockhash());
                        arrayList.add("1");
                        requestGetBlock.setParams(arrayList);
                        requestGetBlock.setId(1);
                        OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils
                                .toJsonStr(requestGetBlock), new INetCallback() {


                            @Override
                            public void onSuccess(int statusCode, String msg, String result) {
                                CpLog.i(TAG, "getblock onSuccess");

                                ResponseGetBlock responseGetBlock = GsonUtils.json2Bean(result,
                                        ResponseGetBlock.class);
                                int blockIndex = responseGetBlock.getResult().getIndex();
                                int blockTime = responseGetBlock.getResult().getTime();

                                mRequestUtxo.setTxid(mEt_transfer_tx_id.getText().toString().trim
                                        ());
                                mRequestUtxo.setBlock(blockIndex);
                                mRequestUtxo.setVout(mVoutBean);
                                mRequestUtxo.setSpentBlock(-1);
                                mRequestUtxo.setSpentTime("");
                                mRequestUtxo.setCreateTime(blockTime + "");
                                mRequestUtxo.setGas("");

                                ArrayList<RequestUtxo> requestUtxos = new ArrayList<>();
                                requestUtxos.add(mRequestUtxo);

                                String utxos = GsonUtils.toJsonStr(requestUtxos);
                                String transfer_neo_amount = mEt_transfer_amount.getText()
                                        .toString().trim();
                                final Tx tx = createAssertTx(Constant.ASSETS_NEO, mWalletFrom
                                                .address(),
                                        mEt_transfer_to_wallet_addr.getText().toString().trim(),
                                        Double.valueOf(transfer_neo_amount), utxos);

                                RequestSendRawTransaction requestSendRawTransaction = new
                                        RequestSendRawTransaction();
                                requestSendRawTransaction.setJsonrpc("2.0");
                                requestSendRawTransaction.setMethod("sendrawtransaction");
                                ArrayList<String> sendDatas = new ArrayList<>();
                                sendDatas.add(tx.getData());
                                requestSendRawTransaction.setParams(sendDatas);
                                requestSendRawTransaction.setId(1);

                                OkHttpClientManager.getInstance().postJson(Constant.URL_CLI,
                                        GsonUtils.toJsonStr(requestSendRawTransaction), new
                                                INetCallback() {


                                                    @Override
                                                    public void onSuccess(int statusCode, String
                                                            msg, String
                                                                                  result) {
                                                        CpLog.i(TAG, "send onSuccess");

                                                        ResponseSendRawTransaction
                                                                responseSendRawTransaction =
                                                                GsonUtils.json2Bean(result,
                                                                        ResponseSendRawTransaction.class);
                                                        if (responseSendRawTransaction.isResult()) {
                                                            CpLog.i(TAG, "broadcast success");
                                                            Toast.makeText(TransferActivity.this,
                                                                    "broadcast success", Toast
                                                                            .LENGTH_LONG).show();

                                                            CpLog.i(TAG, "txid:" + "0x" + tx
                                                                    .getID());

                                                        }

                                                    }

                                                    @Override
                                                    public void onFailed(int failedCode, String
                                                            msg) {
                                                        CpLog.i(TAG, "send onFailed");
                                                    }
                                                });

                            }

                            @Override
                            public void onFailed(int failedCode, String msg) {
                                CpLog.e(TAG, "getblock onFailed");
                            }
                        });


                    }

                    @Override
                    public void onFailed(int failedCode, String msg) {
                        CpLog.e(TAG, "getrawtransaction onFailed");
                    }
                });
            }
        }).start();
    }

}
