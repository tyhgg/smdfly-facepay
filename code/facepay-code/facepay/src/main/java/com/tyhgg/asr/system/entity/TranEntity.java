package com.tyhgg.asr.system.entity;

import java.io.Serializable;

/**
 * 
 * @author hotel_info 表实体类
 *
 */
public class TranEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String tranUrl;
	private String tranName;
	private String tranStatus;
	private String remark;
	private String modId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTranUrl() {
		return tranUrl;
	}
	public void setTranUrl(String tranUrl) {
		this.tranUrl = tranUrl;
	}
	public String getTranName() {
		return tranName;
	}
	public void setTranName(String tranName) {
		this.tranName = tranName;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	@Override
	public String toString() {
		return "TranEntity [id=" + id + ", tranUrl=" + tranUrl + ", tranName=" + tranName + ", tranStatus=" + tranStatus
				+ ", remark=" + remark + "]";
	}
	
}
