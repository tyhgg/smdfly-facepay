package com.tyhgg.asr.system.entity;

import java.io.Serializable;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class MessageCodeEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String userId;
	private String peopleTel;
	private String validCode;
	private int validNum;
	private String validDate;
	private String validType;
	private String createDate;
	private String modTime;

	private int minutes;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPeopleTel() {
		return peopleTel;
	}
	public void setPeopleTel(String peopleTel) {
		this.peopleTel = peopleTel;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public int getValidNum() {
		return validNum;
	}
	public void setValidNum(int validNum) {
		this.validNum = validNum;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getValidType() {
		return validType;
	}
	public void setValidType(String validType) {
		this.validType = validType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	@Override
	public String toString() {
		return "ValidateCodeEntity [id=" + id + ", userId=" + userId + ", peopleTel=" + peopleTel + ", validCode="
				+ validCode + ", validNum=" + validNum + ", validDate=" + validDate + ", validType=" + validType
				+ ", createDate=" + createDate + ", modTime=" + modTime + ", minutes=" + minutes + "]";
	}

}
