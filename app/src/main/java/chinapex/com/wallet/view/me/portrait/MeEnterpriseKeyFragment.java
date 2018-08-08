package chinapex.com.wallet.view.me.portrait;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.utils.CpLog;

/**
 * Created by SteelCabbage on 2018/7/24 0024 15:10.
 * E-Mail：liuyi_61@163.com
 */

public class MeEnterpriseKeyFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = MeEnterpriseKeyFragment.class.getSimpleName();
    private Button mBt_enterprise_key_confirm;
    private OnConfirmClickListener mOnConfirmClickListener;

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        mOnConfirmClickListener = onConfirmClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me_enterprise_key, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mBt_enterprise_key_confirm = view.findViewById(R.id.bt_enterprise_key_confirm);
        mBt_enterprise_key_confirm.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_enterprise_key_confirm:
                if (null == mOnConfirmClickListener) {
                    CpLog.e(TAG, "mOnConfirmClickListener is null!");
                    return;
                }

                mOnConfirmClickListener.onConfirmClick();
                break;
            default:
                break;
        }
    }
}
