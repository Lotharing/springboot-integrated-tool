package top.lothar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.dao.UserMapper;
import top.lothar.entity.User;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getUserList")
    private String getUserList(){
        List<User> user = userMapper.getUserList();
        return user.toString();
    }

}
