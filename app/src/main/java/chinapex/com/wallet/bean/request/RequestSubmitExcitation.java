package chinapex.com.wallet.bean.request;

public class RequestSubmitExcitation {

    /**
     * CPX : XXX
     * ETH : XXX
     * id : 123
     */

    private String CPX;
    private String ETH;
    private int id;

    public String getCPX() {
        return CPX;
    }

    public void setCPX(String CPX) {
        this.CPX = CPX;
    }

    public String getETH() {
        return ETH;
    }

    public void setETH(String ETH) {
        this.ETH = ETH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
