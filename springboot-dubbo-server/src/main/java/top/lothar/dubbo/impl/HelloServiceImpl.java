package top.lothar.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import top.lothar.dubbo.HelloService;
import top.lothar.pojo.Person;

@Service(version = "1.0.0",timeout = 50000)
public class HelloServiceImpl implements HelloService {

    @Override
    public String SayHello(Person person) {
        return "Hello "+person.getName();
    }
}
