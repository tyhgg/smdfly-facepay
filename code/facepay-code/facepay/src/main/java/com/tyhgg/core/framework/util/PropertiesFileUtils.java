package com.tyhgg.core.framework.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * properties资源文件
 * @author zyt5668
 *
 */
public class PropertiesFileUtils {
	private static Properties systemProperties = null; 

	// 只实例化一次system.properties
    static{
    	systemProperties = readPropertiesFile("application.properties");
    }
	
    public static Properties getSystemProperties() {
    	if(null == systemProperties){
    		systemProperties = readPropertiesFile("application.properties");
    	}
        return systemProperties;
    }
    
	// 读取文件内容
	public static Properties readPropertiesFile(String fileName){
		InputStream inputStream = null;
		// Hashtable子类
		Properties properties = new Properties();
		try {
			inputStream = PropertiesFileUtils.class.getResourceAsStream("/" + fileName);
			// 装载properties文件
			properties.load(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != inputStream){
					inputStream.close();
				}
			} catch (Exception ee){
				
			}
		}
		
		return properties;
	}

}
