package top.lothar.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {z

    @RequestMapping("/thymeleaf")
    public String testThymeleaf(ModelMap modelMap){
        modelMap.addAttribute("msg", "Hello LuTong , this is thymeleaf");
        return "thymeleaf";
    }

}
