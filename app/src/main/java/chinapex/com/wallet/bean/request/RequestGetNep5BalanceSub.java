package chinapex.com.wallet.bean.request;

/**
 * Created by SteelCabbage on 2018/7/2 0002 11:33.
 * E-Mail：liuyi_61@163.com
 */

public class RequestGetNep5BalanceSub {

    /**
     * type : Hash160
     * value : 0xa7274594ce215208c8e309e8f2fe05d4a9ae412b
     */

    private String type;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
