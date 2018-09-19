package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/9/13 0013 14:24.
 * E-Mail：liuyi_61@163.com
 */
public class ResponseGetEthAssets {

    /**
     * status : 200
     * msg : null
     * data : [{"type":"ERC20","name":"Ethereum","symbol":"ETH","precision":"18","hash":"","hex_hash":"",
     * "image_url":"https://tracker.chinapex.com.cn/tool/static/icon84/84_Ethereum.png"},{"type":"ERC20","name":"Tronix",
     * "symbol":"TRX","precision":"6","hash":"f230b790e05390fc8295f4d3f60332c93bed42e2",
     * "hex_hash":"0xf230b790e05390fc8295f4d3f60332c93bed42e2","image_url":"https://tracker.chinapex.com
     * .cn/tool/static/icon84/84_Tronix.png"}]
     */

    private int status;
    private Object msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
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
         * type : ERC20
         * name : Ethereum
         * symbol : ETH
         * precision : 18
         * hash :
         * hex_hash :
         * image_url : https://tracker.chinapex.com.cn/tool/static/icon84/84_Ethereum.png
         */

        private String type;
        private String name;
        private String symbol;
        private String precision;
        private String hash;
        private String hex_hash;
        private String image_url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPrecision() {
            return precision;
        }

        public void setPrecision(String precision) {
            this.precision = precision;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getHex_hash() {
            return hex_hash;
        }

        public void setHex_hash(String hex_hash) {
            this.hex_hash = hex_hash;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
