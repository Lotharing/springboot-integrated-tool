package top.lothar.util;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElasticUtil {
    private static Logger log = LoggerFactory.getLogger(ElasticUtil.class);

    private ElasticUtil(){}

    public static Class<?> getClazz(String clazzName){
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param queryBuilder  设置查询对象
     * @param from  设置from选项，确定要开始搜索的结果索引。 默认为0。
     * @param size  设置大小选项，确定要返回的搜索匹配数。 默认为10。
     * @param timeout
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @throws
     * @since
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size, int timeout){

        //使用默认选项创建 SearchSourceBuilder 。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置查询对象。可以使任何类型的 QueryBuilder
        sourceBuilder.query(queryBuilder);
        //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.from(from);
        //设置大小选项，确定要返回的搜索匹配数。 默认为10。
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeout, TimeUnit.SECONDS));
        sourceBuilder.highlighter(getHighlightBuilder(null));
        return sourceBuilder;
    }

    /**
     * @param queryBuilder
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @throws
     * @since
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder){
        return initSearchSourceBuilder(queryBuilder,0,10,60);
    }

    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size){
        return initSearchSourceBuilder(queryBuilder, from, size,60);
    }

    public static SearchRequest getSearchRequest(String indexName, String keyword, int page, int size){
        page = page >0 ? (page -1) * size : page* size;
        size = size > 0 ? size : 10;
        SearchRequest request = new SearchRequest(indexName);
        MatchQueryBuilder queryBuilders=  QueryBuilders.matchQuery("searchKeyword", keyword);
        SearchSourceBuilder searchSourceBuilder = ElasticUtil.initSearchSourceBuilder(queryBuilders, page, size);
        request.source(searchSourceBuilder);
        return request;
    }

    public static HighlightBuilder getHighlightBuilder(List<String > fieldList){
        HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        if(fieldList == null || fieldList.isEmpty()){
            highlightBuilder.field("name");      //高亮查询字段
            highlightBuilder.field("title");      //高亮查询字段
            highlightBuilder.field("userpost");      //高亮查询字段
            highlightBuilder.field("teacherName");      //高亮查询字段
            highlightBuilder.field("itemList.name");      //高亮查询字段
        } else {
           fieldList.forEach(item ->{
               highlightBuilder.field(item);
           });
        }
        highlightBuilder.requireFieldMatch(false);     //如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style=\"color:#ff6337\">");   //高亮设置
        highlightBuilder.postTags("</span>");

        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
        return highlightBuilder;
    }
}
