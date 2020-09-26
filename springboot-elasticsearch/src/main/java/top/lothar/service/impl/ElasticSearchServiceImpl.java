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
import top.lothar.entity.Live;
import top.lothar.entity.Teacher;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.repository.LiveRepository;
import top.lothar.repository.TeacherRepository;
import top.lothar.service.ElasticSearchService;
import top.lothar.util.EntityResultResponse;
import top.lothar.util.EnumSystem;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        }        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                Optional<Teacher> eDo = teacherRepository.findById(id);
                return new EntityResultResponse<>(eDo);
            } else if (EsTypeEnum.LIVE.code() == type) {
                Optional<Live> eDo = liveRepository.findById(id);
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
    public EntityResultResponse<Object> deleteById(Integer type) {
        String indexName = EsTypeEnum.getIndexByCode(type);
        if (StringUtils.isEmpty(indexName)) {
            return new EntityResultResponse<>(EnumSystem.ERROR);
        }
        try {
            if (EsTypeEnum.TEACHER.code() == type) {
                elasticsearchRestTemplate.deleteIndex(Teacher.class);
            } else if (EsTypeEnum.LIVE.code() == type) {
                elasticsearchRestTemplate.deleteIndex(Live.class);
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
                createIndex(indexName, Teacher.class);
            } else if (EsTypeEnum.LIVE.code() == type) {
                createIndex(indexName, Live.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResultResponse<>();
    }

    @Override
    public EntityResultResponse<Object> updateByIds(Integer type, List<Integer> idList) {
        return null;
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
                return searchEs(elasticsearchRestTemplate.getClient(), Teacher.getSearchRequest(keyword, page, size), c);
            } else if (EsTypeEnum.LIVE.code() == type) {
                //return searchEs(elasticsearchRestTemplate.getClient(), Live.getSearchRequest(keyword, page, size), c);
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
                List<Teacher> tList = searchEs(elasticsearchRestTemplate.getClient(), Teacher.getSearchRequest(keyword, page, size), Teacher.class);
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
        return null;
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
