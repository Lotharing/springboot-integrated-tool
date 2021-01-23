package top.lothar.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import top.lothar.model.EsTeacher;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 15:32
 */
@Repository
public interface EsTeacherMapper {
    /**
     * 添加教师信息
     * @param name
     * @param remark
     */
    void insertTeacherInfo(@Param("name")String name,@Param("remark")String remark);
    /**
     * 获取教师信息
     * @param id
     * @return
     */
    EsTeacher getTeacherInfo(@Param("id")Integer id);
}
