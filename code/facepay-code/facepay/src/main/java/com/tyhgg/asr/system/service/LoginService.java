package com.tyhgg.asr.system.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.tyhgg.asr.system.entity.PeopleEntity;

public interface LoginService  {

	// 获取登录信息
	JSONObject handleLoginInfo(HttpServletRequest request, JSONObject bodyJson, PeopleEntity peopleEntity, 
			Map<String, String> msgMap, Map<String, String> systemPropertyMap);
	
}
