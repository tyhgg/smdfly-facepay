package com.tyhgg.asr.system.entity;

import java.io.Serializable;

import com.tyhgg.core.framework.entity.PageEntity;

public class OrgInfoPageEntity extends PageEntity implements Serializable {

	private static final long serialVersionUID = 1961146260473841208L;

	private int id;
	private String orgId;
	private String orgName;
	private String orgFullName;
	private String orgNameEn;
	private String pid;
	private String specificId;
	private String superOrgId;
	private int orgLevel;
	private String isChild;
	private String orgStatus;
	private int sortId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSpecificId() {
		return specificId;
	}
	public void setSpecificId(String specificId) {
		this.specificId = specificId;
	}
	public String getSuperOrgId() {
		return superOrgId;
	}
	public void setSuperOrgId(String superOrgId) {
		this.superOrgId = superOrgId;
	}
	public int getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getIsChild() {
		return isChild;
	}
	public void setIsChild(String isChild) {
		this.isChild = isChild;
	}
	public String getOrgStatus() {
		return orgStatus;
	}
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public String getOrgFullName() {
		return orgFullName;
	}
	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}
	public String getOrgNameEn() {
		return orgNameEn;
	}
	public void setOrgNameEn(String orgNameEn) {
		this.orgNameEn = orgNameEn;
	}
	
	@Override
	public String toString() {
		return "OrgInfoPageEntity [id=" + id + ", orgId=" + orgId + ", orgName=" + orgName + ", orgFullName=" + orgFullName
				+ ", orgNameEn=" + orgNameEn + ", pid=" + pid + ", specificId=" + specificId + ", superOrgId=" + superOrgId
				+ ", orgLevel=" + orgLevel + ", isChild=" + isChild + ", orgStatus=" + orgStatus + ", sortId=" + sortId
				+ "]";
	}
	
}
