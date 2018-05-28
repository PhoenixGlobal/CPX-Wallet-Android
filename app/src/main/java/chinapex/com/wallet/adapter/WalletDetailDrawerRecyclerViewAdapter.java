package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletDetailMenu;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/24 0024.
 */

public class WalletDetailDrawerRecyclerViewAdapter extends RecyclerView
        .Adapter<WalletDetailDrawerRecyclerViewAdapter.WalletDetailDrawerAdapterHolder>
        implements View.OnClickListener {

    private static final String TAG = WalletDetailDrawerRecyclerViewAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<WalletDetailMenu> mWalletDetailMenuList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public WalletDetailDrawerRecyclerViewAdapter(List<WalletDetailMenu> walletDetailMenuList) {
        mWalletDetailMenuList = walletDetailMenuList;
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
    public WalletDetailDrawerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_wallet_detail_menu,
                parent, false);
        WalletDetailDrawerAdapterHolder holder = new WalletDetailDrawerAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletDetailDrawerAdapterHolder holder, int position) {
        holder.menuIcon.setBackground(ApexWalletApplication.getInstance().getResources()
                .getDrawable((mWalletDetailMenuList.get(position).getMenuIcon())));
        holder.menuText.setText(mWalletDetailMenuList.get(position).getMenuText());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mWalletDetailMenuList ? 0 : mWalletDetailMenuList.size();
    }

    class WalletDetailDrawerAdapterHolder extends RecyclerView.ViewHolder {
        ImageView menuIcon;
        TextView menuText;

        WalletDetailDrawerAdapterHolder(View itemView) {
            super(itemView);
            menuIcon = itemView.findViewById(R.id.iv_wallet_detail_drawer_menu_icon);
            menuText = itemView.findViewById(R.id.tv_wallet_detail_drawer_menu_text);
        }
    }
}
