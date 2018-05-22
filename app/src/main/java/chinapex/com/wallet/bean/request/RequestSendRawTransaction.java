package chinapex.com.wallet.bean.request;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/22 0022.
 */

public class RequestSendRawTransaction {

    /**
     * jsonrpc : 2.0
     * method : sendrawtransaction
     * params :
     * ["8000000149e533cd11ee90a07d4bd00a9af53a905bdc21a3d3402a5d5343fd0a011f2add0000029b7cffdaa674beae0f930ebe6085af9093e5fe56b34a5c220ccdcf6efc336fc500c2eb0b00000000fb88884709b8c774dee9efa620629aa44cdf34349b7cffdaa674beae0f930ebe6085af9093e5fe56b34a5c220ccdcf6efc336fc50084d71700000000cb081acce03f01f04c5b73e9e5c9d05befb18e530141405a2e08f9731173f5114123ed0dbdab7056b1ee6ad478a2bd31a28b8e120cfda3744cba01be2cc02bc5a2b8c93eef31560dcd265c2e319924de5967a54568f91a232102ebacb671942aaf6b640dcd557617431782706566dc3c23de3057c9ee52db771fac"]
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
