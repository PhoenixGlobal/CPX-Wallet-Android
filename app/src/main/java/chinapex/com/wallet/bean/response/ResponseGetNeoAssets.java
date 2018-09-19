package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/8 14:24
 * E-Mail：liuyi_61@163.com
 */
public class ResponseGetNeoAssets {

    /**
     * state : 200
     * result : [{"type":"NEP5","symbol":"ASA","precision":"8","name":"Asura World Coin",
     * "image_url":"https://i0.wp.com/www.blockchaindk
     * .com/wp-content/uploads/2017/11/NEON-Wallet-Logo.png",
     * "hex_hash":"0xa58b56b30425d3d1f8902034996fcac4168ef71d",
     * "hash":"a58b56b30425d3d1f8902034996fcac4168ef71d"}]
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
         * type : NEP5
         * symbol : ASA
         * precision : 8
         * name : Asura World Coin
         * image_url : https://i0.wp.com/www.blockchaindk
         * .com/wp-content/uploads/2017/11/NEON-Wallet-Logo.png
         * hex_hash : 0xa58b56b30425d3d1f8902034996fcac4168ef71d
         * hash : a58b56b30425d3d1f8902034996fcac4168ef71d
         */

        private String type;
        private String symbol;
        private String precision;
        private String name;
        private String image_url;
        private String hex_hash;
        private String hash;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getHex_hash() {
            return hex_hash;
        }

        public void setHex_hash(String hex_hash) {
            this.hex_hash = hex_hash;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }
}
