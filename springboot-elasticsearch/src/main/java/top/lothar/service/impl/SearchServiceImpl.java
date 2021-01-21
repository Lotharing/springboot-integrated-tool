package top.lothar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.lothar.entity.Live;
import top.lothar.entity.Teacher;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.esenum.SearchTypeEnum;
import top.lothar.service.ElasticSearchService;
import top.lothar.service.SearchService;
import top.lothar.util.EntityResultResponse;
import top.lothar.vo.SearchResp;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/26 15:59
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticSearchService elasticSearchService;
    /**
     *
     * @param userId
     * @param searchType ${@link SearchTypeEnum}  0 全部  1 课程  2 直播课   5 老师
     * @param filter
     * @param pageNo
     * @param size
     * @return
     */
    @Override
    public EntityResultResponse<Object> queryByFilterKeyWord(int userId, Integer searchType, String filter, Integer pageNo, Integer size) {
        SearchResp resp = new SearchResp();
        if (StringUtils.isEmpty(filter)) {
            return new EntityResultResponse<>(resp);
        }
        //查询标记
        boolean course = false;
        boolean teacher = false;
        boolean live = false;
        //全部
        if (SearchTypeEnum.ALL.code() == searchType) {
            teacher = true;
            live = true;
            course = true;
        }
        //课程
        if (SearchTypeEnum.COURSE.code() == searchType) {
            teacher = false;
            live = false;
            course = true;
        }
        //直播课
        if (SearchTypeEnum.LIVE.code() == searchType) {
            teacher = false;
            live = true;
            course = false;
        }
        //老师
        if (SearchTypeEnum.TEACHER.code() == searchType) {
            teacher = true;
            live = false;
            course = false;
        }

        /**
         * 导师查询
         */
        if (teacher){
            List<Teacher> tList = elasticSearchService.searchCommon(EsTypeEnum.TEACHER.code(), filter, pageNo, size, Teacher.class, null);
            if (tList != null && !tList.isEmpty()) {
                //因为是DEMO级别,这里可以把复杂的字段多的 Teacher 简化一些 返回
                List<Teacher> teacherResp = new ArrayList<>();
                tList.forEach(item -> {
                    teacherResp.add(item);
                });
                //最上层搜索结果包装
                resp.setTeacherList(teacherResp);
            }
        }
        // 课程查询
        if (live){
            List<Live> liveList = elasticSearchService.searchCommon(EsTypeEnum.LIVE.code(), filter, pageNo, size, Live.class,null);
            // 省略简化返回
            resp.setLiveList(liveList);
        }

        //TODO 继续进行 直播课 的查询

        return new EntityResultResponse<>(resp);
    }
}
