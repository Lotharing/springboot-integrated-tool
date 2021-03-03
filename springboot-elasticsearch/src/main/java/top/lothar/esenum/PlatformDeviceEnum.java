package top.lothar.esenum;

import org.springframework.util.StringUtils;

/**
 * 平台信息0 安卓 1 IOS  2 公众号
 */
public enum PlatformDeviceEnum {
    ANDROID(0, "Android"),
    IOS(1, "iphone"),
    H5(2, "公众号网页"),
    ;

    private final int code;
    private final String desc;

    public int code(){return this.code;}

    public String desc(){return this.desc;}

    PlatformDeviceEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static PlatformDeviceEnum getByCode(Integer code){
        if(StringUtils.isEmpty(code)) return null;
        for(PlatformDeviceEnum one: PlatformDeviceEnum.values()){
            if(one.code == code){
                return one;
            }
        }
        return null;
    }

}
