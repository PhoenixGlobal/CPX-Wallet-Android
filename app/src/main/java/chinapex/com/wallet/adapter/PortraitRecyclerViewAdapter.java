package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.PortraitBean;
import chinapex.com.wallet.bean.PortraitTagsBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;

public class PortraitRecyclerViewAdapter extends RecyclerView.Adapter implements View
        .OnClickListener {

    private static final String TAG = PortraitRecyclerViewAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private List<PortraitBean> mPortraitBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PortraitRecyclerViewAdapter(List<PortraitBean> portraitBeans) {
        mPortraitBeans = portraitBeans;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == Constant.TYPE_GENERA_TAGS) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genera_tags, parent,
                    false);
            view.setOnClickListener(this);
            return new GeneraTagsHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genera_list, parent,
                    false);
            view.setOnClickListener(this);
            return new GeneraListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PortraitBean portraitBean = mPortraitBeans.get(position);
        if (null == portraitBean) {
            CpLog.e(TAG, "portraitBean is null!");
            return;
        }

        if (holder instanceof GeneraTagsHolder) {
            GeneraTagsHolder generaTagsHolder = (GeneraTagsHolder) holder;
            generaTagsHolder.tagsTitle.setText(portraitBean.getTitle());
            RecyclerView.Adapter adapter = generaTagsHolder.tags.getAdapter();
            if (null == adapter) {
                final List<PortraitTagsBean> portraitTagsBeans = portraitBean.getData();
                PortraitTagsAdapter portraitTagsAdapter = new PortraitTagsAdapter
                        (portraitTagsBeans);
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager
                        (ApexWalletApplication.getInstance());
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                layoutManager.setFlexWrap(FlexWrap.WRAP);

                generaTagsHolder.tags.setLayoutManager(layoutManager);

                int space = DensityUtil.dip2px(ApexWalletApplication.getInstance(), 3);
                generaTagsHolder.tags.addItemDecoration(new SpacesItemDecorationHorizontal(space));

                portraitTagsAdapter.setOnItemClickListener(new PortraitTagsAdapter
                        .OnItemClickListener() {


                    @Override
                    public void onItemClick(int position) {
                        PortraitTagsBean portraitTagsBean = portraitTagsBeans.get(position);
                        if (null == portraitTagsBean) {
                            CpLog.e(TAG, "portraitTagsBean is null!");
                            return;
                        }

                        CpLog.i(TAG, "portraitTagsBean:" + portraitTagsBean.toString());
                    }
                });
                generaTagsHolder.tags.setAdapter(portraitTagsAdapter);
            }
        } else {
            ((GeneraListHolder) holder).title.setText(portraitBean.getTitle());
            ((GeneraListHolder) holder).content.setText(portraitBean.getSelectedContent());
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        PortraitBean portraitBean = mPortraitBeans.get(position);
        if (null == portraitBean) {
            CpLog.e(TAG, "portraitBean is null");
            return Constant.TYPE_UNKNOW;
        }

        switch (portraitBean.getType()) {
            case Constant.TYPE_TAGS:
                return Constant.TYPE_GENERA_TAGS;
            default:
                return Constant.TYPE_GENERA_LIST;
        }
    }

    @Override
    public int getItemCount() {
        return null == mPortraitBeans ? 0 : mPortraitBeans.size();
    }


    class GeneraListHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        GeneraListHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_genera_title);
            content = itemView.findViewById(R.id.tv_genera_content);
        }
    }

    class GeneraTagsHolder extends RecyclerView.ViewHolder {
        TextView tagsTitle;
        RecyclerView tags;

        GeneraTagsHolder(View itemView) {
            super(itemView);
            tagsTitle = itemView.findViewById(R.id.tv_genera_tags_title);
            tags = itemView.findViewById(R.id.rv_genera_tags);
        }
    }
}
