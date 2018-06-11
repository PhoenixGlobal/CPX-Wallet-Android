package chinapex.com.wallet.utils;

import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.view.assets.AssetsFragment;
import chinapex.com.wallet.view.discover.DiscoverFragment;
import chinapex.com.wallet.view.me.MeFragment;
import chinapex.com.wallet.view.me.MeManageDetailFragment;
import chinapex.com.wallet.view.me.MeTransactionRecordFragment;
import chinapex.com.wallet.view.wallet.BackupFragment;
import chinapex.com.wallet.view.wallet.ConfirmMnemonicFragment;
import chinapex.com.wallet.view.wallet.CopyMnemonicFragment;
import chinapex.com.wallet.view.wallet.ImportKeystoreFragment;
import chinapex.com.wallet.view.wallet.ImportMnemonicFragment;

public class FragmentFactory {

    private static DiscoverFragment sDiscoverFragment; //0
    private static AssetsFragment sAssetsFragment; //1
    private static MeFragment sMeFragment; //2

    private static BackupFragment sBackupFragment;
    private static CopyMnemonicFragment sCopyMnemonicFragment;
    private static ConfirmMnemonicFragment sConfirmMnemonicFragment;

    private static MeManageDetailFragment sMeManageDetailFragment;
    private static MeTransactionRecordFragment sMeTransactionRecordFragment;

    private static ImportMnemonicFragment sImportMnemonicFragment;
    private static ImportKeystoreFragment sImportKeystoreFragment;

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (null == sDiscoverFragment) {
                    sDiscoverFragment = new DiscoverFragment();
                }
                baseFragment = sDiscoverFragment;
                break;
            case 1:
                if (null == sAssetsFragment) {
                    sAssetsFragment = new AssetsFragment();
                }
                baseFragment = sAssetsFragment;
                break;
            case 2:
                if (null == sMeFragment) {
                    sMeFragment = new MeFragment();
                }
                baseFragment = sMeFragment;
                break;
            default:
                break;
        }
        return baseFragment;
    }

    public static BaseFragment getFragment(String fragmentTag) {
        BaseFragment baseFragment = null;
        switch (fragmentTag) {
            case Constant.FRAGMENT_TAG_BACKUP:
                if (null == sBackupFragment) {
                    sBackupFragment = new BackupFragment();
                }
                baseFragment = sBackupFragment;
                break;
            case Constant.FRAGMENT_TAG_COPY_MNEMONIC:
                if (null == sCopyMnemonicFragment) {
                    sCopyMnemonicFragment = new CopyMnemonicFragment();
                }
                baseFragment = sCopyMnemonicFragment;
                break;
            case Constant.FRAGMENT_TAG_CONFIRM_MNEMONIC:
                if (null == sConfirmMnemonicFragment) {
                    sConfirmMnemonicFragment = new ConfirmMnemonicFragment();
                }
                baseFragment = sConfirmMnemonicFragment;
                break;
            case Constant.FRAGMENT_TAG_ME_MANAGE_DETAIL:
                if (null == sMeManageDetailFragment) {
                    sMeManageDetailFragment = new MeManageDetailFragment();
                }
                baseFragment = sMeManageDetailFragment;
                break;
            case Constant.FRAGMENT_TAG_ME_TRANSACTION_RECORD:
                if (null == sMeTransactionRecordFragment) {
                    sMeTransactionRecordFragment = new MeTransactionRecordFragment();
                }
                baseFragment = sMeTransactionRecordFragment;
                break;
            case Constant.FRAGMENT_TAG_IMPORT_MNEMONIC:
                if (null == sImportMnemonicFragment) {
                    sImportMnemonicFragment = new ImportMnemonicFragment();
                }
                baseFragment = sImportMnemonicFragment;
                break;
            case Constant.FRAGMENT_TAG_IMPORT_KEYSTORE:
                if (null == sImportKeystoreFragment) {
                    sImportKeystoreFragment = new ImportKeystoreFragment();
                }
                baseFragment = sImportKeystoreFragment;
                break;
            default:
                break;
        }
        return baseFragment;
    }
}
