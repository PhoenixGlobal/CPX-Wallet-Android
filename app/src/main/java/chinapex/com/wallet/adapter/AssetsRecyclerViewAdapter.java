package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class AssetsRecyclerViewAdapter extends RecyclerView.Adapter<AssetsRecyclerViewAdapter
        .AssetsAdapterHolder> implements
        View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = AssetsRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private List<WalletBean> mWalletBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public AssetsRecyclerViewAdapter(List<WalletBean> walletBeans) {
        mWalletBeans = walletBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null == mOnItemClickListener) {
            CpLog.e(TAG, "mOnItemClickListener is null!");
            return;
        }
        mOnItemClickListener.onItemClick((Integer) v.getTag());
    }

    @Override
    public boolean onLongClick(View v) {
        if (null == mOnItemLongClickListener) {
            CpLog.e(TAG, "mOnItemLongClickListener is null!");
            return false;
        }
        mOnItemLongClickListener.onItemLongClick((Integer) v.getTag());
        return true;
    }

    @NonNull
    @Override
    public AssetsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_assets_item,
                parent, false);
        AssetsAdapterHolder holder = new AssetsAdapterHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssetsAdapterHolder holder, int position) {
        holder.walletName.setText(String.valueOf(Constant.WALLET_NAME + mWalletBeans.get
                (position).getWalletName()));
        holder.walletAddr.setText(mWalletBeans.get(position).getWalletAddr());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mWalletBeans ? 0 : mWalletBeans.size();
    }

    class AssetsAdapterHolder extends RecyclerView.ViewHolder {
        TextView walletName;
        TextView walletAddr;

        AssetsAdapterHolder(View itemView) {
            super(itemView);
            walletName = itemView.findViewById(R.id.tv_assets_rv_item_wallet_name);
            walletAddr = itemView.findViewById(R.id.tv_assets_rv_item_wallet_addr);
        }
    }
}
