package chinapex.com.wallet.view.wallet;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

/**
 * Created by SteelCabbage on 2018/5/28 0028.
 */

public class CopyMnemonicFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = CopyMnemonicFragment.class.getSimpleName();
    private TextView mTv_copy_mnemonic;
    private Button mBt_copy_mnemonic_next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View fragment_copy_mnemonic = inflater.inflate(R.layout.fragment_copy_mnemonic,
                container, false);
        return fragment_copy_mnemonic;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initView(View view) {
        mTv_copy_mnemonic = view.findViewById(R.id.tv_copy_mnemonic);
        mBt_copy_mnemonic_next = view.findViewById(R.id.bt_copy_mnemonic_next);

        mBt_copy_mnemonic_next.setOnClickListener(this);
    }

    private void initData() {
        BackupWalletActivity backupWalletActivity = (BackupWalletActivity) getActivity();
        if (null == backupWalletActivity) {
            CpLog.e(TAG, "backupWalletActivity is null!");
            return;
        }

        mTv_copy_mnemonic.setText(backupWalletActivity.getBackupMnemonic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_copy_mnemonic_next:
                toConfirmMnemonicFragment();
                break;
            default:
                break;
        }
    }

    private void toConfirmMnemonicFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager()
                .beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(9);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_backup, fragment, "" + 9);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.show(fragment).hide(FragmentFactory.getFragment(8)).commit();

        BackupWalletActivity backupWalletActivity = (BackupWalletActivity) getActivity();
        if (null == backupWalletActivity) {
            CpLog.e(TAG, "backupWalletActivity is null!");
            return;
        }

        TextView tv_backup_title = backupWalletActivity.findViewById(R.id.tv_backup_title);
        tv_backup_title.setText(backupWalletActivity.getBackupTitles()[2]);
    }
}
