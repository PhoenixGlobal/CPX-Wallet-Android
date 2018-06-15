package chinapex.com.wallet.view.assets;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.AssetsRecyclerViewAdapter;
import chinapex.com.wallet.adapter.DrawerMenuRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.DrawerMenu;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.changelistener.OnItemAddListener;
import chinapex.com.wallet.changelistener.OnItemDeleteListener;
import chinapex.com.wallet.changelistener.OnItemNameUpdateListener;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.view.wallet.CreateWalletActivity;
import chinapex.com.wallet.view.wallet.ImportWalletActivity;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class AssetsFragment extends BaseFragment implements AssetsRecyclerViewAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AssetsRecyclerViewAdapter
        .OnItemLongClickListener, OnItemDeleteListener, OnItemAddListener, DrawerLayout
        .DrawerListener, DrawerMenuRecyclerViewAdapter.DrawerMenuOnItemClickListener, View
        .OnClickListener, OnItemNameUpdateListener {

    private static final String TAG = AssetsFragment.class.getSimpleName();
    private RecyclerView mRv_assets;
    private List<WalletBean> mWalletBeans;
    private SwipeRefreshLayout mSl_assets_rv;
    private AssetsRecyclerViewAdapter mAssetsRecyclerViewAdapter;
    private DrawerLayout mDl_assets;
    private RecyclerView mRv_assets_drawer_menu;
    private DrawerMenuRecyclerViewAdapter mDrawerMenuRecyclerViewAdapter;
    private ImageButton mIb_assets_ellipsis;
    private LinearLayout mLl_assets_drawer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        View fragment_assets = inflater.inflate(R.layout.fragment_assets, container, false);
        return fragment_assets;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mRv_assets = (RecyclerView) view.findViewById(R.id.rv_assets);
        mSl_assets_rv = (SwipeRefreshLayout) view.findViewById(R.id.sl_assets_rv);

        mRv_assets.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(),
                LinearLayoutManager.VERTICAL,
                false));
        mWalletBeans = getData();
        mAssetsRecyclerViewAdapter = new AssetsRecyclerViewAdapter(mWalletBeans);
        mAssetsRecyclerViewAdapter.setOnItemClickListener(this);
        mAssetsRecyclerViewAdapter.setOnItemLongClickListener(this);

        int space = DensityUtil.dip2px(getActivity(), 5);
        mRv_assets.addItemDecoration(new SpacesItemDecoration(space));

        mRv_assets.setAdapter(mAssetsRecyclerViewAdapter);

        mSl_assets_rv.setColorSchemeColors(this.getActivity().getResources().getColor(R.color
                .colorPrimary));
        mSl_assets_rv.setOnRefreshListener(this);

        // 侧滑布局
        mLl_assets_drawer = view.findViewById(R.id.ll_assets_drawer);

        // 省略号按钮
        mIb_assets_ellipsis = view.findViewById(R.id.ib_assets_ellipsis);
        mIb_assets_ellipsis.setOnClickListener(this);

        // 侧滑菜单
        mDl_assets = (DrawerLayout) view.findViewById(R.id.dl_assets);
        mRv_assets_drawer_menu = view.findViewById(R.id.rv_assets_drawer_menu);

        mDl_assets.addDrawerListener(this);
        mRv_assets_drawer_menu.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mDrawerMenuRecyclerViewAdapter = new DrawerMenuRecyclerViewAdapter(getAssetsMenus());
        mDrawerMenuRecyclerViewAdapter.setDrawerMenuOnItemClickListener(this);
        mRv_assets_drawer_menu.setAdapter(mDrawerMenuRecyclerViewAdapter);

    }

    private void initData() {
        ApexListeners.getInstance().addOnItemDeleteListener(this);
        ApexListeners.getInstance().addOnItemAddListener(this);
        ApexListeners.getInstance().addOnItemNameUpdateListener(this);
    }

    @Override
    public void onItemClick(int position) {
        startActivityParcelable(AssetsOverviewActivity.class, false, Constant.WALLET_BEAN,
                mWalletBeans.get(position));
    }

    @Override
    public void onItemLongClick(int position) {
        CpLog.i(TAG, "长按了onItemLongClick:" + position);
        // 预留长按左滑删除逻辑
    }

    private List<WalletBean> getData() {
        List<WalletBean> walletBeans = new ArrayList<>();
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null！");
            return walletBeans;
        }

        walletBeans.addAll(apexWalletDbDao.queryWalletBeans(Constant.TABLE_APEX_WALLET));
        return walletBeans;
    }


    @Override
    public void onRefresh() {
        // 预留后续刷新功能
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSl_assets_rv.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemDelete(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "onItemDelete() -> walletBean is null!");
            return;
        }

        if (!mWalletBeans.contains(walletBean)) {
            CpLog.e(TAG, "onItemDelete() -> this wallet not exist!");
            return;
        }
        mWalletBeans.remove(walletBean);
        mAssetsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemAdd(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "onItemAdd() -> walletBean is null!");
            return;
        }

        if (mWalletBeans.contains(walletBean)) {
            CpLog.e(TAG, "onItemAdd() -> this wallet has existed!");
            return;
        }

        mWalletBeans.add(walletBean);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAssetsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
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

    private List<DrawerMenu> getAssetsMenus() {
        ArrayList<DrawerMenu> drawerMenus = new ArrayList<>();
        //drawable数组要用TypedArray获取
        TypedArray ar = getResources().obtainTypedArray(R.array.assets_drawer_icons);
        String[] menuTexts = getResources().getStringArray(R.array.assets_drawer_texts);

        for (int i = 0; i < ar.length(); i++) {
            DrawerMenu drawerMenu = new DrawerMenu();
            drawerMenu.setMenuIcon(ar.getResourceId(i, 0));
            drawerMenu.setMenuText(menuTexts[i]);
            drawerMenus.add(drawerMenu);
        }
        ar.recycle();
        return drawerMenus;
    }

    @Override
    public void drawerMenuOnItemClick(int position) {
        switch (position) {
            case 0:
                // 添加资产
                break;
            case 1:
                // 创建钱包
                startActivity(CreateWalletActivity.class, false);
                break;
            case 2:
                // 导入钱包
                startActivity(ImportWalletActivity.class, false);
                break;
            default:
                break;
        }
        closeDrawer(mLl_assets_drawer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_assets_ellipsis:
                openDrawer(mLl_assets_drawer);
                break;
            default:
                break;
        }
    }

    private void openDrawer(View drawer) {
        if (!mDl_assets.isDrawerOpen(drawer)) {
            mDl_assets.openDrawer(drawer);
        }
    }

    private void closeDrawer(View drawer) {
        if (mDl_assets.isDrawerOpen(drawer)) {
            mDl_assets.closeDrawer(drawer);
        }
    }

    @Override
    public void OnItemNameUpdate(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        for (WalletBean walletBeanTmp : mWalletBeans) {
            if (null == walletBeanTmp) {
                CpLog.e(TAG, "walletBeanTmp is null!");
                continue;
            }

            if (walletBeanTmp.equals(walletBean)) {
                walletBeanTmp.setWalletName(walletBean.getWalletName());
            }
        }

        mAssetsRecyclerViewAdapter.notifyDataSetChanged();
    }
}
