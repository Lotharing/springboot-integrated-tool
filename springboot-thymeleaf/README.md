#### SpringBoot2.2.4+Thymeleaf

1.引入依赖
````
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        同时为了解决html严格校验报错的问题，增添依赖nekohtml
        <dependency>
            <groupId>net.sourceforge.nekohtml</groupId>
            <artifactId>nekohtml</artifactId>
            <version>1.9.15</version>
        /dependency>
````

2.spring.thymeleaf.prefix=classpath:/templates/  配置HTML页面路径，编写Controller返回String @RequestMapping注解 自动通过DispatchServlet 视图解析

3.页面可以获取到modelMap中的信息

