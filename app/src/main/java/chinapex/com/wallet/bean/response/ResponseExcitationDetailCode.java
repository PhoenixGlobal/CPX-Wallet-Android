package chinapex.com.wallet.bean.response;

/**
 * Created by SteelCabbage on 2018/5/18 0018.
 */

public class ResponseExcitationDetailCode {


    /**
     * status : 201
     * msg : exception
     * data : {"ETH":1,"CPX":2}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ETH : 1
         * CPX : 2
         */

        private int ETH;
        private int CPX;

        public int getETH() {
            return ETH;
        }

        public void setETH(int ETH) {
            this.ETH = ETH;
        }

        public int getCPX() {
            return CPX;
        }

        public void setCPX(int CPX) {
            this.CPX = CPX;
        }
    }
}
