/**
 * 
 */
package com.tyhgg.asr.wechat.mapper;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.wechat.entity.WechatTokenEntity;

/**
 * 
 * @author zyt5668
 *
 */
@Repository
public interface WechatTokenMapper {
	
	WechatTokenEntity queryWechatToken(String corpId);

	int updateWechatToken(WechatTokenEntity hangXinTokenEntity);

	int save(WechatTokenEntity hangXinTokenEntity);

	int del(WechatTokenEntity hangXinTokenEntity);
}
