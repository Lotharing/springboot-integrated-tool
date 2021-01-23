package top.lothar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/11/3 16:55
 */
@RestController
public class WebController {



    @ResponseBody
    @RequestMapping("/index")
    public String index(){
        return "Hello docker";
    }
}
