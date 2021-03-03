package top.lothar.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.lothar.entity.click.EsClickDO;

/**
 * <h1>点击事件 Resp ES API</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/01/23 17:44
 */
public interface ClickRepository extends ElasticsearchRepository<EsClickDO, Integer> {
}
