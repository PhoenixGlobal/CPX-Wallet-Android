package chinapex.com.wallet.view.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.AddAssetsRecyclerViewAdapter;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class AddAssetsDialog extends DialogFragment implements View.OnClickListener,
        AddAssetsRecyclerViewAdapter.OnItemClickListener, TextWatcher, DialogInterface
                .OnKeyListener {

    private static final String TAG = AddAssetsDialog.class.getSimpleName();

    private RecyclerView mRv_add_assets;
    private EditText mEt_add_assets_search;
    private ImageButton mIb_add_assets_cancel;
    private onCheckedAssetsListener mOnCheckedAssetsListener;
    private AddAssetsRecyclerViewAdapter mAddAssetsRecyclerViewAdapter;

    private List<String> mCheckedAssets;
    private List<String> mCurrentAssets;
    private List<AssetBean> mAssetBeans;
    private List<AssetBean> mSearchAssetBeans;

    public interface onCheckedAssetsListener {
        void onCheckedAssets(List<String> checkedAssets);
    }

    public void setOnCheckedAssetsListener(onCheckedAssetsListener onCheckedAssetsListener) {
        mOnCheckedAssetsListener = onCheckedAssetsListener;
    }

    public static AddAssetsDialog newInstance() {
        return new AddAssetsDialog();
    }

    public void setCurrentAssets(List<String> currentAssets) {
        mCurrentAssets = currentAssets;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        // 去掉边框
        Window window = getDialog().getWindow();
        if (null == window) {
            CpLog.e(TAG, "window is null!");
            return null;
        }
        window.setBackgroundDrawable(new ColorDrawable(0));

        // 点击空白区域不可取消
        setCancelable(false);

        // 设置style
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialog);

        // 可设置dialog的位置
        window.setGravity(Gravity.BOTTOM);

        // 消除边距
        window.getDecorView().setPadding(0, 0, 0, 0);

        // 设置全屏
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.windowAnimations = R.style.BottomDialogAnim;
        window.setAttributes(lp);

        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 监听返回键回调
        this.getDialog().setOnKeyListener(this);

        return inflater.inflate(R.layout.dialog_add_assets, container, false);
    }

    // 返回键回调
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (null != mOnCheckedAssetsListener) {
                mOnCheckedAssetsListener.onCheckedAssets(mCheckedAssets);
            }

            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void initView(View view) {
        ImageButton ib_add_assets_close = view.findViewById(R.id.ib_add_assets_close);
        mEt_add_assets_search = view.findViewById(R.id.et_add_assets_search);
        mIb_add_assets_cancel = view.findViewById(R.id.ib_add_assets_cancel);

        mRv_add_assets = view.findViewById(R.id.rv_add_assets);

        ib_add_assets_close.setOnClickListener(this);
        mEt_add_assets_search.addTextChangedListener(this);
        mIb_add_assets_cancel.setOnClickListener(this);
    }

    private void initData() {
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        mAssetBeans = apexWalletDbDao.queryAssetsByType(Constant.TABLE_NEO_ASSETS, Constant.ASSET_TYPE_NEP5);
        if (null == mAssetBeans || mAssetBeans.isEmpty()) {
            CpLog.e(TAG, "assetBeans is null or empty!");
            return;
        }

        List<AssetBean> governingAssets = apexWalletDbDao.queryAssetsByType(Constant.TABLE_NEO_ASSETS, Constant
                .ASSET_TYPE_GOVERNING);
        if (null == governingAssets || governingAssets.isEmpty()) {
            CpLog.e(TAG, "governingAssets is null or empty!");
            return;
        }

        mAssetBeans.addAll(1, governingAssets);

        List<AssetBean> utilityAssets = apexWalletDbDao.queryAssetsByType(Constant.TABLE_NEO_ASSETS, Constant.ASSET_TYPE_UTILITY);
        if (null == utilityAssets || utilityAssets.isEmpty()) {
            CpLog.e(TAG, "utilityAssets is null or empty!");
            return;
        }

        mAssetBeans.addAll(1, utilityAssets);

        if (null == mCurrentAssets) {
            CpLog.e(TAG, "mCurrentAssets is null!");
            return;
        }

        mSearchAssetBeans = new ArrayList<>();
        mSearchAssetBeans.addAll(mAssetBeans);

        for (AssetBean assetBean : mAssetBeans) {
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            if (mCurrentAssets.contains(assetBean.getHexHash())) {
                assetBean.setChecked(true);
            }
        }

        mAddAssetsRecyclerViewAdapter = new AddAssetsRecyclerViewAdapter(mAssetBeans);
        mAddAssetsRecyclerViewAdapter.setOnItemClickListener(this);
        mRv_add_assets.setLayoutManager(new LinearLayoutManager(ApexWalletApplication.getInstance(), LinearLayoutManager
                .VERTICAL, false));
        mRv_add_assets.setAdapter(mAddAssetsRecyclerViewAdapter);
        mCheckedAssets = new ArrayList<>();
        mCheckedAssets.addAll(mCurrentAssets);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_add_assets_close:
                if (null != mOnCheckedAssetsListener) {
                    mOnCheckedAssetsListener.onCheckedAssets(mCheckedAssets);
                }

                dismiss();
            case R.id.ib_add_assets_cancel:
                mEt_add_assets_search.getText().clear();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        if (null == mAssetBeans || mAssetBeans.isEmpty()) {
            CpLog.e(TAG, "mAssetBeans is null or empty!");
            return;
        }

        AssetBean assetBean = mAssetBeans.get(position);
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        if (null == mCheckedAssets) {
            CpLog.e(TAG, "mCheckedAssets is null!");
            return;
        }

        String assetHexHash = assetBean.getHexHash();
        if (TextUtils.isEmpty(assetHexHash)) {
            CpLog.e(TAG, "hexHash is null!");
            return;
        }

        if (assetBean.isChecked()) {
            if (!mCheckedAssets.contains(assetHexHash)) {
                mCheckedAssets.add(assetHexHash);
            }
        } else {
            if (mCheckedAssets.contains(assetHexHash)) {
                mCheckedAssets.remove(assetHexHash);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mAssetBeans.clear();
        mAssetBeans.addAll(mSearchAssetBeans);

        if (TextUtils.isEmpty(s)) {
            CpLog.w(TAG, "onTextChanged() -> is empty!");
            mIb_add_assets_cancel.setVisibility(View.INVISIBLE);
            mAddAssetsRecyclerViewAdapter.notifyDataSetChanged();
            return;
        }

        mIb_add_assets_cancel.setVisibility(View.VISIBLE);
        Iterator<AssetBean> iterator = mAssetBeans.iterator();
        while (iterator.hasNext()) {
            AssetBean assetBean = iterator.next();
            if (null == assetBean) {
                CpLog.e(TAG, "assetBean is null!");
                continue;
            }

            if (!assetBean.getSymbol().contains(s.toString().toUpperCase())
                    && !assetBean.getSymbol().contains(s.toString().toLowerCase())) {
                iterator.remove();
            }
        }

        mAddAssetsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
