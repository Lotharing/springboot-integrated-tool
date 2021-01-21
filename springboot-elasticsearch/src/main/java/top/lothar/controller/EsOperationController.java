package top.lothar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.entity.Teacher;
import top.lothar.service.ElasticSearchService;
import top.lothar.service.SearchService;
import top.lothar.util.EntityResultResponse;
import top.lothar.util.EnumSystem;
import top.lothar.vo.ElasticsearchRequestVO;

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
     * 建立索引 根据封装的 Teacher / Live 的 Es对应配置对象 建立索引
     * @return
     */
    @GetMapping("createIndex")
    public EntityResultResponse<Object> save(){
        //创建 indexName [project_live]  直播课 索引
        elasticSearchService.createIndex(0);
        //创建 indexName [project_teacher] 老师 索引
        elasticSearchService.createIndex(1);
        return new EntityResultResponse<Object>(EnumSystem.SUCCESS);
    }

    /**
     * 删除索引 下 数据
     * @return
     */
    @GetMapping("deleteIndex")
    public EntityResultResponse<Object> delete(){
        // type = 2 是直播  id 1 是删除索引下id 1 的直播
        elasticSearchService.deleteById(0,1);
        // type = 1 是老师  id 99 是删除索引下 id 99 的导师信息
        elasticSearchService.deleteById(1,99);
        return new EntityResultResponse<Object>(EnumSystem.SUCCESS);
    }

    /**
     * 根据关键字全局搜索引擎入口查询
     * @return
     */
    @GetMapping(value = "query",produces = "application/json;charset=utf-8")
    public EntityResultResponse<Object> queryByFilterKeyWord(@RequestParam("keyword")String keyword){
        return searchService.queryByFilterKeyWord(1,0,keyword,1,10);
    }

    /**
     * TODO 暂时报漏接口 , 后期可通过MQ传递数据存储ES
     * @return
     */
    @GetMapping("set/teacher/data")
    public EntityResultResponse setTeacherDataToES(){
        ElasticsearchRequestVO elasticsearchRequestVO = new ElasticsearchRequestVO();
        // 1 老师 0 直播课
        elasticsearchRequestVO.setReqType(0);
        // 一般是课程ID 或者 老师ID 在索引封装操作中 对此ID进行遍历删除 之后在同步相关数据 保持修改过的信息保持ES同步保持最新
        elasticsearchRequestVO.setId(1);
        // 操作封装 0 ADD 1 UPDATE 2 DELETE
        elasticsearchRequestVO.setReqOperator(0);
        return elasticSearchService.esIndexOperator(JSONObject.toJSONString(elasticsearchRequestVO));
    }

    /**
     * 根据索引类型 0 直播 1 老师  查找指定id Es index下数据
     * @param id
     * @return
     */
    @GetMapping("get/teacher")
    public EntityResultResponse findById(@RequestParam("id")Integer id){
        return elasticSearchService.findById(1,id);
    }


}
