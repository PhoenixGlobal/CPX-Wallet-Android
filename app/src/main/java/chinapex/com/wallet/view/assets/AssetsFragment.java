package chinapex.com.wallet.view.assets;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.AssetsRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.WalletKeyStore;
import chinapex.com.wallet.bean.request.RequestGetAccountState;
import chinapex.com.wallet.bean.response.ResponseGetAccountState;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.net.INetCallback;
import chinapex.com.wallet.net.OkHttpClientManager;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.GsonUtils;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.view.WalletDetailActivity;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class AssetsFragment extends BaseFragment implements AssetsRecyclerViewAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AssetsFragment.class.getSimpleName();
    private RecyclerView mRv_assets;
    private ArrayList<WalletBean> mWalletBeans;
    private SwipeRefreshLayout mSl_assets_rv;
    private AssetsRecyclerViewAdapter mAssetsRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        View fragment_assets = inflater.inflate(R.layout.fragment_assets, container, false);
        initView(fragment_assets);
        return fragment_assets;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRv_assets.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(),
                LinearLayoutManager.VERTICAL,
                false));
        mAssetsRecyclerViewAdapter = new AssetsRecyclerViewAdapter
                (getData());
        mAssetsRecyclerViewAdapter.setOnItemClickListener(this);

        int space = 20;
        mRv_assets.addItemDecoration(new SpacesItemDecoration(space));

        mRv_assets.setAdapter(mAssetsRecyclerViewAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initView(View view) {
        mRv_assets = (RecyclerView) view.findViewById(R.id.rv_assets);
        mSl_assets_rv = (SwipeRefreshLayout) view.findViewById(R.id.sl_assets_rv);

        mSl_assets_rv.setColorSchemeColors(this.getActivity().getResources().getColor(R.color
                .colorPrimary));
        mSl_assets_rv.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(int position) {
        CpLog.i(TAG, "onItemClick:" + position);
        startActivityParcelable(WalletDetailActivity.class, false, Constant.WALLET_BEAN,
                mWalletBeans.get(position));

    }

    private List<WalletBean> getData() {
        String keyStores = (String) SharedPreferencesUtils.getParam(this.getActivity(), Constant
                .SP_WALLET_KEYSTORE, "");
        if (TextUtils.isEmpty(keyStores)) {
            CpLog.e(TAG, "keyStores is null!");
            return null;
        }

        List<WalletKeyStore> walletKeyStores = GsonUtils.json2List(keyStores);
        if (null == walletKeyStores) {
            CpLog.e(TAG, "jsonListObject is null!");
            return null;
        }

        mWalletBeans = new ArrayList<>();
        for (WalletKeyStore walletKeyStore : walletKeyStores) {
            WalletBean walletBean = new WalletBean();
            walletBean.setWalletName(walletKeyStore.getWalletName());
            walletBean.setWalletAddr(walletKeyStore.getWalletAddr());
            walletBean.setBalance(0.0);
            mWalletBeans.add(walletBean);
        }

        return mWalletBeans;
    }


    @Override
    public void onRefresh() {
        getBalance(mWalletBeans);
    }

    private void getBalance(List<WalletBean> walletBeanList) {
        for (final WalletBean walletBean : walletBeanList) {

            final RequestGetAccountState requestGetAccountState = new RequestGetAccountState();
            requestGetAccountState.setJsonrpc("2.0");
            requestGetAccountState.setMethod("getaccountstate");
            requestGetAccountState.setId(1);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(walletBean.getWalletAddr());
            requestGetAccountState.setParams(arrayList);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClientManager.getInstance().postJson(Constant.URL_CLI, GsonUtils.toJsonStr
                            (requestGetAccountState), new INetCallback() {
                        @Override
                        public void onSuccess(int statusCode, String msg, String result) {
                            CpLog.i(TAG, "onSuccess");
                            ResponseGetAccountState responseGetAccountState = GsonUtils.json2Bean
                                    (result, ResponseGetAccountState.class);
                            List<ResponseGetAccountState.ResultBean.BalancesBean> balances =
                                    responseGetAccountState.getResult().getBalances();
                            if (null == balances || balances.isEmpty()) {
                                walletBean.setBalance(0.0);
                            } else {
                                for (ResponseGetAccountState.ResultBean.BalancesBean balance :
                                        balances) {
                                    if (Constant.ASSETS_NEO.equals(balance.getAsset())) {
                                        String balanceValue = balance.getValue();
                                        walletBean.setBalance(Double.valueOf(balanceValue));
                                    }
                                }
                            }

                            AssetsFragment.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mSl_assets_rv.setRefreshing(false);
                                    mAssetsRecyclerViewAdapter.notifyDataSetChanged();
                                }
                            });

                        }

                        @Override
                        public void onFailed(int failedCode, String msg) {
                            CpLog.e(TAG, "onFailed");
                            mSl_assets_rv.setRefreshing(false);
                        }
                    });
                }
            }).start();

        }
    }
}
