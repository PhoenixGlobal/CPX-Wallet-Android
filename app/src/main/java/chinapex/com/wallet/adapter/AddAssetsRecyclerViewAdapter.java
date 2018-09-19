package chinapex.com.wallet.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.global.GlideApp;
import chinapex.com.wallet.utils.CpLog;

public class AddAssetsRecyclerViewAdapter extends RecyclerView.Adapter<AddAssetsRecyclerViewAdapter
        .AddAssetsHolder> implements View.OnClickListener {

    private static final String TAG = AddAssetsRecyclerViewAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private List<AssetBean> mAssetBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AddAssetsRecyclerViewAdapter(List<AssetBean> assetBeans) {
        mAssetBeans = assetBeans;
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

        if (null == mAssetBeans || mAssetBeans.isEmpty()) {
            CpLog.e(TAG, "mAssetBeans is null or empty!");
            return;
        }

        Integer position = (Integer) v.getTag();
        AssetBean assetBean = mAssetBeans.get(position);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null or empty!");
            return;
        }

        boolean checked = assetBean.isChecked();
        String assetId = assetBean.getHexHash();
        if (!Constant.ASSETS_CPX.equals(assetId)
                && !Constant.ASSETS_NEO.equals(assetId)
                && !Constant.ASSETS_NEO_GAS.equals(assetId)
                && !Constant.ASSETS_ETH.equals(assetId)) {
            assetBean.setChecked(!checked);
        }
        notifyItemChanged(position, 0);
        mOnItemClickListener.onItemClick(position);
    }

    @NonNull
    @Override
    public AddAssetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_add_assets_item, parent, false);
        AddAssetsHolder holder = new AddAssetsHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddAssetsHolder holder, int position, @NonNull List<Object> payloads) {
        AssetBean assetBean = mAssetBeans.get(position);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            int type = (int) payloads.get(0);
            switch (type) {
                case 0:
                    if (assetBean.isChecked()) {
                        holder.assetChecked.setImageDrawable(ApexWalletApplication.getInstance().getResources()
                                .getDrawable(R.drawable.icon_checked_asset));
                    } else {
                        holder.assetChecked.setImageDrawable(ApexWalletApplication.getInstance().getResources()
                                .getDrawable(R.drawable.icon_unchecked_asset));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final AddAssetsHolder holder, int position) {
        AssetBean assetBean = mAssetBeans.get(position);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        // tmp 后台更新logo后删除
        switch (assetBean.getHexHash()) {
            case Constant.ASSETS_NEO:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_neo).into(holder.assetLogo);
                break;
            case Constant.ASSETS_NEO_GAS:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_gas).into(holder.assetLogo);
                break;
            case Constant.ASSETS_CPX:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_cpx).into(holder.assetLogo);
                break;
            case Constant.ASSETS_APH:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_aph).into(holder.assetLogo);
                break;
            case Constant.ASSETS_AVA:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ava).into(holder.assetLogo);
                break;
            case Constant.ASSETS_DBC:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_dbc).into(holder.assetLogo);
                break;
            case Constant.ASSETS_EXT:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ext).into(holder.assetLogo);
                break;
            case Constant.ASSETS_LRN:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_lrn).into(holder.assetLogo);
                break;
            case Constant.ASSETS_NKN:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_nkn).into(holder.assetLogo);
                break;
            case Constant.ASSETS_ONT:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ont).into(holder.assetLogo);
                break;
            case Constant.ASSETS_PKC:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_pkc).into(holder.assetLogo);
                break;
            case Constant.ASSETS_RPX:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_rpx).into(holder.assetLogo);
                break;
            case Constant.ASSETS_SOUL:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_soul).into(holder.assetLogo);
                break;
            case Constant.ASSETS_SWTH:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_swth).into(holder.assetLogo);
                break;
            case Constant.ASSETS_TKY:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_tky).into(holder.assetLogo);
                break;
            case Constant.ASSETS_ZPT:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_zpt).into(holder.assetLogo);
                break;
            case Constant.ASSETS_ETH:
                Glide.with(ApexWalletApplication.getInstance()).load(R.drawable.icon_wallet_type_eth).into(holder.assetLogo);
                break;
            default:
                switch (assetBean.getType()) {
                    case Constant.ASSET_TYPE_NEP5:
                        GlideApp.with(ApexWalletApplication.getInstance())
                                .load(assetBean.getImageUrl())
                                .placeholder(R.drawable.logo_global_neo)
                                .error(R.drawable.logo_global_neo)
                                .into(holder.assetLogo);
                        break;
                    case Constant.ASSET_TYPE_ERC20:
                        GlideApp.with(ApexWalletApplication.getInstance())
                                .load(assetBean.getImageUrl())
                                .placeholder(R.drawable.icon_wallet_type_eth)
                                .error(R.drawable.icon_wallet_type_eth)
                                .into(holder.assetLogo);
                        break;
                    default:
                        break;
                }

                break;
        }

        holder.assetSymbol.setText(assetBean.getSymbol());
        holder.assetName.setText(assetBean.getName());
        if (assetBean.isChecked()) {
            holder.assetChecked.setImageDrawable(ApexWalletApplication.getInstance().getResources()
                    .getDrawable(R.drawable.icon_checked_asset));
        } else {
            holder.assetChecked.setImageDrawable(ApexWalletApplication.getInstance().getResources()
                    .getDrawable(R.drawable.icon_unchecked_asset));
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mAssetBeans ? 0 : mAssetBeans.size();
    }


    class AddAssetsHolder extends RecyclerView.ViewHolder {
        ImageView assetLogo;
        TextView assetSymbol;
        TextView assetName;
        ImageView assetChecked;

        AddAssetsHolder(View itemView) {
            super(itemView);
            assetLogo = itemView.findViewById(R.id.iv_add_assets_logo);
            assetSymbol = itemView.findViewById(R.id.tv_add_assets_symbol);
            assetName = itemView.findViewById(R.id.tv_add_assets_name);
            assetChecked = itemView.findViewById(R.id.iv_add_assets_checked_state);
        }
    }
}
