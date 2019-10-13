package com.tyhgg.asr.common.entity;

import com.tyhgg.core.framework.entity.PageEntity;

public class SystemPropertiesEntity extends PageEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5659110130167419464L;
	
	private int id;
	private String sysKey;
    private String sysValue;
    private String rem;
    private String searchKey;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysValue() {
		return sysValue;
	}
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
    
}
