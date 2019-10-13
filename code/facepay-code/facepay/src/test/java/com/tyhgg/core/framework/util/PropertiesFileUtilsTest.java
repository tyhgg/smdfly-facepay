package com.tyhgg.core.framework.util;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.tyhgg.core.framework.util.PropertiesFileUtils;

public class PropertiesFileUtilsTest {
	
	@Test
	public void testGetSystemProperties() {
		Properties properties = PropertiesFileUtils.getSystemProperties();
		
		Assert.assertTrue("java:/comp/env/jdbc/ecsDS".equals(properties.get("dataSource.name")));
	}
	
	@Test
	public void testReadPropertiesFile() {
		Properties properties = PropertiesFileUtils.readPropertiesFile("system.properties");
		
		Assert.assertTrue("java:/comp/env/jdbc/ecsDS".equals(properties.get("dataSource.name")));
	}
}
