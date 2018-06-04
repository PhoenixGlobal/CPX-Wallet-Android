package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WalletBean implements Parcelable {

    private String mWalletName;
    private String mWalletAddr;
    private int backupState;
    private String keyStore;
    private Double mBalance;
    private boolean isSelected;

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

    public int getBackupState() {
        return backupState;
    }

    public void setBackupState(int backupState) {
        this.backupState = backupState;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public Double getBalance() {
        return mBalance;
    }

    public void setBalance(Double balance) {
        mBalance = balance;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mWalletName);
        dest.writeString(this.mWalletAddr);
        dest.writeInt(this.backupState);
        dest.writeString(this.keyStore);
        dest.writeValue(this.mBalance);
    }

    protected WalletBean(Parcel in) {
        this.mWalletName = in.readString();
        this.mWalletAddr = in.readString();
        this.backupState = in.readInt();
        this.keyStore = in.readString();
        this.mBalance = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<WalletBean> CREATOR = new Creator<WalletBean>() {
        @Override
        public WalletBean createFromParcel(Parcel source) {
            return new WalletBean(source);
        }

        @Override
        public WalletBean[] newArray(int size) {
            return new WalletBean[size];
        }
    };

    @Override
    public String toString() {
        return "WalletBean{" +
                "mWalletName='" + mWalletName + '\'' +
                ", mWalletAddr='" + mWalletAddr + '\'' +
                ", backupState=" + backupState +
                ", keyStore='" + keyStore + '\'' +
                ", mBalance=" + mBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletBean that = (WalletBean) o;

        if (!mWalletName.equals(that.mWalletName)) return false;
        return mWalletAddr.equals(that.mWalletAddr);
    }

    @Override
    public int hashCode() {
        int result = mWalletName.hashCode();
        result = 31 * result + mWalletAddr.hashCode();
        return result;
    }
}
