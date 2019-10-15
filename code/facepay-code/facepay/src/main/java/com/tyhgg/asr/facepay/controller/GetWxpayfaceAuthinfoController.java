package com.tyhgg.asr.facepay.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.JsonXmlObjConvertUtils;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.RestTemplateUtils;
import com.tyhgg.core.framework.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 获取调用凭证
 * @RestController = @Controller + @ResponseBody
 * @author zyt5668
 *
 */
@RestController
public class GetWxpayfaceAuthinfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetWxpayfaceAuthinfoController.class);
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private PeopleMapper peopleMapper;

	/**
	 * 获取调用凭证(get_wxpayface_authinfo)
	 * 
	 * @param header
	 *            http报文头
	 * @return
	 */
	@RequestMapping(value = "/unlogin/getWxpayfaceAuthinfo", method = {RequestMethod.POST})
	public String getAccessToken(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {
		
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		RestTemplate restTemplate = restTemplateBuilder.build();
		
		JSONObject bodyJson = JSONObject.fromObject(body);
		
		JSONObject requestJson = new JSONObject();
		// 门店编号， 由商户定义， 各门店唯一。
		String store_id = "";
		requestJson.put("store_id", store_id);
		// 门店名称，由商户定义。（可用于展示）
		String store_name = "";
		requestJson.put("store_name", store_name);
		// 终端设备编号，由商户定义。
		String device_id ="";
		requestJson.put("device_id", device_id);
		//否 附加字段。字段格式使用Json
		String attach="";
		requestJson.put("attach", attach);
		// 初始化数据。由微信人脸SDK的接口返回。
		String rawdata="";
		requestJson.put("rawdata", rawdata);
		// 商户号绑定的公众号/小程序 appid
		String appid="";
		requestJson.put("appid", appid);
		// 商户号
		String mch_id="";
		requestJson.put("mch_id", mch_id);
		//否 子商户绑定的公众号/小程序 appid(服务商模式)
		String sub_appid="";
		requestJson.put("sub_appid", sub_appid);
		//否 子商户号(服务商模式)
		String sub_mch_id="";
		requestJson.put("sub_mch_id", sub_mch_id);
		// 取当前时间，10位unix时间戳。 例如：1239878956
		String now="";
		requestJson.put("now", now);
		// 版本号。固定为1
		String version="";
		requestJson.put("version", version);
		// 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
		String sign_type="";
		requestJson.put("sign_type", sign_type);
		// 随机字符串，不长于32位
		String nonce_str="";
		requestJson.put("nonce_str", nonce_str);
		// 参数签名。详见微信支付签名算法
		String sign="";
		requestJson.put("sign", sign);
		
		String uri = systemPropertyMap.get("wechat.token.uri");
		String appSecret = systemPropertyMap.get("wechat.appsecret");
		String appId = StringUtil.getString(bodyJson.get("appId"));
		if(StringUtil.isEmpty(appId)){
			appId = systemPropertyMap.get("wechat.appid");
		}
		
		String url = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";
		
		LOGGER.info("访问微信URL：" + url);
		
		String resultXml = "";
		try{
			
			resultXml = RestTemplateUtils.postXmlRequest(restTemplate, url, 
					JsonXmlObjConvertUtils.jsonToXml(requestJson.toString())); 
			String resultJson = JsonXmlObjConvertUtils.xmlToJson(resultXml);
			
			JSONObject tempJson = JSONObject.fromObject(resultJson);
			// 如果接口调用成功就直接返回
			if(tempJson.containsKey("return_code") && SystemConstants.WECHAT_PAY_SUCCESS.equals(
					StringUtil.getString(tempJson.get("return_code")))) {
				return resultJson;
			} else {
				return ResponseUtils.responseErrorStr(StringUtil.getString(tempJson.get("return_code")), 
						StringUtil.getString(tempJson.get("return_msg")));
			}
			
		}catch (Exception e) {
			LOGGER.error("调用微信服务异常", e);
		}
		
		return ResponseUtils.responseErrorStr(ExceptionCode.EC_000035, 
				SystemCacheHolder.getSystemPropertyCache().getMaps().get(ExceptionCode.EC_000035));
	}
	
	
}
