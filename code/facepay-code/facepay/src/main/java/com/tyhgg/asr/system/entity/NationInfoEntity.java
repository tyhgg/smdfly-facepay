package com.tyhgg.asr.system.entity;

import java.io.Serializable;

public class NationInfoEntity implements Serializable {

	private static final long serialVersionUID = 1961146260473841208L;
		
	private String nationId;
	private String nationName;

	public String getNationId() {
		return nationId;
	}
	public void setNationId(String nationId) {
		this.nationId = nationId;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
	@Override
	public String toString() {
		return "NationInfoEntity [nationId=" + nationId + ", nationName="
				+ nationName + "]";
	}

	

}
