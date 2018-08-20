package chinapex.com.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SteelCabbage on 2018/8/15 0015 10:32.
 * E-Mail：liuyi_61@163.com
 */

public  class WalletBean implements Parcelable {
    private String name;
    private String address;
    private int backupState;
    private String keyStore;
    private String assetJson;
    private String colorAssetJson;
    private boolean isSelected;
    private int selectedTag;

    public WalletBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAssetJson() {
        return assetJson;
    }

    public void setAssetJson(String assetJson) {
        this.assetJson = assetJson;
    }

    public String getColorAssetJson() {
        return colorAssetJson;
    }

    public void setColorAssetJson(String colorAssetJson) {
        this.colorAssetJson = colorAssetJson;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(int selectedTag) {
        this.selectedTag = selectedTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletBean that = (WalletBean) o;

        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeInt(this.backupState);
        dest.writeString(this.keyStore);
        dest.writeString(this.assetJson);
        dest.writeString(this.colorAssetJson);
    }

    protected WalletBean(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.backupState = in.readInt();
        this.keyStore = in.readString();
        this.assetJson = in.readString();
        this.colorAssetJson = in.readString();
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
