package top.lothar.manual;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.lothar.constants.RabbitMQConstants;

import java.util.Map;

/**
 * <h1></h1>
 * 如果没有异常，则手动确认回复RabbitMQ服务端basicAck(消费成功)。
 * 如果抛出某些可以重回队列的异常，我们就回复basicNack并且设置重回队列。
 * 如果是抛出不可重回队列的异常，就回复basicNack并且设置从RabbitMQ的队列中删除。
 *
 *
 * @author LuTong.Zhao
 * @Date 2021/1/17 01:36
 */
@Component
public class RabbitDemoConsumer {

    enum Action {
        //处理成功
        SUCCESS,
        //可以重试的错误，消息重回队列
        RETRY,
        //无需重试的错误，拒绝消息，并从队列中删除
        REJECT
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(RabbitMQConstants.MY_FIRST_QUEUE))
    //@RabbitListener(queues = RabbitMQConstants.MY_FIRST_QUEUE)
    public void process(Map msg, Message message, Channel channel) {
        // 该消息的index
        long tag = message.getMessageProperties().getDeliveryTag();
        // 默认发成功的消息
        Action action = Action.SUCCESS;
        try {
            System.out.println("消费者RabbitDemoConsumer从RabbitMQ服务端消费消息：" + msg);
            if ("bad".equals(msg.get("msg").toString())) {
                throw new IllegalArgumentException("测试：抛出可重回队列的异常");
            }
            if ("error".equals(msg.get("msg").toString())) {
                throw new Exception("测试：抛出无需重回队列的异常");
            }
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
            //根据异常的类型判断，设置action是可重试的，还是无需重试的
            action = Action.RETRY;
        } catch (Exception e2) {
            //打印异常
            e2.printStackTrace();
            //根据异常的类型判断，设置action是可重试的，还是无需重试的
            action = Action.REJECT;
        } finally {
            try {
                if (action == Action.SUCCESS) {
                    //multiple 表示是否批量处理。true表示批量ack处理小于tag的所有消息。false则处理当前消息
                    channel.basicAck(tag, false);
                } else if (action == Action.RETRY) {
                    //Nack，拒绝策略，消息重回队列 「批量」
                    channel.basicNack(tag, false, true);
                } else {
                    //Nack，拒绝策略，并且从队列中删除 「批量」
                    //channel.basicNack(tag, false, false);
                    // 消息index 是否重回队列 , 只能一次拒绝一条消息
                    channel.basicReject(tag, false);
                }
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
