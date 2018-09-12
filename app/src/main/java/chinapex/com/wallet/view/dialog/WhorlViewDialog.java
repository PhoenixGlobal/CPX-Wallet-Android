package chinapex.com.wallet.view.dialog;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.tt.whorlviewlibrary.WhorlView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.utils.DensityUtil;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class WhorlViewDialog extends DialogFragment {

    private static final String TAG = WhorlViewDialog.class.getSimpleName();

    private WhorlView mWl_common_portrait;

    public static WhorlViewDialog newInstance() {
        return new WhorlViewDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        // 去掉边框
        Window window = getDialog().getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }

        // 点击空白区域不可取消
        setCancelable(false);

        return inflater.inflate(R.layout.dialog_whorlview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dip2px(getActivity(), 150), DensityUtil
                .dip2px(getActivity(), 150));
    }

    private void initData() {

    }

    private void initView(View view) {
        mWl_common_portrait = view.findViewById(R.id.wl_common_portrait);
        mWl_common_portrait.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                WhorlViewDialog.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWl_common_portrait.stop();
                        dismiss();
                    }
                });
            }
        }).start();
    }

}
