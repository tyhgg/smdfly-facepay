package com.tyhgg.asr.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientTranMapper {
	
    /**
     * 查询clientid列表
     */
    List<Map<String,Object>> queryClientList(Map<String,Object> map);

	/**
     * 查询列表
     * @param clientid
     */
    List<Map<String,Object>> queryTranList();
    
    /**
     * 查询列表
     * @param clientid
     */
    List<Map<String,Object>> queryClientTranListByClientId(String clientid);

    /**
     * 根据主键修改接口选中状态
     * @param id
     */
    void insertClientTranRelById(Map<String,Object> map);

    /**
     * 根据主键修改接口选中状态
     * @param id
     */
    void deleteClientTranRelById(String id);
}
