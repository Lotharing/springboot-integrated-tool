package top.lothar.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    /**
     * PageHelper 分页插件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getPageUserList")
    private Page<User> getPageUserList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<User> user = userMapper.getPageUserList();
        System.out.println("总数量：" + user.getTotal());
        System.out.println("当前页查询记录：" + user.getResult().size());
        System.out.println("当前页码：" + user.getPageNum());
        System.out.println("每页显示数量：" + user.getPageSize());
        System.out.println("总页：" + user.getPages());
        return user;
    }

    @RequestMapping("/getPageUserListExample")
    private PageInfo<User> getPageUserListExample(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.getPageUserListExample();
        PageInfo<User> page = new PageInfo<>(userList);
        System.out.println("总数量：" + page.getTotal());
        System.out.println("当前页查询记录：" + page.getList().size());
        System.out.println("当前页码：" + page.getPageNum());
        System.out.println("每页显示数量：" + page.getPageSize());
        System.out.println("总页：" + page.getPages());
        return page;
    }

}
