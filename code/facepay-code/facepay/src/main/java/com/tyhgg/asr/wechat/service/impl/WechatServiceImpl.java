package com.tyhgg.asr.wechat.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tyhgg.asr.wechat.entity.WechatTokenEntity;
import com.tyhgg.asr.wechat.mapper.WechatTokenMapper;
import com.tyhgg.asr.wechat.service.WechatService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.util.HttpClientUtil;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 
 * @类名称: WechatServiceImpl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年11月20日 下午4:18:24
 * @修改备注：
 */
@Service(value = "wechatService")
public class WechatServiceImpl implements WechatService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatServiceImpl.class);
	@Resource
	private WechatTokenMapper wechatTokenMapper;
	// 微信成功标识
	private static String WECHAT_SUCCESS = "0";

	/**
	 * 调用微信get接口
	 */
    public String doWechatGet(String tranUrl){

		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		// 微信地址
	    String wechatUrl = systemPropertyMap.get("wechat.url");

    	String appId = systemPropertyMap.get("wechat.appid");
    	
    	WechatTokenEntity wechatToken = this.wechatTokenMapper.queryWechatToken(appId);
    	String accessToken = "";
    	if(null == wechatToken){
    		JSONObject tokenJson = this.getHangXinToken(systemPropertyMap);
    		if(WECHAT_SUCCESS.equals(StringUtil.getString(tokenJson.get("errcode")))){
    			accessToken = StringUtil.getString(tokenJson.get("access_token"));
    			// 更新hangxin_token表信息
    			WechatTokenEntity hangXinTokenEntity = new WechatTokenEntity();
    			hangXinTokenEntity.setAccessToken(StringUtil.getString(tokenJson.get("access_token")));
    			hangXinTokenEntity.setCorpId(appId);
    			String expiresIn = StringUtil.getString(tokenJson.get("expires_in"));
    			try{
    				hangXinTokenEntity.setExpiresIn(Integer.parseInt(expiresIn));
    			} catch (Exception e) {
    				LOGGER.warn("", e);
    			}
    			LOGGER.info("-----------------------新增hangxin_token信息：" + hangXinTokenEntity);
    			wechatTokenMapper.save(hangXinTokenEntity);
    		}
    	} else {
    		accessToken = wechatToken.getAccessToken();
    	}
    	
    	String accessUrl = "";
    	if(tranUrl.indexOf("?") > 0){
    		accessUrl = wechatUrl + tranUrl + "&access_token=" + accessToken;
    	} else {
    		accessUrl = wechatUrl + tranUrl + "?access_token=" + accessToken;
    	}
    	
    	LOGGER.info("-----------------------调用微信get接口：" + accessUrl);
	    String result = HttpClientUtil.httpGet(accessUrl);
    	LOGGER.info("-----------------------调用微信get接口返回：" + result);
	    
    	JSONObject jsonResult = JSONObject.fromObject(result);
    	// 40014不合法的access_token，需要重新获取access_token,42001 access_token expired
    	if("40014".equals(StringUtil.getString(jsonResult.get("errcode")))
    			|| "42001".equals(StringUtil.getString(jsonResult.get("errcode")))){
	    	LOGGER.info("-----------------------token过期，再次调用微信gettoken接口");
    		JSONObject tokenJson = this.getHangXinToken(systemPropertyMap);
	    	LOGGER.info("-----------------------token过期，再次调用微信gettoken接口返回");
    		if("0".equals(StringUtil.getString(tokenJson.get("errcode")))){
    			// 更新hangxin_token表信息
    			wechatToken.setAccessToken(StringUtil.getString(tokenJson.get("access_token")));
    			String expiresIn = StringUtil.getString(tokenJson.get("expires_in"));
    			try{
    				wechatToken.setExpiresIn(Integer.parseInt(expiresIn));
    			} catch (Exception e) {
    				LOGGER.warn("", e);
    			}
    			
    			LOGGER.info("-----------------------修改wechatToken信息：" + wechatToken);
    			this.wechatTokenMapper.updateWechatToken(wechatToken);
    			
    			accessToken = StringUtil.getString(tokenJson.get("access_token"));
//    			accessUrl = hangXinUrl + tranUrl + "&access_token=" + accessToken;
    			if(tranUrl.indexOf("?") > 0){
    	    		accessUrl = wechatUrl + tranUrl + "&access_token=" + accessToken;
    	    	} else {
    	    		accessUrl = wechatUrl + tranUrl + "?access_token=" + accessToken;
    	    	}
    			
    			LOGGER.info("-----------------------token过期，再次调用微信get接口" + accessUrl);
    		    result = HttpClientUtil.httpGet(accessUrl);
    			LOGGER.info("-----------------------token过期，再次调用微信get接口返回：" + result);
    			
    			jsonResult = JSONObject.fromObject(result);
    			
    		} else {
    			jsonResult = tokenJson;
    		}
    		
    	} 
    	
    	if(!WECHAT_SUCCESS.equals(StringUtil.getString(jsonResult.get("errcode")))){
    		jsonResult.put("msgcde", StringUtil.getString(jsonResult.get("errcode")));
    		jsonResult.put("rtnmsg", StringUtil.getString(jsonResult.get("errmsg")));
    	}
    	
    	return jsonResult.toString();
    }

    /**
	 * 调用微信post接口
	 */
    public String doWechatPost(
    		JSONObject bodyJson, String tranUrl){

    	Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
	    String hangXinUrl = systemPropertyMap.get("hangxin.url") + tranUrl;

    	String corpId = systemPropertyMap.get("hangxin.corpid");
    	
    	WechatTokenEntity hangXinToken = this.wechatTokenMapper.queryWechatToken(corpId);
    	String accessToken = "";
    	if(null == hangXinToken){
    		JSONObject tokenJson = this.getHangXinToken(systemPropertyMap);
    		if(WECHAT_SUCCESS.equals(StringUtil.getString(tokenJson.get("errcode")))){
    			accessToken = StringUtil.getString(tokenJson.get("access_token"));
    			// 更新hangxin_token表信息
    			WechatTokenEntity hangXinTokenEntity = new WechatTokenEntity();
    			hangXinTokenEntity.setAccessToken(StringUtil.getString(tokenJson.get("access_token")));
    			hangXinTokenEntity.setCorpId(corpId);
    			String expiresIn = StringUtil.getString(tokenJson.get("expires_in"));
    			try{
    				hangXinTokenEntity.setExpiresIn(Integer.parseInt(expiresIn));
    			} catch (Exception e) {
    				LOGGER.warn("", e);
    			}
    			LOGGER.info("-----------------------新增hangxin_token信息：" + hangXinTokenEntity);
    			wechatTokenMapper.save(hangXinTokenEntity);
    		}
    		
    	} else {
    		accessToken = hangXinToken.getAccessToken();
    	}
    	String accessUrl = hangXinUrl  + "?access_token=" + accessToken;
    	
    	LOGGER.info("-----------------------调用微信post接口：" + accessUrl);
	    String result = HttpClientUtil.httpPost(accessUrl, bodyJson.toString());
    	LOGGER.info("-----------------------调用微信post接口返回：" + result);
    	
    	if(result==null){
    		LOGGER.info("-----------------------微信服务器连接异常！");
    		return null;
    	}
    	
    	
    	JSONObject jsonResult = JSONObject.fromObject(result);
    	// 40014不合法的access_token，需要重新获取access_token,42001 access_token expired
    	if("40014".equals(StringUtil.getString(jsonResult.get("errcode")))
    			|| "42001".equals(StringUtil.getString(jsonResult.get("errcode")))){
	    	LOGGER.info("-----------------------token过期，再次调用微信gettoken接口");
    		JSONObject tokenJson = this.getHangXinToken(systemPropertyMap);
	    	LOGGER.info("-----------------------token过期，再次调用微信gettoken接口返回");
    		if("0".equals(StringUtil.getString(tokenJson.get("errcode")))){
    			// 更新hangxin_token表信息
    			hangXinToken.setAccessToken(StringUtil.getString(tokenJson.get("access_token")));
    			String expiresIn = StringUtil.getString(tokenJson.get("expires_in"));
    			try{
    				hangXinToken.setExpiresIn(Integer.parseInt(expiresIn));
    			} catch (Exception e) {
    				LOGGER.warn("", e);
    			}
    			
    			LOGGER.info("-----------------------修改hangxin_token信息：" + hangXinToken);
    			this.wechatTokenMapper.updateWechatToken(hangXinToken);
    			
    			accessToken = StringUtil.getString(tokenJson.get("access_token"));
    			accessUrl = hangXinUrl  + "?access_token=" + accessToken;
    			LOGGER.info("-----------------------token过期，再次调用微信get接口" + accessUrl);
    		    result = HttpClientUtil.httpPost(accessUrl, bodyJson.toString());
    			LOGGER.info("-----------------------token过期，再次调用微信get接口返回：" + result);
    			
    			jsonResult = JSONObject.fromObject(result);
    			
    		} else {
    			jsonResult = tokenJson;
    		}
    		
    	} 
    	
    	if(!WECHAT_SUCCESS.equals(StringUtil.getString(jsonResult.get("errcode")))){
    		jsonResult.put("msgcde", StringUtil.getString(jsonResult.get("errcode")));
    		jsonResult.put("rtnmsg", StringUtil.getString(jsonResult.get("errmsg")));
    	}
    	
    	return jsonResult.toString();
    }
    
    /**
	 * 获取微信access_token
	 */
    private JSONObject getHangXinToken(Map<String, String> systemPropertyMap){
    	String corpId = systemPropertyMap.get("wechat.appid");
		String corpSecret = systemPropertyMap.get("wechat.appkey");
		String hangXinUrl = systemPropertyMap.get("wechat.url");
	    String tranUrl = hangXinUrl + "/gettoken?corpid=" + corpId + "&corpsecret=" + corpSecret;
	    LOGGER.info("token过期，再次调用微信gettoken接口：" + tranUrl);
	    String result = HttpClientUtil.httpGet(tranUrl);
	    LOGGER.info("token过期，再次调用微信gettoken接口返回：" + result);
	    return JSONObject.fromObject(result);
    }
    
}
