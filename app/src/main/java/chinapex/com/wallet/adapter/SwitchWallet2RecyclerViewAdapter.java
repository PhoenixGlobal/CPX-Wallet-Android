package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.utils.CpLog;

public class SwitchWallet2RecyclerViewAdapter extends RecyclerView
        .Adapter<SwitchWallet2RecyclerViewAdapter.SwitchWallet2Holder> implements View
        .OnClickListener {

    private static final String TAG = SwitchWallet2RecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<WalletBean> mWalletBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SwitchWallet2RecyclerViewAdapter(List<WalletBean> walletBeans) {
        mWalletBeans = walletBeans;
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

        if (null == mWalletBeans || mWalletBeans.isEmpty()) {
            CpLog.e(TAG, "mWalletBeans is null or empty!");
            return;
        }

        Integer position = (Integer) v.getTag();

        for (int i = 0; i < mWalletBeans.size(); i++) {
            WalletBean walletBean = mWalletBeans.get(i);
            if (null == walletBean) {
                CpLog.i(TAG, "walletBean is null!");
                continue;
            }

            if (i == position) {
                walletBean.setSelected(true);
            } else {
                walletBean.setSelected(false);
            }
        }

        notifyDataSetChanged();
        mOnItemClickListener.onItemClick(position);
    }

    @NonNull
    @Override
    public SwitchWallet2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_choose_wallet, parent, false);
        SwitchWallet2Holder holder = new SwitchWallet2Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SwitchWallet2Holder holder, int position) {
        WalletBean walletBean = mWalletBeans.get(position);
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        holder.walletName.setText(walletBean.getWalletName());
        holder.walletAddress.setText(walletBean.getWalletAddr());
        if (walletBean.isSelected()) {
            holder.checkState.setVisibility(View.VISIBLE);
        } else {
            holder.checkState.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mWalletBeans ? 0 : mWalletBeans.size();
    }


    class SwitchWallet2Holder extends RecyclerView.ViewHolder {
        TextView walletName;
        TextView walletAddress;
        ImageView checkState;

        SwitchWallet2Holder(View itemView) {
            super(itemView);
            walletName = itemView.findViewById(R.id.tv_choose_wallet_name);
            walletAddress = itemView.findViewById(R.id.tv_choose_wallet_address);
            checkState = itemView.findViewById(R.id.iv_choose_wallet_check_state);
        }
    }
}
