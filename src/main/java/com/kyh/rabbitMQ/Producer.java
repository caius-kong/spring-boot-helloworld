package com.kyh.rabbitMQ;

import java.io.IOException;
import java.io.Serializable;

import com.rabbitmq.client.MessageProperties;
import org.springframework.util.SerializationUtils;


/**
 * The producer endpoint that writes to the queue.
 */
public class Producer extends EndPoint {

    public Producer(String QUEUE_NAME) throws Exception {
        super(QUEUE_NAME);
    }

    // 消息持久性：写入磁盘/缓存。该持久性用于简单任务队列，如需强大的保证use publisher confirms
    public void sendMessage(Serializable object) throws Exception {
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));
    }
}