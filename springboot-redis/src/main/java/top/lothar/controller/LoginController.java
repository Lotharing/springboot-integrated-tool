package top.lothar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lothar.redis.RedisService;
import top.lothar.redis.UserKey;
import top.lothar.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisService redisService;

    private static final String  userKey = ":LoginInfo";

    /**
     * Login Method
     * @param userId
     * @param name
     * @return
     */
    @RequestMapping("/to_login")
    @ResponseBody
    public Map<String,Object> login(@RequestParam("user_id")int userId,@RequestParam("name")String name){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        if (checkLoginInfo(userId,name)){
            UserVo userVo = new UserVo(userId,name);
            Boolean flag = redisService.set(UserKey.getByUserId,userKey,userVo);
            if (flag){
                modelMap.put("success",true);
                modelMap.put("msg","登录信息已经存入Redis");
            }
        }
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/getUserInfo")
    public UserVo getLoginInfo(){
        UserVo redisUserVo = redisService.get(UserKey.getByUserId,userKey,UserVo.class);
        return redisUserVo;
    }

    @ResponseBody
    @RequestMapping("/del_loginInfo")
    public Boolean delLoginInfo(){
        return redisService.delete(UserKey.getByUserId, userKey);
    }

    /**
     * 登录信息检查，可引入数据库
     * @param id
     * @param name
     * @return
     */
    public Boolean checkLoginInfo(int id,String name){
        if (id==1 && name.equals("tong")){
            return true;
        }else{
            return false;
        }
    }

}
