package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SteelCabbage on 2018/6/7 0007.
 */

public class BalanceBean implements Parcelable {
    // 映射状态值
    private int mapState;
    // 当前资产
    private String assetsID;
    private String assetSymbol;
    private String assetType;
    private int assetDecimal;
    private String assetsValue;

    public BalanceBean() {
    }

    public int getMapState() {
        return mapState;
    }

    public void setMapState(int mapState) {
        this.mapState = mapState;
    }

    public String getAssetsID() {
        return assetsID;
    }

    public void setAssetsID(String assetsID) {
        this.assetsID = assetsID;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public int getAssetDecimal() {
        return assetDecimal;
    }

    public void setAssetDecimal(int assetDecimal) {
        this.assetDecimal = assetDecimal;
    }

    public String getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(String assetsValue) {
        this.assetsValue = assetsValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mapState);
        dest.writeString(this.assetsID);
        dest.writeString(this.assetSymbol);
        dest.writeString(this.assetType);
        dest.writeInt(this.assetDecimal);
        dest.writeString(this.assetsValue);
    }

    protected BalanceBean(Parcel in) {
        this.mapState = in.readInt();
        this.assetsID = in.readString();
        this.assetSymbol = in.readString();
        this.assetType = in.readString();
        this.assetDecimal = in.readInt();
        this.assetsValue = in.readString();
    }

    public static final Creator<BalanceBean> CREATOR = new Creator<BalanceBean>() {
        @Override
        public BalanceBean createFromParcel(Parcel source) {
            return new BalanceBean(source);
        }

        @Override
        public BalanceBean[] newArray(int size) {
            return new BalanceBean[size];
        }
    };
}
