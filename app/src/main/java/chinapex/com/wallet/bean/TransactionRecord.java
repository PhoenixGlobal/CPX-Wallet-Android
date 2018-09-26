package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionRecord implements Parcelable {

    private String walletAddress;
    private String txType;
    private String txID;
    private String txAmount;
    private int txState;
    private String txFrom;
    private String txTo;
    private String gasConsumed;
    private String assetID;
    private String assetSymbol;
    private String assetLogoUrl;
    private int assetDecimal;
    private String gasPrice;
    private String blockNumber;
    private String gasFee;
    private long txTime;

    public TransactionRecord() {

    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getTxID() {
        return txID;
    }

    public void setTxID(String txID) {
        this.txID = txID;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public int getTxState() {
        return txState;
    }

    public void setTxState(int txState) {
        this.txState = txState;
    }

    public String getTxFrom() {
        return txFrom;
    }

    public void setTxFrom(String txFrom) {
        this.txFrom = txFrom;
    }

    public String getTxTo() {
        return txTo;
    }

    public void setTxTo(String txTo) {
        this.txTo = txTo;
    }

    public String getGasConsumed() {
        return gasConsumed;
    }

    public void setGasConsumed(String gasConsumed) {
        this.gasConsumed = gasConsumed;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public String getAssetLogoUrl() {
        return assetLogoUrl;
    }

    public void setAssetLogoUrl(String assetLogoUrl) {
        this.assetLogoUrl = assetLogoUrl;
    }

    public int getAssetDecimal() {
        return assetDecimal;
    }

    public void setAssetDecimal(int assetDecimal) {
        this.assetDecimal = assetDecimal;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getGasFee() {
        return gasFee;
    }

    public void setGasFee(String gasFee) {
        this.gasFee = gasFee;
    }

    public long getTxTime() {
        return txTime;
    }

    public void setTxTime(long txTime) {
        this.txTime = txTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionRecord that = (TransactionRecord) o;

        if (!txID.equals(that.txID)) return false;
        if (!txFrom.equals(that.txFrom)) return false;
        return txTo.equals(that.txTo);
    }

    @Override
    public int hashCode() {
        int result = txID.hashCode();
        result = 31 * result + txFrom.hashCode();
        result = 31 * result + txTo.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.walletAddress);
        dest.writeString(this.txType);
        dest.writeString(this.txID);
        dest.writeString(this.txAmount);
        dest.writeInt(this.txState);
        dest.writeString(this.txFrom);
        dest.writeString(this.txTo);
        dest.writeString(this.gasConsumed);
        dest.writeString(this.assetID);
        dest.writeString(this.assetSymbol);
        dest.writeString(this.assetLogoUrl);
        dest.writeInt(this.assetDecimal);
        dest.writeString(this.gasPrice);
        dest.writeString(this.blockNumber);
        dest.writeString(this.gasFee);
        dest.writeLong(this.txTime);
    }

    protected TransactionRecord(Parcel in) {
        this.walletAddress = in.readString();
        this.txType = in.readString();
        this.txID = in.readString();
        this.txAmount = in.readString();
        this.txState = in.readInt();
        this.txFrom = in.readString();
        this.txTo = in.readString();
        this.gasConsumed = in.readString();
        this.assetID = in.readString();
        this.assetSymbol = in.readString();
        this.assetLogoUrl = in.readString();
        this.assetDecimal = in.readInt();
        this.gasPrice = in.readString();
        this.blockNumber = in.readString();
        this.gasFee = in.readString();
        this.txTime = in.readLong();
    }

    public static final Creator<TransactionRecord> CREATOR = new Creator<TransactionRecord>() {
        @Override
        public TransactionRecord createFromParcel(Parcel source) {
            return new TransactionRecord(source);
        }

        @Override
        public TransactionRecord[] newArray(int size) {
            return new TransactionRecord[size];
        }
    };
}
