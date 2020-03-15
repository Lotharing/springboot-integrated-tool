package top.lothar.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.lothar.entity.User;

import java.util.List;
import java.util.Map;

@Repository
public interface UserPlusMapper extends BaseMapper<User> {

    List<User> getUserListPlus();

    List<User> selectByMap(@Param("map")Map<String,Object> map);

}
