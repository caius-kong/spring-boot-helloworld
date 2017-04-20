package com.kyh;

import org.mapstruct.MapperConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kongyunhui on 2017/3/28.
 *
 * 1、引入maven依赖
 *    Maven依赖中引入spring-boot-starter-web，相当于引入了springMvc,tomcat,springBoot等一系列依赖。
 * 2、注解
 *    由于大量项目都会在主要的配置类上添加@Configuration,@EnableAutoConfiguration,@ComponentScan三个注解，@SpringBootApplication代替他们
 * 3、启动：
 *    SpringApplication.run(Application.class, args) ==> 启动服务
 */

@SpringBootApplication
@MapperScan("com.kyh.dao") // 扫描Mapper类的package (注1：Mapper不需要@Repository，注2：需要在application.properties中指定mapper.xml目录)
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
