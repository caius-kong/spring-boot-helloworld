package com.kyh.dao;

import com.kyh.Application;
import com.kyh.dao.primary.UserMapper;
import com.kyh.model.primary.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kongyunhui on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DaoTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        List<User> all = userMapper.query(null);
        System.out.println(all);
    }
}
