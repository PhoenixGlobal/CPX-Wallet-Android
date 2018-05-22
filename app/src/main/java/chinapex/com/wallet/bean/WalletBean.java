package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WalletBean implements Parcelable {

    private String mWalletName;
    private String mWalletAddr;
    private Double mBalance;

    public WalletBean() {
    }

    public String getWalletName() {
        return mWalletName;
    }

    public void setWalletName(String walletName) {
        mWalletName = walletName;
    }

    public String getWalletAddr() {
        return mWalletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        mWalletAddr = walletAddr;
    }

    public Double getBalance() {
        return mBalance;
    }

    public void setBalance(double balance) {
        mBalance = balance;
    }

    @Override
    public String toString() {
        return "WalletBean{" +
                "mWalletName='" + mWalletName + '\'' +
                ", mWalletAddr='" + mWalletAddr + '\'' +
                ", mBalance='" + mBalance + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mWalletName);
        dest.writeString(this.mWalletAddr);
        dest.writeValue(this.mBalance);
    }

    protected WalletBean(Parcel in) {
        this.mWalletName = in.readString();
        this.mWalletAddr = in.readString();
        this.mBalance = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<WalletBean> CREATOR = new Parcelable
            .Creator<WalletBean>() {
        @Override
        public WalletBean createFromParcel(Parcel source) {
            return new WalletBean(source);
        }

        @Override
        public WalletBean[] newArray(int size) {
            return new WalletBean[size];
        }
    };
}
