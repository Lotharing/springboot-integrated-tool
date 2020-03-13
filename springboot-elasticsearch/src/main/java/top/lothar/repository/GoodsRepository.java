package top.lothar.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Component;
import top.lothar.entity.Goods;

@Component
public interface GoodsRepository extends ElasticsearchCrudRepository<Goods,Long> {

}
