package com.tyhgg.manager.system.service;

import net.sf.json.JSONObject;

import com.tyhgg.asr.system.entity.PeopleEntity;

public interface ManagerPeopleService	  {
	
	public JSONObject savePeople(PeopleEntity peopleEntity, JSONObject resultJson);

	public JSONObject deletePeople(PeopleEntity peopleEntity);

    /**
     * 判断用户是否是管理员 orleId：1-系统超级管理员 2.ehrId admin结尾的用户是管理员
     * */
    public boolean peopleIsAdmin(String orleId, String ehrId);
}
