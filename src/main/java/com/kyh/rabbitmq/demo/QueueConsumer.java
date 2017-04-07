package com.kyh.rabbitmq.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.util.SerializationUtils;


/**
 * 读取队列的程序端
 * 1、实现了Runnable接口 （方便创建消费者线程并自动执行c.basicConsume）
 * 2、Consumer接口（若不实现Consumer接口，也可以new DefaultConsumer）。
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {
    private String queueName;
    private boolean autoAck = false;

    public QueueConsumer(String exchangeName, String bindingKey) throws Exception {
        super(exchangeName);
        // random queue (为什么使用临时队列？因为我们希望每一个消费者都获取所有消息！因此每一个消费者连接服务器时都创建一个具有随机名称的队列)
        queueName = channel.queueDeclare().getQueue();
        // bind exchange and queue
        channel.queueBind(queueName, exchangeName, bindingKey);
        // 每个工作人员预取消息数 （在处理并确认前一个消息之前，不要向工作人员发送新消息）
        channel.basicQos(1);
    }

    public void run() {
        try {
            //start consuming messages. Don't auto acknowledge messages.
            //true: 接到消息，自动消息确认。
            //false: 关闭自动消息确认。在工作全部完成时，手动发出消息确认。(确保消费者接收消息后，若未执行完任务，消息不丢失，重新入队)
            channel.basicConsume(queueName, autoAck, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println(consumerTag + ": Message Number " + map.get("message number") + " received.");
        // long time work...
        try {
            Thread.sleep(1000);
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(consumerTag + ": Message Number " + map.get("message number")  + " Done.");
            channel.basicAck(env.getDeliveryTag(), false); // 工作完成，手动发出消息确认
        }
    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}