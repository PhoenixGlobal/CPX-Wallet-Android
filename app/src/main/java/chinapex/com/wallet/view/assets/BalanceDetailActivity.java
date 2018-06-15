package chinapex.com.wallet.view.assets;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.DrawerMenuRecyclerViewAdapter;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.DrawerMenu;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.wallet.CreateWalletActivity;

public class BalanceDetailActivity extends BaseActivity implements View.OnClickListener,
        DrawerLayout.DrawerListener, DrawerMenuRecyclerViewAdapter.DrawerMenuOnItemClickListener {

    private static final String TAG = BalanceDetailActivity.class.getSimpleName();
    private Button mBt_balance_detail_transfer;
    private Button mBt_balance_detail_gathering;
    private TextView mTv_balance_detail_assets_name;
    private TextView mTv_balance_detail_assets_value;
    private WalletBean mWalletBean;
    private DrawerLayout mDl_balance_detail;
    private TextView mTv_balance_detail_drawer_title;
    private RecyclerView mRv_balance_detail_drawer_menu;
    private DrawerMenuRecyclerViewAdapter mDrawerMenuRecyclerViewAdapter;
    private BalanceBean mBalanceBean;
    private LinearLayout mLl_balance_detail_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_detail);

        initView();
        initData();
    }

    private void initView() {
        mBt_balance_detail_transfer = (Button) findViewById(R.id.bt_balance_detail_transfer);
        mBt_balance_detail_gathering = (Button) findViewById(R.id.bt_balance_detail_gathering);
        mTv_balance_detail_assets_name = (TextView) findViewById(R.id
                .tv_balance_detail_assets_name);
        mTv_balance_detail_assets_value = (TextView) findViewById(R.id
                .tv_balance_detail_assets_value);
        mDl_balance_detail = (DrawerLayout) findViewById(R.id.dl_balance_detail);
        mTv_balance_detail_drawer_title = (TextView) findViewById(R.id
                .tv_balance_detail_drawer_title);
        mRv_balance_detail_drawer_menu = findViewById(R.id.rv_balance_detail_drawer_menu);
        mLl_balance_detail_map = (LinearLayout) findViewById(R.id.ll_balance_detail_map);

        mBt_balance_detail_transfer.setOnClickListener(this);
        mBt_balance_detail_gathering.setOnClickListener(this);
        mDl_balance_detail.addDrawerListener(this);

        mRv_balance_detail_drawer_menu.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        mDrawerMenuRecyclerViewAdapter = new DrawerMenuRecyclerViewAdapter(getWalletDetailMenus());
        mDrawerMenuRecyclerViewAdapter.setDrawerMenuOnItemClickListener(this);
        mRv_balance_detail_drawer_menu.setAdapter(mDrawerMenuRecyclerViewAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = intent.getParcelableExtra(Constant.WALLET_BEAN);
        mBalanceBean = intent.getParcelableExtra(Constant.BALANCE_BEAN);

        if (null == mWalletBean || null == mBalanceBean) {
            CpLog.e(TAG, "mWalletBean or mBalanceBean is null!");
            return;
        }

        switch (mBalanceBean.getAssetsID()) {
            case Constant.ASSETS_NEO:
                mTv_balance_detail_assets_name.setText(Constant.MARK_NEO);
                mLl_balance_detail_map.setVisibility(View.INVISIBLE);
                break;
            case Constant.ASSETS_NEO_GAS:
                mTv_balance_detail_assets_name.setText(Constant.MARK_NEO_GAS);
                mLl_balance_detail_map.setVisibility(View.INVISIBLE);
                break;
            case Constant.ASSETS_CPX:
                mTv_balance_detail_assets_name.setText(Constant.MARK_CPX);
                mLl_balance_detail_map.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        mTv_balance_detail_assets_value.setText(mBalanceBean.getAssetsValue());

        //设置侧滑title
        mTv_balance_detail_drawer_title.setText(String.valueOf(Constant.WALLET_NAME + mWalletBean
                .getWalletName()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_balance_detail_transfer:
                HashMap<String, Parcelable> hashMap = new HashMap<>();
                hashMap.put(Constant.PARCELABLE_WALLET_BEAN_TRANSFER, mWalletBean);
                hashMap.put(Constant.PARCELABLE_BALANCE_BEAN_TRANSFER, mBalanceBean);
                startActivityParcelables(TransferActivity.class, false, hashMap);
                break;
            case R.id.bt_balance_detail_gathering:
                startActivityParcelable(GatheringActivity.class, false, Constant
                        .PARCELABLE_WALLET_BEAN_GATHERING, mWalletBean);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    //recyclerView of the drawerLayout
    @Override
    public void drawerMenuOnItemClick(int position) {
        switch (position) {
            case 0:
                CpLog.i(TAG, "扫一扫");
                startActivity(new Intent(BalanceDetailActivity.this, CaptureActivity.class));
                break;
            case 1:
                CpLog.i(TAG, "创建钱包");
                startActivity(CreateWalletActivity.class, true);
                break;
            default:
                break;
        }
    }

    private List<DrawerMenu> getWalletDetailMenus() {
        ArrayList<DrawerMenu> drawerMenus = new ArrayList<>();
        //drawable数组要用TypedArray获取
        TypedArray ar = getResources().obtainTypedArray(R.array.balance_detail_drawer_icons);
        String[] menuTexts = getResources().getStringArray(R.array.balance_detail_drawer_texts);

        for (int i = 0; i < ar.length(); i++) {
            DrawerMenu drawerMenu = new DrawerMenu();
            drawerMenu.setMenuIcon(ar.getResourceId(i, 0));
            drawerMenu.setMenuText(menuTexts[i]);
            drawerMenus.add(drawerMenu);
        }
        ar.recycle();
        return drawerMenus;
    }
}
