package com.kyh.service;

import com.kyh.Application;
import com.kyh.dao.UserMapper;
import com.kyh.model.Role;
import com.kyh.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kongyunhui on 2017/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
@Transactional // 默认回滚事务
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void test1(){
        List<String> roles = roleService.getRoles(3l);
        System.out.println(roles);
        Assert.assertEquals(2, roles.size());
    }
}
