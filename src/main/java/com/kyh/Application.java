package com.kyh;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

@SpringBootApplication(scanBasePackages={"com.kyh", "augtek.rabbitmq"})
//@MapperScan("com.kyh.dao") // 扫描Mapper类的package (注1：Mapper不需要@Repository，注2：需要在application.properties中指定mapper.xml目录)
@EnableScheduling // 开启定时任务
@EnableAsync // 开启异步调用
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

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
