package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/8 14:24
 * E-Mail：liuyi_61@163.com
 */
public class ResponseGetNeoAssets {


    /**
     * status : 200
     * msg : null
     * data : [{"type":"NEP5","name":"Switcheo","symbol":"SWTH","precision":"8",
     * "hash":"ab38352559b8b203bde5fddfa0b07d8b2525e132","hex_hash":"0xab38352559b8b203bde5fddfa0b07d8b2525e132",
     * "image_url":"todo_imageURL"}]
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
         * type : NEP5
         * name : Switcheo
         * symbol : SWTH
         * precision : 8
         * hash : ab38352559b8b203bde5fddfa0b07d8b2525e132
         * hex_hash : 0xab38352559b8b203bde5fddfa0b07d8b2525e132
         * image_url : todo_imageURL
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
