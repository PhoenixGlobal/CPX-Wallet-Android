package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/2 0002 11:20.
 * E-Mail：liuyi_61@163.com
 */

public class ResponseGetNep5Balance {

    /**
     * jsonrpc : 2.0
     * id : 3
     * result :
     * {"script":"142b41aea9d405fef2e809e3c8085221ce944527a751c10962616c616e63654f6667f91d6b7085db7c5aaf09f19eeec1ca3c0db2c6ec","state":"HALT, BREAK","gas_consumed":"0.338","stack":[{"type":"ByteArray","value":"0084d717"}]}
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
         * script :
         * 142b41aea9d405fef2e809e3c8085221ce944527a751c10962616c616e63654f6667f91d6b7085db7c5aaf09f19eeec1ca3c0db2c6ec
         * state : HALT, BREAK
         * gas_consumed : 0.338
         * stack : [{"type":"ByteArray","value":"0084d717"}]
         */

        private String script;
        private String state;
        private String gas_consumed;
        private List<StackBean> stack;

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGas_consumed() {
            return gas_consumed;
        }

        public void setGas_consumed(String gas_consumed) {
            this.gas_consumed = gas_consumed;
        }

        public List<StackBean> getStack() {
            return stack;
        }

        public void setStack(List<StackBean> stack) {
            this.stack = stack;
        }

        public static class StackBean {
            /**
             * type : ByteArray
             * value : 0084d717
             */

            private String type;
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
