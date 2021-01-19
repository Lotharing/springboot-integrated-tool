package top.lothar.config;

import top.lothar.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列注入Spring IOC
 */
@Configuration
public class QueueConfiguration {

    /**
     * Direct模式
     * 在启动的时候，这些 queue 会注册进rabbitmq
     */
    @Bean
    public Queue getFirstQueue(){
        return new Queue(RabbitMQConstants.MY_FIRST_QUEUE,true);
    }


    /**
     * Topic模式 根据RoutineKey去绑定接收的消息
     */
    @Bean
    public Queue topicQueueOne(){
        return new Queue(RabbitMQConstants.TOPIC_QUEUE_1,true);
    }

    @Bean
    public Queue topicQueueTwo(){
        return new Queue(RabbitMQConstants.TOPIC_QUEUE_2,true);
    }

    //Topic模式下交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMQConstants.TOPIC_EXCHANGE);
    }

    //绑定交换机  with 中的参数可以用通配符
    @Bean
    public Binding topicBindingOne(){
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(RabbitMQConstants.ROUTINE_KEY_1);
    }

    @Bean
    public Binding topicBindingTwo(){
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(RabbitMQConstants.ROUTINE_KEY_2);
    }

    /**
     * Fanout模式-广播
     */

    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(RabbitMQConstants.FANOUT_QUEUE_One, true);
    }
    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(RabbitMQConstants.FANOUT_QUEUE_Two, true);
    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConstants.FANOUT_EXCHANGE);
    }
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    /**
     * Header模式：键值对模式匹配
     */
    @Bean
    public Queue headerQueue() {
        return new Queue(RabbitMQConstants.HEADERS_QUEUE, true);
    }

    @Bean
    public HeadersExchange headersExchage(){
        return new HeadersExchange(RabbitMQConstants.HEADERS_EXCHANGE);
    }

    @Bean
    public Binding headerBinding() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("h1", "v1");
        map.put("h2", "v2");
        return BindingBuilder.bind(headerQueue()).to(headersExchage()).whereAll(map).match();
    }

}
