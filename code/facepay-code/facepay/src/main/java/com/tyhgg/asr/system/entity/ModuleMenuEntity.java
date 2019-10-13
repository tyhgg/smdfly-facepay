
package com.tyhgg.asr.system.entity;

import java.io.Serializable;
import java.util.List;


public class ModuleMenuEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private int id;
	private int modId;
	private String modName;
	private String modUrl;
	private String modVueUrl;
	private int modPid;
	private String isChild;
	private int sortId;
	private String modStatus;
	private String rem;
	private String roleIds;
	private String isChecked;// 1是选中
	private boolean vueChecked;
	
	private List<ModuleMenuEntity> moduleMenuList; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getModUrl() {
		return modUrl;
	}
	public void setModUrl(String modUrl) {
		this.modUrl = modUrl;
	}
	public String getModVueUrl() {
		return modVueUrl;
	}
	public void setModVueUrl(String modVueUrl) {
		this.modVueUrl = modVueUrl;
	}
	public int getModPid() {
		return modPid;
	}
	public void setModPid(int modPid) {
		this.modPid = modPid;
	}
	public String getIsChild() {
		return isChild;
	}
	public void setIsChild(String isChild) {
		this.isChild = isChild;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public String getModStatus() {
		return modStatus;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public void setModStatus(String modStatus) {
		this.modStatus = modStatus;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	
	public boolean isVueChecked() {
		return vueChecked;
	}
	public void setVueChecked(boolean vueChecked) {
		this.vueChecked = vueChecked;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public void setModuleMenuList(List<ModuleMenuEntity> moduleMenuList) {
		this.moduleMenuList = moduleMenuList;
	}
	
	public List<ModuleMenuEntity> getModuleMenuList() {
		return moduleMenuList;
	}
	@Override
	public String toString() {
		return "ModuleMenuEntity [id=" + id + ", modId=" + modId + ", modName=" + modName + ", modUrl=" + modUrl
				+ ", modVueUrl=" + modVueUrl + ", modPid=" + modPid + ", isChild=" + isChild + ", sortId=" + sortId
				+ ", modStatus=" + modStatus + ", rem=" + rem + ", roleIds=" + roleIds + ", isChecked=" + isChecked
				+ ", vueChecked=" + vueChecked + ", moduleMenuList=" + moduleMenuList + "]";
	}
	
}