package chinapex.com.wallet.view;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.view.wallet.CreateWalletActivity;
import chinapex.com.wallet.view.wallet.ImportWalletActivity;

public class NewVisitorActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NewVisitorActivity.class.getSimpleName();

    private Button mCreate_wallet;
    private Button mImport_wallet;

    @Override
    protected void setContentView() {
        super.setContentView();

        setContentView(R.layout.activity_new_visitor);
    }

    @Override
    protected void init() {
        super.init();

        initView();
    }

    private void initView() {
        mCreate_wallet = (Button) findViewById(R.id.bt_new_visitor_create_wallet);
        mImport_wallet = (Button) findViewById(R.id.bt_new_visitor_import_wallet);

        mCreate_wallet.setOnClickListener(this);
        mImport_wallet.setOnClickListener(this);

        // 设置透明导航键
        setNavigationBarColorTransparent();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_new_visitor_create_wallet:
                startActivity(CreateWalletActivity.class, true);
                break;
            case R.id.bt_new_visitor_import_wallet:
                startActivity(ImportWalletActivity.class, true);
                break;
        }
    }

    private void setNavigationBarColorTransparent() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }
}
