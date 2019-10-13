package com.tyhgg.core.framework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class SystemBootRestTemplate {
	@Autowired
	RestTemplateBuilder restTemplateBuilder ;
	public void foo ( ) {
		RestTemplate restTemplate = restTemplateBuilder.build ();
		
	}
}
