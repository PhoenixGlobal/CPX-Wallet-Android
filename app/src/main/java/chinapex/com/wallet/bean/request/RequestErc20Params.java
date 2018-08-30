package chinapex.com.wallet.bean.request;

/**
 * Created by SteelCabbage on 2018/8/29 0029 17:55.
 * E-Mail：liuyi_61@163.com
 */
public class RequestErc20Params {

    /**
     * to : 0xc7773e07adb2642a1eb03c5e340430b6cedc2aa9
     * data : 0x70a082310000000000000000000000005372cADc9DdA99b137f5f8DcF014a4e93114bAc2
     */

    private String to;
    private String data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
