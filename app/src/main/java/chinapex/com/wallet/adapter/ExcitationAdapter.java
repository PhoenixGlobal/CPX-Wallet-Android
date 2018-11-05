package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.ExcitationBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.PhoneUtils;

import static chinapex.com.wallet.R.drawable.new_event_status;
import static chinapex.com.wallet.R.drawable.new_event_status_end;


public class ExcitationAdapter extends RecyclerView.Adapter<ExcitationAdapter.ExcitationAdapterHolder> implements View
        .OnClickListener {
    private static final String TAG = ExcitationAdapter.class.getSimpleName();


    private int ITEM_TYPE_NORMAL = 0;
    private int ITEM_TYPE_HEADER = 1;

    private View mHeaderView;

    private List<ExcitationBean> mExcitationBeans;
    private OnItemClickListener mOnItemClickListener = null;

    public ExcitationAdapter(List<ExcitationBean> list) {
        mExcitationBeans = list;
    }

    @NonNull
    @Override
    public ExcitationAdapter.ExcitationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_HEADER) {
            mHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .fragment_excitation_recyclerview_item_header,
                    parent, false);
            ExcitationAdapterHolder excitationAdapterHolder = new ExcitationAdapterHolder(mHeaderView, viewType);
            excitationAdapterHolder.itemView.setOnClickListener(this);
            return excitationAdapterHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .fragment_excitation_recyclerview_item,
                    parent, false);
            ExcitationAdapterHolder excitationAdapterHolder = new ExcitationAdapterHolder(view, viewType);
            excitationAdapterHolder.itemView.setOnClickListener(this);

            return excitationAdapterHolder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (null != mHeaderView && position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ExcitationAdapterHolder holder, int position) {
        if (ITEM_TYPE_HEADER == getItemViewType(position)) {
            return;
        }
        int realPos = getRealItemPosition(position);
        ExcitationBean bean = mExcitationBeans.get(realPos);
        if (null == bean) {
            CpLog.e(TAG, "ExcitationBean is null!");
            return;
        }

        if (bean.isEventNew()) {
            if (PhoneUtils.getAppLanguage().contains(Locale.CHINA.toString())) {
                holder.newEventTagCN.setVisibility(View.VISIBLE);
                holder.newEventTagEN.setVisibility(View.GONE);
            } else {
                holder.newEventTagEN.setVisibility(View.VISIBLE);
                holder.newEventTagCN.setVisibility(View.GONE);
            }
        } else {
            holder.newEventTagCN.setVisibility(View.GONE);
            holder.newEventTagEN.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(bean.getNewEventPic())) {
            holder.newEventPic.setVisibility(View.GONE);
        } else {
            Glide.with(ApexWalletApplication.getInstance()).load(bean.getNewEventPic()).into(holder.newEventPic);
        }

        if (!TextUtils.isEmpty(bean.getNewEventText())) {
            holder.newEventText.setText(bean.getNewEventText());
        }

        int newEventStatus = bean.getNewEventStatus();
        if (newEventStatus == Constant.EXCITATION_EXCITATION_AOUBT_TO_BEGIN) {
            holder.newEventStatus.setText(ApexWalletApplication.getInstance().getResources().getText(R.string
                    .excitation_about_to_begin));
            holder.newEventStatus.setBackground(ApexWalletApplication.getInstance().getResources().getDrawable
                    (new_event_status_end));
            holder.newEventStatus.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_ff666666));
        } else if (newEventStatus == Constant.EXCITATION_EXCITATION_IN_PROGRESS) {
            holder.newEventStatus.setText(ApexWalletApplication.getInstance().getResources().getText(R.string
                    .excitation_in_progress));
            holder.newEventStatus.setBackground(ApexWalletApplication.getInstance().getResources().getDrawable(new_event_status));
            holder.newEventStatus.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_FFFFFF));
        } else {
            holder.newEventStatus.setText(ApexWalletApplication.getInstance().getResources().getText(R.string.excitation_closed));
            holder.newEventStatus.setBackground(ApexWalletApplication.getInstance().getResources().getDrawable
                    (new_event_status_end));
            holder.newEventStatus.setTextColor(ApexWalletApplication.getInstance().getResources().getColor(R.color.c_ff666666));
        }
        holder.itemView.setTag(realPos);
    }


    @Override
    public int getItemCount() {
        if (null == mExcitationBeans) {
            return 0;
        }

        int itemCount = mExcitationBeans.size();

        if (null != mHeaderView) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public void onClick(View view) {
        if (null == mOnItemClickListener) {
            CpLog.e(TAG, "mOnItemClickListener is null!");
            return;
        }
        mOnItemClickListener.onItemClick((Integer) view.getTag());
    }

    private int getRealItemPosition(int position) {
        if (null != mHeaderView) {
            return position - 1;
        }
        return position;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class ExcitationAdapterHolder extends RecyclerView.ViewHolder {
        ImageView newEventPic;
        ImageView newEventTagCN;
        ImageView newEventTagEN;
        TextView newEventText;
        TextView newEventStatus;
        View recyclerViewHeader;

        ExcitationAdapterHolder(View itemView, int itemType) {
            super(itemView);
            if (itemType == ITEM_TYPE_HEADER) {
                recyclerViewHeader = itemView.findViewById(R.id.view_recyclerview_header);
            } else {
                newEventPic = itemView.findViewById(R.id.new_event_pic);
                newEventTagCN = itemView.findViewById(R.id.new_event_tag_chinese);
                newEventTagEN = itemView.findViewById(R.id.new_event_tag_english);
                newEventStatus = itemView.findViewById(R.id.new_event_status);
                newEventText = itemView.findViewById(R.id.new_event_text);
            }
        }
    }
}
