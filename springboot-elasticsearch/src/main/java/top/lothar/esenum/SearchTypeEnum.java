package top.lothar.esenum;

/**
 * 搜索类型
 */
public enum SearchTypeEnum {
    ALL(0, "全部"),
    COURSE(1, "课程"),
    LIVE(2, "直播课"),
    BATTALION(3, "训练营"),
    MILL(4, "工作坊"),
    TEACHER(5, "老师"),
    SHOP(6, "店铺");

    private int code;
    private String desc;

    public int code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }

    SearchTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
