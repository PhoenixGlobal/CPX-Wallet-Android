package chinapex.com.wallet.view.wallet;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.WalletDetailDrawerRecyclerViewAdapter;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.WalletDetailMenu;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class WalletDetailActivity extends BaseActivity implements View.OnClickListener,
        DrawerLayout.DrawerListener, WalletDetailDrawerRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = WalletDetailActivity.class.getSimpleName();
    private Button mBt_wallet_detail_transfer;
    private Button mBt_wallet_detail_gathering;
    private TextView mTv_wallet_detail_wallet_name;
    private TextView mTv_wallet_detail_balance;
    private TextView mTv_wallet_detail_addr;
    private WalletBean mWalletBean;
    private DrawerLayout mDl_wallet_detail;
    private TextView mTv_wallet_detail_drawer_title;
    private RecyclerView mRv_wallet_detail_drawer_menu;
    private WalletDetailDrawerRecyclerViewAdapter mWalletDetailDrawerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        initView();
        initData();
    }

    private void initView() {
        mBt_wallet_detail_transfer = (Button) findViewById(R.id.bt_wallet_detail_transfer);
        mBt_wallet_detail_gathering = (Button) findViewById(R.id.bt_wallet_detail_gathering);
        mTv_wallet_detail_wallet_name = (TextView) findViewById(R.id.tv_wallet_detail_wallet_name);
        mTv_wallet_detail_balance = (TextView) findViewById(R.id.tv_wallet_detail_balance);
        mTv_wallet_detail_addr = (TextView) findViewById(R.id.tv_wallet_detail_addr);
        mDl_wallet_detail = (DrawerLayout) findViewById(R.id.dl_wallet_detail);
        mTv_wallet_detail_drawer_title = (TextView) findViewById(R.id
                .tv_wallet_detail_drawer_title);
        mRv_wallet_detail_drawer_menu = findViewById(R.id.rv_wallet_detail_drawer_menu);

        mBt_wallet_detail_transfer.setOnClickListener(this);
        mBt_wallet_detail_gathering.setOnClickListener(this);
        mDl_wallet_detail.addDrawerListener(this);

        mRv_wallet_detail_drawer_menu.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
//        int space = 40;
//        mRv_wallet_detail_drawer_menu.addItemDecoration(new SpacesItemDecoration(space));
        mWalletDetailDrawerRecyclerViewAdapter = new WalletDetailDrawerRecyclerViewAdapter
                (getWalletDetailMenus());
        mWalletDetailDrawerRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_wallet_detail_drawer_menu.setAdapter(mWalletDetailDrawerRecyclerViewAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null == intent) {
            CpLog.e(TAG, "intent is null!");
            return;
        }

        mWalletBean = (WalletBean) intent.getParcelableExtra(Constant.WALLET_BEAN);
        CpLog.i(TAG, "walletBean:" + mWalletBean.toString());
        mTv_wallet_detail_wallet_name.setText(mWalletBean.getWalletName());
        mTv_wallet_detail_balance.setText(String.valueOf(mWalletBean.getBalance()));
        mTv_wallet_detail_addr.setText(mWalletBean.getWalletAddr());

        //设置侧滑title
        mTv_wallet_detail_drawer_title.setText(String.valueOf(Constant.WALLET_NAME + mWalletBean
                .getWalletName()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_wallet_detail_transfer:
                startActivityParcelable(TransferActivity.class, false, Constant
                        .PARCELABLE_WALLET_BEAN_TRANSFER, mWalletBean);
                break;
            case R.id.bt_wallet_detail_gathering:
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
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                CpLog.i(TAG, "扫一扫");

                break;
            case 1:
                CpLog.i(TAG, "创建钱包");
                startActivity(CreateWalletActivity.class, false, TAG);
                break;
            default:
                break;
        }
    }

    private List<WalletDetailMenu> getWalletDetailMenus() {
        ArrayList<WalletDetailMenu> walletDetailMenus = new ArrayList<>();
        //drawable数组要用TypedArray获取
        TypedArray ar = getResources().obtainTypedArray(R.array.wallet_detail_drawer_icons);
        String[] menuTexts = getResources().getStringArray(R.array.wallet_detail_drawer_texts);

        for (int i = 0; i < ar.length(); i++) {
            WalletDetailMenu walletDetailMenu = new WalletDetailMenu();
            walletDetailMenu.setMenuIcon(ar.getResourceId(i, 0));
            walletDetailMenu.setMenuText(menuTexts[i]);
            walletDetailMenus.add(walletDetailMenu);
        }
        ar.recycle();
        return walletDetailMenus;
    }
}
