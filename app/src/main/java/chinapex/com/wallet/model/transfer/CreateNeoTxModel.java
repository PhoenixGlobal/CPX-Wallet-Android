package chinapex.com.wallet.model.transfer;

import android.text.TextUtils;

import chinapex.com.wallet.R;
import chinapex.com.wallet.bean.AssertTxBean;
import chinapex.com.wallet.bean.AssetBean;
import chinapex.com.wallet.bean.Nep5TxBean;
import chinapex.com.wallet.bean.TransactionRecord;
import chinapex.com.wallet.bean.tx.ITxBean;
import chinapex.com.wallet.bean.tx.NeoTxBean;
import chinapex.com.wallet.executor.TaskController;
import chinapex.com.wallet.executor.callback.ICreateAssertTxCallback;
import chinapex.com.wallet.executor.callback.ICreateNep5TxCallback;
import chinapex.com.wallet.executor.callback.IGetUtxosCallback;
import chinapex.com.wallet.executor.callback.ISendRawTransactionCallback;
import chinapex.com.wallet.executor.runnable.CreateAssertTx;
import chinapex.com.wallet.executor.runnable.CreateNep5Tx;
import chinapex.com.wallet.executor.runnable.GetUtxos;
import chinapex.com.wallet.executor.runnable.SendRawTransaction;
import chinapex.com.wallet.global.ApexGlobalTask;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.model.ApexWalletDbDao;
import chinapex.com.wallet.utils.CpLog;
import neomobile.Tx;
import neomobile.Wallet;

/**
 * Created by SteelCabbage on 2018/8/24 0024 15:48.
 * E-Mail：liuyi_61@163.com
 */
public class CreateNeoTxModel implements ICreateTxModel, IGetUtxosCallback, ICreateAssertTxCallback,
        ISendRawTransactionCallback, ICreateNep5TxCallback {

    private static final String TAG = CreateNeoTxModel.class.getSimpleName();

    private ICreateTxModelCallback mICreateTxModelCallback;
    private NeoTxBean mNeoTxBean;
    private String mOrder;

    public CreateNeoTxModel(ICreateTxModelCallback ICreateTxModelCallback) {
        mICreateTxModelCallback = ICreateTxModelCallback;
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

        if (iTxBean instanceof NeoTxBean) {
            mNeoTxBean = (NeoTxBean) iTxBean;
        }

        if (null == mNeoTxBean) {
            CpLog.e(TAG, "mNeoTxBean is null!");
            return;
        }

        Wallet wallet = mNeoTxBean.getWallet();
        if (null == wallet) {
            CpLog.e(TAG, "neo wallet is null!");
            return;
        }

        TaskController.getInstance().submit(new GetUtxos(wallet.address(), this));
    }

    @Override
    public void getUtxos(String utxos) {
        if (TextUtils.isEmpty(utxos) || "[]".equals(utxos)) {
            CpLog.e(TAG, "utxos is null or []!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .generate_utxo_failed), false);
            return;
        }

        AssertTxBean assertTxBean = new AssertTxBean();
        assertTxBean.setAssetsID(mNeoTxBean.getAssetID());
        assertTxBean.setAddrFrom(mNeoTxBean.getFromAddress());
        assertTxBean.setAddrTo(mNeoTxBean.getToAddress());
        assertTxBean.setTransferAmount(Double.valueOf(mNeoTxBean.getAmount()));
        assertTxBean.setUtxos(utxos);

        TaskController.getInstance().submit(new CreateAssertTx(mNeoTxBean.getWallet(), assertTxBean, this));
    }

    @Override
    public void createAssertTx(Tx tx) {
        if (null == tx) {
            CpLog.e(TAG, "createAssertTx() -> tx is null！");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_creation_failed), false);
            return;
        }

        mOrder = "0x" + tx.getID();
        CpLog.i(TAG, "createAssertTx order:" + mOrder);
        TaskController.getInstance().submit(new SendRawTransaction(tx.getData(), this));
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

        if (iTxBean instanceof NeoTxBean) {
            mNeoTxBean = (NeoTxBean) iTxBean;
        }

        if (null == mNeoTxBean) {
            CpLog.e(TAG, "mNeoTxBean is null!");
            return;
        }

        Nep5TxBean nep5TxBean = new Nep5TxBean();
        nep5TxBean.setAssetID(mNeoTxBean.getAssetID());
        nep5TxBean.setAssetDecimal(mNeoTxBean.getAssetDecimal());
        nep5TxBean.setAddrFrom(mNeoTxBean.getFromAddress());
        nep5TxBean.setAddrTo(mNeoTxBean.getToAddress());
        nep5TxBean.setTransferAmount(mNeoTxBean.getAmount());
        nep5TxBean.setUtxos("[]");

        TaskController.getInstance().submit(new CreateNep5Tx(mNeoTxBean.getWallet(), nep5TxBean, this));
    }

    @Override
    public void createNep5Tx(Tx tx) {
        if (null == tx) {
            CpLog.e(TAG, "createNep5Tx() -> tx is null！");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_creation_failed), false);
            return;
        }

        mOrder = "0x" + tx.getID();
        CpLog.i(TAG, "createNep5Tx order:" + mOrder);

        TaskController.getInstance().submit(new SendRawTransaction(tx.getData(), this));
    }

    @Override
    public void sendTxData(Boolean isSuccess) {
        // write db
        ApexWalletDbDao apexWalletDbDao = ApexWalletDbDao.getInstance(ApexWalletApplication.getInstance());
        if (null == apexWalletDbDao) {
            CpLog.e(TAG, "apexWalletDbDao is null!");
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .db_exception), true);
            return;
        }

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setWalletAddress(mNeoTxBean.getFromAddress());
        transactionRecord.setTxAmount("-" + mNeoTxBean.getAmount());
        transactionRecord.setTxFrom(mNeoTxBean.getFromAddress());
        transactionRecord.setTxTo(mNeoTxBean.getToAddress());
        transactionRecord.setTxTime(0);
        transactionRecord.setTxID(mOrder);

        AssetBean assetBean = apexWalletDbDao.queryAssetByHash(Constant.TABLE_NEO_ASSETS, mNeoTxBean.getAssetID());
        if (null == assetBean) {
            CpLog.e(TAG, "assetBean is null!");
            return;
        }

        transactionRecord.setAssetID(mNeoTxBean.getAssetID());
        transactionRecord.setAssetLogoUrl(assetBean.getImageUrl());
        transactionRecord.setAssetSymbol(assetBean.getSymbol());

        if (isSuccess) {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_PACKAGING);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_TX_CACHE, transactionRecord);
        } else {
            transactionRecord.setTxState(Constant.TRANSACTION_STATE_FAIL);
            apexWalletDbDao.insertTxRecord(Constant.TABLE_TRANSACTION_RECORD, transactionRecord);
        }

        // start polling
        ApexGlobalTask.getInstance().startPolling(mOrder, mNeoTxBean.getFromAddress());

        // prompt the user
        if (isSuccess) {
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_broadcast_successful), true);
        } else {
            mICreateTxModelCallback.CreateTxModel(ApexWalletApplication.getInstance().getResources().getString(R.string
                    .transaction_broadcast_failed), true);
        }
    }

}
