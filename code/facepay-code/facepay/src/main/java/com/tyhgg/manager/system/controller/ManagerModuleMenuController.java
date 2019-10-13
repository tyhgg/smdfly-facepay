package com.tyhgg.manager.system.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.system.entity.ModuleMenuEntity;
import com.tyhgg.asr.system.entity.TranEntity;
import com.tyhgg.asr.system.mapper.ModuleMenuMapper;
import com.tyhgg.asr.system.mapper.TranMapper;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;

@Controller
public class ManagerModuleMenuController {

	private Logger logger = LoggerFactory.getLogger(ManagerModuleMenuController.class);
	@Resource
	private ModuleMenuMapper moduleMenuMapper;
	@Resource
	private TranMapper tranMapper;

	/**
	 * 根据角色查询系统路由-vue,超级管理员返回所有菜单
	 * 
	 * @param header
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/manager/menu/loadSystemModule", method = { RequestMethod.POST })
	@ResponseBody
	public String loadModule(@RequestHeader HttpHeaders header, @RequestBody String body) {

		JSONObject jsonBody = JSONObject.fromObject(body);
		// 返回结果
		JSONObject jsonResult = new JSONObject();
		String userId = StringUtil.getString(jsonBody.get("userId"));
		String roleIds = StringUtil.getString(jsonBody.get("roleId"));

		if (StringUtil.isEmpty(userId)) {
			return ResponseUtils.responseWebErrorJson("未获取到用户ID").toString();
		}

		List<String> menuList = getModuleMenuList(userId, roleIds);

		logger.info("userId:" + userId + ",roleIds:" + roleIds + ",获取到菜单列表：" + menuList);
		if (menuList == null) {
			jsonResult.put("menuList", "");
		} else {
			jsonResult.put("menuList", menuList);
		}
		return ResponseUtils.responseSuccessJson(jsonResult).toString();
	}

	private List<String> getModuleMenuList(String userId, String roleIds) {

		List<String> menuList = new ArrayList<String>();
		ModuleMenuEntity moduleMenuEntity = new ModuleMenuEntity();
		// 系统管理员角色
		if ("sysadmin".equals(userId)) {
			roleIds = "admin";
		} else if (SystemConstants.ROLE_ADMINSTRATOR.equals(roleIds)) {
			roleIds = "('1')";
		}

		moduleMenuEntity.setRoleIds(roleIds);

		List<ModuleMenuEntity> moduleList = this.moduleMenuMapper.queryModuleMenuList(moduleMenuEntity);

		// 管理员账户没分配菜单时显示全部菜单
		if ((null == moduleList || moduleList.size() == 0) && ("('1')").equals(roleIds)) {
			// tyhggadmin用户可看到开发人员菜单
			roleIds = "1";

			moduleMenuEntity.setRoleIds(roleIds);
			moduleList = this.moduleMenuMapper.queryModuleMenuList(moduleMenuEntity);
		}

		// 角色可以查看的mod_id
		StringBuilder modId = new StringBuilder();
        if(null != moduleList && moduleList.size() > 0){
        	modId.append("(");
        	int size = moduleList.size();
        	for (int i = 0; i < size; i++) {
    			ModuleMenuEntity module = moduleList.get(i);
    			menuList.add(module.getModVueUrl());
    			// 系统超级管理员查出了包含-1所有的菜单
    			int modPid = module.getModPid();
    			
    			if(i == 0){
    				modId.append("'").append(modPid).append("'");
                } else {
                	if(!modId.toString().contains("'" + String.valueOf(modPid) + "'")){
                		modId.append(",'").append(modPid).append("'");
                	}
                }
    		}
        	modId.append(")");
        	
        } else {
        	return menuList;
        }
		
		// 添加后台管理端非菜单页面
		TranEntity tranEntity = new TranEntity();
		tranEntity.setTranStatus("9");
		tranEntity.setModId(modId.toString());
		List<TranEntity> queryTranList = this.tranMapper.queryTranList(tranEntity);
		for (int i = 0; i < queryTranList.size(); i++) {
			TranEntity tranEntityTemp = queryTranList.get(i);
			menuList.add(tranEntityTemp.getTranUrl());
		}

		// 去重
		HashSet<String> hs = new HashSet<String>(menuList);
		menuList.clear();
		menuList.addAll(hs);

		// 添加欢迎页面
		menuList.add("/systemManage/welcome");
		
		return menuList;
	}

}
