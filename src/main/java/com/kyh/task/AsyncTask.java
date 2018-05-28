package com.kyh.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * 异步调用/并发调用
 * 注1：@Async所修饰的函数不要定义为static类型，这样异步调用不会生效
 * 注2：需要手动调用，调用的各个方法是并发执行的。
 * 注3：由于返回Future，因此可以任务回调，判断是否完成。
 * 注4：必须交给spring管理，否则不生效
 *
 * 后记：为了保障应用健康，需要控制这些异步执行。例如控制并发线程数等 --> 自定义线程池
 * 1、启动类中创建线程池实例，@Bean("taskExecutor")
 * 2、在@Async注解中指定线程池名
 */
@Component
public class AsyncTask {
    public static Random random = new Random();

    @Async("taskExecutor")
    public Future<String> doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult("任务一完成");
    }

    @Async("taskExecutor")
    public Future<String> doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return new AsyncResult("任务二完成");
    }

    @Async("taskExecutor")
    public Future<String> doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
        return new AsyncResult("任务三完成");
    }
}