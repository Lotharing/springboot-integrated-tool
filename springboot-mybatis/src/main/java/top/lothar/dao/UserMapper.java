package top.lothar.dao;


import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.lothar.entity.User;
import java.util.List;

@Repository
public interface UserMapper {

    List<User> getUserList();

    @Select("SELECT * FROM sd_sys_user")
    Page<User> getPageUserList();

    @Select("SELECT * FROM sd_sys_user")
    List<User> getPageUserListExample();

}
