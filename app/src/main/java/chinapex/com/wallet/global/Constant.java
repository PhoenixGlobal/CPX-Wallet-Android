package chinapex.com.wallet.global;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class Constant {
    //net
    public static final long CONNECT_TIMEOUT = 5;
    public static final long READ_TIMEOUT = 5;
    public static final long WRITE_TIMEOUT = 5;
    public static final int NET_ERROR = -1;
    public static final int NET_SUCCESS = 1;
    public static final String HOSTNAME_VERIFIER = "40.125.171.0";
    public static final String URL_CLI = "http://40.125.171.0:20332";

    //assets
    public static final String WALLET_BEAN = "walletBean";

    //create wallet
    public static final String SP_WALLET_KEYSTORE = "spWalletKeystore";
    public static final String WHERE_FROM_ACTIVITY = "whereFromActivity";
    public static final String WHERE_FROM_NEW_VISITOR_ACTIVITY = "NewVisitorActivity";
    public static final String WHERE_FROM_WALLET_DETAIL_ACTIVITY = "WalletDetailActivity";

    //backup wallet
    public static final String BACKUP_MNEMONIC = "backupMnemonic";

    //wallet detail transfer
    public static final String PARCELABLE_WALLET_BEAN_TRANSFER = "parcelableWalletBeanTransfer";
    //wallet detail gathering
    public static final String PARCELABLE_WALLET_BEAN_GATHERING = "parcelableWalletBeanGathering";

    //asset name
    public static final String ASSETS_NEO =
            "0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b";
    public static final String ASSETS_GAS =
            "0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7";

    //txids
    public static final String SP_TX_ID = "spTxId";

    //wallet title
    public static final String WALLET_NAME = "Wallet ";

}
