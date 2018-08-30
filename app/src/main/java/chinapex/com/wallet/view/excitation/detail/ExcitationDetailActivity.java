package chinapex.com.wallet.view.excitation.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import chinapex.com.wallet.view.excitation.ExcitationFragment;

public class ExcitationDetailActivity extends BaseActivity implements View.OnClickListener,
        IDetailView, IGetLocalCpxSumCallback {

    private static final String TAG = ExcitationFragment.class.getSimpleName();

    private EditText mCpxAddressInput;
    private EditText mEthAddressInput;
    private TextView mWrongAddressNote;
    private Button mExcitationCommit;
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
        ExcitationDialog excitationDialog = ExcitationDialog.newInstance();
        excitationDialog.show(getFragmentManager(), "ExcitationDialog");
    }

    private void initView() {
        mCpxAddressInput = findViewById(R.id.cpx_address_input);
        mEthAddressInput = findViewById(R.id.eth_address_input);
        mWrongAddressNote = findViewById(R.id.tv_excitation_detail_wrong_address_note);
        mExcitationCommit = findViewById(R.id.btn_excitation_submit);

        mExcitationCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_excitation_submit:
                submitExcitation();
                break;
            default:
                break;
        }
    }

    private void submitExcitation() {
        String cpxAddress = mCpxAddressInput.getText().toString().trim();
        String ethAddress = mEthAddressInput.getText().toString().trim();

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
                int ethCode = addressResultCode.getEthCode();

                if (cpxCode == 5200 || ethCode == 5200) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_ok));
                    finish();
                    return;
                }

                if (cpxCode == 5000 || ethCode == 5000) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_repeat));
                    return;
                }

                if (cpxCode == 5003 || ethCode == 5003) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_format_err));
                    return;
                }

                if (cpxCode == 5001 || ethCode == 5001) {
                    ToastUtils.getInstance().showToast(getString(R.string.excitation_save_fail));
                }
            }
        });

    }


}
