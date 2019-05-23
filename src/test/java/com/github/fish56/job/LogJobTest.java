package com.github.fish56.job;

import org.junit.Before;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.junit.Assert.*;

public class LogJobTest {
    private Scheduler scheduler;
    @Before
    public void init() throws SchedulerException{
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();

        // 通过schedulerFactory获取一个调度器
        scheduler = schedulerfactory.getScheduler();
    }

    @Test
    public void startJob() throws Exception{

        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDetail job = JobBuilder.newJob(LogJob.class)
                .withIdentity("logger1", "Logger")
                .build();

        // 定义调度触发规则
        //  corn表达式  每五秒执行一次
        Trigger trigger= TriggerBuilder.newTrigger().withIdentity("CronTrigger1", "CronTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).startNow().build();

        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(job, trigger);

        // 启动调度
        scheduler.start();

        Thread.sleep(20 * 1000);

        // 停止调度
        scheduler.shutdown();

    }
}