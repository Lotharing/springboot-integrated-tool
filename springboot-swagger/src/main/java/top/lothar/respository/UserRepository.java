package top.lothar.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.lothar.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * 获取所有账户信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据ID查询
     * @param integer
     * @return
     */
    Optional<User> findById(Integer integer);
}
