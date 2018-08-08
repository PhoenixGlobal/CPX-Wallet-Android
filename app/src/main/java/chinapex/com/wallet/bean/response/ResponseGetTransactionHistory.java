package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/6/22 0022 11:37.
 * E-Mail：liuyi_61@163.com
 */

public class ResponseGetTransactionHistory {

    /**
     * state : 200
     * result : [{"vmstate":null,"value":"+1000","type":"NEO",
     * "txid":"0xee85d489e4428a538f39c1903771e1f222a383f8327c96ed19cc02079149a1fd",
     * "to":"Ae2d6qj91YL3LVUMkza7WQsaTYjzjHm4z1","time":1476724549,"symbol":"todo_symbol",
     * "imageURL":"todo_imageURL","gas_consumed":null,
     * "from":"AQVh2pG732YvtNaxEGkQUei3YA4cvo7d2i","decimal":null,
     * "assetId":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b"}]
     */

    private int state;
    private List<ResultBean> result;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * vmstate : null
         * value : +1000
         * type : NEO
         * txid : 0xee85d489e4428a538f39c1903771e1f222a383f8327c96ed19cc02079149a1fd
         * to : Ae2d6qj91YL3LVUMkza7WQsaTYjzjHm4z1
         * time : 1476724549
         * symbol : todo_symbol
         * imageURL : todo_imageURL
         * gas_consumed : null
         * from : AQVh2pG732YvtNaxEGkQUei3YA4cvo7d2i
         * decimal : null
         * assetId : 0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b
         */

        private Object vmstate;
        private String value;
        private String type;
        private String txid;
        private String to;
        private long time;
        private String symbol;
        private String imageURL;
        private Object gas_consumed;
        private String from;
        private Object decimal;
        private String assetId;

        public Object getVmstate() {
            return vmstate;
        }

        public void setVmstate(Object vmstate) {
            this.vmstate = vmstate;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public Object getGas_consumed() {
            return gas_consumed;
        }

        public void setGas_consumed(Object gas_consumed) {
            this.gas_consumed = gas_consumed;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public Object getDecimal() {
            return decimal;
        }

        public void setDecimal(Object decimal) {
            this.decimal = decimal;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }
    }
}
