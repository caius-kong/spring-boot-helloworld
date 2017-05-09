package com.kyh.service;

import com.kyh.Application;
import com.kyh.dao.UserMapper;
import com.kyh.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kongyunhui on 2017/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
//@Transactional // 默认回滚事务
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        User one = userService.findUser(1l);
        Assert.assertTrue(one.getId() == 1l);
        System.out.println("func1 end: " + one);
        User two = userService.findUser(1l);
        Assert.assertTrue(one.getId() == 1l);
        System.out.println("func2 end: " + two);
    }

    @Test
    public void test2(){
        User user = new User();
        user.setId(4l);
        user.setUsername("kongyunhui");
        user.setPassword("123");
        int count = userService.createUser(user);
        Assert.assertEquals(true, count==1);
    }
}
