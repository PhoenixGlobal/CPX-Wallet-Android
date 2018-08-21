package chinapex.com.wallet.global;

/**
 * Created by SteelCabbage on 2018/5/21 0021.
 */

public class Constant {
    // application state value
    public static final String IS_FIRST_ENTER_APP = "isFirstEnterApp";
    public static final String IS_FIRST_ENTER_MAIN = "isFirstEnterMain";

    // language settings
    public static final String CURRENT_LANGUAGE = "currentLanguage";

    // net
    public static final long CONNECT_TIMEOUT = 5;
    public static final long READ_TIMEOUT = 5;
    public static final long WRITE_TIMEOUT = 5;
    public static final int NET_ERROR = -1;
    public static final int NET_SUCCESS = 1;
    public static final int NET_BODY_NULL = 0;

    // neo正式网
    public static final String HOSTNAME_VERIFIER = "tracker.chinapex.com.cn";
    public static final String URL_CLI = "https://tracker.chinapex.com.cn/neo-cli/";
    public static final String URL_UTXOS = "https://tracker.chinapex.com.cn/tool/utxos/";
    public static final String URL_ASSETS = "https://tracker.chinapex.com.cn/tool/assets";

    // ApexGlobalTask
    public static final long ASSETS_POLLING_TIME = 10 * 1000;
    public static final String UPDATE_ASSETS_OK = "updateAssetsOk";

    // Portrait type
    public static final int TYPE_GENERA_LIST = 1;
    public static final int TYPE_GENERA_TAGS = 2;
    public static final int TYPE_UNKNOW = -1;
    public static final int TYPE_INPUT = 0;
    public static final int TYPE_LEVEL_ONE_LINKAGE = 10;
    public static final int TYPE_LEVEL_TWO_LINKAGE = 20;
    public static final int TYPE_LEVEL_THREE_LINKAGE = 30;
    public static final int TYPE_TAGS = 40;
    public static final String EXTRA_TYPE_INPUT_TITLE = "extraTypeInputTitle";
    public static final String EXTRA_TYPE_INPUT_CONTENT = "extraTypeInputContent";
    public static final String EXTRA_TYPE_INPUT_POSITION = "extraTypeInputPosition";

    // wallet type
    public static final int WALLET_TYPE_NEO = 0;
    public static final int WALLET_TYPE_ETH = 1;
    public static final int WALLET_TYPE_CPX = 2;

    // wallet type name
    public static final String WALLET_TYPE_NAME_NEO = "NEO";
    public static final String WALLET_TYPE_NAME_ETH = "ETH";
    public static final String WALLET_TYPE_NAME_CPX = "CPX";

    // 交易记录
    public static final String URL_TRANSACTION_HISTORY = "https://tracker.chinapex.com" +
            ".cn/tool/transaction-history/";
    public static final long TX_POLLING_TIME = 20 * 1000;
    public static final long TX_CONFIRM_EXCEPTION = -1;
    public static final long TX_UN_CONFIRM = 0;
    public static final long TX_CONFIRM_OK = 6;
    public static final long NO_NEED_MODIFY_TX_TIME = -2;

    // fragmentTag
    public static final String FRAGMENT_TAG_IMPORT_MNEMONIC = "ImportMnemonicFragment";
    public static final String FRAGMENT_TAG_IMPORT_KEYSTORE = "ImportKeystoreFragmentNeo";
    public static final String FRAGMENT_TAG_BACKUP = "BackupFragment";
    public static final String FRAGMENT_TAG_COPY_MNEMONIC = "CopyMnemonicFragment";
    public static final String FRAGMENT_TAG_CONFIRM_MNEMONIC = "ConfirmMnemonicFragment";
    public static final String FRAGMENT_TAG_ME_MANAGE_DETAIL = "MeManageDetailFragment";
    public static final String FRAGMENT_TAG_ME_TRANSACTION_RECORD = "MeTransactionRecordFragment";
    public static final String FRAGMENT_TAG_ME_PORTRAIT = "MePortraitFragment";
    public static final String FRAGMENT_TAG_ME_COMMON_PORTRAIT = "MeCommonPortraitFragment";
    public static final String FRAGMENT_TAG_ME_ENTERPRISE_PORTRAIT = "MeEnterprisePortraitFragment";
    public static final String FRAGMENT_TAG_ME_ENTERPRISE_KEY = "MeEnterpriseKeyFragment";


