package com.tyhgg.core.framework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.entity.SystemLogEntity;
import com.tyhgg.core.framework.mapper.SystemLogMapper;
import com.tyhgg.core.framework.util.ContextUtil;

/**
 * 记录数据库日志线程类
 * 无需获取线程返回值
 * @author zyt5668
 *
 */
public class SystemLogThreadWorker implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogThreadWorker.class);
	private SystemLogEntity logEntity;

	private SystemLogMapper systemlogMapper;

	{
		if (null == systemlogMapper) {
			systemlogMapper = ContextUtil.getBean(SystemLogMapper.class);
		}
	}
	
	public SystemLogThreadWorker(){
		
	}
	
	public SystemLogThreadWorker(SystemLogEntity logEntity){
		this.logEntity = logEntity;
	}
	
	@Override
	public void run() {
		if (logEntity != null) {
			LOGGER.info("新增数据库日志uuid为:" + logEntity.getUuid());
			
			try {
				if (null == systemlogMapper) {
					systemlogMapper = ContextUtil.getBean(SystemLogMapper.class);
				}
				
				this.systemlogMapper.save(this.logEntity);
			} catch (Exception e) {
				LOGGER.error("新增数据库日志异常，不影响正常业务：", e);
			}
			
		}
	}   
} 