package chinapex.com.wallet.utils;

import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.view.assets.AssetsFragment;
import chinapex.com.wallet.view.discover.DiscoverFragment;
import chinapex.com.wallet.view.me.MeFragment;
import chinapex.com.wallet.view.wallet.BackupFragment;
import chinapex.com.wallet.view.wallet.ConfirmMnemonicFragment;
import chinapex.com.wallet.view.wallet.CopyMnemonicFragment;

public class FragmentFactory {

    private static DiscoverFragment sDiscoverFragment; //0
    private static AssetsFragment sAssetsFragment; //1
    private static MeFragment sMeFragment; //2

    private static BackupFragment sBackupFragment; //7
    private static CopyMnemonicFragment sCopyMnemonicFragment; //8
    private static ConfirmMnemonicFragment sConfirmMnemonicFragment; //9

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
            case 3:
                //home page reserved
                break;
            case 4:
                //home page reserved
                break;
            case 5:
                //home page reserved
                break;
            case 6:
                //home page reserved
                break;

            //backup page
            case 7:
                if (null == sBackupFragment) {
                    sBackupFragment = new BackupFragment();
                }
                baseFragment = sBackupFragment;
                break;
            case 8:
                if (null == sCopyMnemonicFragment) {
                    sCopyMnemonicFragment = new CopyMnemonicFragment();
                }
                baseFragment = sCopyMnemonicFragment;
                break;
            case 9:
                if (null == sConfirmMnemonicFragment) {
                    sConfirmMnemonicFragment = new ConfirmMnemonicFragment();
                }
                baseFragment = sConfirmMnemonicFragment;
                break;
        }
        return baseFragment;
    }
}
