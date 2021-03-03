package top.lothar.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.lothar.model.ClickElement;

/**
 * <h1>点击事件 mapper</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 17:50
 */
@Repository
public interface ClickElementMapper {
    /**
     * 查找对应label所属位置等信息
     * @param label
     * @return
     */
    ClickElement getClickElementByLabel(@Param("label")String label);
}
