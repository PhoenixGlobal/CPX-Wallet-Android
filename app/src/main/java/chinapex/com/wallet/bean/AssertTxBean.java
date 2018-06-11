package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/6/8 01:14
 * E-Mail：liuyi_61@163.com
 */
public class AssertTxBean {
    private String assetsID;
    private String addrFrom;
    private String addrTo;
    private double transferAmount;
    private String utxos;

    public String getAssetsID() {
        return assetsID;
    }

    public void setAssetsID(String assetsID) {
        this.assetsID = assetsID;
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

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getUtxos() {
        return utxos;
    }

    public void setUtxos(String utxos) {
        this.utxos = utxos;
    }
}
