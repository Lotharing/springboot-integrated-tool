package top.lothar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class Live implements Serializable {
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

}
