package chinapex.com.wallet.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;

public class NewVisitorActivity extends BaseActivity implements View.OnClickListener {

    private Button mCreate_wallet;
    private Button mImport_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visitor);

        initView();
    }

    private void initView() {
        mCreate_wallet = (Button) findViewById(R.id.bt_new_visitor_create_wallet);
        mImport_wallet = (Button) findViewById(R.id.bt_new_visitor_import_wallet);

        mCreate_wallet.setOnClickListener(this);
        mImport_wallet.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_new_visitor_create_wallet:
                startActivity(CreateWalletActivity.class, true);
                break;
            case R.id.bt_new_visitor_import_wallet:
                break;
        }
    }
}
