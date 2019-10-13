package com.tyhgg.manager.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyhgg.asr.system.entity.ModuleMenuEntity;
import com.tyhgg.asr.system.entity.RoleEntity;
import com.tyhgg.asr.system.entity.RoleMenuRelEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelEntity;
import com.tyhgg.asr.system.entity.RoleRelEntity;
import com.tyhgg.asr.system.mapper.RoleMapper;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerRoleService;

@Service(value = "managerRoleService")
public class ManagerRoleServiceImpl implements ManagerRoleService  {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerRoleServiceImpl.class);
	@Resource
	private RoleMapper roleMapper;

	/**
	 * 保存角色及角色菜单信息
	 */
	@Transactional
	public JSONObject saveRoleAndMenuRel(RoleRelEntity roleRelEntity){
		if(null == roleRelEntity){
			return ResponseUtils.responseWebSuccessJson();
		}
    	String roleId = roleRelEntity.getRoleId();
    	String userIdJson = roleRelEntity.getUserIdJson();
    	LOGGER.info("角色人员id信息="+userIdJson);
    	
		// 修改
        RoleEntity roleEntity = roleMapper.queryRole(roleId);
        if(null != roleEntity){
        	roleEntity.setRoleName(roleRelEntity.getRoleName());
        	roleEntity.setRoleRem(roleRelEntity.getRoleRem());
            this.roleMapper.updateRole(roleEntity);
        } else {
        	roleEntity = new RoleEntity();
        	roleEntity.setRoleId(roleRelEntity.getRoleId());
        	roleEntity.setRoleName(roleRelEntity.getRoleName());
        	roleEntity.setRoleRem(roleRelEntity.getRoleRem());
            this.roleMapper.save(roleEntity);
        }
		/*
        // 新增修改角色人员信息
    	//先删除角色人员信息
		LOGGER.info("删除角色人员信息" + roleId);
		RolePeopleRelVo rolePeopleRelVo = new RolePeopleRelVo();
		rolePeopleRelVo.setRoleId(roleId);
    	this.roleMapper.deleteRolePeopleRel(rolePeopleRelVo);
    	List<RolePeopleRelVo> rolePeopleRelVoList = roleVo.getRolePeopleRelVoList();
		LOGGER.info("新增角色人员信息" + rolePeopleRelVoList);
    	界面优化
    	 * for(RolePeopleRelVo tempVo: rolePeopleRelVoList){
    		if(StringUtil.isNotEmpty(tempVo.getUserId())){
        		tempVo.setRoleId(roleId);
        		this.roleMapper.saveRolePeopleRel(tempVo);
    		}
    	}
		if(userIdJson!=null){
		JSONArray ja = JSONArray.fromObject(userIdJson);
	    	if(ja != null && ja.size()>0){
	    		for(int i=0;i<ja.size();i++){
	    			String userId = ((JSONObject)ja.get(i)).getString("userId");
	    			if(StringUtil.isNotEmpty(userId)){
	    				RolePeopleRelVo tempVo = new RolePeopleRelVo();
	    				tempVo.setRoleId(roleId);
	    				tempVo.setUserId(userId);
	            		this.roleMapper.saveRolePeopleRel(tempVo);
	    			}
	    			
	    		}
	    	}
		}*/
        
    	// 只有系统管理员才有权限修改菜单权限?
        // 新增修改角色权限信息
		LOGGER.info("删除角色权限信息" + roleId);
		RoleMenuRelEntity roleMenuRelEntity = new RoleMenuRelEntity();
		roleMenuRelEntity.setRoleId(roleId);
    	//先删除角色权限信息
    	this.roleMapper.deleteRoleMenuRel(roleMenuRelEntity);
    	List<ModuleMenuEntity> roleMenuRelList = roleRelEntity.getModuleMenuList();
		LOGGER.info("新增角色权限信息" + roleMenuRelList);
    	for(ModuleMenuEntity tempEntity: roleMenuRelList){
    		// 未选择的checkbox是false，选择是true
    		if(0 != tempEntity.getModId() && tempEntity.isVueChecked()){
    			RoleMenuRelEntity tempRoleMenuRelEntity = new RoleMenuRelEntity();
    			tempRoleMenuRelEntity.setRoleId(roleId);
    			tempRoleMenuRelEntity.setModId(tempEntity.getModId());
    			// 新增角色权限信息
        		this.roleMapper.saveRoleMenuRel(tempRoleMenuRelEntity);
    		}
    	}
    	
    	return ResponseUtils.responseWebSuccessJson();
	}

	/**
	 * 新增角色人员
	 */
	public JSONObject addSelectedRolePeo(String roleId, String[] peolist) {
		int size = peolist.length;
		for(int i = 0; i < size; i++){
			String userId = peolist[i];
			if(StringUtil.isNotEmpty(userId)){
				RolePeopleRelEntity tempVo = new RolePeopleRelEntity();
				tempVo.setRoleId(roleId);
				tempVo.setUserId(userId);
				
				List<RolePeopleRelEntity> list = this.roleMapper.queryPeopleRoleRelList(tempVo);
				
				if(null == list || list.size() < 1){
					LOGGER.info("新增角色用户：" + tempVo);
	        		this.roleMapper.saveRolePeopleRel(tempVo);
				}
			}
		}
		
		return ResponseUtils.responseWebSuccessJson();
	}
	
}
