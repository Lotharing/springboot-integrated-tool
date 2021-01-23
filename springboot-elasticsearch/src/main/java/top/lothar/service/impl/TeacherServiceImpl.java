package top.lothar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lothar.mapper.EsTeacherMapper;
import top.lothar.model.EsTeacher;
import top.lothar.service.TeacherService;
import top.lothar.util.EntityResultResponse;
import top.lothar.util.EnumSystem;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 15:39
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private EsTeacherMapper esTeacherMapper;

    @Override
    public EntityResultResponse<Object> addTeacherInfo() {
        for (int i = 2; i <= 100; i++){
            esTeacherMapper.insertTeacherInfo("路通-"+i,"帅爆炸了-"+i);
        }
        return new EntityResultResponse<Object>(EnumSystem.SUCCESS);
    }
}
