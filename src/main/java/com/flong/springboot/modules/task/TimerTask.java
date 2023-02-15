package com.flong.springboot.modules.task;


import com.flong.springboot.modules.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling    //开启定时任务
public class TimerTask {
    @Autowired
    OrderService orderService;

    //容器启动后,延迟10秒后再执行一次定时器,以后每10秒再执行一次该定时器。
    @Scheduled(initialDelay = 10000, fixedRate = 1000*60*60*24)
    private void myTasks3() {
        log.info("定时任务");
        //orderService.pushEvaOrder();
    }
}