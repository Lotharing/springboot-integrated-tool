/*
 * Copyright 2019. All rights reserved.
 * 随心瑜（北京）科技有限公司
 */

package top.lothar.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ElasticsearchRequestVO class
 *
 * @author jy
 * @date 2020/02/24
 */
@Data
public class ElasticsearchRequestVO implements Serializable {
    /**
     * 请求类型 0 直播 1 老师
     */
    private Integer reqType;
    /**
     * 请求操作 0 新增 1更新 2删除
     */
    private Integer reqOperator;
    private Integer id;
    private Map<String, Object> data = new HashMap<>();
}
