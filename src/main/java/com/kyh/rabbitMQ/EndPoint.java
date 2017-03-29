package com.kyh.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Represents a connection with a queue
 */
public abstract class EndPoint {

    protected Channel channel;
    protected Connection connection;
    protected String QUEUE_NAME;

    public EndPoint(String queue_name) throws Exception {
        this.QUEUE_NAME = queue_name;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //hostname of your rabbitmq server (localhost默认使用guest用户)
        factory.setHost("127.0.0.1");
        factory.setUsername("kong");
        factory.setPassword("123");

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        //declaring a exchange for this channel
        channel.exchangeDeclare("logs", "fanout");

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        boolean durable = true; // 队列持久性 （注：RabbitMQ不允许重新定义具有不同参数的现有队列，因此需要换QUEUE_NAME）
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

    }


    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     *
     * @throws Exception
     */
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}