package chinapex.com.wallet.bean;

public class AddressResultCode {
    private int ethCode;
    private int cpxCode;

    public int getEthCode() {
        return ethCode;
    }

    public void setEthCode(int ethCode) {
        this.ethCode = ethCode;
    }

    public int getCpxCode() {
        return cpxCode;
    }

    public void setCpxCode(int cpxCode) {
        this.cpxCode = cpxCode;
    }

    @Override
    public String toString() {
        return "AddressResultCode{" +
                "ethCode=" + ethCode +
                ", cpxCode=" + cpxCode +
                '}';
    }
}
