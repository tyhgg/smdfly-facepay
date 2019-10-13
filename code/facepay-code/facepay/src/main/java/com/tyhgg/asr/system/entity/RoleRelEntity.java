
package com.tyhgg.asr.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RoleRelEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private String roleId;
	private String roleName;
	private String roleRem;

	private List<ModuleMenuEntity> moduleMenuList;
	
	private List<RolePeopleRelEntity> rolePeopleRelVoList = new RolePeopleRelVoList();
	private List<RoleMenuRelEntity> roleMenuRelVoList = new RoleMenuRelVoList();
	private String userIdJson;
	public String getRoleRem() {
		return roleRem;
	}
	public void setRoleRem(String roleRem) {
		this.roleRem = roleRem;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<ModuleMenuEntity> getModuleMenuList() {
		return moduleMenuList;
	}
	public void setModuleMenuList(List<ModuleMenuEntity> moduleMenuList) {
		this.moduleMenuList = moduleMenuList;
	}
	

	public String getUserIdJson() {
		return userIdJson;
	}
	public void setUserIdJson(String userIdJson) {
		this.userIdJson = userIdJson;
	}
	public List<RolePeopleRelEntity> getRolePeopleRelVoList() {
		return rolePeopleRelVoList;
	}
	public void setRolePeopleRelVoList(List<RolePeopleRelEntity> rolePeopleRelVoList) {
		this.rolePeopleRelVoList = rolePeopleRelVoList;
	}
	public List<RoleMenuRelEntity> getRoleMenuRelVoList() {
		return roleMenuRelVoList;
	}
	public void setRoleMenuRelVoList(List<RoleMenuRelEntity> roleMenuRelVoList) {
		this.roleMenuRelVoList = roleMenuRelVoList;
	}
	
	class RolePeopleRelVoList extends ArrayList<RolePeopleRelEntity> implements Serializable {
		private static final long serialVersionUID = 2844274989602064974L;

		/**
		 * 此方法重载了ArrayList类的get（int)方法， 以防下标异常
		 */
		public RolePeopleRelEntity get(int index) {
			while (index >= size()) {
				add(new RolePeopleRelEntity());
			}
			return super.get(index);
		}
	}

	class RoleMenuRelVoList extends ArrayList<RoleMenuRelEntity> implements Serializable {
		private static final long serialVersionUID = 2844274989602064974L;

		/**
		 * 此方法重载了ArrayList类的get（int)方法， 以防下标异常
		 */
		public RoleMenuRelEntity get(int index) {
			while (index >= size()) {
				add(new RoleMenuRelEntity());
			}
			return super.get(index);
		}
	}
	
	@Override
	public String toString() {
		return "RoleEntity [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleRem=" + roleRem + "]";
	}
}