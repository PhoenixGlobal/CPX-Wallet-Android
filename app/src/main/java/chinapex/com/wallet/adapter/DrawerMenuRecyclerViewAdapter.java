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
import chinapex.com.wallet.bean.DrawerMenu;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/24 0024.
 */

public class DrawerMenuRecyclerViewAdapter extends RecyclerView
        .Adapter<DrawerMenuRecyclerViewAdapter.DrawerMenuAdapterHolder>
        implements View.OnClickListener {

    private static final String TAG = DrawerMenuRecyclerViewAdapter.class.getSimpleName();
    private DrawerMenuOnItemClickListener mDrawerMenuOnItemClickListener;
    private List<DrawerMenu> mDrawerMenuList;

    public interface DrawerMenuOnItemClickListener {
        void drawerMenuOnItemClick(int position);
    }

    public DrawerMenuRecyclerViewAdapter(List<DrawerMenu> drawerMenuList) {
        mDrawerMenuList = drawerMenuList;
    }

    public void setDrawerMenuOnItemClickListener(DrawerMenuOnItemClickListener drawerMenuOnItemClickListener) {
        mDrawerMenuOnItemClickListener = drawerMenuOnItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null == mDrawerMenuOnItemClickListener) {
            CpLog.e(TAG, "mDrawerMenuOnItemClickListener is null!");
            return;
        }
        mDrawerMenuOnItemClickListener.drawerMenuOnItemClick((Integer) v.getTag());
    }

    @NonNull
    @Override
    public DrawerMenuAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .recyclerview_drawer_menu,
                parent, false);
        DrawerMenuAdapterHolder holder = new DrawerMenuAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerMenuAdapterHolder holder, int position) {
        holder.menuIcon.setBackground(ApexWalletApplication.getInstance().getResources()
                .getDrawable((mDrawerMenuList.get(position).getMenuIcon())));
        holder.menuText.setText(mDrawerMenuList.get(position).getMenuText());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mDrawerMenuList ? 0 : mDrawerMenuList.size();
    }

    class DrawerMenuAdapterHolder extends RecyclerView.ViewHolder {
        ImageView menuIcon;
        TextView menuText;

        DrawerMenuAdapterHolder(View itemView) {
            super(itemView);
            menuIcon = itemView.findViewById(R.id.iv_wallet_detail_drawer_menu_icon);
            menuText = itemView.findViewById(R.id.tv_wallet_detail_drawer_menu_text);
        }
    }
}
