package top.lothar.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lothar.constants.RabbitMQConstants;
import top.lothar.entity.click.ClickDO;
import top.lothar.service.EsClickService;
import top.lothar.util.EntityResultResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 18:05
 */
@RestController
@RequestMapping(value = "/click")
public class ClickEventController {

    @Autowired
    private EsClickService esClickService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 点击事件收集
     * @param label  唯一标签
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public EntityResultResponse<Object> clickInfo(@RequestParam(value = "label") String label) {
        ClickDO clickDO = new ClickDO();
        // 随机一个用户Id
        clickDO.setUserId(new Random().nextInt(999999));
        // 随机一个 ios / android / h5 的平台
        clickDO.setPlatform(new Random().nextInt(3));

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        // 点击时间
        clickDO.setTime(calendar.getTime());
        // 唯一标签
        clickDO.setLabel(label);
        // 随机一个课程ID
        int targetId = new Random().nextInt(50);
        clickDO.setTarget(String.valueOf(targetId));

        //esClickService.saveCommonClick(clickDO);

        //用消息队列做缓冲
        rabbitTemplate.convertAndSend(RabbitMQConstants.MQ_COMMON_CLICK_TO_ES,JSON.toJSONString(clickDO));
        return new EntityResultResponse<>();
    }



}
