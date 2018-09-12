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
import chinapex.com.wallet.bean.MeFunction;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

public class MeFunctionRecyclerViewAdapter extends RecyclerView
        .Adapter<MeFunctionRecyclerViewAdapter.MeFunctionAdapterHolder> implements View
        .OnClickListener {

    private static final String TAG = MeFunctionRecyclerViewAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private List<MeFunction> mMeFunctions;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MeFunctionRecyclerViewAdapter(List<MeFunction> meFunctions) {
        mMeFunctions = meFunctions;
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
    public MeFunctionAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_me,
                parent, false);
        MeFunctionAdapterHolder holder = new MeFunctionAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeFunctionAdapterHolder holder, int position) {
        MeFunction meFunction = mMeFunctions.get(position);
        if (null == meFunction) {
            CpLog.e(TAG, "meFunction is null!");
            return;
        }

        holder.functionIcon.setImageDrawable(ApexWalletApplication.getInstance().getResources()
                .getDrawable(meFunction.getFunctionIcon()));
        holder.functionText.setText(meFunction.getFunctionText());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mMeFunctions ? 0 : mMeFunctions.size();
    }

    class MeFunctionAdapterHolder extends RecyclerView.ViewHolder {
        ImageView functionIcon;
        TextView functionText;

        MeFunctionAdapterHolder(View itemView) {
            super(itemView);

            functionIcon = itemView.findViewById(R.id.iv_me_function_icon);
            functionText = itemView.findViewById(R.id.tv_me_function_text);
        }
    }
}
