package com.tyhgg.core.framework.log;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.entity.SystemLogEntity;
import com.tyhgg.core.framework.mapper.SystemLogMapper;
import com.tyhgg.core.framework.util.SpringUtil;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.core.framework.util.SystemLogUtil;

@Getter
@Setter
public class SystemLogBackDbAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	Layout<ILoggingEvent> layout;

	private SystemLogMapper systemLogMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogBackDbAppender.class);

    // 记数据库日志线程池默认数
 	public static int THREAD_POOL_COUNT = 3;

 	private static ExecutorService threadPool = null;
	

 	static {
 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
 		// 记数据库日志线程池数据库配置参数
 		String systemlogPoolCount = systemPropertyMap.get("systemlog.pool.count");
 		if(StringUtil.isNotEmpty(systemlogPoolCount)){
 	 		// 线程池数
 	 		THREAD_POOL_COUNT = Integer.valueOf(systemlogPoolCount);
 		}
 		
 		LOGGER.info("------------线程池数量为 " + THREAD_POOL_COUNT);

 		// 任务缓存队列及排队策略:LinkedBlockingQueue 基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE
// 		BlockingQueue workQueue = new LinkedBlockingQueue(5);
 		// 任务拒绝策略:丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
// 		RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
 		
 		// 创建固定容量大小的缓冲池,采用LinkedBlockingQueue任务缓存队列及排队策略,DiscardOldestPolicy任务拒绝策略
 		// ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、invokeAny以及shutDown等
 		threadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);  
 	}
 	
	@Override
	public void start() {
		LOGGER.info("vvvvvvvvvvvvvvvvvvvvvvvvv");
		if (layout == null) {
			addWarn("Layout was not defined");
		}

		super.start();
	}

	@Override
	public void stop() {
		if (!isStarted()) {
			return;
		}
		super.stop();
	}

	@Override
	protected void append(ILoggingEvent evnet) {
		
		if (evnet == null || !isStarted()) {
			return;
		}
		SystemLogEntity logEntity = SystemLogUtil.LOG_ENTITY.get();
		SystemLogUtil.LOG_ENTITY.remove();
		if (systemLogMapper == null) {
			systemLogMapper = SpringUtil.getBean(SystemLogMapper.class);

		}
		// 不为空就用线程池方式新增数据库日志
		if (logEntity != null) {
			if(null == threadPool){
	 			threadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);  
	 		}
	 		
	 		threadPool.execute(new SystemLogThreadWorker(logEntity));
		}

	}

}
