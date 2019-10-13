package com.tyhgg.asr.system.entity;

import java.io.Serializable;

public class CountryInfoEntity implements Serializable {
	
	
	
	private static final long serialVersionUID = 1961146260473841208L;

	
	private String countryId;
	private String countryName;
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Override
	public String toString() {
		return "CountryInfoEntity [countryId=" + countryId + ", countryName="
				+ countryName + "]";
	}
	
	

}
