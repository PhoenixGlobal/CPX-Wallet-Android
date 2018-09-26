package chinapex.com.wallet.bean.response;


/**
 * Created by SteelCabbage on 2018/9/20 0020 14:17.
 * E-Mail：liuyi_61@163.com
 */
public class ResponseEthTxByHash {


    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"blockHash":"0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd","blockNumber":"0x2e0178",
     * "from":"0x5372cadc9dda99b137f5f8dcf014a4e93114bac2","gas":"0x15f90","gasPrice":"0x3b9aca00",
     * "hash":"0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866",
     * "input":"0xa9059cbb0000000000000000000000005a897a6a4dcf546459d4221b0e2a636e9be9f41f0000000000000000000000000000000000000000000000000000000000000001","nonce":"0x9","to":"0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9","transactionIndex":"0x18","value":"0x0","v":"0x1b","r":"0xaabcbc1803f7673809ebe20f149a65678702f4a9b2a22ce5e85e8f64affebd85","s":"0x4b27f26e46fbbb48e40d80d8e7a6372f8a82a54d7feee590025676dc98d8b966"}
     */

    private String jsonrpc;
    private int id;
    private ResultBean result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * blockHash : 0x50da008d950d9cf850fbee47d1eb59cc54e0b35dcc5994ff49d7e4b62688ffcd
         * blockNumber : 0x2e0178
         * from : 0x5372cadc9dda99b137f5f8dcf014a4e93114bac2
         * gas : 0x15f90
         * gasPrice : 0x3b9aca00
         * hash : 0x10cd239af747009bc48f84ef853d31c412882c90a96b6359a91affd4fd6fe866
         * input :
         * 0xa9059cbb0000000000000000000000005a897a6a4dcf546459d4221b0e2a636e9be9f41f0000000000000000000000000000000000000000000000000000000000000001
         * nonce : 0x9
         * to : 0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9
         * transactionIndex : 0x18
         * value : 0x0
         * v : 0x1b
         * r : 0xaabcbc1803f7673809ebe20f149a65678702f4a9b2a22ce5e85e8f64affebd85
         * s : 0x4b27f26e46fbbb48e40d80d8e7a6372f8a82a54d7feee590025676dc98d8b966
         */

        private String blockHash;
        private String blockNumber;
        private String from;
        private String gas;
        private String gasPrice;
        private String hash;
        private String input;
        private String nonce;
        private String to;
        private String transactionIndex;
        private String value;
        private String v;
        private String r;
        private String s;

        public String getBlockHash() {
            return blockHash;
        }

        public void setBlockHash(String blockHash) {
            this.blockHash = blockHash;
        }

        public String getBlockNumber() {
            return blockNumber;
        }

        public void setBlockNumber(String blockNumber) {
            this.blockNumber = blockNumber;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getGas() {
            return gas;
        }

        public void setGas(String gas) {
            this.gas = gas;
        }

        public String getGasPrice() {
            return gasPrice;
        }

        public void setGasPrice(String gasPrice) {
            this.gasPrice = gasPrice;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTransactionIndex() {
            return transactionIndex;
        }

        public void setTransactionIndex(String transactionIndex) {
            this.transactionIndex = transactionIndex;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
