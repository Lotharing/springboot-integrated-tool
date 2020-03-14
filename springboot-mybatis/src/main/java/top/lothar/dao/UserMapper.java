package top.lothar.dao;


import org.springframework.stereotype.Repository;
import top.lothar.entity.User;
import java.util.List;

@Repository
public interface UserMapper {

    List<User> getUserList();

}
