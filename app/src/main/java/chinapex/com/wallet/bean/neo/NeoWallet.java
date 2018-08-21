package chinapex.com.wallet.bean.neo;

import android.os.Parcel;

import chinapex.com.wallet.bean.WalletBean;

public class NeoWallet extends WalletBean {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public NeoWallet() {
    }

    protected NeoWallet(Parcel in) {
        super(in);
    }

    public static final Creator<NeoWallet> CREATOR = new Creator<NeoWallet>() {
        @Override
        public NeoWallet createFromParcel(Parcel source) {
            return new NeoWallet(source);
        }

        @Override
        public NeoWallet[] newArray(int size) {
            return new NeoWallet[size];
        }
    };
}
