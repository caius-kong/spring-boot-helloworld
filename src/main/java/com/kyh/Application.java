package com.kyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
@EnableScheduling
public class Application {

    @Bean
    public WebMvcConfigurer CORSConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/APIs/**");
            }
        };
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
