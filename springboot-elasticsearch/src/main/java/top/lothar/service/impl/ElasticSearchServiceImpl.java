package top.lothar.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.lothar.entity.EsLive;
import top.lothar.entity.EsTeacher;
import top.lothar.esenum.EnumElasticsearchOperator;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.model.Teacher;
import top.lothar.repository.LiveRepository;
import top.lothar.repository.TeacherRepository;
import top.lothar.service.ElasticSearchService;
import top.lothar.service.TeacherService;
import top.lothar.util.EntityResultResponse;
import top.lothar.util.EnumSystem;
import top.lothar.vo.ElasticsearchRequestVO;

import java.math.BigDecimal;
import java.util.*;

/**
 * <h1>搜索引擎操作逻辑接口实现</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 10:41
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    //操作ES API   transport 的 ElasticsearchTemplate
    //@Autowired
    //private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private LiveRepository liveRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    /**
     * 查找索引
     * @param type
     * @param id
     * @return
     */
    @Override
    public EntityResultResponse<Object> findById(Integer type, Integer id) {
        //获取索引名称
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                Optional<EsTeacher> eDo = teacherRepository.findById(id);
                return new EntityResultResponse<>(eDo);
            } else if (EsTypeEnum.LIVE.code() == type) {
                Optional<EsLive> eDo = liveRepository.findById(id);
                return new EntityResultResponse<>(eDo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除索引
     * @param type
     * @return
     */
    @Override
    public EntityResultResponse<Object> deleteById(Integer type,Integer id) {
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                teacherRepository.deleteById(id);
            } else if (EsTypeEnum.LIVE.code() == type) {
                liveRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResultResponse<>();
    }

    /**
     * 创建索引
     * @param type
     * @return
     */
    @Override
    public EntityResultResponse<Object> createIndex(Integer type) {
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                createIndex(indexName, EsTeacher.class);
            } else if (EsTypeEnum.LIVE.code() == type) {
                createIndex(indexName, EsLive.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResultResponse<>();
    }

    @Override
    public EntityResultResponse<Object> updateByIds(Integer type, List<Integer> idList) {
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (idList != null && !idList.isEmpty()) {
                // ID不为空 先清一遍 [Teacher id 先清理 在添加 保持最新]
                idList.forEach(item -> {
                    deleteById(type, item);
                });
            }
            if (EsTypeEnum.TEACHER.code() == type) {
                //校验索引是否存在
                if (!elasticsearchRestTemplate.indexExists(EsTypeEnum.getIndexByCode(type))) {
                    elasticsearchRestTemplate.createIndex(EsTeacher.class);
                }
                // 从数据库查处所有老师信息并存储Es
                List<EsTeacher> list = new ArrayList<>();
                List<Teacher> teacherInfo = teacherService.getTeacherInfo();
                for (int i = 0; i < teacherInfo.size(); i++){
                    list.add(new EsTeacher(teacherInfo.get(i).getId(),teacherInfo.get(i).getName(),teacherInfo.get(i).getRemark()));
                }
                if (idList == null) {
                    teacherRepository.deleteAll();
                }
                if (list != null && !list.isEmpty()) {
                    teacherRepository.saveAll(list);
                }
            } else if (EsTypeEnum.LIVE.code() == type) {
                if (!elasticsearchRestTemplate.indexExists(EsTypeEnum.getIndexByCode(type))) {
                    elasticsearchRestTemplate.createIndex(EsLive.class);
                }
                //TODO 根据IDList查询数据库Live数据,暂时写死
                List<EsLive> list = new ArrayList<>();
                for (long i = 1; i < 200; i++){
                    EsLive esLive = new EsLive(i,"课程"+i,new BigDecimal(99.00),"赵路通",new Date());
                    list.add(esLive);
                }
                if (idList == null){
                    liveRepository.deleteAll();
                }
                if (list!=null && !list.isEmpty()){
                    liveRepository.saveAll(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResultResponse<>();
    }

    @Override
    public <T> List<T> searchCommon(Integer type, String keyword, Integer page, Integer size, Class<T> c, Integer detailType) {
        //索引名称
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return null;
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                return searchEs(elasticsearchRestTemplate.getClient(), EsTeacher.getSearchRequest(keyword, page, size), c);
            } else if (EsTypeEnum.LIVE.code() == type) {
                return searchEs(elasticsearchRestTemplate.getClient(), EsLive.getSearchRequest(keyword, page, size), c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param type  搜索类型 ： 0 直播课 1 老师
     * @param keyword 搜索关键字
     * @param page
     * @param size
     * @return
     */
    @Override
    public EntityResultResponse<Object> search(Integer type, String keyword, Integer page, Integer size) {
        //索引名称
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                List<EsTeacher> tList = searchEs(elasticsearchRestTemplate.getClient(), EsTeacher.getSearchRequest(keyword, page, size), EsTeacher.class);
                return new EntityResultResponse<>(tList);
            } else if (EsTypeEnum.LIVE.code() == type) {
                //List<Live> list = searchEs(elasticsearchRestTemplate.getClient(), Live.getSearchRequest(keyword, page, size, null), EsZhiboDO.class);
                //return new EntityResultResponse<>(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResultResponse<>();
    }

    @Override
    public EntityResultResponse<Object> esIndexOperator(String jsonData) {
        try {
            // 解析数据类型
            ElasticsearchRequestVO requestVO = JSON.parseObject(jsonData, ElasticsearchRequestVO.class);
            if (requestVO != null) {
                if (StringUtils.isEmpty(requestVO.getReqOperator()) || StringUtils.isEmpty(requestVO.getReqType())) {
                    return new EntityResultResponse<>(EnumSystem.ERROR);
                }
                int reqType = requestVO.getReqType();
                int reqOperator = requestVO.getReqOperator();
                Integer reqId = requestVO.getId();

                //删除操作
                if (reqOperator == EnumElasticsearchOperator.ELASTIC_DELETE.type()) {
                    // 删除
                    deleteById(reqType, reqId);
                } else if (reqOperator == EnumElasticsearchOperator.ELASTIC_UPDATE.type() || reqOperator == EnumElasticsearchOperator.ELASTIC_ADD.type()) {
                    // 更新/新增操作
                    List<Integer> idList = new ArrayList<>();
                    if (!StringUtils.isEmpty(reqId)) {
                        idList.add(reqId);
                    } else {
                        idList = null;
                    }
                    updateByIds(reqType, idList);
                }
            }
            return new EntityResultResponse<>();
        } catch (Exception e) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
    }


    /**
     * 创建索引
     * @param indexName 索引名称
     * @param c
     * @param <T>
     */
    private <T> void createIndex(String indexName, Class<T> c) {
        try {
            if (elasticsearchRestTemplate.indexExists(indexName)) {
                elasticsearchRestTemplate.deleteIndex(indexName);
            }
            elasticsearchRestTemplate.createIndex(c);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 携带检索request 高亮等设置 查询
     * @param client
     * @param request
     * @param c
     * @param <T>
     * @return
     */
    private <T> List<T> searchEs(RestHighLevelClient client, SearchRequest request, Class<T> c) {
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                Map<String, Object> source = hit.getSourceAsMap();
                for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
                    if (entry.getValue() != null) {
                        Text[] fragments = entry.getValue().fragments();
                        String name = "";
                        if (fragments != null && fragments.length > 0) {
                            name += fragments[0];
                        }
                        source.put(entry.getKey(), name);   //高亮字段替换掉原本的内容
                        if ("itemList.name".equals(entry.getKey())) {
                            if (!source.containsKey("subTitle") || StringUtils.isEmpty(source.get("subTitle"))) {
                                source.put("subTitle", name);
                            }
                        }
                    }
                }
                res.add(JSON.parseObject(JSON.toJSONString(source), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
