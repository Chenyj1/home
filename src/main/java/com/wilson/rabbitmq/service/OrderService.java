package com.wilson.rabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class OrderService {

    @Autowired
    private DelaySendService delaSendService;
    public void createOrder(CreateOrderVo order) {
        String orderNo = "100000000201808191000982091";
        order.setOrderNo(orderNo);
        //封装消息内容
        Message message = new Message();
        message.setContent(JSONObject.toJSONString(order));
        message.setId(1);
        message.setTtl(1, TimeUnit.MINUTES);
        delaSendService.sendDelayMessage(message);

    }
}