    // import wallet from mnemonic
    public static final String WALLET_NAME_IMPORT_DEFAULT = "Wallet";

    // assets
    public static final String PARCELABLE_WALLET_TYPE = "parcelableWalletType";
    public static final String WALLET_BEAN = "walletBean";
    public static final String BALANCE_BEAN = "balanceBean";

    // 一键映射
    public static final int MAP_STATE_FINISHED = 1;
    public static final int MAP_STATE_UNFINISHED = 0;

    // me
    public static final String ME_MANAGER_DETAIL_BUNDLE = "meManagerDetailBundle";
    public static final String ME_SKIP_ACTIVITY_FRAGMENT_TAG = "meSkipActivityFragmentTag";
    public static final String ME_2_SHOULD_BE_SHOW = "me2ShouldBeShow";
    public static final String ME_2_SHOULD_BE_SHOW_MANAGE_WALLET = "me2ShouldBeShowManageWallet";
    public static final String ME_2_SHOULD_BE_SHOW_TX_RECORDS = "me2ShouldBeShowTxRecords";
    public static final String PARCELABLE_WALLET_BEAN_MANAGE_DETAIL =
            "parcelableWalletBeanManageDetail";

    // transaction record
    public static final int TRANSACTION_STATE_FAIL = 0;
    public static final int TRANSACTION_STATE_SUCCESS = 1;
    public static final int TRANSACTION_STATE_CONFIRMING = 2;
    public static final int TRANSACTION_STATE_PACKAGING = 3;

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

    // ETH
    public static final String ASSET_ETH = "ether123";


    // tmp 后台更新后删除
    public static final String ASSETS_APH = "0xa0777c3ce2b169d4a23bcba4565e3225a0122d95";
    public static final String ASSETS_AVA = "0xde2ed49b691e76754c20fe619d891b78ef58e537";
    public static final String ASSETS_DBC = "0xb951ecbbc5fe37a9c280a76cb0ce0014827294cf";
    public static final String ASSETS_EXT = "0xe8f98440ad0d7a6e76d84fb1c3d3f8a16e162e97";
    public static final String ASSETS_LRN = "0x06fa8be9b6609d963e8fc63977b9f8dc5f10895f";
    public static final String ASSETS_NKN = "0xc36aee199dbba6c3f439983657558cfb67629599";
    public static final String ASSETS_ONT = "0xceab719b8baa2310f232ee0d277c061704541cfb";
    public static final String ASSETS_PKC = "0xaf7c7328eee5a275a3bcaee2bf0cf662b5e739be";
    public static final String ASSETS_RPX = "0xecc6b20d3ccac1ee9ef109af5a7cdb85706b1df9";
    public static final String ASSETS_SOUL = "0xed07cffad18f1308db51920d99a2af60ac66a7b3";
    public static final String ASSETS_SWTH = "0xab38352559b8b203bde5fddfa0b07d8b2525e132";
    public static final String ASSETS_TKY = "0x132947096727c84c7f9e076c90f08fec3bc17f18";
    public static final String ASSETS_ZPT = "0xac116d4b8d4ca55e6b6d4ecce2192039b51cccc5";


    // asset type
    public static final String ASSET_TYPE_GLOBAL = "GLOBAL";
    public static final String ASSET_TYPE_NEP5 = "NEP5";
    public static final String ASSET_TYPE_UTILITY = "UtilityToken";
    public static final String ASSET_TYPE_GOVERNING = "GoverningToken";

