package chinapex.com.wallet.global;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class Constant {
    // application state value
    public static final String IS_FIRST_ENTER_APP = "isFirstEnterApp";
    public static final String IS_FIRST_ENTER_MAIN = "isFirstEnterMain";

    // net
    public static final long CONNECT_TIMEOUT = 5;
    public static final long READ_TIMEOUT = 5;
    public static final long WRITE_TIMEOUT = 5;
    public static final int NET_ERROR = -1;
    public static final int NET_SUCCESS = 1;
    public static final int NET_BODY_NULL = 0;

    // neo测试网ip
//    public static final String HOSTNAME_VERIFIER = "40.125.171.0";
//    public static final String URL_CLI = "http://40.125.171.0:20332";
//    public static final String URL_UTXOS = "http://40.125.171.0:8083/utxos/";

    // neo测试网域名
//    public static final String HOSTNAME_VERIFIER = "dev.chinapex.com.cn";
//    public static final String URL_CLI = "http://dev.chinapex.com.cn:10086/neo-cli/";
//    public static final String URL_UTXOS = "http://dev.chinapex.com.cn:10086/tool/utxos/";

    // neo正式网
    public static final String HOSTNAME_VERIFIER = "tracker.chinapex.com.cn";
    public static final String URL_CLI = "https://tracker.chinapex.com.cn/neo-cli/";
    public static final String URL_UTXOS = "https://tracker.chinapex.com.cn/tool/utxos/";

    // 交易记录
    public static final String URL_TRANSACTION_HISTORY = "https://tracker.chinapex.com" +
            ".cn/tool/transaction-history/";
    public static final long TX_POLLING_TIME = 20 * 1000;
    public static final long TX_CONFIRM_EXCEPTION = -1;
    public static final long TX_CONFIRM_ONE = 1;
    public static final long TX_CONFIRM_OK = 6;
    public static final long NO_NEED_MODIFY_TX_TIME = -2;

    // fragmentTag
    public static final String FRAGMENT_TAG_IMPORT_MNEMONIC = "ImportMnemonicFragment";
    public static final String FRAGMENT_TAG_IMPORT_KEYSTORE = "ImportKeystoreFragment";
    public static final String FRAGMENT_TAG_BACKUP = "BackupFragment";
    public static final String FRAGMENT_TAG_COPY_MNEMONIC = "CopyMnemonicFragment";
    public static final String FRAGMENT_TAG_CONFIRM_MNEMONIC = "ConfirmMnemonicFragment";
    public static final String FRAGMENT_TAG_ME_MANAGE_DETAIL = "MeManageDetailFragment";
    public static final String FRAGMENT_TAG_ME_TRANSACTION_RECORD = "MeTransactionRecordFragment";

    // import wallet from mnemonic
    public static final String WALLET_NAME_IMPORT_DEFAULT = "Wallet";

    // assets
    public static final String WALLET_BEAN = "walletBean";
    public static final String BALANCE_BEAN = "balanceBean";

    // 一键映射
    public static final int MAP_STATE_FINISHED = 1;
    public static final int MAP_STATE_UNFINISHED = 0;

    // me
    public static final String ME_MANAGER_DETAIL_BUNDLE = "meManagerDetailBundle";
    public static final String ME_SKIP_ACTIVITY_FRAGMENT_TAG = "meSkipActivityFragmentTag";
    public static final String PARCELABLE_WALLET_BEAN_MANAGE_DETAIL =
            "parcelableWalletBeanManageDetail";

    // transaction record
    public static final int TRANSACTION_STATE_FAIL = 0;
    public static final int TRANSACTION_STATE_SUCCESS = 1;
    public static final int TRANSACTION_STATE_CONFIRMING = 2;
    public static final int TRANSACTION_STATE_PACKAGING = 3;

    public static final String TRANSACTION_STATE_FAIL_TEXT = "交易失败";
    public static final String TRANSACTION_STATE_SUCCESS_TEXT = "交易成功";
    public static final String TRANSACTION_STATE_CONFIRMING_TEXT = "交易确认中";
    public static final String TRANSACTION_STATE_PACKAGING_TEXT = "交易打包中";

    public static final String PARCELABLE_TRANSACTION_RECORD = "parcelableTransactionRecord";

    // backup wallet
    public static final String BACKUP_KEYSTORE = "backupKeystore";
    public static final String BACKUP_MNEMONIC = "backupMnemonic";
    public static final int BACKUP_UNFINISHED = 0;
    public static final int BACKUP_FINISH = 1;

    // wallet detail transfer
    public static final String PARCELABLE_WALLET_BEAN_TRANSFER = "parcelableWalletBeanTransfer";
    public static final String PARCELABLE_BALANCE_BEAN_TRANSFER = "parcelableBalanceBeanTransfer";
    public static final String PARCELABLE_QR_CODE_TRANSFER = "parcelableQrCodeTransfer";

    // wallet detail gathering
    public static final String PARCELABLE_WALLET_BEAN_GATHERING = "parcelableWalletBeanGathering";

    // asset ID
    public static final String ASSETS_NEO =
            "0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b";
    public static final String ASSETS_NEO_GAS =
            "0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7";
    public static final String ASSETS_CPX = "0x45d493a6f73fa5f404244a5fb8472fc014ca5885";

    // asset type
    public static final String ASSET_TYPE_GLOBAL = "GLOBAL";
    public static final String ASSET_TYPE_NEP5 = "NEP5";

    public static final String MARK_NEO = "NEO";
    public static final String MARK_NEO_GAS = "GAS";
    public static final String MARK_CPX = "CPX";

    public static final String UNIT_NEO = "neo";
    public static final String UNIT_NEO_GAS = "gas";
    public static final String UNIT_CPX = "cpx";

    // wallet title
    public static final String WALLET_NAME = "Wallet ";

    // selected tag
    public static final int SELECTED_TAG_MANAGER_WALLET = 0;
    public static final int SELECTED_TAG_TRANSACTION_RECORED = 1;


    // db
    // table wallet
    public static final String TABLE_APEX_WALLET = "apex_wallet";

    public static final String FIELD_ID = "_id";
    public static final String FIELD_WALLET_NAME = "wallet_name";
    public static final String FIELD_WALLET_ADDRESS = "wallet_address";
    public static final String FIELD_BACKUP_STATE = "backup_state";
    public static final String FIELD_WALLET_KEYSTORE = "wallet_keystore";
    public static final String FIELD_WALLET_ASSETS_JSON = "wallet_assets_json";
    public static final String FIELD_WALLET_ASSETS_NEP5_JSON = "wallet_assets_nep5_json";
    public static final String FIELD_CREATE_TIME = "create_time";

    public static final String SQL_CREATE_APEX_WALLET = "create table " + TABLE_APEX_WALLET
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_WALLET_NAME + " text, "
            + FIELD_WALLET_ADDRESS + " text, "
            + FIELD_BACKUP_STATE + " integer, "
            + FIELD_WALLET_KEYSTORE + " text, "
            + FIELD_WALLET_ASSETS_JSON + " text, "
            + FIELD_WALLET_ASSETS_NEP5_JSON + " text, "
            + FIELD_CREATE_TIME + " integer)";


    // table transaction record
    public static final String TABLE_TRANSACTION_RECORD = "transaction_record";

    public static final String FIELD_TX_TYPE = "tx_type";
    public static final String FIELD_TX_ID = "tx_id";
    public static final String FIELD_TX_AMOUNT = "tx_amount";
    public static final String FIELD_TX_STATE = "tx_state";
    public static final String FIELD_TX_FROM = "tx_from";
    public static final String FIELD_TX_TO = "tx_to";
    public static final String FIELD_GAS_CONSUMED = "gas_consumed";
    public static final String FIELD_ASSET_ID = "asset_id";
    public static final String FIELD_ASSET_SYMBOL = "asset_symbol";
    public static final String FIELD_ASSET_LOGO_URL = "asset_logo_url";
    public static final String FIELD_ASSET_DECIMAL = "asset_decimal";

    public static final String SQL_CREATE_TRANSACTION_RECORD = "create table " +
            TABLE_TRANSACTION_RECORD
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_WALLET_ADDRESS + " text, "
            + FIELD_TX_TYPE + " text, "
            + FIELD_TX_ID + " text, "
            + FIELD_TX_AMOUNT + " text, "
            + FIELD_TX_STATE + " integer, "
            + FIELD_TX_FROM + " text, "
            + FIELD_TX_TO + " text, "
            + FIELD_GAS_CONSUMED + " text, "
            + FIELD_ASSET_ID + " text, "
            + FIELD_ASSET_SYMBOL + " text, "
            + FIELD_ASSET_LOGO_URL + " text, "
            + FIELD_ASSET_DECIMAL + " integer, "
            + FIELD_CREATE_TIME + " integer)";

    // table tx cache
    public static final String TABLE_TX_CACHE = "txCache";

    public static final String SQL_CREATE_TX_CACHE = "create table " +
            TABLE_TX_CACHE
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_WALLET_ADDRESS + " text, "
            + FIELD_TX_TYPE + " text, "
            + FIELD_TX_ID + " text, "
            + FIELD_TX_AMOUNT + " text, "
            + FIELD_TX_STATE + " integer, "
            + FIELD_TX_FROM + " text, "
            + FIELD_TX_TO + " text, "
            + FIELD_GAS_CONSUMED + " text, "
            + FIELD_ASSET_ID + " text, "
            + FIELD_ASSET_SYMBOL + " text, "
            + FIELD_ASSET_LOGO_URL + " text, "
            + FIELD_ASSET_DECIMAL + " integer, "
            + FIELD_CREATE_TIME + " integer)";

}
