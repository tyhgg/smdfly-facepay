package com.tyhgg.core.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvConfig {
	@Autowired
	private Environment env;

	public int getServerPort() {
		env.getProperty("user.dir"); // 程序运行的目录，如果在IDE 中运行， 就是工程目录， user.dir是系统属性
		return env.getProperty("server.port", Integer.class);
	}
}