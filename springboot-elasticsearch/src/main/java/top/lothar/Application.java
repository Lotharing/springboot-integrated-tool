package top.lothar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


/**
 * @author LuTong.Zhao
 * @Date 2020/9/25 10:41
 */
@SpringBootApplication
@EnableElasticsearchRepositories
@MapperScan(basePackages = "top.lothar.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
