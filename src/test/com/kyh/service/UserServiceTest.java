package com.kyh.service;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kyh.Application;
import com.kyh.model.primary.User;
import com.kyh.model.enums.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void query(){
        User one = userService.findUser(1l);
        Assert.assertTrue(one.getId() == 1l);
        System.out.println("func1 end: " + one);
        User two = userService.findUser(1l);
        Assert.assertTrue(one.getId() == 1l);
        System.out.println("func2 end: " + two);
    }

    @Test
    public void add() throws Exception{
        User user = new User();
        user.setId(3l);
        user.setUsername("zbzy");
        user.setPassword("123abc!");
        user.setUserType(UserType.ADMIN);
        user.setRoleIds(Lists.newArrayList(1l,2l));
        userService.createUser(user);
        Assert.assertEquals(true, user.getId()==3l);
    }

    @Test
    public void list() throws Exception{
        List<User> list = userService.list(null);
        System.out.println(list);
        System.out.println(new PageInfo<User>(list));
    }
}
