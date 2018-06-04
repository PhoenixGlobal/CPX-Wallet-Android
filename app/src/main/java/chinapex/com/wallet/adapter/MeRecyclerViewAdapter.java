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
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;

public class MeRecyclerViewAdapter extends RecyclerView.Adapter<MeRecyclerViewAdapter
        .MeAdapterHolder> implements View
        .OnClickListener {

    private static final String TAG = MeRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<WalletBean> mWalletBeans;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MeRecyclerViewAdapter(List<WalletBean> walletBeans) {
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
        mOnItemClickListener.onItemClick((Integer) v.getTag());
    }

    @NonNull
    @Override
    public MeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_me_item,
                parent, false);
        MeAdapterHolder holder = new MeAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeAdapterHolder holder, int position) {
        WalletBean walletBean = mWalletBeans.get(position);
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        holder.walletName.setText(String.valueOf(Constant.WALLET_NAME + walletBean.getWalletName
                ()));
        holder.walletAddr.setText(walletBean.getWalletAddr());
        holder.balance.setText(String.valueOf(walletBean.getBalance()));

        int backupState = walletBean.getBackupState();
        switch (backupState) {
            //未备份
            case 0:
                holder.isBackup.setVisibility(View.VISIBLE);
                break;
            //已备份
            case 1:
                holder.isBackup.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mWalletBeans ? 0 : mWalletBeans.size();
    }

    class MeAdapterHolder extends RecyclerView.ViewHolder {
        TextView walletName;
        TextView walletAddr;
        TextView balance;
        TextView isBackup;


        MeAdapterHolder(View itemView) {
            super(itemView);
            walletName = itemView.findViewById(R.id.tv_me_rv_item_wallet_name);
            walletAddr = itemView.findViewById(R.id.tv_me_rv_item_wallet_addr);
            balance = itemView.findViewById(R.id.tv_me_rv_item_balance);
            isBackup = itemView.findViewById(R.id.tv_me_rv_item_backup);
        }
    }
}
