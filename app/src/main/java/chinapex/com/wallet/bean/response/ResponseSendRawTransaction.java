package chinapex.com.wallet.bean.response;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class ResponseSendRawTransaction {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : true
     */

    private String jsonrpc;
    private int id;
    private boolean result;

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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
