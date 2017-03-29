package com.kyh.rabbitMQ;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public Main() throws Exception {
        // 消费者线程，接收消息
        ExecutorService executors = Executors.newFixedThreadPool(3);
        for(int i=0; i<3; i++){
            executors.submit(new QueueConsumer("queue1"));
        }

        // 生产者，发送消息
        Producer producer = new Producer("queue1");
        for (int i = 0; i < 10; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number " + i + " sent.");
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Main();
    }
}