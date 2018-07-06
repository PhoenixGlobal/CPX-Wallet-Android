package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/6/27 0027 14:56.
 * E-Mail：liuyi_61@163.com
 */

public class Nep5TxBean {
    private String assetID;
    private int assetDecimal;
    private String addrFrom;
    private String addrTo;
    private String transferAmount;
    private String utxos;

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public int getAssetDecimal() {
        return assetDecimal;
    }

    public void setAssetDecimal(int assetDecimal) {
        this.assetDecimal = assetDecimal;
    }

    public String getAddrFrom() {
        return addrFrom;
    }

    public void setAddrFrom(String addrFrom) {
        this.addrFrom = addrFrom;
    }

    public String getAddrTo() {
        return addrTo;
    }

    public void setAddrTo(String addrTo) {
        this.addrTo = addrTo;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getUtxos() {
        return utxos;
    }

    public void setUtxos(String utxos) {
        this.utxos = utxos;
    }
}
