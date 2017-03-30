package com.kyh.rabbitMQ;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public Main() throws Exception {
        // 消费者线程，接收消息
        ExecutorService executors = Executors.newFixedThreadPool(3);
        for(int i=0; i<3; i++){
            QueueConsumer consumer = new QueueConsumer("direct_logs", "level" + (i+1));
            executors.submit(consumer);
        }

        // 生产者，发送消息
        Producer producer = new Producer("direct_logs");
        for (int i = 0; i < 10; i++) {
            if(i<3) producer.setBindingKey("level1");
            else if(i<6) producer.setBindingKey("level2");
            else producer.setBindingKey("level3");

            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number " + i + " sent.");
        }
        producer.close();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Main();
    }
}