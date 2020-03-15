package top.lothar.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.entity.User;
import top.lothar.respository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/users")
@Api(value="操作用户接口",tags={"操作用户接口"})
public class UserSwagger {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @GetMapping(value="/getUserList")
    public List getUserList(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "根据ID查询用户信息",notes = "获取用户信息")
    @GetMapping(value = "/getUserById")
    public Optional<User> getUserById(){return  userRepository.findById(1);}


    /**
     * 其他 userRepository.jpa操作可自行查看源码
     */
}
