package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class RequestGetRawTransaction {

    /**
     * jsonrpc : 2.0
     * method : getrawtransaction
     * params : ["0xdec964156bf2fda50661d24c4355ce947bb3581852d4b2a72854fe2d559b76f2",1]
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
