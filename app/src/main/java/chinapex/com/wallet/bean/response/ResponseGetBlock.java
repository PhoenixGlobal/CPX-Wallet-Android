package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class ResponseGetBlock {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"hash":"0x7a05b2f829ba56d35d502915cab72fb436c6ed3c7d59ccf940314ee6a86b7534",
     * "size":888,"version":0,
     * "previousblockhash":"0xed014f3d9f7660639eb09fbd93fcb78bd5d0fe1b83073cab310f1a6880d841da",
     * "merkleroot":"0x60be173b7219e9c19557911b1c027b75ff82d9fe601eeeb1efcaa8f89a08950a",
     * "time":1527043984,"index":1487935,"nonce":"2e970bd6cc3e5f4f",
     * "nextconsensus":"AaRDxTygdb5xYk1fku7uz7L5aTgNtn3J1w",
     * "script":{"invocation
     * ":"403e719b298e357d581f7c67769428a1bc5b549b2b6437cb6faf6f96bd70e7721cec30d5bf9e7195817d946f7050e449e640c326023e24c6e38a943f5bf515291640bd5e89eb6c868c6ed43bebe20728e3314301b7eef0dea9d8274e8784bb82131347a80c586b092daf7788f9e65bc93b4e31192f149576c5894e3b30d8c46b02c940db599eda2718de39c6820f67d6f0607afdb80cde80ba42d205771d29972d2393a194ee7c0f130f8a094b90f143341d50dd6be6e66303230229ffe0fe27eaee2740524040bca3b90142a3a01da980b992a15e0c64e90bdb5c438650aba822515dc35fbca0d39baa2cc35b0e9eef01c046878e1706b7c5ca4e18f8f29d53825c62cb40957eb50e4cd845e49a552c7b8816e08c549616bb689e0f7cbd77a05cc7ada21444712ba3b172a85b24ce65993806e0cba06bfddf56c311eb2482a3376fde1046","verification":"55210209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9210327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8210344925b6126c8ae58a078b5b2ce98de8fff15a22dac6f57ffd3b108c72a0670d121025bdf3f181f53e9696227843950deb72dcd374ded17c057159513c3d0abe20b6421026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d2221039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf92103c089d7122b840a4935234e82e26ae5efd0c2acb627239dc9f207311337b6f2c157ae"},"tx":[{"txid":"0xf3be55e43ff279fc904393884da19aabe13533df0afdc8b376e031fb0a1f1372","size":10,"type":"MinerTransaction","version":0,"attributes":[],"vin":[],"vout":[],"sys_fee":"0","net_fee":"0","scripts":[],"nonce":3426639695},{"txid":"0x24fe3fce8b65973ac3aba4a174e93f27d31618a3802f7c0faa1698a7bea24f96","size":202,"type":"ContractTransaction","version":0,"attributes":[],"vin":[{"txid":"0xfa257868244839897f7f607f9eab804c0ed93073f9eba4a66941efc35ea9b58b","vout":0}],"vout":[{"n":0,"asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"1","address":"AGh2PSiD5WVcrQJyywikGWmBG9r8CVrPB5"}],"sys_fee":"0","net_fee":"0","scripts":[{"invocation":"40dca5046ba538bee8cc4fe5ae343714f4f4b60b0a3f0f6298e4dc72b1d3e65f5a113ea5db7cca7f915656f703f78983358358b298b1609945a5492034295d8dff","verification":"210283c09741edf085b971317268ed2a782953651c8531782306986fa1e9ac4b5520ac"}]}],"confirmations":3,"nextblockhash":"0x724c4bcaf4ce353fc84bd0fb62723a7233cfdf5bd0108f0f67ec3e462b888ed7"}
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
         * hash : 0x7a05b2f829ba56d35d502915cab72fb436c6ed3c7d59ccf940314ee6a86b7534
         * size : 888
         * version : 0
         * previousblockhash : 0xed014f3d9f7660639eb09fbd93fcb78bd5d0fe1b83073cab310f1a6880d841da
         * merkleroot : 0x60be173b7219e9c19557911b1c027b75ff82d9fe601eeeb1efcaa8f89a08950a
         * time : 1527043984
         * index : 1487935
         * nonce : 2e970bd6cc3e5f4f
         * nextconsensus : AaRDxTygdb5xYk1fku7uz7L5aTgNtn3J1w
         * script :
         * {"invocation":"403e719b298e357d581f7c67769428a1bc5b549b2b6437cb6faf6f96bd70e7721cec30d5bf9e7195817d946f7050e449e640c326023e24c6e38a943f5bf515291640bd5e89eb6c868c6ed43bebe20728e3314301b7eef0dea9d8274e8784bb82131347a80c586b092daf7788f9e65bc93b4e31192f149576c5894e3b30d8c46b02c940db599eda2718de39c6820f67d6f0607afdb80cde80ba42d205771d29972d2393a194ee7c0f130f8a094b90f143341d50dd6be6e66303230229ffe0fe27eaee2740524040bca3b90142a3a01da980b992a15e0c64e90bdb5c438650aba822515dc35fbca0d39baa2cc35b0e9eef01c046878e1706b7c5ca4e18f8f29d53825c62cb40957eb50e4cd845e49a552c7b8816e08c549616bb689e0f7cbd77a05cc7ada21444712ba3b172a85b24ce65993806e0cba06bfddf56c311eb2482a3376fde1046","verification":"55210209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9210327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8210344925b6126c8ae58a078b5b2ce98de8fff15a22dac6f57ffd3b108c72a0670d121025bdf3f181f53e9696227843950deb72dcd374ded17c057159513c3d0abe20b6421026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d2221039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf92103c089d7122b840a4935234e82e26ae5efd0c2acb627239dc9f207311337b6f2c157ae"}
         * tx : [{"txid":"0xf3be55e43ff279fc904393884da19aabe13533df0afdc8b376e031fb0a1f1372",
         * "size":10,"type":"MinerTransaction","version":0,"attributes":[],"vin":[],"vout":[],
         * "sys_fee":"0","net_fee":"0","scripts":[],"nonce":3426639695},
         * {"txid":"0x24fe3fce8b65973ac3aba4a174e93f27d31618a3802f7c0faa1698a7bea24f96",
         * "size":202,"type":"ContractTransaction","version":0,"attributes":[],
         * "vin":[{"txid":"0xfa257868244839897f7f607f9eab804c0ed93073f9eba4a66941efc35ea9b58b",
         * "vout":0}],"vout":[{"n":0,
         * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"1","address":"AGh2PSiD5WVcrQJyywikGWmBG9r8CVrPB5"}],"sys_fee":"0",
         * "net_fee":"0",
         * "scripts":[{"invocation
         * ":"40dca5046ba538bee8cc4fe5ae343714f4f4b60b0a3f0f6298e4dc72b1d3e65f5a113ea5db7cca7f915656f703f78983358358b298b1609945a5492034295d8dff","verification":"210283c09741edf085b971317268ed2a782953651c8531782306986fa1e9ac4b5520ac"}]}]
         * confirmations : 3
         * nextblockhash : 0x724c4bcaf4ce353fc84bd0fb62723a7233cfdf5bd0108f0f67ec3e462b888ed7
         */

        private String hash;
        private int size;
        private int version;
        private String previousblockhash;
        private String merkleroot;
        private int time;
        private int index;
        private String nonce;
        private String nextconsensus;
        private ScriptBean script;
        private int confirmations;
        private String nextblockhash;
        private List<TxBean> tx;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getPreviousblockhash() {
            return previousblockhash;
        }

        public void setPreviousblockhash(String previousblockhash) {
            this.previousblockhash = previousblockhash;
        }

        public String getMerkleroot() {
            return merkleroot;
        }

        public void setMerkleroot(String merkleroot) {
            this.merkleroot = merkleroot;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getNextconsensus() {
            return nextconsensus;
        }

        public void setNextconsensus(String nextconsensus) {
            this.nextconsensus = nextconsensus;
        }

        public ScriptBean getScript() {
            return script;
        }

        public void setScript(ScriptBean script) {
            this.script = script;
        }

        public int getConfirmations() {
            return confirmations;
        }

        public void setConfirmations(int confirmations) {
            this.confirmations = confirmations;
        }

        public String getNextblockhash() {
            return nextblockhash;
        }

        public void setNextblockhash(String nextblockhash) {
            this.nextblockhash = nextblockhash;
        }

        public List<TxBean> getTx() {
            return tx;
        }

        public void setTx(List<TxBean> tx) {
            this.tx = tx;
        }

        public static class ScriptBean {
            /**
             * invocation :
             * 403e719b298e357d581f7c67769428a1bc5b549b2b6437cb6faf6f96bd70e7721cec30d5bf9e7195817d946f7050e449e640c326023e24c6e38a943f5bf515291640bd5e89eb6c868c6ed43bebe20728e3314301b7eef0dea9d8274e8784bb82131347a80c586b092daf7788f9e65bc93b4e31192f149576c5894e3b30d8c46b02c940db599eda2718de39c6820f67d6f0607afdb80cde80ba42d205771d29972d2393a194ee7c0f130f8a094b90f143341d50dd6be6e66303230229ffe0fe27eaee2740524040bca3b90142a3a01da980b992a15e0c64e90bdb5c438650aba822515dc35fbca0d39baa2cc35b0e9eef01c046878e1706b7c5ca4e18f8f29d53825c62cb40957eb50e4cd845e49a552c7b8816e08c549616bb689e0f7cbd77a05cc7ada21444712ba3b172a85b24ce65993806e0cba06bfddf56c311eb2482a3376fde1046
             * verification :
             * 55210209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9210327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8210344925b6126c8ae58a078b5b2ce98de8fff15a22dac6f57ffd3b108c72a0670d121025bdf3f181f53e9696227843950deb72dcd374ded17c057159513c3d0abe20b6421026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d2221039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf92103c089d7122b840a4935234e82e26ae5efd0c2acb627239dc9f207311337b6f2c157ae
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

        public static class TxBean {
            /**
             * txid : 0xf3be55e43ff279fc904393884da19aabe13533df0afdc8b376e031fb0a1f1372
             * size : 10
             * type : MinerTransaction
             * version : 0
             * attributes : []
             * vin : []
             * vout : []
             * sys_fee : 0
             * net_fee : 0
             * scripts : []
             * nonce : 3426639695
             */

            private String txid;
            private int size;
            private String type;
            private int version;
            private String sys_fee;
            private String net_fee;
            private long nonce;
            private List<?> attributes;
            private List<?> vin;
            private List<?> vout;
            private List<?> scripts;

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

            public long getNonce() {
                return nonce;
            }

            public void setNonce(long nonce) {
                this.nonce = nonce;
            }

            public List<?> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<?> attributes) {
                this.attributes = attributes;
            }

            public List<?> getVin() {
                return vin;
            }

            public void setVin(List<?> vin) {
                this.vin = vin;
            }

            public List<?> getVout() {
                return vout;
            }

            public void setVout(List<?> vout) {
                this.vout = vout;
            }

            public List<?> getScripts() {
                return scripts;
            }

            public void setScripts(List<?> scripts) {
                this.scripts = scripts;
            }
        }
    }
}
