package com.kyh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kongyunhui on 2017/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
    @Test
    public void test1(){
        String encode = new BCryptPasswordEncoder().encode("123abc!");
        System.out.println("--->" + encode);
    }
}
