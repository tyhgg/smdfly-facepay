package com.tyhgg.asr.wechat.entity;

import java.io.Serializable;

public class WechatTokenEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1961146260473841208L;

	private int id;
	private String corpId;
	private String accessToken;
    private String modTime;
    private int expiresIn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	@Override
	public String toString() {
		return "WechatTokenEntity [id=" + id + ", corpId=" + corpId + ", accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", modTime=" + modTime + "]";
	}

}
