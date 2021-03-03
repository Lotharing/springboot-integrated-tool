package top.lothar.entity.click;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.lothar.esenum.PlatformDeviceEnum;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)  //chain 一个布尔值。如果为真，产生的setter返回的this而不是void。默认是假。
@Document(indexName = "project_click", shards = 1, replicas = 0, type = "_doc", useServerConfiguration = true, createIndex = true)

public class EsClickDO {

    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Keyword)
    private String platformInfo;

    @Field(type = FieldType.Keyword)
    private String label;

    @Field(type = FieldType.Keyword)
    private String page;

    @Field(type = FieldType.Keyword)
    private String position;

    @Field(type = FieldType.Keyword)
    private String target;

    @Field(type = FieldType.Date)
    private Date time;

    public EsClickDO(){}

    public EsClickDO(ClickDO clickDO){
        BeanUtils.copyProperties(clickDO, this);
        PlatformDeviceEnum platformEnum = PlatformDeviceEnum.getByCode(clickDO.getPlatform());
        if(platformEnum != null){
            this.platformInfo = platformEnum.desc();
        } else {
            this.platformInfo = "未知";
        }
    }

}
