package com.tyhgg.core.framework.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {
	
	/**
	 * post方式xml请求
	 * @param restTemplate
	 * @param url
	 * @param xmlString
	 * @return
	 */
	public static String postXmlRequest(RestTemplate restTemplate, String url, String xmlString) {

		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_XML);
		
		// 创建 HttpEntity
		HttpEntity<String> requestEntity = new HttpEntity<>(xmlString, requestHeader);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, 
				requestEntity, String.class);
		return responseEntity.getBody();
	}

	/**
	 *  post方式json请求
	 * @param restTemplate
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static String postJsonRequest(RestTemplate restTemplate, String url, String jsonString) {

		String response = restTemplate.postForObject(url, jsonString, String.class);
		
		return response;
	}
}

