package com.wilson.rabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.config.RabbitConfig;
import com.wilson.rabbitmq.enums.ErrorCodeEnum;
import com.wilson.rabbitmq.exception.SystemException;
import com.wilson.rabbitmq.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class DelaySendService {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    /**
     * 发送延时消息，每个消息都自己有自己的过期时间
     */
    public void sendDelayMessage(Message msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("发送消息的时间为：{}",sdf.format(new Date()));
        //设置发送时间，开始发送
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE,RabbitConfig.DELAY_ROUTING_KEY,msg,message ->{
                message.getMessageProperties().setExpiration(String.valueOf(msg.getTtl()));
                return message;
            } );
        } catch (AmqpException e) {
            log.error("消息发送失败，请检查消息中间件是否正常", JSONObject.toJSONString(msg));
            throw new SystemException(ErrorCodeEnum.UNKNOWING_ERROR);
        }
    }
    public void sendDelayQueueMessage(Message msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("发送消息的时间为：{}",sdf.format(new Date()));
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.DELAY_QUEUE_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, msg);
        } catch (AmqpException e) {
            log.error("消息发送失败，请检查消息中间件是否正常", JSONObject.toJSONString(msg));
            throw new SystemException(ErrorCodeEnum.UNKNOWING_ERROR);
        }

    }
}
