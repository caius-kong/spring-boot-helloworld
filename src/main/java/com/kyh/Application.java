package com.kyh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kongyunhui on 2017/3/28.
 *
 * 1、Maven依赖中引入spring-boot-starter-web，相当于引入了springMvc,tomcat,springBoot等一系列依赖。
 * 2、@EnableAutoConfiguration + @ComponentScan
 * 3、SpringApplication.run(Application.class, args) ==> 启动服务
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.kyh")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
