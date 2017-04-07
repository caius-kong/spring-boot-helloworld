package com.kyh.rabbitmq.spring_rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Sender implements RabbitTemplate.ConfirmCallback {
    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    private RabbitTemplate rabbitTemplate;  
  
    /**  
     * 构造方法注入  
     */  
    @Autowired
    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;  
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容  
    }  
  
    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY, content, correlationId);
        LOG.debug("send message's correlationId: {}", correlationId);
    }
  
    /**  
     * 消息确认回调
     */  
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
        if (ack) {
            LOG.debug("回调id: {}, 消息成功消费", correlationData);
        } else {
            LOG.debug("回调id: {}, 消息消费失败, cause:{}", correlationData, cause);
        }  
    }  
}