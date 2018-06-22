package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionRecord implements Parcelable {
    private String logoUrl;
    private String txID;
    private String txAmount;
    private long time;
    private int txState;
    private String symbol;
    private String from;
    private String to;

    public TransactionRecord() {

    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTxState() {
        return txState;
    }

    public void setTxState(int txState) {
        this.txState = txState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.logoUrl);
        dest.writeString(this.txID);
        dest.writeString(this.txAmount);
        dest.writeLong(this.time);
        dest.writeInt(this.txState);
        dest.writeString(this.symbol);
        dest.writeString(this.from);
        dest.writeString(this.to);
    }

    protected TransactionRecord(Parcel in) {
        this.logoUrl = in.readString();
        this.txID = in.readString();
        this.txAmount = in.readString();
        this.time = in.readLong();
        this.txState = in.readInt();
        this.symbol = in.readString();
        this.from = in.readString();
        this.to = in.readString();
    }

    public static final Parcelable.Creator<TransactionRecord> CREATOR = new Parcelable
            .Creator<TransactionRecord>() {
        @Override
        public TransactionRecord createFromParcel(Parcel source) {
            return new TransactionRecord(source);
        }

        @Override
        public TransactionRecord[] newArray(int size) {
            return new TransactionRecord[size];
        }
    };

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "logoUrl='" + logoUrl + '\'' +
                ", txID='" + txID + '\'' +
                ", txAmount='" + txAmount + '\'' +
                ", time=" + time +
                ", txState=" + txState +
                ", symbol='" + symbol + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
