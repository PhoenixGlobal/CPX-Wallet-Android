package chinapex.com.wallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SteelCabbage on 2018/8/6 0006 17:36.
 * E-Mail：liuyi_61@163.com
 */

public class EmptyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = EmptyAdapter.class.getSimpleName();

    private static final int EMPTY_VIEW = 0;
    private static final int NOT_EMPTY_VIEW = 1;

    private Context mContext;
    private RecyclerView.Adapter mAdapter; //需要装饰的Adapter
    private int mEmptyViewId;

    public EmptyAdapter(RecyclerView.Adapter adapter, Context context, int emptyViewId) {
        mAdapter = adapter;
        mContext = context;
        mEmptyViewId = emptyViewId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(LayoutInflater.from(mContext).inflate(mEmptyViewId,
                    parent, false));
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            return;
        }

        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (null == mAdapter) {
            return 1;
        }

        if (mAdapter.getItemCount() > 0) {
            return mAdapter.getItemCount();
        }

        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mAdapter) {
            return EMPTY_VIEW;
        }

        if (mAdapter.getItemCount() > 0) {
            return NOT_EMPTY_VIEW;
        }

        return EMPTY_VIEW;
    }

    public void notifyAdapter() {
//        有些时候数据的变化不仅要刷新当前adapter还要刷新传入的原始adapter
//        if (mAdapter != null) {
//            mAdapter.notifyDataSetChanged();
//        }
        notifyDataSetChanged();
    }


    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
