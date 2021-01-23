package top.lothar.vo;

import lombok.Data;
import top.lothar.entity.EsLive;
import top.lothar.entity.EsTeacher;

import java.util.List;

/**
 * 搜索引擎 查询返回VO
 */
@Data
public class SearchResp {
    List<EsTeacher> esTeacherList;
    List<EsLive> esLiveList;
}
