package chinapex.com.wallet.view.dialog;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.utils.DensityUtil;

/**
 * Created by SteelCabbage on 2018/5/31 0031.
 */

public class ExcitationDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = ExcitationDialog.class.getSimpleName();

    public static ExcitationDialog newInstance() {
        return new ExcitationDialog();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // 去掉边框
        Window window = getDialog().getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }

        // 点击空白区域不可取消
        setCancelable(false);

        return inflater.inflate(R.layout.dialog_excitation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dip2px(getActivity(), 257), DensityUtil
                .dip2px(getActivity(), 159));
    }

    private void initView(View view) {
        Button bt_excitation_confirm = view.findViewById(R.id.bt_excitation_confirm);
        bt_excitation_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_excitation_confirm:
                dismiss();
                getActivity().finish();
                break;
            default:
                break;
        }
    }


}
