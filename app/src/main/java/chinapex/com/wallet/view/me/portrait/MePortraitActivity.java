package chinapex.com.wallet.view.me.portrait;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.viewpager.FragmentUpdateAdapter;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;
import chinapex.com.wallet.view.dialog.SwitchWallet2Dialog;

public class MePortraitActivity extends BaseActivity implements View.OnClickListener,
        SwitchWallet2Dialog.onSelectedWalletListener {

    private static final String TAG = MePortraitActivity.class.getSimpleName();
    private TextView mTv_portrait_address;
    private ImageButton mIb_portrait_switch_wallet;
    private List<WalletBean> mWalletBeans;
    private TabLayout mTl_portrait;
    private ViewPager mVp_portrait;
    private List<BaseFragment> mBaseFragments;
    private List<String> mTitles;
    private FragmentUpdateAdapter mFragmentUpdateAdapter;
    private WalletBean mCurrentCheckedWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_portrait);

        initView();
        initData();
    }

    private void initView() {
        mTv_portrait_address = (TextView) findViewById(R.id.tv_portrait_address);
        mIb_portrait_switch_wallet = (ImageButton) findViewById(R.id.ib_portrait_switch_wallet);
        mTl_portrait = findViewById(R.id.tl_portrait);
        mVp_portrait = findViewById(R.id.vp_portrait);

        mIb_portrait_switch_wallet.setOnClickListener(this);
    }

    private void initData() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        mWalletBeans = apexWalletDbDao.queryWallets(Constant.TABLE_NEO_WALLET);
        if (null == mWalletBeans || mWalletBeans.isEmpty()) {
            CpLog.e(TAG, "walletBeans is null or empty!");
            return;
        }

        WalletBean walletBean = mWalletBeans.get(0);
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        mCurrentCheckedWallet = walletBean;
        mTv_portrait_address.setText(walletBean.getAddress());

        mTl_portrait.setupWithViewPager(mVp_portrait);

        mBaseFragments = new ArrayList<>();
        MeCommonPortraitFragment commonPortraitFragment = (MeCommonPortraitFragment)
                FragmentFactory.getFragment(Constant.FRAGMENT_TAG_ME_COMMON_PORTRAIT);
        mBaseFragments.add(commonPortraitFragment);
        MeEnterprisePortraitFragment enterprisePortraitFragment = (MeEnterprisePortraitFragment)
                FragmentFactory.getFragment(Constant.FRAGMENT_TAG_ME_ENTERPRISE_PORTRAIT);
        mBaseFragments.add(enterprisePortraitFragment);
        mTitles = Arrays.asList(getResources().getStringArray(R.array.me_portrait_type));

        mFragmentUpdateAdapter = new FragmentUpdateAdapter(getFragmentManager(), mBaseFragments,
                mTitles);
        mVp_portrait.setAdapter(mFragmentUpdateAdapter);
    }

//    @Override
//    public void onConfirmClick() {
//        mBaseFragments.remove(FragmentFactory.getFragment(Constant
//                .FRAGMENT_TAG_ME_ENTERPRISE_KEY));
//        BaseFragment fragment = FragmentFactory.getFragment(Constant
//                .FRAGMENT_TAG_ME_ENTERPRISE_PORTRAIT);
//
//        if (!mBaseFragments.contains(fragment)) {
//            mBaseFragments.add(fragment);
//        }
//
//        mFragmentUpdateAdapter.setNewFragments();
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_portrait_switch_wallet:
                showDialog();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        SwitchWallet2Dialog switchWallet2Dialog = SwitchWallet2Dialog.newInstance();
        switchWallet2Dialog.setCurrentWallet(mCurrentCheckedWallet);
        switchWallet2Dialog.setOnSelectedWalletListener(this);
        switchWallet2Dialog.show(getFragmentManager(), "SwitchWallet2Dialog");
    }

    @Override
    public void onSelectedWallet(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        mCurrentCheckedWallet = walletBean;
        mTv_portrait_address.setText(walletBean.getAddress());
    }
}
