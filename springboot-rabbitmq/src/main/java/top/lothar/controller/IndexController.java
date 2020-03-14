package top.lothar.controller;

import top.lothar.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @ResponseBody
    @RequestMapping("/firstDirectQueue")
    public Map sendDirectQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<String> list  = new ArrayList<String>();
        list.add("First Direct MQ QUEUE");
        amqpTemplate.convertAndSend(RabbitMQConstants.MY_FIRST_QUEUE,list);
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
        amqpTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE,RabbitMQConstants.ROUTINE_KEY_1,list+"FirstList");
        amqpTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE, RabbitMQConstants.ROUTINE_KEY_2, list + "SecondList");
        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/firstFanoutQueue")
    public Map sendFanoutQueue(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<String> list  = new ArrayList<String>();
        list.add("Current QUERY Fanout Model");
        amqpTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, "", list);
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
        amqpTemplate.convertAndSend(RabbitMQConstants.HEADERS_EXCHANGE, "", message);
        modelMap.put("msg","控制台打印消费信息");
        return modelMap;
    }

}
