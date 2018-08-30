package chinapex.com.wallet.view.excitation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.ExcitationAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecorationBottom;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.ExcitationBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.view.excitation.detail.ExcitationDetailActivity;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class ExcitationFragment extends BaseFragment implements ExcitationAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, /*View.OnScrollChangeListener,*/
        IGetExcitationView {

    private static final String TAG = ExcitationFragment.class.getSimpleName();

    private List<ExcitationBean> mList;
    private RecyclerView mExcitationEvnet;
    private RelativeLayout mExcitationApexHeader;
    private RelativeLayout mExcitationHeader;
    private SwipeRefreshLayout mExcitationRefresh;
    private IGetExcitationPresenter mIGetExcitationPresenter;
    private ExcitationAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        return inflater.inflate(R.layout.fragment_excitation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mExcitationApexHeader = (RelativeLayout) view.findViewById(R.id.excitation_apex_header);
        mExcitationHeader = (RelativeLayout) view.findViewById(R.id.excitation_header);
        mExcitationEvnet = (RecyclerView) view.findViewById(R.id.new_event);
        mExcitationRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_event_refresh);

        mExcitationEvnet.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mList = new ArrayList<>();
        mAdapter = new ExcitationAdapter(mList);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout
                .fragment_excitation_recyclerview_item_header, mExcitationEvnet, false);
        mAdapter.addHeaderView(header);
        mAdapter.setOnItemClickListener(this);
        mExcitationEvnet.setAdapter(mAdapter);
//        mExcitationEvnet.setOnScrollChangeListener(this);

        int space = DensityUtil.dip2px(getActivity(), 15);
        mExcitationEvnet.addItemDecoration(new SpacesItemDecorationBottom(space));
        mExcitationRefresh.setColorSchemeColors(ApexWalletApplication.getInstance().getResources
                ().getColor(R.color.c_1253BF));
        mExcitationRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        mIGetExcitationPresenter = new GetExcitationPresenter(this);
        mIGetExcitationPresenter.getExcitation();
    }

    @Override
    public void onItemClick(int position) {
        ExcitationBean excitationBean = mList.get(position);
        if (null == excitationBean) {
            CpLog.e(TAG, "excitationBean is null!");
            return;
        }

        Intent intent = new Intent(getActivity(), ExcitationDetailActivity.class);
        intent.putExtra(Constant.EXCITATION_GAS_LIMIT, excitationBean.getGasLimit());
        intent.putExtra(Constant.EXCITATION_ACTIVITY_ID, excitationBean.getActivityId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mIGetExcitationPresenter.getExcitation();
    }

//    @Override
//    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//        if (null == view) return;
//        RecyclerView.LayoutManager layoutManager = mExcitationEvnet.getLayoutManager();
//        if (layoutManager instanceof LinearLayoutManager) {
//            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
//            if (firstItemPosition != 0) {
//                mExcitationApexHeader.setVisibility(View.INVISIBLE);
//            } else {
//                mExcitationApexHeader.setVisibility(View.VISIBLE);
//            }
//        }
//    }


    @Override
    public void getExcitation(List<ExcitationBean> excitationBeans) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mExcitationRefresh.isRefreshing()) {
                    mExcitationRefresh.setRefreshing(false);
                }
            }
        });

        if (null == excitationBeans) {
            CpLog.e(TAG, "excitationBeans is null");
            return;
        }

        mList.clear();
        mList.addAll(excitationBeans);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
