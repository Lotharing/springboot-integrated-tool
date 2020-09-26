package top.lothar.esenum;

import org.springframework.util.StringUtils;

/**
 *  ES索引 业务类型
 */
public enum EsTypeEnum {
    LIVE(0, "直播课", "project_live"),
    TEACHER(1, "老师", "project_teacher"),;

    private int code;
    //描述
    private String desc;
    //ES索引名
    private String index;

    public int code(){return this.code;}
    public String desc(){return this.desc;}
    public String index(){return this.index;}

    EsTypeEnum(int code, String desc, String index){
        this.code = code;
        this.desc = desc;
        this.index = index;
    }

    /**
     * 根据code获取对应搜索索引名
     */
    public static String getIndexByCode(Integer code){
        if(StringUtils.isEmpty(code)) return "";
        for(EsTypeEnum one: EsTypeEnum.values()){
            if(one.code == code){
                return one.index;
            }
        }
        return "";
    }

    public static boolean exists(EsTypeEnum typeEnum){
        if(StringUtils.isEmpty(typeEnum)) return false;
        for(EsTypeEnum one: EsTypeEnum.values()){
            if(one.code == typeEnum.code){
                return true;
            }
        }
        return false;
    }
}
