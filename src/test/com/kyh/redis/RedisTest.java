package com.kyh.redis;

import com.kyh.Application;
import com.kyh.model.primary.User;
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

    @Test
    public void test3(){
//        redisTemplate.delete("spring:session:sessions:be8f7c50-2e97-4811-ace9-231eb3455eb0");
//        redisTemplate.delete("spring:session:sessions:expires:be8f7c50-2e97-4811-ace9-231eb3455eb0");
//        Long notExistKey = redisTemplate.boundHashOps("spring:session:sessions:e8b02f1e-254a-442b-a85a-638df6bbcd42").delete("notExistKey");
//        System.out.println(notExistKey);

        redisTemplate.delete("spring:session:sessions:expires:730b9560-a31b-4a0d-acd5-dec05e929f13");

        redisTemplate.delete(redisTemplate.keys("*findUser*"));
    }
}
