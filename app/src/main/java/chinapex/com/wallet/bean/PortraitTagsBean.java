package chinapex.com.wallet.bean;

/**
 * Created by SteelCabbage on 2018/7/26 0026 9:59.
 * E-Mail：liuyi_61@163.com
 */

public class PortraitTagsBean {
    private String name;
    private String id;
    private boolean isChecked;

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "PortraitTagsBean{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
