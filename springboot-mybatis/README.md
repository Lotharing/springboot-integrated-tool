## SpringBoot v2.2.4 + Mybatis v2.1.0



- 搭建

    1.添加依赖 mybatis依赖 mysql驱动

    2.application.properties中配置datasource,mybatis,log等
    
    3.编写Mapper XML->Service(省略)->Controller
    
    4.可根据Mybatis API 进行开发 [Mybatis API](https://mybatis.org/mybatis-3/zh/java-api.html)
    
- PageHelper分页插件的使用

    1.添加依赖(pagehelper-spring-boot-autoconfigure)缺少这个会出现分页失败查询出所有数据
    ```
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.0.4</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-autoconfigure</artifactId>
            <version>1.2.7</version>
        </dependency>
    ```
    2.请求中携带分页信息  
    ```
        PageHelper.startPage(pageNum,pageSize);这个设置分页信息，可以查看PageHelper源码
        Page<T> / PageInfo<T> .. 接收分页数据
    ```
- Mybatis-Plus

    1.引入Mybatis的依赖  
    
    2.写一个没有方法的接口实现BaseMapper
    
    3.引入这个方法,看BaseMapper源码中的实现方法,通常不用写接口方法和对应Mapper.xml
    
    
- Mybatis-generator 逆向工程生成插件
    
    1.pom中plugins中增加依赖
    ````
                <!--mybatis自动生成代码插件-->
                <plugin>
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-maven-plugin</artifactId>
                    <version>1.3.6</version>
                    <configuration>
                        <!-- 是否覆盖，true表示会替换生成的JAVA文件，false则不覆盖 -->
                        <overwrite>true</overwrite>
                    </configuration>
                    <dependencies>
                        <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                            <version>5.1.47</version>
                        </dependency>
                    </dependencies>
                </plugin>
    ````
    2.resources下建立generatorConfig文件（配置可参考文件）
    
    3.在Maven插件中运行此插件，在对应包下就可以生成mapper / mapper.xml / pojo 