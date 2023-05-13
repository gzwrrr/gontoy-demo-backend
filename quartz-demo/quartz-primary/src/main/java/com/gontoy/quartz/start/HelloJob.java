package com.gontoy.quartz.start;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gzw
 */
@PersistJobDataAfterExecution
public class HelloJob implements Job {

    private int count;

    public HelloJob() {
        System.out.println("访问 HelloJob");
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 输出当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);

        System.out.println("============================================ 任务开始执行 ============================================");
        System.out.println("====================== HelloJob ======================");

        // 从 JobExecutionContext 获取调度器传递过来的信息
        System.out.println("[jobKey 中的信息]：");
        JobKey jobKey = context.getJobDetail().getKey();
        System.out.printf("工作任务的名称：%s；工作任务的组：%s\n", jobKey.getName(), jobKey.getGroup());
        System.out.printf("任务类的名称（带路径）：%s\n", context.getJobDetail().getJobClass().getName());
        System.out.printf("任务类的名称：%s\n\n", context.getJobDetail().getJobClass().getSimpleName());

        System.out.println("[triggerKey 中的信息]：");
        TriggerKey triggerKey = context.getTrigger().getKey();
        System.out.printf("触发器的名称：%s；触发器的组：%s\n\n", triggerKey.getName(), triggerKey.getGroup());

        // 从 JobDetail 中获取 JobDataMap 数据
        System.out.println("[jobDataMap 中的信息]：");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String jobDataMessage = jobDataMap.getString("message");
        System.out.printf("任务数据的参数值：%s\n\n", jobDataMessage);

        // 获取 Trigger 对象的 JobDataMap 的数据
        System.out.println("[jobDataMap 中的信息]：");
        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();
        String triggerDataMessage = triggerDataMap.getString("message");
        System.out.printf("触发器数据的参数值：%s\n\n", jobDataMessage);

        // 其他信息
        System.out.println("[JobExecutionContext 中的其他信息]：");
        System.out.printf("当前任务的执行时间：%s\n", format.format(context.getFireTime()));
        System.out.printf("下次任务的执行时间：%s\n\n", format.format(context.getNextFireTime()));

        // 执行具体的业务
        System.out.println("[执行任务]：");
        System.out.printf("正在进行数据库的备份，备份时间为：%s\n\n", dateString);

        // 累加 count，测试有状态 Job
        System.out.println("[有状态 Job 维护的数据]：");
        ++count;
        context.getJobDetail().getJobDataMap().put("count", count);
        System.out.printf("count: %s\n", count);
        System.out.println("====================== HelloJob ======================");
        System.out.println("============================================ 任务执行完毕 ============================================\n");
    }
}
