package com.tyhgg.asr.system.entity;

import java.io.Serializable;

public class LoginCodeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 流水号
	 * */
	private String id;
	/**
	 * ehr号
	 * */
	private String userId;
	
	/**
	 * 验证码
	 * */
	private String validCode;
	
	/**
	 * 有效时间
	 * */
	private String validTime;
	
	/**
	 * 验证次数
	 * */
	private int validNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public int getValidNum() {
		return validNum;
	}

	public void setValidNum(int validNum) {
		this.validNum = validNum;
	}

	@Override
	public String toString() {
		return "LoginCodeEntity [id=" + id + ", userId=" + userId
				+ ", validCode=" + validCode + ", validTime=" + validTime
				+ ", validNum=" + validNum + "]";
	}
	
}
