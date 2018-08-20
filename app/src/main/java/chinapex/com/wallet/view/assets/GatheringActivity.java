package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.ToastUtils;

public class GatheringActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = GatheringActivity.class.getSimpleName();
    private WalletBean mWalletBean;
    private TextView mTv_gathering_wallet_name;
    private TextView mTv_gathering_wallet_addr;
    private Button mBt_gathering_copy_addr;
    private ImageView mIv_gathering_qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering);

        initView();
        initData();
    }

    private void initView() {
        mTv_gathering_wallet_name = (TextView) findViewById(R.id.tv_gathering_wallet_name);
        mTv_gathering_wallet_addr = (TextView) findViewById(R.id.tv_gathering_wallet_addr);
        mBt_gathering_copy_addr = (Button) findViewById(R.id.bt_gathering_copy_addr);
        mIv_gathering_qr_code = (ImageView) findViewById(R.id.iv_gathering_qr_code);

        mBt_gathering_copy_addr.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = intent.getParcelableExtra(Constant.PARCELABLE_WALLET_BEAN_GATHERING);
        if (null == mWalletBean) {
            CpLog.e(TAG, "mWalletBean is null!");
            return;
        }
        mTv_gathering_wallet_name.setText(mWalletBean.getName());
        mTv_gathering_wallet_addr.setText(mWalletBean.getAddress());

        //生成二维码
        String walletAddr = mWalletBean.getAddress();
        Bitmap bitmap;
        try {
            bitmap = BitmapUtils.create2DCode(walletAddr);
            mIv_gathering_qr_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            CpLog.e(TAG, "qrCode exception:" + e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_gathering_copy_addr:
                CpLog.i(TAG, "bt_gathering_copy_addr is click！");
                PhoneUtils.copy2Clipboard(ApexWalletApplication.getInstance(), mWalletBean.getAddress());
                ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                        .getResources().getString(R.string.wallet_copied_share));
                break;
            default:
                break;
        }
    }

}
