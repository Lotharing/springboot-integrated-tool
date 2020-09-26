package top.lothar.vo;

import lombok.Data;
import top.lothar.entity.Live;
import top.lothar.entity.Teacher;

import java.util.List;

/**
 * 搜索引擎 查询返回VO
 */
@Data
public class SearchResp {
    List<Teacher> teacherList;
    List<Live> LiveList;
}
