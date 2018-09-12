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
import chinapex.com.wallet.global.ApexWalletApplication;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_me_item, parent, false);
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

        switch (walletBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                holder.walletType.setImageDrawable(ApexWalletApplication.getInstance().getResources().getDrawable(R.drawable
                        .icon_wallet_type_neo));
                break;
            case Constant.WALLET_TYPE_ETH:
                holder.walletType.setImageDrawable(ApexWalletApplication.getInstance().getResources().getDrawable(R.drawable
                        .icon_wallet_type_eth));
                break;
            case Constant.WALLET_TYPE_CPX:
                break;
            default:
                break;
        }

        holder.walletName.setText(walletBean.getName());
        holder.walletAddr.setText(walletBean.getAddress());

        int selectedTag = walletBean.getSelectedTag();

        if (selectedTag == Constant.SELECTED_TAG_TRANSACTION_RECORED) {
            holder.isBackup.setVisibility(View.INVISIBLE);
            holder.itemView.setTag(position);
            return;
        }

        if (selectedTag == Constant.SELECTED_TAG_MANAGER_WALLET) {
            int backupState = walletBean.getBackupState();
            switch (backupState) {
                case Constant.BACKUP_UNFINISHED:
                    holder.isBackup.setVisibility(View.VISIBLE);
                    break;
                case Constant.BACKUP_FINISH:
                    holder.isBackup.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mWalletBeans ? 0 : mWalletBeans.size();
    }

    class MeAdapterHolder extends RecyclerView.ViewHolder {
        ImageView walletType;
        TextView walletName;
        TextView walletAddr;
        TextView isBackup;


        MeAdapterHolder(View itemView) {
            super(itemView);
            walletType = itemView.findViewById(R.id.iv_me_rv_item_wallet_type);
            walletName = itemView.findViewById(R.id.tv_me_rv_item_wallet_name);
            walletAddr = itemView.findViewById(R.id.tv_me_rv_item_wallet_addr);
            isBackup = itemView.findViewById(R.id.tv_me_rv_item_backup);
        }
    }
}
