package com.kyh.mongodb;

import com.kyh.Application;
import com.kyh.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kongyunhui on 2017/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MongodbTest {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Test
//    public void test1(){
//        User user = new User();
//        user.setId(1l);
//        user.setUsername("kong");
//        user.setPassword("123");
//        mongoTemplate.save(user);
//    }
//
//    @Test
//    public void test2(){
//        Query query = new Query(Criteria.where("username").is("kong"));
//        User one = mongoTemplate.findOne(query, User.class);
//        System.out.println(one);
//    }
}
