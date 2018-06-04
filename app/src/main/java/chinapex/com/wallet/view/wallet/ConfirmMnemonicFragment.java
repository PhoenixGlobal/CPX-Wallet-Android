package chinapex.com.wallet.view.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.BackupClickMnemonicAdapter;
import chinapex.com.wallet.adapter.BackupShowMnemonicAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecoration;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.MnemonicState;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.view.MainActivity;

/**
 * Created by SteelCabbage on 2018/5/28 0028.
 */

public class ConfirmMnemonicFragment extends BaseFragment implements View.OnClickListener,
        BackupClickMnemonicAdapter.OnItemClickListener, BackupShowMnemonicAdapter
                .OnItemClickShowListener {

    private static final String TAG = ConfirmMnemonicFragment.class.getSimpleName();
    private Button mBt_confirm_mnemonic_confirm;
    private String mWhereFromActivity;
    private RecyclerView mRv_confirm_mnemonic_show;
    private RecyclerView mRv_confirm_mnemonic_click;
    private BackupClickMnemonicAdapter mBackupClickMnemonicAdapter;
    private BackupShowMnemonicAdapter mBackupShowMnemonicAdapter;
    private ArrayList<MnemonicState> mMnemonicStatesShow;
    private ArrayList<MnemonicState> mMnemonicStatesClick;

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
        mRv_confirm_mnemonic_show = view.findViewById(R.id.rv_confirm_mnemonic_show);
        mRv_confirm_mnemonic_click = view.findViewById(R.id.rv_confirm_mnemonic_click);

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

        //设置点击的助记词
        String[] backupMnemonics = backupMnemonic.split(" ");
        mMnemonicStatesClick = new ArrayList<>();
        for (int i = 0; i < backupMnemonics.length; i++) {
            MnemonicState mnemonicState = new MnemonicState();
            mnemonicState.setMnemonic(backupMnemonics[i]);
            mnemonicState.setSelected(false);
            mMnemonicStatesClick.add(mnemonicState);
        }
        mBackupClickMnemonicAdapter = new BackupClickMnemonicAdapter(mMnemonicStatesClick);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(ApexWalletApplication
                .getInstance());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRv_confirm_mnemonic_click.setLayoutManager(layoutManager);
        mRv_confirm_mnemonic_click.setAdapter(mBackupClickMnemonicAdapter);
        int spaceClick = 10;
        mRv_confirm_mnemonic_click.addItemDecoration(new SpacesItemDecoration(spaceClick));
        mBackupClickMnemonicAdapter.setOnItemClickListener(this);

        //设置展示的助记词
        mMnemonicStatesShow = new ArrayList<>();
        mBackupShowMnemonicAdapter = new BackupShowMnemonicAdapter(mMnemonicStatesShow);
        FlexboxLayoutManager layoutManagerShow = new FlexboxLayoutManager(ApexWalletApplication
                .getInstance());
        layoutManagerShow.setFlexDirection(FlexDirection.ROW);
        layoutManagerShow.setJustifyContent(JustifyContent.FLEX_START);
        mRv_confirm_mnemonic_show.setLayoutManager(layoutManagerShow);
        mRv_confirm_mnemonic_show.setAdapter(mBackupShowMnemonicAdapter);
        int spaceShow = 10;
        mRv_confirm_mnemonic_show.addItemDecoration(new SpacesItemDecoration(spaceShow));
        mBackupShowMnemonicAdapter.setOnItemClickShowListener(this);

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

    //展示助记词的回调
    @Override
    public void onItemClickShow(int position) {
        MnemonicState mnemonicStateShow = mMnemonicStatesShow.get(position);
        for (MnemonicState stateClick : mMnemonicStatesClick) {
            if (mnemonicStateShow.getMnemonic().equals(stateClick.getMnemonic())) {
                stateClick.setSelected(false);
            }
        }
        mMnemonicStatesShow.remove(mnemonicStateShow);
        mBackupClickMnemonicAdapter.notifyDataSetChanged();
        mBackupShowMnemonicAdapter.notifyDataSetChanged();
    }

    //点击助记词的回调
    @Override
    public void onItemClick(int position) {
        MnemonicState mnemonicState = mMnemonicStatesClick.get(position);
        if (mnemonicState.isSelected()) {
            mMnemonicStatesShow.add(mnemonicState);
        } else {
            mMnemonicStatesShow.remove(mnemonicState);
        }
        mBackupShowMnemonicAdapter.notifyDataSetChanged();
    }
}
