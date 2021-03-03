package top.lothar.model;



/**
 * <h1>对应点击事件转换</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 17:47
 */

public class ClickElement {

    private Integer id;

    private String label;

    private String page;

    private String position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
