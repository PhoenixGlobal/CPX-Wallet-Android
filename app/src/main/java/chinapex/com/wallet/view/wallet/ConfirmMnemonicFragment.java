package chinapex.com.wallet.view.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.MainActivity;

/**
 * Created by SteelCabbage on 2018/5/28 0028.
 */

public class ConfirmMnemonicFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ConfirmMnemonicFragment.class.getSimpleName();
    private Button mBt_confirm_mnemonic_confirm;
    private String mWhereFromActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View fragment_verify_mnemonic = inflater.inflate(R.layout.fragment_confirm_mnemonic,
                container, false);
        return fragment_verify_mnemonic;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();

    }

    private void initView(View view) {
        mBt_confirm_mnemonic_confirm = view.findViewById(R.id.bt_confirm_mnemonic_confirm);

        mBt_confirm_mnemonic_confirm.setOnClickListener(this);
    }

    private void initData() {
        BackupWalletActivity backupWalletActivity = (BackupWalletActivity) getActivity();
        if (null == backupWalletActivity) {
            CpLog.e(TAG, "backupWalletActivity is null!");
            return;
        }

        String backupMnemonic = backupWalletActivity.getBackupMnemonic();
        mWhereFromActivity = backupWalletActivity.getWhereFromActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm_mnemonic_confirm:
                toMainActivity();
                break;
            default:
                break;
        }
    }

    private void toMainActivity() {
        if (TextUtils.isEmpty(mWhereFromActivity)) {
            CpLog.e(TAG, "mWhereFromActivity is null!");
            return;
        }

        switch (mWhereFromActivity) {
            case Constant.WHERE_FROM_NEW_VISITOR_ACTIVITY:
                CreateWalletActivity.sCreateWalletActivity.finish();
                startActivity(MainActivity.class, true);
                break;
            case Constant.WHERE_FROM_WALLET_DETAIL_ACTIVITY:
                CreateWalletActivity.sCreateWalletActivity.finish();
                getActivity().finish();
                break;
            default:
                break;
        }

    }
}
