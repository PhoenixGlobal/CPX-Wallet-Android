package chinapex.com.wallet.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.MnemonicState;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

public class BackupClickMnemonicAdapter extends RecyclerView.Adapter<BackupClickMnemonicAdapter
        .BackupClickMnemonicAdapterHolder> implements View.OnClickListener {

    private static final String TAG = BackupClickMnemonicAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<MnemonicState> mMnemonicStates;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public BackupClickMnemonicAdapter(List<MnemonicState> mnemonicStates) {
        mMnemonicStates = mnemonicStates;
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

        Integer position = (Integer) v.getTag();
        boolean selected = mMnemonicStates.get(position).isSelected();
        mMnemonicStates.get(position).setSelected(!selected);
        notifyDataSetChanged();
        mOnItemClickListener.onItemClick(position);
    }

    @NonNull
    @Override
    public BackupClickMnemonicAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_backup_click_mnemonic, parent, false);
        BackupClickMnemonicAdapterHolder holder = new BackupClickMnemonicAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BackupClickMnemonicAdapterHolder holder, int
            position) {
        MnemonicState mnemonicState = mMnemonicStates.get(position);
        holder.clickMnemonic.setText(mnemonicState.getMnemonic());
        if (mnemonicState.isSelected()) {
            holder.clickMnemonic.setBackgroundResource(R.drawable.shape_backup_click_mnemonic);
            holder.clickMnemonic.setTextColor(Color.WHITE);
        } else {
            holder.clickMnemonic.setBackgroundResource(R.drawable.shape_backup_click_mnemonic_def);
            holder.clickMnemonic.setTextColor(ApexWalletApplication.getInstance().getResources()
                    .getColor(R.color.colorPrimary));
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mMnemonicStates ? 0 : mMnemonicStates.size();
    }


    class BackupClickMnemonicAdapterHolder extends RecyclerView.ViewHolder {
        TextView clickMnemonic;

        BackupClickMnemonicAdapterHolder(View itemView) {
            super(itemView);
            clickMnemonic = itemView.findViewById(R.id.tv_recyclerview_backup_click_mnemonic);
        }
    }
}
