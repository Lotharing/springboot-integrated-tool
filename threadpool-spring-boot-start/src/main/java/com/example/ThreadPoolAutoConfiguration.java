package com.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 11:24
 */
@Configuration
public class ThreadPoolAutoConfiguration {

    @Bean
    @ConditionalOnClass(ThreadPoolAutoConfiguration.class) // 需要项目中存在这个类，类jdk自带成立
    public ThreadPoolExecutor MyThreadPool(){
        return new ThreadPoolExecutor(10,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
    }
}
