/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.service.DelaySendService;
import com.wilson.rabbitmq.utils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 延迟队列，每个消息发送时需要指定消息的过期时间
 */
@Api(value = "1.0")
@RestController
@RequestMapping(value = "/api/delay")
public class DelayController {

    @Autowired
    private DelaySendService delaySendService;

    @ApiOperation(value = "发送延时消息，每个消息都有自己的过期时间", notes = "消息自带过期时间")
    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public String sendDelayMessage( Message message) {
        CreateOrderVo order = new CreateOrderVo();
        String orderNo = message.getId().toString();
        order.setOrderNo(orderNo);
        message.setContent(JSONObject.toJSONString(order));
        delaySendService.sendDelayMessage(message);
        return "success";
    }

    @ApiOperation(value = "发送延时消息队列，整个队列有自己的过期时间", notes = "队列整体有固定的过期时间")
    @RequestMapping(value = "/queue/msg", method = RequestMethod.POST)
    public String sendDelayQueueMessage(@RequestBody Message message) {

        delaySendService.sendDelayQueueMessage(message);
        return "success";
    }

}
