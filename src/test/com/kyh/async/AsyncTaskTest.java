package com.kyh.async;

import com.kyh.Application;
import com.kyh.task.AsyncTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;

/**
 * Created by kongyunhui on 2017/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AsyncTaskTest {
    @Autowired
    private AsyncTask asyncTask = new AsyncTask();

    @Test
    public void test() throws Exception{
        long start = System.currentTimeMillis();
        Future<String> task1 = asyncTask.doTaskOne();
        Future<String> task2 = asyncTask.doTaskTwo();
        Future<String> task3 = asyncTask.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }
}
