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
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(200); // 缓冲队列
        executor.setKeepAliveSeconds(60); // 超过核心线程数的线程空闲事件超过60s就关闭
        executor.setThreadNamePrefix("taskExecutor-"); // 线程名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 线程池对拒绝任务的处理策略。默认AbortPolicy - 拒绝就抛出异常；CallerRunsPolicy - 调用者的线程会执行该任务,如果执行器已关闭,则丢弃
        executor.setWaitForTasksToCompleteOnShutdown(true); // 线程池/应用优雅关闭。即避免应用关闭了，异步任务还在执行导致的某些bean数据依赖异常。true：线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean。
        executor.setAwaitTerminationSeconds(60); // 仅优雅等待60s，防止应用无法关闭。
        return executor;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
