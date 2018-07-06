package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class ResponseGetRawTransaction {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"txid":"0xdec964156bf2fda50661d24c4355ce947bb3581852d4b2a72854fe2d559b76f2",
     * "size":262,"type":"ContractTransaction","version":0,"attributes":[],
     * "vin":[{"txid":"0xdd2a1f010afd43535d2a40d3a321dc5b903af59a0ad04b7da090ee11cd33e549",
     * "vout":0}],"vout":[{"n":0,
     * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"2",
     * "address":"Aehrp5dWNETcULcTMjgqN1LD9a3fN4zoQB"},{"n":1,
     * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"4",
     * "address":"AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6"}],"sys_fee":"0","net_fee":"0",
     * "scripts":[{"invocation
     * ":"405a2e08f9731173f5114123ed0dbdab7056b1ee6ad478a2bd31a28b8e120cfda3744cba01be2cc02bc5a2b8c93eef31560dcd265c2e319924de5967a54568f91a","verification":"2102ebacb671942aaf6b640dcd557617431782706566dc3c23de3057c9ee52db771fac"}],"blockhash":"0xf85a2d3caafdec7002156fab430cf8e2dd8a545f45d8eafd48b0d0f97a61ce5a","confirmations":1,"blocktime":1526539769}
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
         * txid : 0xdec964156bf2fda50661d24c4355ce947bb3581852d4b2a72854fe2d559b76f2
         * size : 262
         * type : ContractTransaction
         * version : 0
         * attributes : []
         * vin : [{"txid":"0xdd2a1f010afd43535d2a40d3a321dc5b903af59a0ad04b7da090ee11cd33e549",
         * "vout":0}]
         * vout : [{"n":0,
         * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"2","address":"Aehrp5dWNETcULcTMjgqN1LD9a3fN4zoQB"},{"n":1,
         * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"4","address":"AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6"}]
         * sys_fee : 0
         * net_fee : 0
         * scripts :
         * [{"invocation":"405a2e08f9731173f5114123ed0dbdab7056b1ee6ad478a2bd31a28b8e120cfda3744cba01be2cc02bc5a2b8c93eef31560dcd265c2e319924de5967a54568f91a","verification":"2102ebacb671942aaf6b640dcd557617431782706566dc3c23de3057c9ee52db771fac"}]
         * blockhash : 0xf85a2d3caafdec7002156fab430cf8e2dd8a545f45d8eafd48b0d0f97a61ce5a
         * confirmations : 1
         * blocktime : 1526539769
         */

        private String txid;
        private int size;
        private String type;
        private int version;
        private String sys_fee;
        private String net_fee;
        private String blockhash;
        private long confirmations;
        private int blocktime;
        private List<?> attributes;
        private List<VinBean> vin;
        private List<VoutBean> vout;
        private List<ScriptsBean> scripts;

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getSys_fee() {
            return sys_fee;
        }

        public void setSys_fee(String sys_fee) {
            this.sys_fee = sys_fee;
        }

        public String getNet_fee() {
            return net_fee;
        }

        public void setNet_fee(String net_fee) {
            this.net_fee = net_fee;
        }

        public String getBlockhash() {
            return blockhash;
        }

        public void setBlockhash(String blockhash) {
            this.blockhash = blockhash;
        }

        public long getConfirmations() {
            return confirmations;
        }

        public void setConfirmations(long confirmations) {
            this.confirmations = confirmations;
        }

        public int getBlocktime() {
            return blocktime;
        }

        public void setBlocktime(int blocktime) {
            this.blocktime = blocktime;
        }

        public List<?> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<?> attributes) {
            this.attributes = attributes;
        }

        public List<VinBean> getVin() {
            return vin;
        }

        public void setVin(List<VinBean> vin) {
            this.vin = vin;
        }

        public List<VoutBean> getVout() {
            return vout;
        }

        public void setVout(List<VoutBean> vout) {
            this.vout = vout;
        }

        public List<ScriptsBean> getScripts() {
            return scripts;
        }

        public void setScripts(List<ScriptsBean> scripts) {
            this.scripts = scripts;
        }

        public static class VinBean {
            /**
             * txid : 0xdd2a1f010afd43535d2a40d3a321dc5b903af59a0ad04b7da090ee11cd33e549
             * vout : 0
             */

            private String txid;
            private int vout;

            public String getTxid() {
                return txid;
            }

            public void setTxid(String txid) {
                this.txid = txid;
            }

            public int getVout() {
                return vout;
            }

            public void setVout(int vout) {
                this.vout = vout;
            }
        }

        public static class VoutBean {
            /**
             * n : 0
             * asset : 0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b
             * value : 2
             * address : Aehrp5dWNETcULcTMjgqN1LD9a3fN4zoQB
             */

            private int n;
            private String asset;
            private String value;
            private String address;

            public int getN() {
                return n;
            }

            public void setN(int n) {
                this.n = n;
            }

            public String getAsset() {
                return asset;
            }

            public void setAsset(String asset) {
                this.asset = asset;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public static class ScriptsBean {
            /**
             * invocation : 405a2e08f9731173f5114123ed0dbdab7056b1ee6ad478a2bd31a28b8e120cfda3744cba01be2cc02bc5a2b8c93eef31560dcd265c2e319924de5967a54568f91a
             * verification : 2102ebacb671942aaf6b640dcd557617431782706566dc3c23de3057c9ee52db771fac
             */

            private String invocation;
            private String verification;

            public String getInvocation() {
                return invocation;
            }

            public void setInvocation(String invocation) {
                this.invocation = invocation;
            }

            public String getVerification() {
                return verification;
            }

            public void setVerification(String verification) {
                this.verification = verification;
            }
        }
    }
}
