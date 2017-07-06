package com.kyh.rabbitconsole;

import augtek.rabbitmq.api.QueueAPIs;
import augtek.rabbitmq.api.UserAPIs;
import augtek.rabbitmq.exception.ResponseException;
import augtek.rabbitmq.resp.User;
import com.kyh.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kongyunhui on 2017/7/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitmqConsoleTest {
    private final Logger LOG = LoggerFactory.getLogger(RabbitmqConsoleTest.class);

    @Autowired
    private UserAPIs userAPIs;

    @Autowired
    private QueueAPIs queueAPIs;

    @Test
    public void consoleTest(){
        try {
            System.out.println("--->" + userAPIs.findAllUsers4POJO());

            System.out.println("--->" + queueAPIs.findQueue4POJO("/", "augtek_Q_1"));
        }catch(ResponseException e){
            LOG.error("error: {}", e.getMessage());
        }
    }
}
