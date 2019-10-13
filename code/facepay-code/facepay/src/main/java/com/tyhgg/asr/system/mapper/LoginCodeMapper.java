/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.LoginCodeEntity;

/**
 * 
 * @author zyt5668
 *
 */
@Repository
public interface LoginCodeMapper {
	
	LoginCodeEntity queryLoginCodeEntity(LoginCodeEntity loginCodeEntity);

	int updateLoginCodeEntity(LoginCodeEntity loginCodeEntity);

	int save(LoginCodeEntity loginCodeEntity);

	int updateLoginNum(LoginCodeEntity loginCodeEntity);
	
	/**
     * 删除登录code
     * @return
     */
   int deleteLoginCode(String userId);

   int deleteLoginCodeByCode(String code);
   
}
