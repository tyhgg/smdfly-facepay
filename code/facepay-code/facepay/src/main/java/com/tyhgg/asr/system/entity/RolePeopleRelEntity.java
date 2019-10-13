
package com.tyhgg.asr.system.entity;

import java.io.Serializable;


public class RolePeopleRelEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private int id;
	private String roleId;
	private String orgId;
	private String orgName;
	private String userId;
	private String peopleName;
	private String peopleTitle;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}
	
	public String getPeopleTitle() {
		return peopleTitle;
	}
	public void setPeopleTitle(String peopleTitle) {
		this.peopleTitle = peopleTitle;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Override
	public String toString() {
		return "RolePeopleRelVo [id=" + id + ", roleId=" + roleId + ", userId="
				+ userId + "]";
	}
	
}