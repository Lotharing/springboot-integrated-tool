package top.lothar.service;

import top.lothar.model.EsTeacher;
import top.lothar.util.EntityResultResponse;

import java.util.List;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 15:38
 */
public interface TeacherService {
    /**
     * 添加教师信息
     * @return
     */
    EntityResultResponse<Object> addTeacherInfo();

    /**
     * 获取导师信息
     * @return
     */
    List<EsTeacher> getTeacherInfo();
}
