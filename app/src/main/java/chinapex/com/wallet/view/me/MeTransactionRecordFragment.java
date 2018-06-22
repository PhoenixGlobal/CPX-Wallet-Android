package chinapex.com.wallet.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.TransactionRecordRecyclerViewAdapter;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.MeSkipActivity;
import chinapex.com.wallet.view.dialog.SwitchWalletDialog;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class MeTransactionRecordFragment extends BaseFragment implements View.OnClickListener,
        SwitchWalletDialog.onItemSelectedListener, SwipeRefreshLayout.OnRefreshListener,
        TransactionRecordRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = MeTransactionRecordFragment.class.getSimpleName();
    private TextView mTv_me_transaction_record_title;
    private TextView mTv_me_transaction_record_address;
    private ImageButton mIb_me_transaction_record_switch;
    private WalletBean mCurrentClickedWalletBean;
    private SwipeRefreshLayout mSl_transaction_record;
    private RecyclerView mRv_transaction_record;
    private List<TransactionRecord> mTransactionRecords;
    private TransactionRecordRecyclerViewAdapter mTransactionRecordRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_transaction_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mTv_me_transaction_record_title = view.findViewById(R.id.tv_me_transaction_record_title);
        mTv_me_transaction_record_address = view.findViewById(R.id
                .tv_me_transaction_record_address);
        mIb_me_transaction_record_switch = view.findViewById(R.id.ib_me_transaction_record_switch);
        mSl_transaction_record = view.findViewById(R.id.sl_transaction_record);
        mRv_transaction_record = view.findViewById(R.id.rv_transaction_record);

        mRv_transaction_record.setLayoutManager(new LinearLayoutManager(ApexWalletApplication
                .getInstance(), LinearLayoutManager.VERTICAL, false));
        mTransactionRecords = getData();
        mTransactionRecordRecyclerViewAdapter = new TransactionRecordRecyclerViewAdapter
                (mTransactionRecords);
        mTransactionRecordRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_transaction_record.setAdapter(mTransactionRecordRecyclerViewAdapter);

        mIb_me_transaction_record_switch.setOnClickListener(this);
        mSl_transaction_record.setColorSchemeColors(this.getActivity().getResources().getColor(R
                .color.colorPrimary));
        mSl_transaction_record.setOnRefreshListener(this);
    }

    private void initData() {
        MeSkipActivity meSkipActivity = (MeSkipActivity) getActivity();
        mCurrentClickedWalletBean = meSkipActivity.getWalletBean();
        if (null == mCurrentClickedWalletBean) {
            CpLog.e(TAG, "currentClickedWalletBean is null!");
            return;
        }

        mTv_me_transaction_record_title.setText(String.valueOf(Constant.WALLET_NAME +
                mCurrentClickedWalletBean
                        .getWalletName()));
        mTv_me_transaction_record_address.setText(mCurrentClickedWalletBean.getWalletAddr());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_me_transaction_record_switch:
                showDialog(mCurrentClickedWalletBean);
                break;
            default:
                break;
        }
    }

    private void showDialog(WalletBean currentClickedWalletBean) {
        SwitchWalletDialog switchWalletDialog = SwitchWalletDialog.newInstance();
        switchWalletDialog.setCurrentWalletBean(currentClickedWalletBean);
        switchWalletDialog.setOnItemSelectedListener(this);
        switchWalletDialog.show(getFragmentManager(), "SwitchWalletDialog");
    }

    @Override
    public void onItemSelected(WalletBean walletBean) {
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        mCurrentClickedWalletBean = walletBean;
        mTv_me_transaction_record_title.setText(String.valueOf(Constant.WALLET_NAME + walletBean
                .getWalletName()));
        mTv_me_transaction_record_address.setText(walletBean.getWalletAddr());
    }

    @Override
    public void onItemClick(int position) {
        if (null == mTransactionRecords || mTransactionRecords.isEmpty()) {
            CpLog.e(TAG, "mTransactionRecords is null or is empty!");
            return;
        }

        TransactionRecord transactionRecord = mTransactionRecords.get(position);
        if (null == transactionRecord) {
            CpLog.e(TAG, "transactionRecord is null!");
            return;
        }

        startActivityParcelable(TransactionDetailActivity.class, false, Constant
                .PARCELABLE_TRANSACTION_RECORD, transactionRecord);
    }

    @Override
    public void onRefresh() {
        // 预留后续刷新功能
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSl_transaction_record.setRefreshing(false);
            }
        });
    }

    private List<TransactionRecord> getData() {
        mTransactionRecords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TransactionRecord transactionRecord = new TransactionRecord();
            transactionRecord.setLogoUrl("");
            transactionRecord.setTxID
                    ("0xfbc12dd529a981b734e9324b3c0693d6b173b29c204922b27840749d661ca53" + i);
            transactionRecord.setTxAmount("+100000.0000000" + i);
            transactionRecord.setTime(System.currentTimeMillis() + i + 100000);
            transactionRecord.setTxState(i % 3);
            transactionRecord.setSymbol("CPX");
            transactionRecord.setFrom("ALDbmTMY54RZnLmibH3eXfHvrZt4fLiZh" + i);
            transactionRecord.setTo("AKJZ6oNkSLzAvStDe8SrBvd83DntY4AvT" + i);
            mTransactionRecords.add(transactionRecord);
        }
        return mTransactionRecords;
    }

}
