package chinapex.com.wallet.bean.eth;

import android.os.Parcel;

import chinapex.com.wallet.bean.WalletBean;

/**
 * Created by SteelCabbage on 2018/8/13 0013 18:05.
 * E-Mail：liuyi_61@163.com
 */

public class EthWallet extends WalletBean {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public EthWallet() {
    }

    protected EthWallet(Parcel in) {
        super(in);
    }

    public static final Creator<EthWallet> CREATOR = new Creator<EthWallet>() {
        @Override
        public EthWallet createFromParcel(Parcel source) {
            return new EthWallet(source);
        }

        @Override
        public EthWallet[] newArray(int size) {
            return new EthWallet[size];
        }
    };
}
