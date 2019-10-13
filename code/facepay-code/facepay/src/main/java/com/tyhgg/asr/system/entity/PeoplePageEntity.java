package com.tyhgg.asr.system.entity;

import java.io.Serializable;

import com.tyhgg.core.framework.entity.PageEntity;

public class PeoplePageEntity extends PageEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1961146260473841208L;
	

    private int id;
    private String userId;
    private String peopleName;
    private int sortId;
    private String peoplePass;
    private String peopleIdtype;
    private String peopleIdno;
    private String countryId;
    private String peopleCountry;
    private String nationId;
    private String peopleNation;
    private String titleId;
    private String titleName;
    private String birthday;
    private String address;
    private String peopleSex;
    private String peopleTel;
    private String peoplePhone;
    private String rem;
    private String orgId;
    private String orgName;
    private String roleId;
    private String roleName;
    private String createTime;
    private String modTime;
    private String queryKey;
    private String peopleStatus;
    private String updatePass;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public String getPeoplePass() {
		return peoplePass;
	}
	public void setPeoplePass(String peoplePass) {
		this.peoplePass = peoplePass;
	}
	public String getPeopleIdtype() {
		return peopleIdtype;
	}
	public void setPeopleIdtype(String peopleIdtype) {
		this.peopleIdtype = peopleIdtype;
	}
	public String getPeopleIdno() {
		return peopleIdno;
	}
	public void setPeopleIdno(String peopleIdno) {
		this.peopleIdno = peopleIdno;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getPeopleCountry() {
		return peopleCountry;
	}
	public void setPeopleCountry(String peopleCountry) {
		this.peopleCountry = peopleCountry;
	}
	public String getNationId() {
		return nationId;
	}
	public void setNationId(String nationId) {
		this.nationId = nationId;
	}
	public String getPeopleNation() {
		return peopleNation;
	}
	public void setPeopleNation(String peopleNation) {
		this.peopleNation = peopleNation;
	}
	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPeopleSex() {
		return peopleSex;
	}
	public void setPeopleSex(String peopleSex) {
		this.peopleSex = peopleSex;
	}
	public String getPeopleTel() {
		return peopleTel;
	}
	public void setPeopleTel(String peopleTel) {
		this.peopleTel = peopleTel;
	}
	public String getPeoplePhone() {
		return peoplePhone;
	}
	public void setPeoplePhone(String peoplePhone) {
		this.peoplePhone = peoplePhone;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	public String getPeopleStatus() {
		return peopleStatus;
	}
	public void setPeopleStatus(String peopleStatus) {
		this.peopleStatus = peopleStatus;
	}
	public String getUpdatePass() {
		return updatePass;
	}
	public void setUpdatePass(String updatePass) {
		this.updatePass = updatePass;
	}
	
	@Override
	public String toString() {
		return "PeoplePageEntity [id=" + id + ", userId=" + userId + ", peopleName=" + peopleName + ", sortId=" + sortId
				+ ", peoplePass=" + peoplePass + ", peopleIdtype=" + peopleIdtype + ", peopleIdno=" + peopleIdno
				+ ", countryId=" + countryId + ", peopleCountry=" + peopleCountry + ", nationId=" + nationId
				+ ", peopleNation=" + peopleNation + ", titleId=" + titleId + ", titleName=" + titleName + ", birthday="
				+ birthday + ", address=" + address + ", peopleSex=" + peopleSex + ", peopleTel=" + peopleTel
				+ ", peoplePhone=" + peoplePhone + ", rem=" + rem + ", orgId=" + orgId + ", orgName=" + orgName
				+ ", roleId=" + roleId + ", roleName=" + roleName + ", createTime=" + createTime + ", modTime=" + modTime
				+ ", queryKey=" + queryKey + ", peopleStatus=" + peopleStatus + ", updatePass=" + updatePass + "]";
	}

}