    public static final String SYMBOL_NEO = "NEO";
    public static final String SYMBOL_NEO_GAS = "GAS";
    public static final String SYMBOL_CPX = "CPX";


    // selected tag
    public static final int SELECTED_TAG_MANAGER_WALLET = 0;
    public static final int SELECTED_TAG_TRANSACTION_RECORED = 1;


    // db
    // table wallet
    public static final String TABLE_NEO_WALLET = "neo_wallet";

    public static final String FIELD_ID = "_id";
    public static final String FIELD_WALLET_TYPE = "wallet_type";
    public static final String FIELD_WALLET_NAME = "wallet_name";
    public static final String FIELD_WALLET_ADDRESS = "wallet_address";
    public static final String FIELD_BACKUP_STATE = "backup_state";
    public static final String FIELD_WALLET_KEYSTORE = "wallet_keystore";
    public static final String FIELD_WALLET_ASSET_JSON = "wallet_asset_json";
    public static final String FIELD_WALLET_COLOR_ASSET_JSON = "wallet_color_asset_json";
    public static final String FIELD_CREATE_TIME = "create_time";

    public static final String SQL_CREATE_NEO_WALLET = "create table " + TABLE_NEO_WALLET
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_WALLET_TYPE + " integer, "
            + FIELD_WALLET_NAME + " text, "
            + FIELD_WALLET_ADDRESS + " text, "
            + FIELD_BACKUP_STATE + " integer, "
            + FIELD_WALLET_KEYSTORE + " text, "
            + FIELD_WALLET_ASSET_JSON + " text, "
            + FIELD_WALLET_COLOR_ASSET_JSON + " text, "
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

    // table assets
    public static final String TABLE_ASSETS = "assets";

    public static final String FIELD_ASSET_TYPE = "asset_type";
    public static final String FIELD_ASSET_PRECISION = "asset_precision";
    public static final String FIELD_ASSET_NAME = "asset_name";
    public static final String FIELD_ASSET_IMAGE_URL = "asset_image_url";
    public static final String FIELD_ASSET_HEX_HASH = "asset_hex_hash";
    public static final String FIELD_ASSET_HASH = "asset_hash";

    public static final String SQL_CREATE_ASSETS = "create table " + TABLE_ASSETS
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_ASSET_TYPE + " text, "
            + FIELD_ASSET_SYMBOL + " text, "
            + FIELD_ASSET_PRECISION + " text, "
            + FIELD_ASSET_NAME + " text, "
            + FIELD_ASSET_IMAGE_URL + " text, "
            + FIELD_ASSET_HEX_HASH + " text, "
            + FIELD_ASSET_HASH + " text, "
            + FIELD_CREATE_TIME + " integer)";

    // table portrait
    public static final String TABLE_PORTRAIT = "portrait";

    public static final String FIELD_PORTRAIT_TYPE = "portrait_type";
    public static final String FIELD_PORTRAIT_LABEL = "portrait_label";
    public static final String FIELD_PORTRAIT_VALUE = "portrait_value";

    public static final String SQL_CREATE_PORTRAIT = "create table " + TABLE_PORTRAIT
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_PORTRAIT_TYPE + " text, "
            + FIELD_PORTRAIT_LABEL + " text, "
            + FIELD_PORTRAIT_VALUE + " text, "
            + FIELD_CREATE_TIME + " integer)";

    // ETH
    // table wallet
    public static final String TABLE_ETH_WALLET = "eth_wallet";

    public static final String SQL_CREATE_ETH_WALLET = "create table " + TABLE_ETH_WALLET
            + " (" + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_WALLET_TYPE + " integer, "
            + FIELD_WALLET_NAME + " text, "
            + FIELD_WALLET_ADDRESS + " text, "
            + FIELD_BACKUP_STATE + " integer, "
            + FIELD_WALLET_KEYSTORE + " text, "
            + FIELD_WALLET_ASSET_JSON + " text, "
            + FIELD_WALLET_COLOR_ASSET_JSON + " text, "
            + FIELD_CREATE_TIME + " integer)";


}
