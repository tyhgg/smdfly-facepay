package com.tyhgg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan({"com.tyhgg.*.*.mapper","com.tyhgg.*.mapper"})
public class MiniBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniBootApplication.class, args);
    }
}

/**
 * war包部署方式
 *
 *//*
@SpringBootApplication
@ServletComponentScan
@MapperScan({"com.tyhgg.*.*.mapper","com.tyhgg.*.mapper"})
@ImportResource("classpath:applicationContext.xml")
public class EcsBootApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(EcsBootApplication.class);
	}
	
   
}*/