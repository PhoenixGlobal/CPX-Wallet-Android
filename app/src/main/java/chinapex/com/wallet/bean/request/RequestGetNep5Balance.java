package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/2 0002 11:20.
 * E-Mail：liuyi_61@163.com
 */

public class RequestGetNep5Balance {

    /**
     * jsonrpc : 2.0
     * method : invokefunction
     * params : ["0xecc6b20d3ccac1ee9ef109af5a7cdb85706b1df9","balanceOf",[{"type":"Hash160",
     * "value":"0xa7274594ce215208c8e309e8f2fe05d4a9ae412b"}]]
     * id : 3
     */

    private String jsonrpc;
    private String method;
    private int id;
    private List<Object> params;

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

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
