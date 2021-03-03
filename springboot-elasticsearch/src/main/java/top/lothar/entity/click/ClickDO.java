package top.lothar.entity.click;

import lombok.Data;

import java.util.Date;

/**
 * 点击事件收集
 */
@Data
public class ClickDO {
    /**
     * 唯一lable获取位置信息用
     */
    private String label;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 平台信息 0:安卓, 1:IOS, 2:公众号
     */
    private Integer platform;
    /**
     * 目标点击课程ID
     */
    private String target;
    /**
     * 时间
     */
    private Date time;
}
