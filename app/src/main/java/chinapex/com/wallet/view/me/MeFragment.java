package chinapex.com.wallet.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.MeRecyclerViewAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.SharedPreferencesUtils;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class MeFragment extends BaseFragment implements MeRecyclerViewAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MeFragment.class.getSimpleName();
    private RecyclerView mRv_me;
    private MeRecyclerViewAdapter mMeRecyclerViewAdapter;
    private List<TransactionRecord> mTransactionRecords;
    private SwipeRefreshLayout mSl_me;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        mRv_me = view.findViewById(R.id.rv_me);
        mSl_me = view.findViewById(R.id.sl_me);

        mRv_me.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(),
                LinearLayoutManager.VERTICAL,
                false));
        mTransactionRecords = getData();
        mMeRecyclerViewAdapter = new MeRecyclerViewAdapter(mTransactionRecords);
        mMeRecyclerViewAdapter.setOnItemClickListener(this);

        int space = 20;
        mRv_me.addItemDecoration(new SpacesItemDecoration(space));

        mRv_me.setAdapter(mMeRecyclerViewAdapter);

        mSl_me.setColorSchemeColors(this.getActivity().getResources().getColor(R.color
                .colorPrimary));
        mSl_me.setOnRefreshListener(this);

    }

    private List<TransactionRecord> getData() {
        ArrayList<TransactionRecord> transactionRecords = new ArrayList<>();
        String txID = (String) SharedPreferencesUtils.getParam(ApexWalletApplication.getInstance
                (), Constant.SP_TX_ID, "");
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setTxid(txID);
        transactionRecords.add(transactionRecord);
        return transactionRecords;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        List<TransactionRecord> transactionRecords = getData();
        mTransactionRecords.get(0).setTxid(transactionRecords.get(0).getTxid());
        MeFragment.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSl_me.setRefreshing(false);
                mMeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
