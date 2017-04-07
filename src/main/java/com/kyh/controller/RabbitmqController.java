package com.kyh.controller;

import augtek.rabbitmq.api.ExchangeAPIs;
import augtek.rabbitmq.api.UserAPIs;
import augtek.rabbitmq.config.ServerConfig;
import augtek.rabbitmq.constant.ExchangeTypeEnum;
import augtek.rabbitmq.exception.ResponseException;
import augtek.rabbitmq.req.ExchangeOptions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kongyunhui on 2017/4/6.
 */
@Api(value = "RabbitmqController", description = "rabbitmq管理")
@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqController {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ServerConfig serverConfig;

    @ApiOperation(value = "创建交换机的接口")
    @RequestMapping("/createExchange")
    public void createExchange(){
        try {
            ExchangeOptions options = new ExchangeOptions();
            options.setDurable(true);
            boolean isSuccess = ExchangeAPIs.createExchange(serverConfig, "test_v2", "direct_test", ExchangeTypeEnum.DIRECT.getType(), options);
            System.out.println(isSuccess);
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }

    public void CreateUser(){
//        try{
//            UserAPIs.createUser();
//        }catch(ResponseException e){
//            LOG.error("error: {}", e.getMessage());
//        }
    }
}
