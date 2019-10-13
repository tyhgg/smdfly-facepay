/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.MessageCodeEntity;

/**
 * @author Administrator
 *
 */
@Repository
public interface MessageCodeMapper {
	
	List<MessageCodeEntity> queryMessageCodeList(MessageCodeEntity messageCodeEntity);

	MessageCodeEntity queryMessageCode(MessageCodeEntity messageCodeEntity);
	
	int insertMessageCode(MessageCodeEntity messageCodeEntity);

	int deleteMessageCode(String peopleTel);

	int updateMessageNum(MessageCodeEntity messageCodeEntity);
	
	int queryRecentMessageCodeCount(String peopleTel);
	
}
