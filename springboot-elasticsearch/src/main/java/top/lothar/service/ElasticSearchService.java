package top.lothar.service;

import top.lothar.util.EntityResultResponse;

import java.util.List;

/**
 * <h1>搜索引擎操作逻辑接口</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 10:40
 */
public interface ElasticSearchService {

    EntityResultResponse<Object> findById(Integer type, Integer id);

    EntityResultResponse<Object> deleteById(Integer type);

    EntityResultResponse<Object> createIndex(Integer type);

    EntityResultResponse<Object> updateByIds(Integer type, List<Integer> idList);

    <T> List<T> searchCommon(Integer type, String keyword, Integer page, Integer size, Class<T> c, Integer detailType);

    EntityResultResponse<Object> search(Integer type, String keyword, Integer page, Integer size);

    EntityResultResponse<Object> esIndexOperator(String jsonData);

}
