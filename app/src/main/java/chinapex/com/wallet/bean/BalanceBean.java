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
    private String assetsValue;
    // 人民币
    private String cny;
    private String cnyValue;
    // 美元
    private String usd;
    private String usdValue;
    //涨跌幅比率
    private int advanceDecline;

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

    public String getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(String assetsValue) {
        this.assetsValue = assetsValue;
    }

    public String getCny() {
        return cny;
    }

    public void setCny(String cny) {
        this.cny = cny;
    }

    public String getCnyValue() {
        return cnyValue;
    }

    public void setCnyValue(String cnyValue) {
        this.cnyValue = cnyValue;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(String usdValue) {
        this.usdValue = usdValue;
    }

    public int getAdvanceDecline() {
        return advanceDecline;
    }

    public void setAdvanceDecline(int advanceDecline) {
        this.advanceDecline = advanceDecline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mapState);
        dest.writeString(this.assetsID);
        dest.writeString(this.assetsValue);
        dest.writeString(this.cny);
        dest.writeString(this.cnyValue);
        dest.writeString(this.usd);
        dest.writeString(this.usdValue);
        dest.writeInt(this.advanceDecline);
    }

    public BalanceBean() {
    }

    protected BalanceBean(Parcel in) {
        this.mapState = in.readInt();
        this.assetsID = in.readString();
        this.assetsValue = in.readString();
        this.cny = in.readString();
        this.cnyValue = in.readString();
        this.usd = in.readString();
        this.usdValue = in.readString();
        this.advanceDecline = in.readInt();
    }

    public static final Parcelable.Creator<BalanceBean> CREATOR = new Parcelable
            .Creator<BalanceBean>() {
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
