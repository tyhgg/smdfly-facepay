/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.PeopleLoginEntity;

/**
 * 
 * @author zyt5668
 *
 */
@Repository
public interface PeopleLoginMapper {
	
	PeopleLoginEntity queryPeopleLoginEntity(PeopleLoginEntity peopleLoginEntity);

	List<PeopleLoginEntity> queryPeopleLoginList(PeopleLoginEntity peopleLoginEntity);
	
	int updatePeopleLoginEntity(PeopleLoginEntity peopleLoginEntity);

	int save(PeopleLoginEntity peopleLoginEntity);

	/**
	 * 解除密码锁定
	 * @方法名: unlockPeoplePass
	 * @方法描述: 
	 * @param userId
	 * @return
	 * @return int
	 */
	int unlockPeoplePass(String userId);
	   
}
