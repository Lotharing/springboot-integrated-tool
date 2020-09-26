package top.lothar.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.lothar.entity.Live;

/**
 * <h1>直播 Resp ES API</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 14:59
 */
@Repository
public interface LiveRepository extends ElasticsearchRepository<Live,Integer> {
    //DONE Live 直播操作ES接口 API
}
