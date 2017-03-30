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
            // *（星）替代一个单词。
            // ＃（哈希）替换零个或多个单词。
            String bindingKey="";
            switch (i){
                case 0:
                    bindingKey = "kern.*";
                    break;
                case 1:
                    bindingKey = "*.critical";
                    break;
                case 2:
                    bindingKey = "#";
                    break;
            }
            QueueConsumer consumer = new QueueConsumer("topic_logs", bindingKey);
            executors.submit(consumer);
        }

        // 生产者，发送消息
        Producer producer = new Producer("topic_logs");
        for (int i = 0; i < 2; i++) {
            switch (i){
                case 0:
                    producer.setBindingKey("kern.critical"); // 这条消息，被C1、C2、C3接收了
                    break;
                case 1:
                    producer.setBindingKey("A critical kernel error"); // 这条消息，被C3接收
                    break;
            }
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