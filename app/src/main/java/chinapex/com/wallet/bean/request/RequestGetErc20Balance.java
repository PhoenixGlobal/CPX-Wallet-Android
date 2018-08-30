package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/8/29 0029 17:57.
 * E-Mail：liuyi_61@163.com
 */
public class RequestGetErc20Balance {

    /**
     * jsonrpc : 2.0
     * method : eth_call
     * params : [{"to":"0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9",
     * "data":"0x70a082310000000000000000000000005372cADc9DdA99b137f5f8DcF014a4e93114bAc2"},"latest"]
     * id : 1
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
