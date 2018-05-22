package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class ResponseGetBlock {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"hash":"0x65e70b74a56122a8f6b98d1ac95116690d6e50fc644f511a6660d9140c8bcb85",
     * "size":948,"version":0,
     * "previousblockhash":"0x18f59297569eef5695612afa2a00cba1fe31de71810de49d065ed634f4c86a2c",
     * "merkleroot":"0x0740e1926ab751f2081131d96e5f101d18ef1088a06cce52a35d4dd4c9927a7f",
     * "time":1526537679,"index":1471562,"nonce":"ccead94b4055c66c",
     * "nextconsensus":"AaRDxTygdb5xYk1fku7uz7L5aTgNtn3J1w",
     * "script":{"invocation
     * ":"40a5a8e653a8322072c37fd69a9e1a8a59b88112208c5a14535014d4bfb6508786d6601d2962bde809747859295a12c8dda8e93fa196c7a6e9871c48465b8ceb9740bf35665a231ba78c0989e740b71ff63d69a0c4329fe1c12469ce131f2fa8ff50457e86ac3454b83f957f666dd752ceb9c28eea149421eef8f0eaa0e11b305adb40bbe022f60a9673883b21aa8af486a9a7b3281b452cea01ceec1a68bb8a92836dccb578faa3922732a2a80489521da6760186342f4e577cb7e881264b2ffcf3a440ce42625054bdb0a25bb242df876afc49dab524f72faad1043bf6b3fe9e756cb8031de86897a8e78ebf8b0105f50f97b09375779d34a9a4451427993cb6aa81364064854e1f2d806bfd1734755570f120ea9c63d98a4a2ffa4332e7d10a8c119125fae142a0e8933936c71566545a6deeeea6ed358e1cfcbdadc8deb383d89107ae","verification":"55210209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9210327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8210344925b6126c8ae58a078b5b2ce98de8fff15a22dac6f57ffd3b108c72a0670d121025bdf3f181f53e9696227843950deb72dcd374ded17c057159513c3d0abe20b6421026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d2221039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf92103c089d7122b840a4935234e82e26ae5efd0c2acb627239dc9f207311337b6f2c157ae"},"tx":[{"txid":"0x0d40a9166a0f5e7c3b90d1d05681944023a1718a2e3048bf2881e0e1ab5149e1","size":10,"type":"MinerTransaction","version":0,"attributes":[],"vin":[],"vout":[],"sys_fee":"0","net_fee":"0","scripts":[],"nonce":1079363180},{"txid":"0xdd2a1f010afd43535d2a40d3a321dc5b903af59a0ad04b7da090ee11cd33e549","size":262,"type":"ContractTransaction","version":0,"attributes":[],"vin":[{"txid":"0xa976fe69a563f429485d0732016b6c939cf2dab13aaac01f07ee20aa70847457","vout":1}],"vout":[{"n":0,"asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"6","address":"AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6"},{"n":1,"asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"378","address":"ARF9CNWdtpWZhMBvsbRd3jo12s4YcfeQyR"}],"sys_fee":"0","net_fee":"0","scripts":[{"invocation":"40666b5637ad044432e324382ee45ee709fdd6a0e303c762190b94b44fec13db89d678f86e81005c48a36cb7a50ec6e543085cf90aa56d7c967eec5483df6e7fb7","verification":"21024e56d7e0545da86eac54b7de54d398fbfc5def59e6c89dd4702ec38a4618ca13ac"}]}],"confirmations":14459,"nextblockhash":"0x261b1e31fc9c1e84300dc334a8a2cc7106a891ad97584202634999983e6231f2"}
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
         * hash : 0x65e70b74a56122a8f6b98d1ac95116690d6e50fc644f511a6660d9140c8bcb85
         * size : 948
         * version : 0
         * previousblockhash : 0x18f59297569eef5695612afa2a00cba1fe31de71810de49d065ed634f4c86a2c
         * merkleroot : 0x0740e1926ab751f2081131d96e5f101d18ef1088a06cce52a35d4dd4c9927a7f
         * time : 1526537679
         * index : 1471562
         * nonce : ccead94b4055c66c
         * nextconsensus : AaRDxTygdb5xYk1fku7uz7L5aTgNtn3J1w
         * script :
         * {"invocation":"40a5a8e653a8322072c37fd69a9e1a8a59b88112208c5a14535014d4bfb6508786d6601d2962bde809747859295a12c8dda8e93fa196c7a6e9871c48465b8ceb9740bf35665a231ba78c0989e740b71ff63d69a0c4329fe1c12469ce131f2fa8ff50457e86ac3454b83f957f666dd752ceb9c28eea149421eef8f0eaa0e11b305adb40bbe022f60a9673883b21aa8af486a9a7b3281b452cea01ceec1a68bb8a92836dccb578faa3922732a2a80489521da6760186342f4e577cb7e881264b2ffcf3a440ce42625054bdb0a25bb242df876afc49dab524f72faad1043bf6b3fe9e756cb8031de86897a8e78ebf8b0105f50f97b09375779d34a9a4451427993cb6aa81364064854e1f2d806bfd1734755570f120ea9c63d98a4a2ffa4332e7d10a8c119125fae142a0e8933936c71566545a6deeeea6ed358e1cfcbdadc8deb383d89107ae","verification":"55210209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9210327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8210344925b6126c8ae58a078b5b2ce98de8fff15a22dac6f57ffd3b108c72a0670d121025bdf3f181f53e9696227843950deb72dcd374ded17c057159513c3d0abe20b6421026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d2221039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf92103c089d7122b840a4935234e82e26ae5efd0c2acb627239dc9f207311337b6f2c157ae"}
         * tx : [{"txid":"0x0d40a9166a0f5e7c3b90d1d05681944023a1718a2e3048bf2881e0e1ab5149e1",
         * "size":10,"type":"MinerTransaction","version":0,"attributes":[],"vin":[],"vout":[],
         * "sys_fee":"0","net_fee":"0","scripts":[],"nonce":1079363180},
         * {"txid":"0xdd2a1f010afd43535d2a40d3a321dc5b903af59a0ad04b7da090ee11cd33e549",
         * "size":262,"type":"ContractTransaction","version":0,"attributes":[],
         * "vin":[{"txid":"0xa976fe69a563f429485d0732016b6c939cf2dab13aaac01f07ee20aa70847457",
         * "vout":1}],"vout":[{"n":0,
         * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"6","address":"AaHQXZe1eQicV7bfjyDNQEjAtSHXFfMRP6"},{"n":1,
         * "asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"378","address":"ARF9CNWdtpWZhMBvsbRd3jo12s4YcfeQyR"}],"sys_fee":"0",
         * "net_fee":"0",
         * "scripts":[{"invocation
         * ":"40666b5637ad044432e324382ee45ee709fdd6a0e303c762190b94b44fec13db89d678f86e81005c48a36cb7a50ec6e543085cf90aa56d7c967eec5483df6e7fb7","verification":"21024e56d7e0545da86eac54b7de54d398fbfc5def59e6c89dd4702ec38a4618ca13ac"}]}]
         * confirmations : 14459
         * nextblockhash : 0x261b1e31fc9c1e84300dc334a8a2cc7106a891ad97584202634999983e6231f2
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
             * 40a5a8e653a8322072c37fd69a9e1a8a59b88112208c5a14535014d4bfb6508786d6601d2962bde809747859295a12c8dda8e93fa196c7a6e9871c48465b8ceb9740bf35665a231ba78c0989e740b71ff63d69a0c4329fe1c12469ce131f2fa8ff50457e86ac3454b83f957f666dd752ceb9c28eea149421eef8f0eaa0e11b305adb40bbe022f60a9673883b21aa8af486a9a7b3281b452cea01ceec1a68bb8a92836dccb578faa3922732a2a80489521da6760186342f4e577cb7e881264b2ffcf3a440ce42625054bdb0a25bb242df876afc49dab524f72faad1043bf6b3fe9e756cb8031de86897a8e78ebf8b0105f50f97b09375779d34a9a4451427993cb6aa81364064854e1f2d806bfd1734755570f120ea9c63d98a4a2ffa4332e7d10a8c119125fae142a0e8933936c71566545a6deeeea6ed358e1cfcbdadc8deb383d89107ae
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
             * txid : 0x0d40a9166a0f5e7c3b90d1d05681944023a1718a2e3048bf2881e0e1ab5149e1
             * size : 10
             * type : MinerTransaction
             * version : 0
             * attributes : []
             * vin : []
             * vout : []
             * sys_fee : 0
             * net_fee : 0
             * scripts : []
             * nonce : 1079363180
             */

            private String txid;
            private int size;
            private String type;
            private int version;
            private String sys_fee;
            private String net_fee;
            private int nonce;
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

            public int getNonce() {
                return nonce;
            }

            public void setNonce(int nonce) {
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
