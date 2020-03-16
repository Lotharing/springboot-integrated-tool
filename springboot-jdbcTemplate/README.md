## SpringBoot v2.2.4 + JdbcTemplate v2.2.4



- 搭建

    1.添加JdbcTemplate依赖 mysql驱动  
    ````
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-jdbc</artifactId>
                <version>2.2.4.RELEASE</version>
            </dependency>
    ````

    2.application.properties中配置数据库连接信息  
    
    3.编写实体实现 RowMapper<User> 接口 , 重写mapRow方法(结果集实体映射) ORM原理
    
    4.可根据 JdbcTemplate 的源码查看有哪些封装好可以使用的方法  
    
    5.方法介绍  
    ````
    1.createTable方法
    使用execute方法创建User表
    
    2.saveUserSql方法
    使用update方法，传入参数sql语句，直接执行插入操作
    
    3.saveUser方法
    使用update方法，传入sql语句和对应字段值，进行插入操作
    
    4.updateUserPassword方法
    使用update方法，传入sql语句和对应字段值，进行修改操作
    
    5.deleteUserById方法
    使用update方法，传入sql语句和对应字段值，进行删除操作
    
    6.batchSaveUserSql方法
    使用batchUpdate方法，传入sql和参数集合，进行批量更新
    
    7.getUserByUserName方法
    使用query方法，传入sql，实体对象，查询参数，这里就用到了实体类重写的mapRow方法
    
    8.getMapById方法
    使用queryForMap方法，传入sql和参数，返回Map
    
    9.getUserById方法
    使用queryForObject方法，传入sql，实体对象，查询参数，返回User实体类，这里也用到了实体类重写的mapRow方法
    
    ````
    
    更多方法可以查看官方API [JdbcTemplate API](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
    
