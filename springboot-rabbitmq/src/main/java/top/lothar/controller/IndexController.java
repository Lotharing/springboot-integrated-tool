package top.lothar.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.BeanPostProcessor;
import top.lothar.back.RabbitmqConfirmCallback;
import top.lothar.constants.RabbitMQConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("index")
public class IndexController implements BeanPostProcessor {

    @Resource
    private RabbitmqConfirmCallback rabbitmqConfirmCallback;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void init() {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(rabbitmqConfirmCallback);
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(rabbitmqConfirmCallback);
    }

    @ResponseBody
    @RequestMapping("/firstDirectQueue")
    public Map sendDirectQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<String> list  = new ArrayList<String>();
        list.add("First Direct MQ QUEUE");
        //Map<String, Object> message = getMessage(list.get(0));

        Map<String, Object> message = getMessage("suc");
        CorrelationData correlationData = (CorrelationData) message.remove("correlationData");
        rabbitTemplate.convertAndSend(RabbitMQConstants.MY_FIRST_QUEUE,message,correlationData);

        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/firstTopicQueue")
    public Map sendTopicQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<String> list  = new ArrayList<String>();
        list.add("FIRST QUERY Topic Model");
        // 交换机名称  Topic-bidding-key  数据
        rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE,RabbitMQConstants.ROUTINE_KEY_1,list+"FirstList");
        rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE, RabbitMQConstants.ROUTINE_KEY_2, list + "SecondList");
        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/firstFanoutQueue")
    public Map sendFanoutQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<String> list  = new ArrayList<String>();
        list.add("Current QUERY Fanout Model");
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, "", list);
        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/firstHeaderQueue")
    public Map sendHeaderQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        String msg = "First Query Header Model";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("h1", "v1");
        messageProperties.setHeader("h2", "v2");
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(RabbitMQConstants.HEADERS_EXCHANGE, "", message);
        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

    private Map<String, Object> getMessage(String msg) {
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        String sendTime = new Date().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("sendTime", sendTime);
        map.put("msg", msg);
        map.put("correlationData", correlationData);
        return map;
    }

}
