package com.kyh.controller;

import augtek.rabbitmq.api.ExchangeAPIs;
import augtek.rabbitmq.api.UserAPIs;
import augtek.rabbitmq.config.ServerConfig;
import augtek.rabbitmq.constant.ExchangeTypeEnum;
import augtek.rabbitmq.exception.ResponseException;
import augtek.rabbitmq.req.ExchangeOptions;
import com.kyh.rabbitmq.spring_rabbit.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by kongyunhui on 2017/4/6.
 */
@Api(value = "RabbitmqController", description = "rabbitmq管理")
@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqController {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    private Sender sender;

    @ApiOperation(value = "发送消息的接口")
    @RequestMapping("/sendMsg")
    public void sendMsg(){
        sender.sendMsg("hello guest!");
    }
}
