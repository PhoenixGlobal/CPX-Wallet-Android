package chinapex.com.wallet.bean.request;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class RequestUtxo {

    /**
     * txid : 0xa7eab2ce9052759b82e4bc3d6dd8b831d2f495d07ae8c821becae99d3995813b
     * block : 1469183
     * vout : {"Address":"AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6",
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "Value":"4.999","N":1}
     * spentBlock : -1
     * spentTime :
     * createTime : 2018-05-16T18:05:22Z
     * gas :
     */

    private String txid;
    private int block;
    private VoutBean vout;
    private int spentBlock;
    private String spentTime;
    private String createTime;
    private String gas;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public VoutBean getVout() {
        return vout;
    }

    public void setVout(VoutBean vout) {
        this.vout = vout;
    }

    public int getSpentBlock() {
        return spentBlock;
    }

    public void setSpentBlock(int spentBlock) {
        this.spentBlock = spentBlock;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public static class VoutBean {
        /**
         * Address : AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6
         * Asset : 0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7
         * Value : 4.999
         * N : 1
         */

        private String Address;
        private String Asset;
        private String Value;
        private int N;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getAsset() {
            return Asset;
        }

        public void setAsset(String Asset) {
            this.Asset = Asset;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public int getN() {
            return N;
        }

        public void setN(int N) {
            this.N = N;
        }
    }

    @Override
    public String toString() {
        return "RequestUtxo{" +
                "txid='" + txid + '\'' +
                ", block=" + block +
                ", vout=" + vout +
                ", spentBlock=" + spentBlock +
                ", spentTime='" + spentTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", gas='" + gas + '\'' +
                '}';
    }
}
