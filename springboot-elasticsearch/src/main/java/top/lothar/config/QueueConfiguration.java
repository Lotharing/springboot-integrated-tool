package top.lothar.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lothar.constants.RabbitMQConstants;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/3/3 15:36
 */
@Configuration
public class QueueConfiguration {

    @Bean
    public Queue queueMQ_COMMON_CLICK_TO_ES() {
        return new Queue(RabbitMQConstants.MQ_COMMON_CLICK_TO_ES);
    }

}
