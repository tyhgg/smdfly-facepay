package com.tyhgg.asr.wechat.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 微信接口
 * @RestController = @Controller + @ResponseBody
 * @author zyt5668
 *
 */
@RestController
public class WeChatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private PeopleMapper peopleMapper;

	/**
	 * 小程序获取微信AccessToken
	 * 
	 * @param header
	 *            http报文头
	 * @return
	 */
	@RequestMapping(value = "/unlogin/wechat/getAccessToken", method = {RequestMethod.POST})
	public String getAccessToken(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {
		
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		RestTemplate client = restTemplateBuilder.build();
		
		JSONObject bodyJson = JSONObject.fromObject(body);
		String uri = systemPropertyMap.get("wechat.token.uri");
		String appSecret = systemPropertyMap.get("wechat.appsecret");
		String appId = StringUtil.getString(bodyJson.get("appId"));
		if(StringUtil.isEmpty(appId)){
			appId = systemPropertyMap.get("wechat.appid");
		}
		
		StringBuilder url = new StringBuilder(uri);
		
		url.append("?").append("appid=").append(appId)
			.append("&secret=").append(appSecret)
			.append("&grant_type=").append("client_credential");
		
		LOGGER.info("访问微信URL：" + url);
		
		String result = null;
		try{
			result = client.getForObject(url.toString(), String.class);
			JSONObject rsJson = JSONObject.fromObject(result);
			if(rsJson.containsKey("errcode") && "0".equals(StringUtil.getString(rsJson.get("errcode")))){
				return ResponseUtils.responseErrorJson(ExceptionCode.EC_000001, "请求微信获取Token异常").toString();
			}
		}catch (Exception e) {
			LOGGER.error("调用微信服务异常", e);
		}
		
		return result;
	}
	
	/**
	 * 获取微信OpenId
	 * 
	 * @param header
	 *            http报文头
	 * @return
	 */
	@RequestMapping(value = "/unlogin/getOpenId", method = {RequestMethod.POST})
	@ResponseBody
	public String getWeChatOpenId(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {
		
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		RestTemplate client = restTemplateBuilder.build();
		
		JSONObject bodyJson = JSONObject.fromObject(body);
		String uri = systemPropertyMap.get("wechat.openid.uri");
		String appSecret = systemPropertyMap.get("wechat.appsecret");
		
		StringBuilder url = new StringBuilder(uri);
		
		url.append("?")
			.append("appid=").append(bodyJson.get("appId"))
			.append("&secret=").append(appSecret)
			.append("&js_code=").append(bodyJson.get("code"))
			.append("&grant_type=").append(bodyJson.get("granType"));
		
		LOGGER.info("访问微信URL：" + url);
		
		String ret = null;
		try{
			ret = client.getForObject(url.toString(), String.class);
			LOGGER.info("微信返回报文：" + ret);
			JSONObject rsJson = JSONObject.fromObject(ret);
			if("0".equals(StringUtil.getString(rsJson.get("errcode")))){
				return ResponseUtils.responseErrorJson(ExceptionCode.EC_000001, "请求微信获取OpenId异常").toString();
			}
		}catch (Exception e) {
			LOGGER.error("调用微信服务异常", e);
		}
		
		return ret;
	}
	
}
