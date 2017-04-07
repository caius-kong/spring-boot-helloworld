package com.kyh.rabbitmq.spring_rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

/**
 * 设置一个监听的业务类，实现接口MessageListener
 */
public class Receiver implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void onMessage(Message message) {
        LOG.info("receive message: {}", message);
        try {
            LOG.info("receive message body:", new String(message.getBody(), "utf-8"));
        }catch(UnsupportedEncodingException e){
            LOG.error("error: {}", e.getMessage());
        }
    }
}