
package com.tyhgg.asr.system.entity;

import java.io.Serializable;

import com.tyhgg.core.framework.entity.PageEntity;

/**
 * 角色人员
 * @类名称: RolePeopleRelPageEntity
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年8月15日 下午4:20:58
 * @修改备注：
 */
public class RolePeopleRelPageEntity extends PageEntity implements Serializable {
	/**
	 * 序列化实体Bean
	 */
	private static final long serialVersionUID = 4016815834622064809L;
	private int id;
	private String roleId;
	private String userId;
	private String peopleName;
	private String orgId;
	private String orgName;
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
	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Override
	public String toString() {
		return "RolePeopleRelPageEntity [id=" + id + ", roleId=" + roleId + ", userId=" + userId + ", peopleName="
				+ peopleName + ", isChecked=" + isChecked + "]";
	}
	
}