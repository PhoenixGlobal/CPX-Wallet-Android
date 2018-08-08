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
import chinapex.com.wallet.bean.PortraitTagsBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

public class PortraitTagsAdapter extends RecyclerView.Adapter<PortraitTagsAdapter
        .PortraitTagsHolder> implements View.OnClickListener {

    private static final String TAG = PortraitTagsAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<PortraitTagsBean> mPortraitTagsBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PortraitTagsAdapter(List<PortraitTagsBean> portraitTagsBeans) {
        mPortraitTagsBeans = portraitTagsBeans;
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
        PortraitTagsBean portraitTagsBean = mPortraitTagsBeans.get(position);
        if (null == portraitTagsBean) {
            CpLog.e(TAG, "portraitTagsBean is null!");
            return;
        }

        boolean checked = portraitTagsBean.isChecked();
        portraitTagsBean.setChecked(!checked);
        notifyDataSetChanged();
        mOnItemClickListener.onItemClick(position);
    }

    @NonNull
    @Override
    public PortraitTagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_portrait_tag, parent, false);
        PortraitTagsHolder holder = new PortraitTagsHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PortraitTagsHolder holder, int
            position) {
        PortraitTagsBean portraitTagsBean = mPortraitTagsBeans.get(position);
        if (null == portraitTagsBean) {
            CpLog.e(TAG, "portraitTagsBean is null!");
            return;
        }

        holder.tag.setText(portraitTagsBean.getName());
        if (portraitTagsBean.isChecked()) {
            holder.tag.setBackgroundResource(R.drawable.shape_portrait_tag);
            holder.tag.setTextColor(Color.WHITE);
        } else {
            holder.tag.setBackgroundResource(R.drawable.shape_portrait_tag_def);
            holder.tag.setTextColor(ApexWalletApplication.getInstance().getResources()
                    .getColor(R.color.c_3AA03F));
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mPortraitTagsBeans ? 0 : mPortraitTagsBeans.size();
    }


    class PortraitTagsHolder extends RecyclerView.ViewHolder {
        TextView tag;

        PortraitTagsHolder(View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tv_portrait_tag);
        }
    }
}
