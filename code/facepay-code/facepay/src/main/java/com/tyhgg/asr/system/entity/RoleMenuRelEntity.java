
package com.tyhgg.asr.system.entity;

import java.io.Serializable;


public class RoleMenuRelEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private int id;
	private String roleId;
	private int modId;
	private String modName;
	private String isChecked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public int getModId() {
		return modId;
	}
	public void setModId(int modId) {
		this.modId = modId;
	}
	public String getModName() {
		return modName;
	}
	public void setModName(String modName) {
		this.modName = modName;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "RoleMenuRelVo [id=" + id + ", roleId=" + roleId + ", modId="
				+ modId + "]";
	}
	
}