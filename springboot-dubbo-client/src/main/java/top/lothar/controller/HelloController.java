package top.lothar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.dubbo.HelloService;
import top.lothar.pojo.Person;

@RestController
public class HelloController {

    @Reference(version = "1.0.0")
    HelloService helloService;

    @GetMapping("sayHello")
    public String sayHello(String name){
        Person person = new Person();
        person.setName(name);
        return helloService.SayHello(person);
    }

}
