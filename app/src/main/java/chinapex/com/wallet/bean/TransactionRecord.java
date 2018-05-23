package chinapex.com.wallet.bean;

public class TransactionRecord {
    private String walletName;
    private double transactionAmount;
    private long transactionTime;
    private String txid;

    public TransactionRecord() {

    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "walletName='" + walletName + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionTime=" + transactionTime +
                ", txid='" + txid + '\'' +
                '}';
    }
}
