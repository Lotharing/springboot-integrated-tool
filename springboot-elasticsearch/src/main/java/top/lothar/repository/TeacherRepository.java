package top.lothar.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.lothar.entity.Teacher;

/**
 * <h1>老师Resp ES API</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 14:59
 */
@Repository
public interface TeacherRepository extends ElasticsearchRepository<Teacher, Integer> {
    //DONE Teacher 老师操作ES接口 API
}
