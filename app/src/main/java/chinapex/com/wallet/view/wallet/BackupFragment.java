package chinapex.com.wallet.view.wallet;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

public class BackupFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = BackupFragment.class.getSimpleName();

    private Button mBt_backup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View fragment_backup = inflater.inflate(R.layout.fragment_backup, container, false);
        return fragment_backup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    private void initView(View view) {
        mBt_backup = view.findViewById(R.id.bt_backup);

        mBt_backup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_backup:
                toCopyMnemonicFragment();
                break;
            default:
                break;
        }
    }

    private void toCopyMnemonicFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager()
                .beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(Constant.FRAGMENT_TAG_COPY_MNEMONIC);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_backup, fragment, Constant.FRAGMENT_TAG_COPY_MNEMONIC);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.show(fragment).hide(FragmentFactory.getFragment(Constant.FRAGMENT_TAG_BACKUP)).commit();

        BackupWalletActivity backupWalletActivity = (BackupWalletActivity) getActivity();
        if (null == backupWalletActivity) {
            CpLog.e(TAG, "backupWalletActivity is null!");
            return;
        }

        TextView tv_backup_title = backupWalletActivity.findViewById(R.id.tv_backup_title);
        tv_backup_title.setText(backupWalletActivity.getBackupTitles()[1]);
    }


}
