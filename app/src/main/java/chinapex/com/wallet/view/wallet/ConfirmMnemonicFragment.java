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
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;

import chinapex.com.wallet.R;
import chinapex.com.wallet.adapter.BackupClickMnemonicAdapter;
import chinapex.com.wallet.adapter.BackupShowMnemonicAdapter;
import chinapex.com.wallet.adapter.SpacesItemDecorationHorizontal;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.bean.MnemonicState;
import chinapex.com.wallet.bean.neo.NeoWallet;
import chinapex.com.wallet.bean.WalletBean;
import chinapex.com.wallet.bean.eth.EthWallet;
import chinapex.com.wallet.changelistener.ApexListeners;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.DensityUtil;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.utils.ToastUtils;
import chinapex.com.wallet.view.MainActivity;

/**
 * Created by SteelCabbage on 2018/5/28 0028.
 */

public class ConfirmMnemonicFragment extends BaseFragment implements View.OnClickListener,
        BackupClickMnemonicAdapter.OnItemClickListener, BackupShowMnemonicAdapter
                .OnItemClickShowListener {

    private static final String TAG = ConfirmMnemonicFragment.class.getSimpleName();
    private Button mBt_confirm_mnemonic_confirm;
    private RecyclerView mRv_confirm_mnemonic_show;
    private RecyclerView mRv_confirm_mnemonic_click;
    private BackupClickMnemonicAdapter mBackupClickMnemonicAdapter;
    private BackupShowMnemonicAdapter mBackupShowMnemonicAdapter;
    private ArrayList<MnemonicState> mMnemonicStatesShow;
    private ArrayList<MnemonicState> mMnemonicStatesClick;
    private ArrayList<MnemonicState> mFinalRightMnemonics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_mnemonic, container, false);
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

        // 设置点击的助记词
        String backupMnemonic = backupWalletActivity.getBackupMnemonic();
        if (TextUtils.isEmpty(backupMnemonic)) {
            CpLog.e(TAG, "backupMnemonic is null!");
            return;
        }
        String[] backupMnemonics = backupMnemonic.split(" ");
        mMnemonicStatesClick = new ArrayList<>();
        mFinalRightMnemonics = new ArrayList<>();
        for (int i = 0; i < backupMnemonics.length; i++) {
            MnemonicState mnemonicState = new MnemonicState();
            mnemonicState.setMnemonic(backupMnemonics[i]);
            mnemonicState.setSelected(false);
            mFinalRightMnemonics.add(mnemonicState);
            mMnemonicStatesClick.add(mnemonicState);
        }

        // 打乱助记词
        Collections.shuffle(mMnemonicStatesClick);
        mBackupClickMnemonicAdapter = new BackupClickMnemonicAdapter(mMnemonicStatesClick);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(ApexWalletApplication
                .getInstance());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        mRv_confirm_mnemonic_click.setLayoutManager(layoutManager);
        mRv_confirm_mnemonic_click.setAdapter(mBackupClickMnemonicAdapter);
        int space = DensityUtil.dip2px(getActivity(), 4);
        mRv_confirm_mnemonic_click.addItemDecoration(new SpacesItemDecorationHorizontal(space));
        mBackupClickMnemonicAdapter.setOnItemClickListener(this);

        // 设置展示的助记词
        mMnemonicStatesShow = new ArrayList<>();
        mBackupShowMnemonicAdapter = new BackupShowMnemonicAdapter(mMnemonicStatesShow);
        FlexboxLayoutManager layoutManagerShow = new FlexboxLayoutManager(ApexWalletApplication
                .getInstance());
        layoutManagerShow.setFlexDirection(FlexDirection.ROW);
        layoutManagerShow.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        mRv_confirm_mnemonic_show.setLayoutManager(layoutManagerShow);
        mRv_confirm_mnemonic_show.setAdapter(mBackupShowMnemonicAdapter);
        mRv_confirm_mnemonic_show.addItemDecoration(new SpacesItemDecorationHorizontal(space));
        mBackupShowMnemonicAdapter.setOnItemClickShowListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm_mnemonic_confirm:
                if (!checkMnemonicIsRight()) {
                    CpLog.w(TAG, "checkMnemonicIsRight is false!");
                    ToastUtils.getInstance().showToast(ApexWalletApplication.getInstance()
                            .getResources().getString(R.string.mnemonic_incorrect));
                    return;
                }

                updateWalletBackupState();
                isFirstEnter();
                break;
            default:
                break;
        }
    }

    private boolean checkMnemonicIsRight() {
        if (null == mFinalRightMnemonics || mFinalRightMnemonics.isEmpty()) {
            CpLog.w(TAG, "mFinalRightMnemonics is null or empty!");
            return false;
        }

        if (null == mMnemonicStatesShow || mMnemonicStatesShow.isEmpty()) {
            CpLog.w(TAG, "mMnemonicStatesShow is null or empty!");
            return false;
        }

        if (mFinalRightMnemonics.size() != mMnemonicStatesShow.size()) {
            CpLog.w(TAG, "mFinalRightMnemonics and mMnemonicStatesShow size is not same!");
            return false;
        }

        for (int i = 0; i < mFinalRightMnemonics.size(); i++) {
            MnemonicState mnemonicFinal = mFinalRightMnemonics.get(i);
            MnemonicState mnemonicShow = mMnemonicStatesShow.get(i);
            if (!mnemonicFinal.equals(mnemonicShow)) {
                CpLog.w(TAG, "mnemonics out of order!");
                return false;
            }
        }

        return true;
    }

    private void updateWalletBackupState() {
        BackupWalletActivity backupWalletActivity = (BackupWalletActivity) getActivity();
        if (null == backupWalletActivity) {
            CpLog.e(TAG, "updateWalletBackupState() -> backupWalletActivity is null!");
            return;
        }

        WalletBean walletBean = backupWalletActivity.getWalletBean();
        if (null == walletBean) {
            CpLog.e(TAG, "walletBean is null!");
            return;
        }

        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication
                .getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            return;
        }

        switch (walletBean.getClass().getSimpleName()) {
            case "NeoWallet":
                NeoWallet neoWallet = (NeoWallet) walletBean;
                apexWalletDbDao.updateBackupState(Constant.TABLE_NEO_WALLET, neoWallet.getAddress(), Constant.BACKUP_FINISH);
                neoWallet.setBackupState(Constant.BACKUP_FINISH);
                ApexListeners.getInstance().notifyItemStateUpdate(neoWallet);
                break;
            case "EthWallet":
                EthWallet ethWallet = (EthWallet) walletBean;
                apexWalletDbDao.updateBackupState(Constant.TABLE_ETH_WALLET, ethWallet.getAddress(), Constant.BACKUP_FINISH);
                ethWallet.setBackupState(Constant.BACKUP_FINISH);
                ApexListeners.getInstance().notifyEthStateUpdate(ethWallet);
                break;
            default:
                CpLog.e(TAG, "unknown wallet type!");
                break;
        }
    }

    private void isFirstEnter() {
        boolean isFirstExport = (boolean) SharedPreferencesUtils.getParam(ApexWalletApplication
                .getInstance(), Constant.IS_FIRST_ENTER_MAIN, true);
        if (isFirstExport) {
            SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), Constant
                    .IS_FIRST_ENTER_MAIN, false);
            startActivity(MainActivity.class, true);
        } else {
            getActivity().finish();
        }
    }

}
