package top.lothar.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.entity.Goods;
import top.lothar.repository.GoodsRepository;

import java.util.Optional;


@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("save")
    public String save(){
        Goods goodsInfo = new Goods(1L, "商品"+System.currentTimeMillis(),"ElasticSearch-Goods");
        goodsRepository.save(goodsInfo);
        return "success";
    }

    @GetMapping("getOne")
    public Goods getOne(Long id){
        Optional<Goods> goods = goodsRepository.findById(id);
        Goods result = goods.get();
        return result;
    }



}
