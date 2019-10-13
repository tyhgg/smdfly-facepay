package com.tyhgg.asr.system.entity;

import java.io.Serializable;

public class PeopleLoginEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String clientId;
    private String sessionId;
    private String loginType;
    private String openId;
    private String unionId;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private String loginTime;
    private String timeoutTime;
    private String loginStatus;
    private int loginCount;
    private String createTime;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getTimeoutTime() {
		return timeoutTime;
	}
	public void setTimeoutTime(String timeoutTime) {
		this.timeoutTime = timeoutTime;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	@Override
	public String toString() {
		return "PeopleLoginEntity [userId=" + userId + ", clientId=" + clientId + ", sessionId=" + sessionId
				+ ", loginType=" + loginType + ", openId=" + openId + ", unionId=" + unionId + ", accessToken="
				+ accessToken + ", refreshToken=" + refreshToken + ", expiresIn=" + expiresIn + ", loginTime=" + loginTime
				+ ", timeoutTime=" + timeoutTime + ", loginStatus=" + loginStatus + ", loginCount=" + loginCount
				+ ", createTime=" + createTime + "]";
	}
}
