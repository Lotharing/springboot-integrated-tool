#### SpringBoot2.2.4+Swagger2.8.0

- Restful API 在线接口文档

0.Swagger Codegen: 通过Codegen 可以将描述文件生成html格式和cwiki形式的接口文档，同时也能生成多钟语言的服务端和客户端的代码。支持通过jar包，docker，node等方式在本地化执行生成。也可以在后面的Swagger Editor中在线生成。
  
  Swagger UI:提供了一个可视化的UI页面展示描述文件。接口的调用方、测试、项目经理等都可以在该页面中对相关接口进行查阅和做一些简单的接口请求。该项目支持在线导入描述文件和本地部署UI项目。
  
  Swagger Editor: 类似于markendown编辑器的编辑Swagger描述文件的编辑器，该编辑支持实时预览描述文件的更新效果。也提供了在线编辑器和本地部署编辑器两种方式。
  
  Swagger Inspector: 感觉和postman差不多，是一个可以对接口进行测试的在线版的postman。比在Swagger UI里面做接口请求，会返回更多的信息，也会保存你请求的实际请求参数等数据。
  
  Swagger Hub：集成了上面所有项目的各个功能，你可以以项目和版本为单位，将你的描述文件上传到Swagger Hub中。在Swagger Hub中可以完成上面项目的所有工作，需要注册账号，分免费版和收费版。
  
  
1.Pom.xml引入Swagger依赖
````
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.8.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.8.0</version>
        </dependency>
````

2.编写SwaggerConfig配置Swagger页面信息,接口位置扫描等

3.数据访问接口集成 jpa repository
````
public interface UserRepository extends JpaRepository<User,Integer> {}

````

4.Entity 中使用@Table @Entity @ApiModel  @ApiModelProperty @GeneratedValue @Id @Column @NULL @NOTNULL ... 等注解


5.编写接口访问层Controller(具体JPA使用方法可以查看JpaRepository源码)使用以下注解标注,从而可以自动生成Swagger API文档
````
 @Api()用于类,表示标识这个类是swagger的资源 
 
 @ApiOperation()用于方法,表示一个http请求的操作 
 
 @ApiParam()用于方法，参数，字段说明,表示对参数的添加元数据（说明或是否必填等） 
 
 @ApiModel()用于类,表示对类进行说明，用于参数用实体类接收 
 
 @ApiModelProperty()用于方法,字段 表示对model属性的说明或者数据操作更改 
 
 @ApiIgnore()用于类，方法，方法参数,表示这个方法或者类被忽略 

 @ApiImplicitParam() 用于方法,表示单独的请求参数 
 
 @ApiImplicitParams() 用于方法,包含多个 @ApiImplicitParam
 
````

6.启动项目,访问 http://localhost:port/swagger-ui.html,之后就可以操作API 接口文档

7.效果展示

![image.png](https://i.loli.net/2020/03/15/Ggv7FiRmYLJWOn3.png)
                                        
