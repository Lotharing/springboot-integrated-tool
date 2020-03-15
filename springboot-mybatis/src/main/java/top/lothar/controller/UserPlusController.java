package top.lothar.controller;


//mybatis-plus Page
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.dao.UserPlusMapper;
import top.lothar.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("mybatisPlus")
@RestController
public class UserPlusController {

    @Autowired
    private UserPlusMapper userMapperPlus;

    @GetMapping("getUserListPlus")
    public List<User> getUserListPlus(){
        return userMapperPlus.getUserListPlus();
    }

    //条件查询
    @GetMapping("getUserListByAccount")
    public List<User> getUserListByAccount(String account) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("account", account);
        return userMapperPlus.selectByMap(map);
    }

    @GetMapping("getUserListByPage")
    public List<User> getUserListByPage(Integer pageNumber,Integer pageSize)
    {
        Page<User> page =new Page<>(pageNumber,pageSize);
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id", "1");
        return userMapperPlus.selectPage(page,entityWrapper);
    }

}
