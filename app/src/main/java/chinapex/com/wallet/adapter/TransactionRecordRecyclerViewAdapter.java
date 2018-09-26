package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.global.GlideApp;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;
import chinapex.com.wallet.utils.WalletUtils;

public class TransactionRecordRecyclerViewAdapter extends RecyclerView
        .Adapter<TransactionRecordRecyclerViewAdapter.TransactionRecordAdapterHolder> implements
        View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = TransactionRecordRecyclerViewAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private List<TransactionRecord> mTransactionRecords;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public TransactionRecordRecyclerViewAdapter(List<TransactionRecord> transactionRecords) {
        mTransactionRecords = transactionRecords;
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
    public TransactionRecordAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_transaction_record, parent, false);
        TransactionRecordAdapterHolder holder = new TransactionRecordAdapterHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecordAdapterHolder holder, int position) {
        TransactionRecord transactionRecord = mTransactionRecords.get(position);
        if (null == transactionRecord) {
            CpLog.e(TAG, "transactionRecord is null!");
            return;
        }

        holder.txID.setText(transactionRecord.getTxID());
        holder.txAmount.setText(transactionRecord.getTxAmount());
        holder.txTime.setText(PhoneUtils.getFormatTime(transactionRecord.getTxTime()));

        switch (transactionRecord.getTxState()) {
            case Constant.TRANSACTION_STATE_FAIL:
                holder.txState.setText(R.string.tx_failed);
                holder.txState.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_E16A67));
                holder.txTime.setVisibility(View.INVISIBLE);
                break;
            case Constant.TRANSACTION_STATE_SUCCESS:
                holder.txState.setText(R.string.tx_successful);
                holder.txState.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_54CA80));
                holder.txTime.setVisibility(View.VISIBLE);
                break;
            case Constant.TRANSACTION_STATE_CONFIRMING:
                holder.txState.setText(R.string.tx_confirming);
                holder.txState.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_1253BF));
                holder.txTime.setVisibility(View.VISIBLE);
                break;
            case Constant.TRANSACTION_STATE_PACKAGING:
                holder.txState.setText(R.string.tx_blocking);
                holder.txState.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_F5A623));
                holder.txTime.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }

        // tmp 后台更新logo后删除
        String assetID = transactionRecord.getAssetID();
        switch (assetID) {
            case Constant.ASSETS_NEO:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_neo).into(holder.txLogo);
                break;
            case Constant.ASSETS_NEO_GAS:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_gas).into(holder.txLogo);
                break;
            case Constant.ASSETS_CPX:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_cpx).into(holder.txLogo);
                break;
            case Constant.ASSETS_APH:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_aph).into(holder.txLogo);
                break;
            case Constant.ASSETS_AVA:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ava).into(holder.txLogo);
                break;
            case Constant.ASSETS_DBC:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_dbc).into(holder.txLogo);
                break;
            case Constant.ASSETS_EXT:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ext).into(holder.txLogo);
                break;
            case Constant.ASSETS_LRN:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_lrn).into(holder.txLogo);
                break;
            case Constant.ASSETS_NKN:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_nkn).into(holder.txLogo);
                break;
            case Constant.ASSETS_ONT:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ont).into(holder.txLogo);
                break;
            case Constant.ASSETS_PKC:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_pkc).into(holder.txLogo);
                break;
            case Constant.ASSETS_RPX:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_rpx).into(holder.txLogo);
                break;
            case Constant.ASSETS_SOUL:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_soul).into(holder.txLogo);
                break;
            case Constant.ASSETS_SWTH:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_swth).into(holder.txLogo);
                break;
            case Constant.ASSETS_TKY:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_tky).into(holder.txLogo);
                break;
            case Constant.ASSETS_ZPT:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_zpt).into(holder.txLogo);
                break;
            case Constant.ASSETS_PHX:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_phx).into(holder.txLogo);
                break;
            case Constant.ASSETS_ETH:
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_eth).into(holder.txLogo);
                break;
            default:
                switch (transactionRecord.getTxType()) {
                    case Constant.ASSET_TYPE_NEP5:
                        GlideApp.with(ApexWalletApplication.getInstance())
                                .load(transactionRecord.getAssetLogoUrl())
                                .placeholder(R.drawable.logo_global_neo)
                                .error(R.drawable.logo_global_neo)
                                .into(holder.txLogo);
                        break;
                    case Constant.ASSET_TYPE_ERC20:
                        GlideApp.with(ApexWalletApplication.getInstance())
                                .load(transactionRecord.getAssetLogoUrl())
                                .placeholder(R.drawable.logo_global_eth)
                                .error(R.drawable.logo_global_eth)
                                .into(holder.txLogo);
                        break;
                    default:
                        break;
                }
                break;
        }

        String txAmount = transactionRecord.getTxAmount();
        if (TextUtils.isEmpty(txAmount)) {
            CpLog.e(TAG, "txAmount is null!");
            return;
        }

        String sign;
        String value;
        try {
            sign = txAmount.substring(0, 1);
            String amount = txAmount.substring(1);
            value = new BigDecimal(amount).setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            CpLog.e(TAG, "TransactionRecordRecyclerViewAdapter Exception:" + e.getMessage());
            return;
        }

        if ("0".equals(value)
                || "0.00000000".equals(value)) {
            holder.txAmount.setText(String.valueOf(sign + "0"));
        } else {
            holder.txAmount.setText(String.valueOf(sign + value));
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mTransactionRecords ? 0 : mTransactionRecords.size();
    }

    class TransactionRecordAdapterHolder extends RecyclerView.ViewHolder {
        ImageView txLogo;
        TextView txID;
        TextView txAmount;
        TextView txTime;
        TextView txState;

        TransactionRecordAdapterHolder(View itemView) {
            super(itemView);
            txLogo = itemView.findViewById(R.id.iv_transaction_record_tx_logo);
            txID = itemView.findViewById(R.id.tv_transaction_record_tx_id);
            txAmount = itemView.findViewById(R.id.tv_transaction_record_tx_amount);
            txTime = itemView.findViewById(R.id.tv_transaction_record_tx_time);
            txState = itemView.findViewById(R.id.tv_transaction_record_tx_state);
        }
    }
}
