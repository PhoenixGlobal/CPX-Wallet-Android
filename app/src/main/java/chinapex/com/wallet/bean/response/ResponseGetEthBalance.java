package chinapex.com.wallet.bean.response;

/**
 * Created by SteelCabbage on 2018/8/28 0028 16:48.
 * E-Mail：liuyi_61@163.com
 */
public class ResponseGetEthBalance {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : 0xec4c8c64612a8
     */

    private String jsonrpc;
    private int id;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
