package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.TranEntity;

/**
 * 交易
 * @author zyt5668
 *
 */
@Repository
public interface TranMapper {
	
	List<TranEntity> queryTranList(TranEntity tranEntity);
	
}
