package chinapex.com.wallet.bean.response;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/24 0024 9:12.
 * E-Mail：liuyi_61@163.com
 */

public class ResponsePortrait {


    /**
     * code : 200
     * language : en_US
     * result : [{"type":1,"resource":1,"title":"性别","data":[{"name":"男","id":"0"},{"name":"女",
     * "id":"0"}]},{"type":0,"resource":0,"title":"常住地","data":[]},{"type":1,"resource":1,
     * "title":"学历","data":[{"name":"初中及以下","id":"0"},{"name":"高中","id":"0"},{"name":"中技",
     * "id":"0"},{"name":"中专","id":"0"},{"name":"大专","id":"0"},{"name":"本科","id":"0"},
     * {"name":"硕士","id":"0"},{"name":"MBA","id":"0"},{"name":"EMBA","id":"0"},{"name":"博士",
     * "id":"0"},{"name":"博士后","id":"0"}]},{"type":0,"resource":0,"title":"年收入","data":[]},
     * {"type":1,"resource":1,"title":"移动运营商","data":[{"name":"中国电信","id":"0"},{"name":"中国联通",
     * "id":"0"},{"name":"中国移动","id":"0"},{"name":"其他","id":"0"}]},{"type":1,"resource":0,
     * "title":"手机系统","data":[{"name":"iOS","id":"0"},{"name":"Android","id":"0"},{"name":"其他",
     * "id":"0"}]},{"type":0,"resource":0,"title":"职业","data":[]},{"type":4,"resource":1,
     * "title":"用户商业兴趣","data":[{"name":"教育","id":"0"},{"name":"旅游","id":"0"},{"name":"金融",
     * "id":"0"},{"name":"汽车","id":"0"},{"name":"房产","id":"0"},{"name":"家居","id":"0"},
     * {"name":"服饰","id":"0"},{"name":"时尚珠宝","id":"0"},{"name":"餐饮","id":"0"},{"name":"生活服务",
     * "id":"0"},{"name":"美容美发","id":"0"},{"name":"互联网","id":"0"},{"name":"电子产品","id":"0"},
     * {"name":"运动户外","id":"0"},{"name":"医疗健康","id":"0"},{"name":"孕产育婴","id":"0"},{"name":"游戏",
     * "id":"0"},{"name":"政法","id":"0"},{"name":"娱乐休闲","id":"0"}]}]
     */

    private int code;
    private String language;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * type : 1
         * resource : 1
         * title : 性别
         * data : [{"name":"男","id":"0"},{"name":"女","id":"0"}]
         */

        private int type;
        private int resource;
        private String title;
        private List<DataBean> data;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getResource() {
            return resource;
        }

        public void setResource(int resource) {
            this.resource = resource;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : 男
             * id : 0
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
