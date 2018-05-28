package chinapex.com.wallet.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.utils.FragmentFactory;

public class MainActivity extends BaseActivity implements BottomNavigationBar
        .OnTabSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mTb_main;
    private BottomNavigationBar mBn_main;
    private String[] mBnItemTitles;
    private TextView mTv_main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initToolBar();
        initBottomNavigationBar();
        initFragment();
    }

    private void initData() {
        mBnItemTitles = getResources().getStringArray(R.array.main_bn_item_title);
    }

    private void initView() {
        mTb_main = (Toolbar) findViewById(R.id.tb_main);
        mBn_main = (BottomNavigationBar) findViewById(R.id.bn_main);
        mTv_main_title = (TextView) findViewById(R.id.tv_main_title);

    }

    private void initToolBar() {
        mTb_main.setTitle("");
        setSupportActionBar(mTb_main);
        mTv_main_title.setText(mBnItemTitles[0]);

//        ActionBar supportActionBar = getSupportActionBar();
//        if (null == supportActionBar) {
//            CpLog.e(TAG, "supportActionBar is null!");
//            return;
//        }
//        supportActionBar.setDisplayHomeAsUpEnabled(true);
//        supportActionBar.setDisplayShowTitleEnabled(false);
    }

    private void initBottomNavigationBar() {
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_discover,
                mBnItemTitles[0]));
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_assets,
                mBnItemTitles[1]));
        mBn_main.addItem(new BottomNavigationItem(R.drawable.bn_main_item_me,
                mBnItemTitles[2]));
        mBn_main.setActiveColor(R.color.colorPrimary);
        mBn_main.setInActiveColor(R.color.colorAccent);
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

        mTv_main_title.setText(mBnItemTitles[0]);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_main, fragment, "" + position);
        }
        fragmentTransaction.show(fragment).commit();

        mTv_main_title.setText(mBnItemTitles[position]);

        //如果当前fragment为资产页，隐藏ToolBar
        if (position != 0) {
            mTb_main.setVisibility(View.GONE);
        } else {
            mTb_main.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabUnselected(int position) {
        getFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position))
                .commit();
    }

    @Override
    public void onTabReselected(int position) {
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuBuilder builder = (MenuBuilder) menu;
//        builder.setOptionalIconsVisible(true);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_add_friend:
//                break;
//            case R.id.menu_scan:
//                break;
//            case R.id.menu_about:
//                break;
//            case android.R.id.home:
//                finish();
//                break;
//
//        }
//        return true;
//    }

}
