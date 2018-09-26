package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/9/19 0019 15:30.
 * E-Mail：liuyi_61@163.com
 */
public class ResponseGetEthTransactionHistory {


    /**
     * status : 200
     * msg : eth transaction by address
     * data : [{"txid":"0x55ec1d3098f4c445393b4a79c1cfd617e0e402478d89b9cb6f540e43a85e3457","type":"Eth",
     * "assetId":"0xeth99999999","time":1534835307,"from":"0xeeba879b9a962596624cb9c89145f24d7d0e710e",
     * "to":"0x77ebad3064c5ff81bc63632ea10a8a0ba4382cf5","value":"+0.0004","rawValue":"400000000000000","vmstate":"0",
     * "symbol":"ETH","imageURL":"todo_imageURL","decimal":"18","gas_consumed":"21000","gas_price":"2304142222",
     * "block_number":"6185997","gas_fee":"0.000048384"}]
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
         * txid : 0x55ec1d3098f4c445393b4a79c1cfd617e0e402478d89b9cb6f540e43a85e3457
         * type : Eth
         * assetId : 0xeth99999999
         * time : 1534835307
         * from : 0xeeba879b9a962596624cb9c89145f24d7d0e710e
         * to : 0x77ebad3064c5ff81bc63632ea10a8a0ba4382cf5
         * value : +0.0004
         * rawValue : 400000000000000
         * vmstate : 0
         * symbol : ETH
         * imageURL : todo_imageURL
         * decimal : 18
         * gas_consumed : 21000
         * gas_price : 2304142222
         * block_number : 6185997
         * gas_fee : 0.000048384
         */

        private String txid;
        private String type;
        private String assetId;
        private int time;
        private String from;
        private String to;
        private String value;
        private String rawValue;
        private String vmstate;
        private String symbol;
        private String imageURL;
        private String decimal;
        private String gas_consumed;
        private String gas_price;
        private String block_number;
        private String gas_fee;

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRawValue() {
            return rawValue;
        }

        public void setRawValue(String rawValue) {
            this.rawValue = rawValue;
        }

        public String getVmstate() {
            return vmstate;
        }

        public void setVmstate(String vmstate) {
            this.vmstate = vmstate;
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

        public String getDecimal() {
            return decimal;
        }

        public void setDecimal(String decimal) {
            this.decimal = decimal;
        }

        public String getGas_consumed() {
            return gas_consumed;
        }

        public void setGas_consumed(String gas_consumed) {
            this.gas_consumed = gas_consumed;
        }

        public String getGas_price() {
            return gas_price;
        }

        public void setGas_price(String gas_price) {
            this.gas_price = gas_price;
        }

        public String getBlock_number() {
            return block_number;
        }

        public void setBlock_number(String block_number) {
            this.block_number = block_number;
        }

        public String getGas_fee() {
            return gas_fee;
        }

        public void setGas_fee(String gas_fee) {
            this.gas_fee = gas_fee;
        }
    }
}
