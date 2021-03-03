package top.lothar.service;

import top.lothar.entity.click.ClickDO;

/**
 * <h1>点击事件收集Es接口</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 17:41
 */
public interface EsClickService {
    /**
     * 通用存储ES点击信息
     * @param clickDO
     */
    void saveCommonClick(ClickDO clickDO);
}
