///**
// * 
// */
//package com.tyhgg.core.framework.log;
//
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.log4j.AppenderSkeleton;
//import org.apache.log4j.spi.LoggingEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.entity.SystemLog;
//import com.tyhgg.core.framework.util.StringUtil;
//import com.tyhgg.core.framework.util.SystemLogUtil;
//
///**
// * 线程池方式记数据库日志
// * @类名称: SystemLogDBAppender
// * @类描述: 
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2019年1月15日 上午10:51:18
// * @修改备注：
// */
//public class SystemLogDBAppender extends AppenderSkeleton {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogDBAppender.class);
//    
//    // 记数据库日志线程池默认数
// 	public static int THREAD_POOL_COUNT = 3;
//
// 	private static ExecutorService threadPool = null;
//    
// 	static {
// 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
// 		// 记数据库日志线程池数据库配置参数
// 		String systemlogPoolCount = systemPropertyMap.get("systemlog.pool.count");
// 		if(StringUtil.isNotEmpty(systemlogPoolCount)){
// 	 		// 线程池数
// 	 		THREAD_POOL_COUNT = Integer.valueOf(systemlogPoolCount);
// 		}
// 		
// 		LOGGER.info("------------线程池数量为 " + THREAD_POOL_COUNT);
//
// 		// 任务缓存队列及排队策略:LinkedBlockingQueue 基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE
//// 		BlockingQueue workQueue = new LinkedBlockingQueue(5);
// 		// 任务拒绝策略:丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
//// 		RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
// 		
// 		// 创建固定容量大小的缓冲池,采用LinkedBlockingQueue任务缓存队列及排队策略,DiscardOldestPolicy任务拒绝策略
// 		// ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、invokeAny以及shutDown等
// 		threadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);  
// 	}
// 	
//	@Override
//	public void close() {
//
//	}
//
//	@Override
//	public boolean requiresLayout() {
//		return false;
//	}
//
//	@Override
//	protected void append(LoggingEvent event) {
//		// 取线程安全的参数
//		SystemLog logEntity = SystemLogUtil.LOG_ENTITY.get();
//		SystemLogUtil.LOG_ENTITY.remove();
//		// 不为空就用线程池方式新增数据库日志
//		if (logEntity != null) {
//			if(null == threadPool){
//	 			threadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);  
//	 		}
//	 		
//	 		threadPool.execute(new SystemLogThreadWorker(logEntity));
//		}
//	}
//	
//	/*@Override
//	protected void append(LoggingEvent event) {
//		SystemLog logEntity = SystemLogUtil.LOG_ENTITY.get();
//		SystemLogUtil.LOG_ENTITY.remove();
//		if (iSystemlogMapper == null) {
//			iSystemlogMapper = ContextUtil.getBean(SystemlogMapper.class);
//		}
//		if (logEntity != null) {
//			try {
//				iSystemlogMapper.save(logEntity);
//			} catch (Exception e) {
//				LOGGER.debug("新增数据库日志异常，不影响正常业务：", e);
//			}
//			
//		}
//
//	}*/
//}
