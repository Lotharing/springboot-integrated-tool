package top.lothar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.entiry.User;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //http://localhost:8888/jdbc/getMapById?id=1
    @GetMapping("getMapById")
    public Map getMapById(Integer id){
        String sql = "SELECT * FROM sd_sys_user WHERE user_id = ?";
        Map map= jdbcTemplate.queryForMap(sql,id);
        return map;
    }


    //http://localhost:8888/jdbc/getUserById?id=1
    @GetMapping("getUserById")
    public User getUserById(Integer id){
        String sql = "SELECT * FROM sd_sys_user WHERE user_id = ?";
        User user= jdbcTemplate.queryForObject(sql,new User(),new Object[]{id});
        return user;
    }

}
