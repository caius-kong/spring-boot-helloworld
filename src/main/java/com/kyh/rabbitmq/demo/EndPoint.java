package com.kyh.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Represents a connection with a queue
 */
public abstract class EndPoint {
    protected Connection connection;
    protected Channel channel;
    protected String exchangeName;

    /**
     * 连接服务器，打开一个channel，申明一个exchange
     */
    public EndPoint(String exchangeName) throws Exception {
        this.exchangeName = exchangeName;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("kong");
        factory.setPassword("123");
        connection = factory.newConnection();

        channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "topic");
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