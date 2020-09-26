package top.lothar.util;

public enum EnumSystem {

    SUCCESS(200, "成功"),
    ERROR(-1, "失败"),
    NOT_FOUND(404, "未找到"),
    ;

    private int code;

    private String desc;

    public int code() {
        return this.code;
    }

    ;

    public String desc() {
        return this.desc;
    }

    ;

    EnumSystem(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
