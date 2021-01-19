package top.lothar.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * <h1>Rabbit回调</h1>
 *
 * 一条消息从生产者发送到RabbitMQ，首先会发送到Exchange，对应回调函数confirm()。
 * ConfirmCallback
 *
 * 第二步从Exchange路由分配到Queue中，对应回调函数则是returnedMessage()。
 * ReturnCallback
 *
 * @author LuTong.Zhao
 * @Date 2021/1/17 00:26
 */
@Component
public class RabbitmqConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(RabbitmqConfirmCallback.class);

    /**
     * 监听消息是否到达Exchange
     *
     * @param correlationData 包含消息的唯一标识的对象
     * @param ack             true 标识 ack，false 标识 nack
     * @param cause           nack 投递失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("消息投递成功~消息Id：{}", correlationData.getId());
        } else {
            logger.error("消息投递失败，Id：{}，错误提示：{}", correlationData.getId(), cause);
        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息没有路由到队列，获得返回的消息");
        Map map = byteToObject(message.getBody(), Map.class);
        logger.info("message body: {}", map == null ? "" : map.toString());
        logger.info("replyCode: {}", replyCode);
        logger.info("replyText: {}", replyText);
        logger.info("exchange: {}", exchange);
        logger.info("routingKey: {}", exchange);
        logger.info("------------> end <------------");
    }

    @SuppressWarnings("unchecked")
    private <T> T byteToObject(byte[] bytes, Class<T> clazz) {
        T t;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
