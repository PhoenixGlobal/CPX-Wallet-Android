package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/17 0017.
 */

public class RequestGetAccountState {

    /**
     * jsonrpc : 2.0
     * method : getaccountstate
     * params : ["Aehrp5dWNETcULcTMjgqN1LD9a3fN4zoQB"]
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
