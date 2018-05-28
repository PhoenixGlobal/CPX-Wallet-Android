package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.utils.CpLog;

public class MeRecyclerViewAdapter extends RecyclerView.Adapter<MeRecyclerViewAdapter
        .MeAdapterHolder> implements View
        .OnClickListener {

    private static final String TAG = MeRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<TransactionRecord> mTransactionRecords;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MeRecyclerViewAdapter(List<TransactionRecord> transactionRecords) {
        mTransactionRecords = transactionRecords;
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
    public MeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_me_tx_item,
                parent, false);
        MeAdapterHolder holder = new MeAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeAdapterHolder holder, int position) {
        holder.txid.setText(mTransactionRecords.get(position).getTxid());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mTransactionRecords ? 0 : mTransactionRecords.size();
    }

    class MeAdapterHolder extends RecyclerView.ViewHolder {
        TextView walletName;
        TextView transactionAmount;
        TextView transactionTime;
        TextView txid;

        MeAdapterHolder(View itemView) {
            super(itemView);
            txid = itemView.findViewById(R.id.tv_me_rv_item_txid);
        }
    }
}
