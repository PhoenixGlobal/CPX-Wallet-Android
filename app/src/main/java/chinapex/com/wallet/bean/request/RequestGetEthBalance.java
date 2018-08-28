package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/8/28 0028 16:41.
 * E-Mail：liuyi_61@163.com
 */
public class RequestGetEthBalance {

    /**
     * jsonrpc : 2.0
     * method : eth_getBalance
     * params : ["0x9e5817F681770a38f92261869B2aA08C2763C853","latest"]
     * id : 1
     */

    private String jsonrpc;
    private String method;
    private int id;
    private List<String> params;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
