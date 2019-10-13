package com.tyhgg.manager.system.service;

import net.sf.json.JSONObject;

import com.tyhgg.asr.system.entity.RoleRelEntity;

public interface ManagerRoleService  {

	JSONObject saveRoleAndMenuRel(RoleRelEntity roleVo);
	
	JSONObject addSelectedRolePeo(String roleId, String[] peolist);
}
