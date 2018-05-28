package com.kyh.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 */
@Component
public class ScheduledTasks {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     *  每隔5秒执行一次，如果执行时间超过5秒，则等待任务结束后，立马启动一个新的任务
     */
//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("现在时间 task start：" + dateFormat.format(new Date()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("现在时间 task end：" + dateFormat.format(new Date()));
    }

    /**
     * 每隔5秒执行一次，如果执行时间超过5秒，则等待任务结束后，再等待5秒才启动一个新的任务
     */
//    @Scheduled(cron="*/5 * * * * *")
    public void reportCurrentTime2() {
        System.out.println("现在时间2 task start：" + dateFormat.format(new Date()));
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("现在时间2 task end：" + dateFormat.format(new Date()));
    }
}