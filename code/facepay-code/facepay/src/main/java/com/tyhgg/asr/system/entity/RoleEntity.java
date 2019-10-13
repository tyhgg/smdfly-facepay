
package com.tyhgg.asr.system.entity;

import java.io.Serializable;


public class RoleEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private String roleId;
	private String roleIds;
	private String roleName;
	private String roleRem;
	
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
	
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	@Override
	public String toString() {
		return "RoleEntity [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleRem=" + roleRem + "]";
	}
}