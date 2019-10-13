package com.tyhgg.asr.wechat.service;

import com.tyhgg.asr.system.entity.MessageCodeEntity;


public interface MessageService {
	
	/**
	 * 优化中英文短信内容
	 * 根据手机号发送验证码
	 * @param peopleTel 
	 * 
	 * */
	public Object insertValidCodeAndSendMessage(MessageCodeEntity validCodeEntity);
	
	/**
	 * 根据手机号,短信验证码验证验证码是否正确
	 * @param validCodeEntity 
	 * */
	public String checkValidCode(MessageCodeEntity validCodeEntity);
	
}
