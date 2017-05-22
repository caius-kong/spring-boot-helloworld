package com.kyh.rest.controller;

import com.kyh.rabbitmq.spring_rabbit.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private Sender sender;

    @ApiOperation(value = "发送消息的接口", notes = "需要ADMIN权限")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/sendMsg")
    public void sendMsg(){
        sender.sendMsg("hello guest!");
    }
}
