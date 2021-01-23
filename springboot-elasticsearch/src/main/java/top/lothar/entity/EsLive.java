package top.lothar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.util.ElasticUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * public @interface Field {
 *
 * FieldType type() default FieldType.Auto; //自动检测属性的类型，可以根据实际情况自己设置
 *
 * FieldIndex index() default FieldIndex.analyzed; //默认情况下分词，一般默认分词就好，除非这个字段你确定查询时不会用到
 *
 * DateFormat format() default DateFormat.none; //时间类型的格式化
 *
 * String pattern() default "";
 *
 * boolean store() default false; //默认情况下不存储原文
 *
 * String searchAnalyzer() default ""; //指定字段搜索时使用的分词器
 *
 * String indexAnalyzer() default ""; //指定字段建立索引时指定的分词器
 *
 * String[] ignoreFields() default {}; //如果某个字段需要被忽略
 *
 * boolean includeInParent() default false;
 * }
 *
 *
 * 分词器说明
 *
 * 1、ik_max_word
 *
 * 会将文本做最细粒度的拆分，比如会将“中华人民共和国人民大会堂”拆分为“中华人民共和国、中华人民、中华、华人、人民共和国、人民、共和国、大会堂、大会、会堂等词语。
 *
 * 2、ik_smart
 * 会做最粗粒度的拆分，比如会将“中华人民共和国人民大会堂”拆分为中华人民共和国、人民大会堂。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "project_live",shards = 1,replicas = 0, type = "live",useServerConfiguration = true, createIndex = true)
public class EsLive implements Serializable {
    //主键ID
    @Id
    @Field(type = FieldType.Integer)
    private Long id;

    //课程名称
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    //课程价格
    @Field(type = FieldType.Double)
    private BigDecimal price;

    //老师名称
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String teacherName;

    //创建时间
    @Field(type = FieldType.Date)
    private Date createTime;

    public static SearchRequest getSearchRequest(String keyword, int page, int size) {
        page = page > 0 ? (page - 1) * size : page * size;
        size = size > 0 ? size : 10;
        //老师 1  索引
        /**
         * 创建搜索请求对象
         * new SearchRequest("posts","posts"); 查询多个文档库
         *
         */
        SearchRequest request = new SearchRequest(EsTypeEnum.LIVE.index());
        Map<String, Float> fields = new HashMap<>();
        fields.put("name", new Float(6.0));
        fields.put("teacherName", new Float(1.0));
        /**
         * 创建  搜索内容参数设置对象:SearchSourceBuilder
         * 相对于matchQuery，multiMatchQuery针对的是多个fi eld，也就是说，当multiMatchQuery中，fieldNames参数只有一个时，其作用与matchQuery相当；
         * 而当fieldNames有多个参数时，如field1和field2，那查询的结果中，要么field1中包含text，要么field2中包含text。
         */
        QueryBuilder queryBuilders=  QueryBuilders.multiMatchQuery(keyword, "name", "teacherName").fields(fields);
        //设置查询对象
        SearchSourceBuilder searchSourceBuilder = ElasticUtil.initSearchSourceBuilder(queryBuilders, page, size);
        //查询对象封进搜索请求对象
        request.source(searchSourceBuilder);
        return request;
    }

}
