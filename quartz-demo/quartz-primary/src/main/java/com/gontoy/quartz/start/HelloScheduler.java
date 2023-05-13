package com.gontoy.quartz.start;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 调度器
 * @author gzw
 */
public class HelloScheduler {
    public HelloScheduler() {
        System.out.println("访问 HelloScheduler");
    }

    public static void main(String[] args) throws SchedulerException {
        // 调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 任务实例；withIdentity 的第一个参数为任务的名称，第二个参数为任务组的名称
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("Job-1", "Group-1")
                .usingJobData("message", "打印日志")
                .usingJobData("count", 0)
                .build();

        // 打印信息
        System.out.println("\n====================== HelloScheduler ======================");
        System.out.printf("名称：%s\n", jobDetail.getKey().getName());
        System.out.printf("组名称：%s\n", jobDetail.getKey().getGroup());
        System.out.printf("任务类名称：%s\n", jobDetail.getJobClass().getName());
        System.out.println("====================== HelloScheduler ======================\n");


        // 触发器；withIdentity 的第一个参数为触发器的名称，第二个参数为触发器组的名称
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("Trigger-1", "Group-1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .usingJobData("message", "simple 触发器")
                .build();

        // 让调度器关联任务和触发器
        scheduler.scheduleJob(jobDetail, trigger);

        // 启动调度器
        scheduler.start();
    }
}
