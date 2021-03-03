package top.lothar.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.lothar.constants.RabbitMQConstants;
import top.lothar.entity.click.ClickDO;
import top.lothar.service.EsClickService;

/**
 * <h1>RabbitMQ 消费者</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/3/3 15:42
 */
@Component
public class RabbitMQConsumeServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumeServiceImpl.class);

    @Autowired
    private EsClickService esClickService;

    @RabbitListener(queues = RabbitMQConstants.MQ_COMMON_CLICK_TO_ES)
    public void processCommonClickToEs(String jsonMap) {
        logger.info("收到消息:{}  {}", RabbitMQConstants.MQ_COMMON_CLICK_TO_ES, jsonMap);
        try {
            if (StringUtils.isEmpty(jsonMap)) {
                return;
            }
            ClickDO clickDO = JSON.parseObject(jsonMap, ClickDO.class);
            if (clickDO == null) return;
            esClickService.saveCommonClick(clickDO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("点击数据收集ES:{}  {}", RabbitMQConstants.MQ_COMMON_CLICK_TO_ES, jsonMap);
        }
    }

}
