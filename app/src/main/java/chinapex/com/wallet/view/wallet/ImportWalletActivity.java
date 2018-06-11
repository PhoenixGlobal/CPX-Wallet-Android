package chinapex.com.wallet.view.wallet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.FragmentFactory;

public class ImportWalletActivity extends BaseActivity implements View.OnClickListener {

    private Button mBt_import_wallet_mnemonic;
    private Button mBt_import_wallet_keystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);

        initView();
        initData();
    }

    private void initView() {
        mBt_import_wallet_mnemonic = (Button) findViewById(R.id.bt_import_wallet_mnemonic);
        mBt_import_wallet_keystore = (Button) findViewById(R.id.bt_import_wallet_keystore);

        mBt_import_wallet_mnemonic.setOnClickListener(this);
        mBt_import_wallet_keystore.setOnClickListener(this);
    }

    private void initData() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment importMnemonicFragment = FragmentFactory.getFragment(Constant.FRAGMENT_TAG_IMPORT_MNEMONIC);
        fragmentTransaction.add(R.id.fl_import_wallet, importMnemonicFragment, Constant.FRAGMENT_TAG_IMPORT_MNEMONIC);
        fragmentTransaction.show(importMnemonicFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_import_wallet_mnemonic:
                mnemonicIsSelected();
                break;
            case R.id.bt_import_wallet_keystore:
                keystoreIsSelected();
                break;
        }
    }

    private void mnemonicIsSelected() {
        mBt_import_wallet_mnemonic.setBackgroundResource(R.drawable.shape_white_bt_bg);
        mBt_import_wallet_mnemonic.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color
                .colorPrimary));

        mBt_import_wallet_keystore.setBackground(new ColorDrawable(0));
        mBt_import_wallet_keystore.setTextColor(Color.WHITE);

        switchFragment(Constant.FRAGMENT_TAG_IMPORT_MNEMONIC, Constant.FRAGMENT_TAG_IMPORT_KEYSTORE);
    }

    private void keystoreIsSelected() {
        mBt_import_wallet_keystore.setBackgroundResource(R.drawable.shape_white_bt_bg);
        mBt_import_wallet_keystore.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color
                .colorPrimary));

        mBt_import_wallet_mnemonic.setBackground(new ColorDrawable(0));
        mBt_import_wallet_mnemonic.setTextColor(Color.WHITE);

        switchFragment(Constant.FRAGMENT_TAG_IMPORT_KEYSTORE, Constant.FRAGMENT_TAG_IMPORT_MNEMONIC);
    }

    private void switchFragment(String showFragmentTag, String hideFragmentTag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment hideFragment = FragmentFactory.getFragment(hideFragmentTag);
        BaseFragment showFragment = FragmentFactory.getFragment(showFragmentTag);
        if (!showFragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_import_wallet, showFragment, showFragmentTag);
        }
        if (!showFragment.isVisible()) {
            fragmentTransaction.show(showFragment);
        }
        fragmentTransaction.hide(hideFragment);
        fragmentTransaction.commit();
    }
}
