package com.kyh.service;

import com.kyh.Application;
import com.kyh.model.User;
import com.kyh.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kongyunhui on 2017/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
@Transactional // 默认回滚事务
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        User one = userService.findUser(1l);
        Assert.assertEquals(true, one.getId() == 1l);
    }

    @Test
    public void test2(){
        User user = new User();
        user.setId(4l);
        user.setName("kongyunhui");
        int count = userService.createUser(user);
        Assert.assertEquals(true, count==1);
    }
}
