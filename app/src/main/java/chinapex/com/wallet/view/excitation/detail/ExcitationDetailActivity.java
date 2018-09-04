package chinapex.com.wallet.view.excitation.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.AddressResultCode;
import chinapex.com.wallet.bean.request.RequestSubmitExcitation;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.IGetLocalCpxSumCallback;
import chinapex.com.wallet.executor.runnable.GetLocalCpxSum;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.dialog.ExcitationDialog;

public class ExcitationDetailActivity extends BaseActivity implements View.OnClickListener,
        IDetailView, IGetLocalCpxSumCallback {

    private static final String TAG = ExcitationDetailActivity.class.getSimpleName();

    private EditText mCpxAddressInput;
    private EditText mEthAddressInput;
    private TextView mWrongAddressNote;
    private Button mExcitationCommit;
    private ImageButton mCpxAddressInputCancel;
    private ImageButton mEthAddressInputCancel;
    private GetDetailCodePresenter mGetAddressResultPresenter;
    private int mGasLimit;
    private int mExcitationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excitation_detail);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mGasLimit = intent.getIntExtra(Constant.EXCITATION_GAS_LIMIT, 0);
        mExcitationId = intent.getIntExtra(Constant.EXCITATION_ACTIVITY_ID, 0);
        cpxCondition();
        mGetAddressResultPresenter = new GetDetailCodePresenter(this);
    }

    private void cpxCondition() {
        TaskController.getInstance().submit(new GetLocalCpxSum(this));
    }

    @Override
    public void getLocalCpxSum(String gasLimit) {
        CpLog.i(TAG, "gasLimit:" + gasLimit);
        CpLog.i(TAG, "mGasLimit:" + mGasLimit);

        BigDecimal cpxCondition = new BigDecimal(mGasLimit);
        BigDecimal localSum = new BigDecimal(gasLimit);
        int i = localSum.compareTo(cpxCondition);
        if (i == 0 || i == 1) {
            CpLog.i(TAG, "more than");
        } else {
            CpLog.i(TAG, "less than");
            showExcitationDialog();
        }
    }

    private void showExcitationDialog() {
        ExcitationDialog excitationDialog = ExcitationDialog.newInstance(mGasLimit);
        excitationDialog.show(getFragmentManager(), "ExcitationDialog");
    }

    private void initView() {
        mCpxAddressInput = findViewById(R.id.cpx_address_input);
        mEthAddressInput = findViewById(R.id.eth_address_input);
        mWrongAddressNote = findViewById(R.id.tv_excitation_detail_wrong_address_note);
        mCpxAddressInputCancel = findViewById(R.id.cpx_address_input_cancel);
        mEthAddressInputCancel = findViewById(R.id.eth_address_input_cancel);
        mWrongAddressNote = findViewById(R.id.tv_excitation_detail_wrong_address_note);
        mExcitationCommit = findViewById(R.id.btn_excitation_submit);


        mCpxAddressInput.addTextChangedListener(new DetailTextWatcher(mCpxAddressInput));
        mEthAddressInput.addTextChangedListener(new DetailTextWatcher(mEthAddressInput));

        mExcitationCommit.setOnClickListener(this);
        mCpxAddressInputCancel.setOnClickListener(this);
        mEthAddressInputCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_excitation_submit:
                submitExcitation();
                break;
            case R.id.cpx_address_input_cancel:
                clearCpxInput();
                break;
            case R.id.eth_address_input_cancel:
                clearEthInput();
                break;
            default:
                break;
        }
    }

    private void clearCpxInput() {
        mCpxAddressInput.getText().clear();
    }

    private void clearEthInput() {
        mEthAddressInput.getText().clear();
    }

    private void submitExcitation() {
        String cpxAddress = mCpxAddressInput.getText().toString().trim();
        String ethAddress = mEthAddressInput.getText().toString().trim();

        if (TextUtils.isEmpty(cpxAddress)) {
            CpLog.e(TAG, "the CPX address isEmpty!");
            ToastUtils.getInstance().showToast(getResources().getString(R.string.excitation_cpx_address_empty_toast));
            return;
        }

        if (TextUtils.isEmpty(ethAddress)) {
            CpLog.e(TAG, "the ETH address isEmpty!");
            ToastUtils.getInstance().showToast(getResources().getString(R.string.excitation_eth_address_empty_toast));
            return;
        }

        if (!ethAddress.startsWith(Constant.ETH_ADDRESS_START_WITH) || ethAddress.length() != 42) {
            CpLog.e(TAG, "the address is not Eth type!");
            ToastUtils.getInstance().showToast(getResources().getString(R.string.excitation_eth_address_wrong_format_toast));
            return;
        }

        if (!cpxAddress.startsWith(Constant.NEO_ADDRESS_START_WITH) || cpxAddress.length() != 34) {
            CpLog.e(TAG, "the address is not CPX type!");
            ToastUtils.getInstance().showToast(getResources().getString(R.string.excitation_cpx_address_wrong_format_toast));
            return;
        }

        RequestSubmitExcitation requestSubmitExcitation = new RequestSubmitExcitation();
        requestSubmitExcitation.setCPX(cpxAddress);
        requestSubmitExcitation.setETH(ethAddress);
        requestSubmitExcitation.setId(mExcitationId);

        mGetAddressResultPresenter.getDetailCode(requestSubmitExcitation);
    }

    @Override
    public void getDetailCode(final AddressResultCode addressResultCode) {
        if (null == addressResultCode) {
            CpLog.e(TAG, "addressResultCode is null!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.getInstance().showToast(getString(R.string.server_request_failed));
                }
            });
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int cpxCode = addressResultCode.getCpxCode();

                if (cpxCode == 5200) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_ok));
                    finish();
                    return;
                }

                if (cpxCode == 5000) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_repeat));
                    return;
                }

                if (cpxCode == 5003) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_format_err));
                    return;
                }

                if (cpxCode == 5001) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_fail));
                }
            }
        });

    }


    private class DetailTextWatcher implements TextWatcher {
        private EditText mEditText;

        public DetailTextWatcher(EditText editText) {
            mEditText = editText;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int textId = mEditText.getId();
            switch (textId) {
                case R.id.cpx_address_input:
                    if (TextUtils.isEmpty(mEditText.getText())) {
                        mCpxAddressInputCancel.setVisibility(View.GONE);
                    } else {
                        mCpxAddressInputCancel.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.eth_address_input:
                    if (TextUtils.isEmpty(mEditText.getText())) {
                        mEthAddressInputCancel.setVisibility(View.GONE);
                    } else {
                        mEthAddressInputCancel.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }

        }

        public void afterTextChanged(Editable s) {
        }
    }

}
