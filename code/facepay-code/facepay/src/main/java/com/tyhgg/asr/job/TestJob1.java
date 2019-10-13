package com.tyhgg.asr.job;




import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;


public class TestJob1
        extends QuartzJobBean {
    /**
     * logback
     */
    static Logger logger = LoggerFactory.getLogger(TestJob1.class);

    /**
     * 定时任务逻辑实现方法
     * 每当触发器触发时会执行该方法逻辑
     *
     * @param jobExecutionContext 任务执行上下文
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("分布式节点quartz-cluster-node-one，执行任务，任务时间：{}", new Date());
    }
}
