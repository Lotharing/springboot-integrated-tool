package top.lothar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/21 14:28
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverter responseBodyConverter(){
        //解决返回值中文乱码
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("GBK"));
        return converter;
    }

    /**
     * 可以实现的接口
     *
     * 2.1 addInterceptors：拦截器
     *
     * 2.2 addViewControllers：页面跳转
     *
     * 2.3 addResourceHandlers：静态资源
     *
     * 2.4 configureDefaultServletHandling：默认静态资源处理器
     *
     * 2.5 configureViewResolvers：视图解析器
     *
     * 2.6 configureContentNegotiation：配置内容裁决的一些参数
     *
     * 2.7 addCorsMappings：跨域
     *
     * 已实现：  configureMessageConverters： 「信息转换器」
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }


}
