package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/30 0030.
 */

public class ResponseGetNeoUtxos {


    /**
     * status : 200
     * msg :
     * data : [{"txid":"0x260561a23d336f7af69354e544414c2be3f8feab28b54b3fff2ccd84c864fad6","block":2691086,
     * "vout":{"Address":"AQjxjP5EdsY46Degf39Zd3F8P388CmynZX",
     * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7","Value":"0.00211306","N":0},
     * "spentBlock":-1,"spentTime":"","createTime":"1536039597","gas":""}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * txid : 0x260561a23d336f7af69354e544414c2be3f8feab28b54b3fff2ccd84c864fad6
         * block : 2691086
         * vout : {"Address":"AQjxjP5EdsY46Degf39Zd3F8P388CmynZX",
         * "Asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7","Value":"0.00211306","N":0}
         * spentBlock : -1
         * spentTime :
         * createTime : 1536039597
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
             * Address : AQjxjP5EdsY46Degf39Zd3F8P388CmynZX
             * Asset : 0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7
             * Value : 0.00211306
             * N : 0
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
    }
}
