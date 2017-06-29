package com.kyh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by kongyunhui on 2017/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {
    @Test
    public void test1(){
        String encode = new BCryptPasswordEncoder().encode("123abc!");
        System.out.println("--->" + encode);
    }

    @Test
    public void test2(){
        /**
         * FileSystemResource：以文件的绝对路径方式进行访问
         * ClassPathResourcee：以类路径的方式访问
         * ServletContextResource：web应用根目录的方式访问
         */

        FileSystemResource file1 = new FileSystemResource(new File("/Users/kongyunhui/Desktop/a.png"));

        ClassPathResource file2 = new ClassPathResource("application.properties");
    }
}
