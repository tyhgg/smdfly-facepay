package com.tyhgg.asr.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.SqlEntity;

@Repository
public interface AdminSQLMapper {

    
    List<Map<String, ?>> customQueryItem(SqlEntity sqlVo);
    int customQueryCount(SqlEntity sqlVo);
    
    void sqlExcuteItem(String sqlContent);
    
}
