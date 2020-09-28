package top.lothar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.StringUtils;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.util.ElasticUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1></h1>
 *public @interface Document {
 *
 * String indexName(); //索引库的名称，个人建议以项目的名称命名
 *
 * String type() default ""; //类型，个人建议以实体的名称命名
 *
 * short shards() default 5; //默认分区数
 *
 * short replicas() default 1; //每个分区默认的备份数
 *
 * String refreshInterval() default "1s"; //刷新间隔
 *
 * String indexStoreType() default "fs"; //索引文件存储类型
 * }
 * @author LuTong.Zhao
 * @Date 2020/9/25 13:56
 *
 * 重启服务时不创建索引, useServerConfiguration = true, createIndex = false)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "project_teacher",shards = 1,replicas = 0,type = "teacher",useServerConfiguration = true, createIndex = true)
public class Teacher implements Serializable {

    //主键
    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    //老师名称
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    //老师备注
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String remark;

    /**
     * 导师分页查询
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public static SearchRequest getSearchRequest(String keyword, int page, int size) {
        page = page > 0 ? (page - 1) * size : page * size;
        size = size > 0 ? size : 10;
        //老师 1  索引
        SearchRequest request = new SearchRequest(EsTypeEnum.TEACHER.index());

        Map<String, Float> fields = new HashMap<>();
        fields.put("name", new Float(6.0));
        fields.put("remark", new Float(1.0));

        QueryBuilder queryBuilders=  QueryBuilders.multiMatchQuery(keyword, "name", "remark").fields(fields);
        SearchSourceBuilder searchSourceBuilder = ElasticUtil.initSearchSourceBuilder(queryBuilders, page, size);
        request.source(searchSourceBuilder);
        return request;
    }

}
