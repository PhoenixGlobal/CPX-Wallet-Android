package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class AssetsOverviewRecyclerViewAdapter extends RecyclerView
        .Adapter<AssetsOverviewRecyclerViewAdapter.AssetsOverviewAdapterHolder> implements
        View.OnClickListener {

    private static final String TAG = AssetsOverviewRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<BalanceBean> mBalanceBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AssetsOverviewRecyclerViewAdapter(List<BalanceBean> balanceBeans) {
        mBalanceBeans = balanceBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null == mOnItemClickListener) {
            CpLog.e(TAG, "mOnItemClickListener is null!");
            return;
        }
        mOnItemClickListener.onItemClick((Integer) v.getTag());
    }

    @NonNull
    @Override
    public AssetsOverviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_assets_overview_item,
                parent, false);
        AssetsOverviewAdapterHolder holder = new AssetsOverviewAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssetsOverviewAdapterHolder holder, int position) {
        BalanceBean balanceBean = mBalanceBeans.get(position);
        if (null == balanceBean) {
            CpLog.e(TAG, "balanceBean is null!");
            return;
        }

        switch (balanceBean.getAssetsID()) {
            case Constant.ASSETS_NEO:
                holder.assetsName.setText(Constant.MARK_NEO);
                holder.assetsUnit.setText(Constant.UNIT_NEO);
                holder.mapState.setVisibility(View.INVISIBLE);
                break;
            case Constant.ASSETS_NEO_GAS:
                holder.assetsName.setText(Constant.MARK_NEO_GAS);
                holder.assetsUnit.setText(Constant.UNIT_NEO_GAS);
                holder.mapState.setVisibility(View.INVISIBLE);
                break;
            case Constant.ASSETS_CPX:
                holder.assetsName.setText(Constant.MARK_CPX);
                holder.assetsUnit.setText(Constant.UNIT_CPX);
                holder.mapState.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        holder.assetsValue.setText(balanceBean.getAssetsValue());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mBalanceBeans ? 0 : mBalanceBeans.size();
    }

    class AssetsOverviewAdapterHolder extends RecyclerView.ViewHolder {
        TextView assetsName;
        TextView assetsValue;
        Button mapState;
        TextView assetsUnit;

        AssetsOverviewAdapterHolder(View itemView) {
            super(itemView);
            assetsName = itemView.findViewById(R.id.tv_assets_overview_assets_name);
            assetsValue = itemView.findViewById(R.id.tv_assets_overview_assets_value);
            mapState = itemView.findViewById(R.id.bt_assets_overview_map);
            assetsUnit = itemView.findViewById(R.id.tv_assets_overview_assets_unit);
        }
    }
}
