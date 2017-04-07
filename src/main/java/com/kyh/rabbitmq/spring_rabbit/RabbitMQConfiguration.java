package com.kyh.rabbitmq.spring_rabbit;

import augtek.rabbitmq.config.ServerConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kongyunhui on 2017/4/6.
 *
 * @Configuration 让spring来加载该类 等价于<beans/>
 *
 * @Bean 产生一个bean，交给spring管理 等价于<bean/>
 */
@Configuration
public class RabbitMQConfiguration {
    private final static String QUEUE_NAME = "queue2";
    private final static String EXCHANGE_NAME = "direct_test";
    private final static String ROUTING_KEY="T1";

    // RabbitMQ的配置信息
    @Value("${mq.rabbit.host}")
    private String mRabbitHost;
    @Value("${mq.rabbit.port}")
    private Integer mRabbitPort;
    @Value("${mq.rabbit.username}")
    private String mRabbitUsername;
    @Value("${mq.rabbit.password}")
    private String mRabbitPassword;
    @Value("${mq.rabbit.virtualHost}")
    private String mRabbitVirtualHost;

    // 建立一个ServerConfig bean
    @Bean
    public ServerConfig serverConfig(){
        return new ServerConfig(mRabbitHost, mRabbitPort, mRabbitUsername, mRabbitPassword);
    }

    // 建立一个连接容器，类似数据库的连接池。
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(mRabbitHost, mRabbitPort);
        connectionFactory.setUsername(mRabbitUsername);
        connectionFactory.setPassword(mRabbitPassword);
        connectionFactory.setVirtualHost(mRabbitVirtualHost);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    // RabbitMQ的使用入口
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    // 定义一个直连交换机
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 要求RabbitMQ建立一个队列。
    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME);
    }

    // 在spring容器中添加一个监听类
    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    // 声明一个监听容器
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, Receiver messageListener) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{QUEUE_NAME});
        container.setMessageListener(messageListener);

        return container;
    }

    // 要求队列和直连交换机绑定，指定ROUTING_KEY
    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}


