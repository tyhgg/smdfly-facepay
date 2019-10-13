package com.tyhgg.core.framework.util;

import com.tyhgg.core.framework.entity.SystemLogEntity;

public class SystemLogUtil {
	// 创建线程局部变量logEntity，用来保存本地线程的logEntity
	public static final ThreadLocal<SystemLogEntity> LOG_ENTITY = new ThreadLocal<SystemLogEntity>(){
        @Override
        protected SystemLogEntity initialValue() {
            return new SystemLogEntity();
        }
    };	
}
