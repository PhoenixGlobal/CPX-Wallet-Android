package chinapex.com.wallet.bean;

import java.util.List;

/**
 * Created by SteelCabbage on 2018/7/25 0025 15:43.
 * E-Mail：liuyi_61@163.com
 */

public class PortraitBean {

    /**
     * type : 1
     * resource : 1
     * title : 性别
     * data : [{"name":"男","id":"0"},{"name":"女","id":"0"}]
     */

    private int type;
    private int resource;
    private String title;
    private List<PortraitTagsBean> data;
    private String selectedContent;

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

    public List<PortraitTagsBean> getData() {
        return data;
    }

    public void setData(List<PortraitTagsBean> data) {
        this.data = data;
    }

    public String getSelectedContent() {
        return selectedContent;
    }

    public void setSelectedContent(String selectedContent) {
        this.selectedContent = selectedContent;
    }

    @Override
    public String toString() {
        return "PortraitBean{" +
                "type=" + type +
                ", resource=" + resource +
                ", title='" + title + '\'' +
                ", data=" + data +
                ", selectedContent='" + selectedContent + '\'' +
                '}';
    }


}
