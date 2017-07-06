package com.kyh.rabbitmq.spring_rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by kongyunhui on 2017/4/6.
 *
 * @Configuration 让spring来加载该类 等价于<beans/>
 *
 * @Bean 产生一个bean，交给spring管理 等价于<bean/>
 */
//@Configuration
public class RabbitMQConfiguration {
    public final static String QUEUE_NAME = "augtek_Q_1";
    public final static String EXCHANGE_NAME = "augtek_X_direct";
    public final static String ROUTING_KEY="T1";

    // RabbitMQ的配置信息
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    // 建立一个连接容器，类似数据库的连接池。
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }

    /**
     * 如果需要生产者确认消息回调，需要对rabbitTemplate设置ConfirmCallback对象。
     * 由于不同的生产者需要对应不同的ConfirmCallback，如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     *
     * 因此：必须是prototype类型
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * 针对消费者配置
     * 1. 定义一个交换机
     * 2. 定义一个队列
     * 3. 将队列绑定到交换机
     *
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, true); //队列持久
//
//    }
//
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTING_KEY);
//    }

    // 声明一个监听容器
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory, Receiver messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        // 定义消费者自己的connectionFactory (我们不希望使用配置文件设置的那个登录信息)
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername("campany1"); // 消费者campany2被拒绝访问augtek_Q_1，会进入死循环！？？？无限重启
        factory.setPassword("123");
        container.setConnectionFactory(factory);
        container.setQueueNames(new String[]{QUEUE_NAME});
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(messageListener);
        return container;
    }

    @Bean
    public Receiver receiver(){
        return new Receiver();
    }
}


