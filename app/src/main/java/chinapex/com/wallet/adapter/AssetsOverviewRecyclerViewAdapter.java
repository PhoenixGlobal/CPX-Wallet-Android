package chinapex.com.wallet.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.global.GlideApp;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

public class AssetsOverviewRecyclerViewAdapter extends RecyclerView
        .Adapter<AssetsOverviewRecyclerViewAdapter.AssetsOverviewAdapterHolder> implements
        View.OnClickListener {

    private static final String TAG = AssetsOverviewRecyclerViewAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private List<BalanceBean> mBalanceBeans;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AssetsOverviewRecyclerViewAdapter(List<BalanceBean> balanceBeans) {
        mBalanceBeans = balanceBeans;
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
    public AssetsOverviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_assets_overview_item, parent, false);
        AssetsOverviewAdapterHolder holder = new AssetsOverviewAdapterHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssetsOverviewAdapterHolder holder, int position) {
        BalanceBean balanceBean = mBalanceBeans.get(position);
        if (null == balanceBean) {
            CpLog.e(TAG, "balanceBean is null!");
            return;
        }

        String assetsID = balanceBean.getAssetsID();
        if (TextUtils.isEmpty(assetsID)) {
            CpLog.e(TAG, "assetsID is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        String tableName = null;
        switch (balanceBean.getWalletType()) {
            case Constant.WALLET_TYPE_NEO:
                tableName = Constant.TABLE_NEO_ASSETS;
                break;
            case Constant.WALLET_TYPE_ETH:
                tableName = Constant.TABLE_ETH_ASSETS;
                break;
            case Constant.WALLET_TYPE_CPX:
                tableName = Constant.TABLE_CPX_ASSETS;
                break;
            default:
                break;
        }

        AssetBean assetBean = apexWalletDbDao.queryAssetByHash(tableName, assetsID);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        switch (assetsID) {
            case Constant.ASSETS_NEO:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_neo).into(holder.assetLogo);
                break;
            case Constant.ASSETS_NEO_GAS:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_global_gas).into(holder.assetLogo);
                break;
            case Constant.ASSETS_CPX:
                holder.mapState.setVisibility(View.VISIBLE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_cpx).into(holder.assetLogo);
                break;
            case Constant.ASSETS_APH:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_aph).into(holder.assetLogo);
                break;
            case Constant.ASSETS_AVA:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ava).into(holder.assetLogo);
                break;
            case Constant.ASSETS_DBC:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_dbc).into(holder.assetLogo);
                break;
            case Constant.ASSETS_EXT:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ext).into(holder.assetLogo);
                break;
            case Constant.ASSETS_LRN:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_lrn).into(holder.assetLogo);
                break;
            case Constant.ASSETS_NKN:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_nkn).into(holder.assetLogo);
                break;
            case Constant.ASSETS_ONT:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_ont).into(holder.assetLogo);
                break;
            case Constant.ASSETS_PKC:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_pkc).into(holder.assetLogo);
                break;
            case Constant.ASSETS_RPX:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_rpx).into(holder.assetLogo);
                break;
            case Constant.ASSETS_SOUL:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_soul).into(holder.assetLogo);
                break;
            case Constant.ASSETS_SWTH:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_swth).into(holder.assetLogo);
                break;
            case Constant.ASSETS_TKY:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_tky).into(holder.assetLogo);
                break;
            case Constant.ASSETS_ZPT:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.logo_nep5_zpt).into(holder.assetLogo);
                break;
            // eth
            case Constant.ASSETS_ETH:
                holder.mapState.setVisibility(View.GONE);
                GlideApp.with(ApexWalletApplication.getInstance()).load(R.drawable.icon_wallet_type_eth).into(holder.assetLogo);
                break;
            default:
                holder.mapState.setVisibility(View.GONE);

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

        holder.assetsName.setText(balanceBean.getAssetSymbol());

        String assetsValue = balanceBean.getAssetsValue();
        if (TextUtils.isEmpty(assetsValue)) {
            holder.assetsValue.setText("0");
        } else {
            if ("0".equals(assetsValue)
                    || "0000000000000000000000000000000000000000000000000000000000000000".equals(assetsValue)) {
                holder.assetsValue.setText("0");
            } else {
                String value = new BigDecimal(assetsValue).setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
                        .toPlainString();
                holder.assetsValue.setText(value);
            }
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mBalanceBeans ? 0 : mBalanceBeans.size();
    }

    class AssetsOverviewAdapterHolder extends RecyclerView.ViewHolder {
        ImageView assetLogo;
        TextView assetsName;
        TextView assetsValue;
        TextView mapState;

        AssetsOverviewAdapterHolder(View itemView) {
            super(itemView);
            assetLogo = itemView.findViewById(R.id.iv_assets_overview_item_logo);
            assetsName = itemView.findViewById(R.id.tv_assets_overview_assets_name);
            assetsValue = itemView.findViewById(R.id.tv_assets_overview_assets_value);
            mapState = itemView.findViewById(R.id.tv_assets_overview_map);
        }
    }
}
