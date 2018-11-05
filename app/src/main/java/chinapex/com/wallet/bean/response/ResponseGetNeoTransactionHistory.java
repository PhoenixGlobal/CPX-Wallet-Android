package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/6/22 0022 11:37.
 * E-Mail：liuyi_61@163.com
 */

public class ResponseGetNeoTransactionHistory {

    /**
     * status : 200
     * msg :
     * data : [{"txid":"0x692aa230df64f565d39b5ace1a0d62ee3109e69765bbefbac13230d60cbfb544","type":"NEO",
     * "assetId":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7","time":1529394904,
     * "from":"AY8r8tSG3JRNmnmFoA7NALHTQQ71Fd9SEy","to":"AQjxjP5EdsY46Degf39Zd3F8P388CmynZX","value":"+0.0099998",
     * "rawValue":"0.0099998","vmstate":"","symbol":"NEO","imageURL":"todo_imageURL","decimal":"","gas_consumed":"",
     * "gas_price":"","block_number":"","gas_fee":""}]
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
         * txid : 0x692aa230df64f565d39b5ace1a0d62ee3109e69765bbefbac13230d60cbfb544
         * type : NEO
         * assetId : 0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7
         * time : 1529394904
         * from : AY8r8tSG3JRNmnmFoA7NALHTQQ71Fd9SEy
         * to : AQjxjP5EdsY46Degf39Zd3F8P388CmynZX
         * value : +0.0099998
         * rawValue : 0.0099998
         * vmstate :
         * symbol : NEO
         * imageURL : todo_imageURL
         * decimal :
         * gas_consumed :
         * gas_price :
         * block_number :
         * gas_fee :
         */

        private String txid;
        private String type;
        private String assetId;
        private long time;
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

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
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
