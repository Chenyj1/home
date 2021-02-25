package com.wilson.rabbitmq.receives;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.utils.Message;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "process_queue")
@Slf4j
public class OrderDelayConsumer {
    @RabbitHandler
    public void orderConsumer(Message message){
        try {
            CreateOrderVo createOrderVo = JSONObject.parseObject(message.getContent(), CreateOrderVo.class);
            log.info("订单编号为{}",createOrderVo.getOrderNo());
        } catch (Exception e) {
            log.error("订单消息解析异常，请检查消息格式是否正确",message.getContent());
        }

    }
}
