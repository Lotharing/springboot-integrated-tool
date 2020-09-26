package top.lothar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.entity.Teacher;
import top.lothar.service.ElasticSearchService;
import top.lothar.service.SearchService;
import top.lothar.util.EntityResultResponse;
import top.lothar.util.EnumSystem;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 15:09
 */
@RestController
public class EsOperationController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private SearchService searchService;

    /**
     * 建立索引
     * @return
     */
    @GetMapping("createIndex")
    public EntityResultResponse<Object> save(){
        Teacher teacher = new Teacher(1,"Tony","First-class technology");
        //创建 indexName [project_live]  直播课 索引
        elasticSearchService.createIndex(0);
        //创建 indexName [project_teacher] 老师 索引
        elasticSearchService.createIndex(1);
        return new EntityResultResponse<Object>(EnumSystem.SUCCESS);
    }

    /**
     * 删除索引
     * @return
     */
    @GetMapping("deleteIndex")
    public EntityResultResponse<Object> delete(){
        //删除索引
        elasticSearchService.deleteById(0);
        elasticSearchService.deleteById(1);
        return new EntityResultResponse<Object>(EnumSystem.SUCCESS);
    }

    /**
     * 根据关键字查询
     * @return
     */
    @GetMapping("query")
    public EntityResultResponse queryByFilterKeyWord(){
        return searchService.queryByFilterKeyWord(1,5,"直播",1,50);
    }



}
