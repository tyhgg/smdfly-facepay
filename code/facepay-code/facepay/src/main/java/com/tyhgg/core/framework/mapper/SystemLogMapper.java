/**
 * 
 */
package com.tyhgg.core.framework.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.core.framework.entity.SystemLogEntity;

@Repository
public interface SystemLogMapper {
    /**
     * 保存日志
     * 
     * @param entry
     */
    int save(SystemLogEntity entry);
    
    
    /*
     * 查询日志
     * 
     * */
    
    List<SystemLogEntity> querySystemLog(SystemLogEntity entry);
    
}
