package chinapex.com.wallet.view.me.portrait;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class TypeInputActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = TypeInputActivity.class.getSimpleName();
    private TextView mTv_type_input_title;
    private TextView mTv_type_input_save;
    private EditText mEt_type_input_content;
    private static final int RESULT_CODE = 102;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_input);

        initView();
        initData();
    }

    private void initView() {
        mTv_type_input_title = (TextView) findViewById(R.id.tv_type_input_title);
        mTv_type_input_save = (TextView) findViewById(R.id.tv_type_input_save);
        mEt_type_input_content = (EditText) findViewById(R.id.et_type_input_content);

        mTv_type_input_save.setOnClickListener(this);
        if (mEt_type_input_content.getText().toString().trim().length() <= 0) {
            mTv_type_input_save.setClickable(false);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        String title = intent.getStringExtra(Constant.EXTRA_TYPE_INPUT_TITLE);
        mPosition = intent.getIntExtra(Constant.EXTRA_TYPE_INPUT_POSITION, -1);

        mTv_type_input_title.setText(title);

        if (getString(R.string.place_of_residence).equals(title)) {
            mEt_type_input_content.setHint(R.string.example_address);
        } else {
            mEt_type_input_content.setHint(title);
        }

        mEt_type_input_content.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_type_input_save:
                String content = mEt_type_input_content.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra(Constant.EXTRA_TYPE_INPUT_CONTENT, content);
                intent.putExtra(Constant.EXTRA_TYPE_INPUT_POSITION, mPosition);
                setResult(RESULT_CODE, intent);
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mTv_type_input_save.setTextColor(ApexWalletApplication.getInstance().getResources()
                    .getColor(R.color.c_333333));
            mTv_type_input_save.setClickable(true);
        } else {
            mTv_type_input_save.setTextColor(ApexWalletApplication.getInstance().getResources()
                    .getColor(R.color.c_999999));
            mTv_type_input_save.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
