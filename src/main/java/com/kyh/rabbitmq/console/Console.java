package com.kyh.rabbitmq.console;

import augtek.rabbitmq.api.*;
import augtek.rabbitmq.config.ServerConfig;
import augtek.rabbitmq.constant.RoleEnum;
import augtek.rabbitmq.exception.ResponseException;
import augtek.rabbitmq.req.BindingOptions;
import augtek.rabbitmq.req.ExchangeOptions;
import augtek.rabbitmq.req.QueueOptions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by kongyunhui on 2017/4/6.
 *
 * 建立一个交换机、3个队列、3个用户，每个用户只能从自己的队列里取消息。 ==> 权限控制read
 */
public class Console {
    private static final Logger LOG = LoggerFactory.getLogger(Console.class);

    public static void main(String[] args){
        /**
         * 云平台操作
         */
        // 创建X - augtek
//        createX();
        // 创建3个队列
//        create3Q();
        // 3个队列分别以key=T1,T2,T3绑定X
//        bindingQ_X();
        // 创建3个用户
//        create3U();
        // 给三个用户分别授予对Q1,Q2,Q3的读权限
//        grant3UPermissons();
        // 发消息
//        sendMessage();

        /**
         * 应用软件操作
         */
        // 接收消息
//        receiverMessage();

        // other
//        findUser();
        sendMessageToQ1();

    }

    private static void createX(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            ExchangeOptions exchangeOptions = new ExchangeOptions();
            exchangeOptions.setDurable(true);
            boolean exchange = ExchangeAPIs.createExchange(serverConfig, "%2f", "augtek_X_direct", "direct", exchangeOptions);
            System.out.println(exchange);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void create3Q(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            QueueOptions queueOptions = new QueueOptions();
            queueOptions.setDurable(true);
            boolean augtek_q_1 = QueueAPIs.createQueue(serverConfig, "%2f", "augtek_Q_1", queueOptions);
            boolean augtek_q_2 = QueueAPIs.createQueue(serverConfig, "%2f", "augtek_Q_2", queueOptions);
            boolean augtek_q_3 = QueueAPIs.createQueue(serverConfig, "%2f", "augtek_Q_3", queueOptions);
            System.out.println(augtek_q_1);
            System.out.println(augtek_q_2);
            System.out.println(augtek_q_3);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void bindingQ_X(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            BindingOptions options1 = new BindingOptions();
            options1.setRouting_key("T1");
            String url1 = BindingAPIs.createBindingBetweenXAndQ(serverConfig, "%2f", "augtek_X_direct", "augtek_Q_1", options1);

            BindingOptions options2 = new BindingOptions();
            options2.setRouting_key("T2");
            String url2 = BindingAPIs.createBindingBetweenXAndQ(serverConfig, "%2f", "augtek_X_direct", "augtek_Q_2", options2);

            BindingOptions options3 = new BindingOptions();
            options3.setRouting_key("T3");
            String url3 = BindingAPIs.createBindingBetweenXAndQ(serverConfig, "%2f", "augtek_X_direct", "augtek_Q_3", options3);

            System.out.println(url1);
            System.out.println(url2);
            System.out.println(url3);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void create3U(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            // MANAGEMENT: 能查看所在vhost中的资源。例如想要读队列中的消息，首先得是"management"角色
            boolean campany1 = UserAPIs.createUser(serverConfig, "campany1", "123", RoleEnum.MANAGEMENT.value());
            boolean campany2 = UserAPIs.createUser(serverConfig, "campany2", "123", RoleEnum.MANAGEMENT.value());
            boolean campany3 = UserAPIs.createUser(serverConfig, "campany3", "123", RoleEnum.MANAGEMENT.value());
            System.out.println(campany1);
            System.out.println(campany2);
            System.out.println(campany3);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void grant3UPermissons(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            boolean u1p = PermissionAPIs.createUserPermission(serverConfig, "%2f", "campany1", "", "", "augtek_Q_1");
            System.out.println(u1p);
            boolean u2p = PermissionAPIs.createUserPermission(serverConfig, "%2f", "campany2", "", "", "augtek_Q_2");
            System.out.println(u2p);
            boolean u3p = PermissionAPIs.createUserPermission(serverConfig, "%2f", "campany3", "", "", "augtek_Q_3");
            System.out.println(u3p);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void sendMessage(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try {
            JSONObject jsonObject1 = ExchangeAPIs.sendMessageToExchange(serverConfig, "%2f", "augtek_X_direct", MessageProperties.PERSISTENT_TEXT_PLAIN, "T1", "campany1的消息", "string");
            JSONObject jsonObject2 = ExchangeAPIs.sendMessageToExchange(serverConfig, "%2f", "augtek_X_direct", MessageProperties.PERSISTENT_TEXT_PLAIN, "T2", "campany2的消息", "string");
            JSONObject jsonObject3 = ExchangeAPIs.sendMessageToExchange(serverConfig, "%2f", "augtek_X_direct", MessageProperties.PERSISTENT_TEXT_PLAIN, "T3", "campany3的消息", "string");
            System.out.println(jsonObject1);
            System.out.println(jsonObject2);
            System.out.println(jsonObject3);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    private static void receiverMessage(){
        ServerConfig serverConfig1 = new ServerConfig("localhost", 15672, "campany1", "123");
        try {
            JSONArray jsonArray = QueueAPIs.receiveMessageFromQueue(serverConfig1, "%2f", "augtek_Q_1", 1, true, "auto", 50000);
            System.out.println(jsonArray);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    /**
     * ==========================
     */
    public static void findUser(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try{
            JSONObject guest = UserAPIs.findUser(serverConfig, "campany1");

            String name = (String)guest.get("name");

            System.out.println(guest);
        }catch(ResponseException e) {
            LOG.error("error: {}", e.getMessage());
        }
    }

    public static void sendMessageToQ1(){
        ServerConfig serverConfig = new ServerConfig("localhost", 15672, "guest", "guest");
        try{
            JSONObject jsonObject = ExchangeAPIs.sendMessageToExchange(serverConfig, "%2f", "augtek_X_direct", MessageProperties.PERSISTENT_TEXT_PLAIN,
                    "T1", "hello, campany1! Date:" + new Date(), "string");
            System.out.println(jsonObject);
        }catch(ResponseException e) {
            LOG.error("error: {}", e.getMessage());
        }
    }
}
