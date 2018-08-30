package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/5/18 0018.
 */

public class ResponseExcitation {


    /**
     * status : 200
     * msg : null
     * data : [{"id":1,"code":null,"title_cn":"活动1 t","body_cn":"活动 1","title_en":"activity 1 t","body_en":"activity 1","status":2,"imagesurl":"http://www.chinapex.com.cn/media/ckeditor/1533713446-73.jpg","gas_limit":1,"new_flag":1},{"id":3,"code":null,"title_cn":"活动3 t","body_cn":"活动3","title_en":"activity 3 t","body_en":"activity 3","status":-1,"imagesurl":"http://www.chinapex.com.cn/media/posts/270.jpg","gas_limit":1,"new_flag":1},{"id":2,"code":null,"title_cn":"活动2 t","body_cn":"活动2","title_en":"activity 2 t","body_en":"activity 2","status":1,"imagesurl":"http://www.chinapex.com.cn/media/posts/268.jpg","gas_limit":1,"new_flag":1}]
     */

    private int status;
    private Object msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * code : null
         * title_cn : 活动1 t
         * body_cn : 活动 1
         * title_en : activity 1 t
         * body_en : activity 1
         * status : 2
         * imagesurl : http://www.chinapex.com.cn/media/ckeditor/1533713446-73.jpg
         * gas_limit : 1
         * new_flag : 1
         */

        private int id;
        private Object code;
        private String title_cn;
        private String body_cn;
        private String title_en;
        private String body_en;
        private int status;
        private String imagesurl;
        private int gas_limit;
        private int new_flag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getTitle_cn() {
            return title_cn;
        }

        public void setTitle_cn(String title_cn) {
            this.title_cn = title_cn;
        }

        public String getBody_cn() {
            return body_cn;
        }

        public void setBody_cn(String body_cn) {
            this.body_cn = body_cn;
        }

        public String getTitle_en() {
            return title_en;
        }

        public void setTitle_en(String title_en) {
            this.title_en = title_en;
        }

        public String getBody_en() {
            return body_en;
        }

        public void setBody_en(String body_en) {
            this.body_en = body_en;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImagesurl() {
            return imagesurl;
        }

        public void setImagesurl(String imagesurl) {
            this.imagesurl = imagesurl;
        }

        public int getGas_limit() {
            return gas_limit;
        }

        public void setGas_limit(int gas_limit) {
            this.gas_limit = gas_limit;
        }

        public int getNew_flag() {
            return new_flag;
        }

        public void setNew_flag(int new_flag) {
            this.new_flag = new_flag;
        }
    }
}
