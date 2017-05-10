package com.kyh.redis;

import com.kyh.Application;
import com.kyh.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kongyunhui on 2017/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1(){
        User user = new User();
        user.setId(1l);
        user.setUsername("kong");
        user.setPassword("123");
        redisTemplate.opsForValue().set(user.getUsername(), user);
    }

    @Test
    public void test2(){
        Long id = redisTemplate.opsForValue().get("kong") == null ? 0 : ((User)redisTemplate.opsForValue().get("kong")).getId();
        System.out.println(id);
    }
}
