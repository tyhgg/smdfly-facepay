package com.tyhgg.core.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class WechartUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechartUtils.class);

	/**
	 * 生成微信支付签名
	 * @param requestJson
	 * @return
	 */
	public static JSONObject generateSign(JSONObject requestJson) {
		List<String> list = new ArrayList<String>();
		for(Object str: requestJson.keySet()){
			list.add(StringUtil.getString(str) + "=" + StringUtil.getString(requestJson.get(str)));
		}
		// 集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序）
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		
		for(String str: list) {
			sb.append(str).append("&");
		}
		// stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
		String tempStr = sb.toString().substring(0, sb.toString().length() - 1);
		System.out.println(tempStr);
		// sha256加密
		tempStr = SHA256Utils.encryptSHA256(tempStr).toUpperCase();
		System.out.println(tempStr);
		
		requestJson.put("sign", tempStr);
		
		return requestJson;
	}

	public static void main(String[] args) {
		JSONObject requestJson = new JSONObject();
		requestJson.put("1", "45");
		requestJson.put("as", "6");
		requestJson.put("R", "99");
		requestJson.put("b", "99");
		
		System.out.println(generateSign(requestJson));
	}
}
