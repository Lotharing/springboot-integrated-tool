package top.lothar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.lothar.entity.Goods;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
    /**
     * 查询id区间的数据
     * @param goodsId1
     * @param goodsId2
     * @return
     */
    List<Goods> findAllByGoodsIdBetween(Long goodsId1,Long goodsId2);

    /**
     * 查询ids数据
     * @param goodsIds
     * @return
     */
    List<Goods> findAllByGoodsIdIn(List<Long> goodsIds);

}
