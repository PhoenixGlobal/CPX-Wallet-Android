package chinapex.com.wallet.bean.gasfee;

/**
 * Created by SteelCabbage on 2018/9/17 0017 16:44.
 * E-Mail：liuyi_61@163.com
 */
public class NeoTxFee implements ITxFee {
    private String assetType;
    private String address;
    private String balance;
    private String amount;

    public NeoTxFee() {

    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
