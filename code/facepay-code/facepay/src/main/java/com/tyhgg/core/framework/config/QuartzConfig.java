package com.tyhgg.core.framework.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tyhgg.asr.job.TestJob;
import com.tyhgg.asr.job.TestJob1;

/**
 * 定时任务配置
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(TestJob.class).withIdentity("testJob").storeDurably().build();
    }

    @Bean
    public JobDetail testJob1Detail() {
        return JobBuilder.newJob(TestJob1.class).withIdentity("testJob1").storeDurably().build();
    }

    /**
     * 定时任务
     */
//    @Bean
//    public Trigger testJobTrigger() {
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/30 * * * * ?");
//        return TriggerBuilder.newTrigger().forJob(testJobDetail())
//                .withIdentity("testJob")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }

//    @Bean
//    public Trigger testJob1Trigger() {
//        return TriggerBuilder.newTrigger().forJob(testJob1Detail()).withIdentity("testJob1") //定义name/group
//                .startNow()//一旦加入scheduler，立即生效
//                .withSchedule(simpleSchedule())//使用SimpleTrigger
//                .build();
//    }

}
