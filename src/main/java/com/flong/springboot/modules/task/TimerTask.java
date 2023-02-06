package com.flong.springboot.modules.task;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling    //开启定时任务
public class TimerTask {
    //容器启动后,延迟10秒后再执行一次定时器,以后每10秒再执行一次该定时器。
//    @Scheduled(initialDelay = 10000, fixedRate = 10000)
//    private void myTasks3() {
//        System.out.println("我是一个定时任务3");
//    }
}