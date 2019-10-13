package com.tyhgg.asr.wechat.service;

import net.sf.json.JSONObject;

public interface WechatService {

	String doWechatGet(String tranUrl);
	String doWechatPost(JSONObject bodyJson, String tranUrl);
}
