package top.lothar.service;

import top.lothar.util.EntityResultResponse;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/26 15:57
 */
public interface SearchService {

    /**
     * 查询
     * @param userId
     * @param searchType
     * @param filter
     * @param pageNo
     * @param size
     * @return
     */
    EntityResultResponse<Object> queryByFilterKeyWord(int userId, Integer searchType, String filter, Integer pageNo, Integer size);

}
