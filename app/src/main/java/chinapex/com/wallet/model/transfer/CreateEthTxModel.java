package chinapex.com.wallet.model.transfer;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.Map;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.BalanceBean;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.gasfee.EthTxFee;
import chinapex.com.wallet.bean.gasfee.ITxFee;
import chinapex.com.wallet.bean.tx.EthTxBean;
import chinapex.com.wallet.bean.tx.ITxBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.eth.ICreateErc20TxCallback;
import chinapex.com.wallet.executor.callback.eth.ICreateEthTxCallback;
import chinapex.com.wallet.executor.callback.eth.IEthSendRawTransactionCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthBalanceCallback;
import chinapex.com.wallet.executor.callback.eth.IGetEthNonceCallback;
import chinapex.com.wallet.executor.runnable.eth.CreateErc20Tx;
import chinapex.com.wallet.executor.runnable.eth.CreateEthTx;
import chinapex.com.wallet.executor.runnable.eth.EthSendRawTransaction;
import chinapex.com.wallet.executor.runnable.eth.GetEthBalance;
import chinapex.com.wallet.executor.runnable.eth.GetEthNonce;
import chinapex.com.wallet.global.ApexGlobalTask;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.SharedPreferencesUtils;
import chinapex.com.wallet.utils.WalletUtils;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:56.
 * E-Mail：liuyi_61@163.com
 */
