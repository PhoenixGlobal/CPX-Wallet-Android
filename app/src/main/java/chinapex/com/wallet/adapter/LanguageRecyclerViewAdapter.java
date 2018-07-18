package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.LanguageState;
import chinapex.com.wallet.utils.CpLog;

public class LanguageRecyclerViewAdapter extends RecyclerView
        .Adapter<LanguageRecyclerViewAdapter.LanguageAdapterHolder> implements View
        .OnClickListener {

    private static final String TAG = LanguageRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<LanguageState> mLanguageStates;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public LanguageRecyclerViewAdapter(List<LanguageState> languageStates) {
        mLanguageStates = languageStates;
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
    public LanguageAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_me_language_settings, parent, false);
        LanguageAdapterHolder holder = new LanguageAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapterHolder holder, int position) {
        LanguageState languageState = mLanguageStates.get(position);
        if (null == languageState) {
            CpLog.e(TAG, "languageState is null!");
            return;
        }

        holder.languageName.setText(languageState.getLanguageName());
        if (languageState.isChecked()) {
            holder.languageChecked.setVisibility(View.VISIBLE);
        } else {
            holder.languageChecked.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mLanguageStates ? 0 : mLanguageStates.size();
    }

    class LanguageAdapterHolder extends RecyclerView.ViewHolder {
        TextView languageName;
        ImageButton languageChecked;

        LanguageAdapterHolder(View itemView) {
            super(itemView);

            languageName = itemView.findViewById(R.id.tv_me_language_settings_item);
            languageChecked = itemView.findViewById(R.id.ib_me_language_settings_item);
        }
    }
}
