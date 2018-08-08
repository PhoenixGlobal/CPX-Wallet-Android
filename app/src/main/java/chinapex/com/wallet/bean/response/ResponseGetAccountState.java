package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/18 0018.
 */

public class ResponseGetAccountState {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"version":0,"script_hash":"0x3434df4ca49a6220a6efe9de74c7b809478888fb",
     * "frozen":false,"votes":[],
     * "balances":[{"asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
     * "value":"0.001"},
     * {"asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b","value":"2"}]}
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
         * version : 0
         * script_hash : 0x3434df4ca49a6220a6efe9de74c7b809478888fb
         * frozen : false
         * votes : []
         * balances :
         * [{"asset":"0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7",
         * "value":"0.001"},
         * {"asset":"0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b",
         * "value":"2"}]
         */

        private int version;
        private String script_hash;
        private boolean frozen;
        private List<?> votes;
        private List<BalancesBean> balances;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getScript_hash() {
            return script_hash;
        }

        public void setScript_hash(String script_hash) {
            this.script_hash = script_hash;
        }

        public boolean isFrozen() {
            return frozen;
        }

        public void setFrozen(boolean frozen) {
            this.frozen = frozen;
        }

        public List<?> getVotes() {
            return votes;
        }

        public void setVotes(List<?> votes) {
            this.votes = votes;
        }

        public List<BalancesBean> getBalances() {
            return balances;
        }

        public void setBalances(List<BalancesBean> balances) {
            this.balances = balances;
        }

        public static class BalancesBean {
            /**
             * asset : 0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7
             * value : 0.001
             */

            private String asset;
            private String value;

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
        }
    }
}