public class CreateEthTxModel implements ICreateTxModel, ICreateEthTxCallback, IEthSendRawTransactionCallback,
        IGetEthNonceCallback, ICreateErc20TxCallback, IGetEthBalanceCallback {

    private static final String TAG = CreateEthTxModel.class.getSimpleName();

    private ICreateTxModelCallback mICreateTxModelCallback;
    private EthTxFee mEthTxFee;
    private EthTxBean mEthTxBean;

    public CreateEthTxModel(ICreateTxModelCallback ICreateTxModelCallback) {
        mICreateTxModelCallback = ICreateTxModelCallback;
    }

    @Override
    public void checkTxFee(ITxFee iTxFee) {
        if (null == mICreateTxModelCallback) {
            CpLog.e(TAG, "mICreateTxModelCallback is null!");
            return;
        }

        if (null == iTxFee) {
            CpLog.e(TAG, "iTxFee is null!");
            return;
        }

        if (iTxFee instanceof EthTxFee) {
            mEthTxFee = (EthTxFee) iTxFee;
        }

        if (null == mEthTxFee) {
            CpLog.e(TAG, "mEthTxFee is null!");
            return;
        }

        CpLog.i(TAG, "mEthTxFee:" + mEthTxFee.toString());

        TaskController.getInstance().submit(new GetEthBalance(mEthTxFee.getAddress(), this));
    }

    @Override
    public void getEthBalance(Map<String, BalanceBean> balanceBeans) {
        if (null == balanceBeans || balanceBeans.isEmpty()) {
            CpLog.e(TAG, "balanceBeans is null or empty!");
            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .insufficient_balance));
            return;
        }

        BalanceBean balanceBean = balanceBeans.get(Constant.ASSETS_ETH);
        if (null == balanceBean) {
            CpLog.e(TAG, "balanceBean is null!");
            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .insufficient_balance));
            return;
        }

        String assetType = mEthTxFee.getAssetType();
        if (TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "assetType is null or empty!");
            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .no_this_type_asset));
            return;
        }

        switch (assetType) {
            case Constant.ASSET_TYPE_ETH:
                checkEthIsEnough();
                break;
            case Constant.ASSET_TYPE_ERC20:
                checkErc20IsEnough(balanceBean.getAssetsValue());
                break;
            default:
                mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                        .no_this_type_asset));
                break;
        }
    }

    private void checkEthIsEnough() {
        try {
            BigDecimal balance = new BigDecimal(mEthTxFee.getBalance());
            BigDecimal amount = new BigDecimal(mEthTxFee.getAmount());
            BigDecimal gasPrice = new BigDecimal(mEthTxFee.getGasPrice()).divide(new BigDecimal(10).pow(9));
            BigDecimal gasLimit = new BigDecimal(mEthTxFee.getGasLimit());

            BigDecimal bigDecimal = gasPrice.multiply(gasLimit).add(amount).subtract(balance);

            int compareTo = BigDecimal.ZERO.compareTo(bigDecimal);
            if (compareTo == 1 || compareTo == 0) {
                mICreateTxModelCallback.checkTxFee(true, null);
                return;
            }

            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .insufficient_balance));
        } catch (Exception e) {
            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .illegal_input));
            CpLog.e(TAG, "checkTxFee Exception:" + e.getMessage());
        }
    }

    private void checkErc20IsEnough(String ethBalance) {
        try {
            BigDecimal ethBalanceFee = new BigDecimal(ethBalance);
            BigDecimal balance = new BigDecimal(mEthTxFee.getBalance());
            BigDecimal amount = new BigDecimal(mEthTxFee.getAmount());
            BigDecimal gasPrice = new BigDecimal(mEthTxFee.getGasPrice()).divide(new BigDecimal(10).pow(9));
            BigDecimal gasLimit = new BigDecimal(mEthTxFee.getGasLimit());

            if (amount.compareTo(balance) == 1) {
                mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance()
                        .getResources().getString(R.string.insufficient_balance));
                return;
            }

            BigDecimal gasFee = gasPrice.multiply(gasLimit);

            if (gasFee.compareTo(ethBalanceFee) == 1) {
                mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance()
                        .getResources().getString(R.string.insufficient_gas));
                return;
            }

            mICreateTxModelCallback.checkTxFee(true, null);
        } catch (Exception e) {
            mICreateTxModelCallback.checkTxFee(false, ApexWalletApplication.getInstance().getResources().getString(R.string
                    .illegal_input));
            CpLog.e(TAG, "checkTxFee Exception:" + e.getMessage());
        }
    }

    @Override
    public void createGlobalTx(ITxBean iTxBean) {
        if (null == mICreateTxModelCallback) {
            CpLog.e(TAG, "mICreateTxModelCallback is null!");
            return;
        }

        if (null == iTxBean) {
            CpLog.e(TAG, "iTxBean is null!");
            return;
        }

        if (iTxBean instanceof EthTxBean) {
            mEthTxBean = (EthTxBean) iTxBean;
        }

        if (null == mEthTxBean) {
            CpLog.e(TAG, "mEthTxBean is null!");
            return;
        }

        TaskController.getInstance().submit(new GetEthNonce(mEthTxBean.getFromAddress(), this));
    }

    @Override
    public void createColorTx(ITxBean iTxBean) {
        if (null == mICreateTxModelCallback) {
            CpLog.e(TAG, "mICreateTxModelCallback is null!");
            return;
        }

        if (null == iTxBean) {
            CpLog.e(TAG, "iTxBean is null!");
            return;
        }

        if (iTxBean instanceof EthTxBean) {
            mEthTxBean = (EthTxBean) iTxBean;
        }

        if (null == mEthTxBean) {
            CpLog.e(TAG, "mEthTxBean is null!");
            return;
        }

        TaskController.getInstance().submit(new GetEthNonce(mEthTxBean.getFromAddress(), this));
    }

    @Override
    public void getEthNonce(String nonce) {
        if (TextUtils.isEmpty(nonce)) {
            CpLog.e(TAG, "nonce is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .eth_nonce_null), false);
            return;
        }

        mEthTxBean.setNonce(nonce);

        String assetType = mEthTxBean.getAssetType();
        if (TextUtils.isEmpty(assetType)) {
            CpLog.e(TAG, "assetType is null!");
            return;
        }

        switch (assetType) {
            case Constant.ASSET_TYPE_ETH:
                TaskController.getInstance().submit(new CreateEthTx(mEthTxBean, this));
                break;
            case Constant.ASSET_TYPE_ERC20:
                TaskController.getInstance().submit(new CreateErc20Tx(mEthTxBean, this));
                break;
            default:
                CpLog.w(TAG, "illegal asset");
                break;
        }
    }

    @Override
    public void createEthTx(String data) {
        if (TextUtils.isEmpty(data)) {
            CpLog.e(TAG, "createEthTx() -> data is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .eth_data_null), false);
            return;
        }

        CpLog.i(TAG, "createEthTx() -> data:" + data);
        TaskController.getInstance().submit(new EthSendRawTransaction(data, this));
    }

    @Override
    public void createErc20Tx(String data) {
        if (TextUtils.isEmpty(data)) {
            CpLog.e(TAG, "createErc20Tx() -> data is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .eth_data_null), false);
            return;
        }

        CpLog.i(TAG, "createErc20Tx() -> data:" + data);
        TaskController.getInstance().submit(new EthSendRawTransaction(data, this));
    }

    @Override
    public void ethSendRawTransaction(Boolean isSendSuccess, String txId) {
        if (TextUtils.isEmpty(txId)) {
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .server_err_txId_null), false);
            return;
        }

        // write db
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .db_exception), true);
            return;
        }

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setWalletAddress(mEthTxBean.getFromAddress());
        transactionRecord.setTxType(mEthTxBean.getAssetType());
        transactionRecord.setTxID(txId);
        String amountDec = WalletUtils.toDecString(mEthTxBean.getAmount(), String.valueOf(mEthTxBean.getAssetDecimal()));
        transactionRecord.setTxAmount("-" + amountDec);
        transactionRecord.setTxFrom(mEthTxBean.getFromAddress());
        transactionRecord.setTxTo(mEthTxBean.getToAddress());
        transactionRecord.setTxTime(0);

        AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_ETH_ASSETS, mEthTxBean.getAssetID());
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        transactionRecord.setAssetID(mEthTxBean.getAssetID());
        transactionRecord.setAssetLogoUrl(assetBean.getImageUrl());
        transactionRecord.setAssetSymbol(assetBean.getSymbol());

        if (isSendSuccess) {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_PACKAGING);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_ETH_TX_CACHE, transactionRecord);
        } else {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_FAIL);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_ETH_TRANSACTION_RECORD, transactionRecord);
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_broadcast_failed), true);
        }

        // start polling
        SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), txId, System.currentTimeMillis());
        SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), Constant.TX_ETH_NONCE + txId,
                mEthTxBean.getNonce());
        ApexGlobalTask.getInstance().startEthPolling(txId, mEthTxBean.getFromAddress());
        mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                .transaction_broadcast_successful), true);
    }

}
