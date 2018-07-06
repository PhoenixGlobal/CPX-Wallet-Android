package chinapex.com.wallet.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.changelistener.OnItemDeleteListener;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

public class MeSkipActivity extends BaseActivity implements OnItemDeleteListener {

    private static final String TAG = MeSkipActivity.class.getSimpleName();
    private WalletBean mWalletBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_skip);

        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {
        ApexListeners.getInstance().addOnItemDeleteListener(this);

        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        Bundle bundle = intent.getBundleExtra(Constant.ME_MANAGER_DETAIL_BUNDLE);
        if (null == bundle) {
            CpLog.e(TAG, "bundle is null!");
            return;
        }

        mWalletBean = bundle.getParcelable(Constant.PARCELABLE_WALLET_BEAN_MANAGE_DETAIL);

        String fragmentTag = bundle.getString(Constant.ME_SKIP_ACTIVITY_FRAGMENT_TAG);
        initFragment(fragmentTag);

    }

    private void initFragment(String fragmentTag) {
        if (TextUtils.isEmpty(fragmentTag)) {
            CpLog.e(TAG, "fragmentTag is null!");
            return;
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(fragmentTag);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_me_skip, fragment, fragmentTag);
        }
        fragmentTransaction.show(fragment).commit();
    }

    public WalletBean getWalletBean() {
        return mWalletBean;
    }

    @Override
    public void onItemDelete(WalletBean walletBean) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApexListeners.getInstance().removeOnItemDeleteListener(this);
    }
}
