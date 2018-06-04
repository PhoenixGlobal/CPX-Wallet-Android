package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.MnemonicState;
import chinapex.com.wallet.utils.CpLog;

public class BackupShowMnemonicAdapter extends RecyclerView.Adapter<BackupShowMnemonicAdapter
        .BackupShowMnemonicAdapterHolder> implements View
        .OnClickListener {

    private static final String TAG = BackupShowMnemonicAdapter.class.getSimpleName();
    private OnItemClickShowListener mOnItemClickShowListener;
    private List<MnemonicState> mMnemonicStates;

    public interface OnItemClickShowListener {
        void onItemClickShow(int position);
    }

    public BackupShowMnemonicAdapter(List<MnemonicState> mnemonicStates) {
        mMnemonicStates = mnemonicStates;
    }

    public void setOnItemClickShowListener(OnItemClickShowListener onItemClickShowListener) {
        mOnItemClickShowListener = onItemClickShowListener;
    }

    @Override
    public void onClick(View v) {
        if (null == mOnItemClickShowListener) {
            CpLog.e(TAG, "mOnItemClickShowListener is null!");
            return;
        }
        mOnItemClickShowListener.onItemClickShow((Integer) v.getTag());
    }

    @NonNull
    @Override
    public BackupShowMnemonicAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_backup_show_mnemonic, parent, false);
        BackupShowMnemonicAdapterHolder holder = new BackupShowMnemonicAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BackupShowMnemonicAdapterHolder holder, int position) {
        holder.showMnemonic.setText(mMnemonicStates.get(position).getMnemonic());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mMnemonicStates ? 0 : mMnemonicStates.size();
    }


    class BackupShowMnemonicAdapterHolder extends RecyclerView.ViewHolder {
        TextView showMnemonic;


        BackupShowMnemonicAdapterHolder(View itemView) {
            super(itemView);
            showMnemonic = itemView.findViewById(R.id.tv_recyclerview_backup_show_mnemonic);

        }
    }
}
