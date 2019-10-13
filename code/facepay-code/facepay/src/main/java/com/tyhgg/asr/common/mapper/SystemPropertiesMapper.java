package com.tyhgg.asr.common.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.common.entity.SystemPropertiesEntity;

@Repository
public interface SystemPropertiesMapper {

	void addProperties(SystemPropertiesEntity systemPropertiesEntity);
	void updateProperties(SystemPropertiesEntity systemPropertiesEntity);
	List<Map<String, Object>> getProperties(SystemPropertiesEntity systemPropertiesEntity);
	int getPropertiesCount(SystemPropertiesEntity systemPropertiesEntity);
	int getSamePropertiesCount(String sysKey);
	void deleteProperties(String sysKey);
	HashMap<String, String> getSystemProperties(String sysKey);
}
