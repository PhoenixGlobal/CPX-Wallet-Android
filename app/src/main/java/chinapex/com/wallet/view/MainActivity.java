package chinapex.com.wallet.view;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.FragmentFactory;
import chinapex.com.wallet.utils.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements BottomNavigationBar
        .OnTabSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationBar mBn_main;
    private String[] mBnItemTitles;
    private static final int REQUEST_PERMISSION = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), Constant.IS_FIRST_ENTER_MAIN, false);

        initData();
        initView();
        initBottomNavigationBar();
        initFragment();
        checkPermission();
    }

    private void initData() {
        mBnItemTitles = getResources().getStringArray(R.array.main_bn_item_title);
    }

    private void initView() {
        mBn_main = (BottomNavigationBar) findViewById(R.id.bn_main);
    }

    private void initBottomNavigationBar() {
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_assets, mBnItemTitles[0]));
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_me, mBnItemTitles[1]));
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_excitation, mBnItemTitles[2]));
        mBn_main.setActiveColor(R.color.c_1253BF);
        mBn_main.setInActiveColor(R.color.c_979797);
        mBn_main.initialise();
        mBn_main.setTabSelectedListener(this);
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < mBnItemTitles.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(i + "");
            if (null != fragment) {
                fragmentTransaction.remove(fragment);
            }
        }
        fragmentTransaction.add(R.id.fl_main, FragmentFactory.getFragment(0), "0");
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_main, fragment, "" + position);
        }
        fragmentTransaction.show(fragment).commit();
    }

    @Override
    public void onTabUnselected(int position) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(FragmentFactory.getFragment(position));
        fragmentTransaction.commit();
    }

    @Override
    public void onTabReselected(int position) {

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        REQUEST_PERMISSION);
            }
        }
    }

}
