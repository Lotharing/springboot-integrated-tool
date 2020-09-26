package top.lothar.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ResultsMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/25 19:09
 */
@Configuration
public class ElasticsearchConfig {


    /**
     * 使用spring-data-elasticsearch 自动提供的 RestHighLevelClient等构建 ElasticsearchRestTemplate
     * @param client
     * @param converter
     * @param resultsMapper
     * @return
     */
    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client, ElasticsearchConverter converter,
                                                           ResultsMapper resultsMapper) {
        return new ElasticsearchRestTemplate(client, converter, resultsMapper);
    }
}
