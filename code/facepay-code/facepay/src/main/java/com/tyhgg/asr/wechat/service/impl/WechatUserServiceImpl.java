package com.tyhgg.asr.wechat.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyhgg.asr.system.entity.LoginCodeEntity;
import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.PeopleLoginEntity;
import com.tyhgg.asr.system.mapper.LoginCodeMapper;
import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.system.service.LoginService;
import com.tyhgg.asr.wechat.service.WechatUserService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.SHA256Utils;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 
 * @类名称: WechatUserServiceImpl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年11月20日 下午4:18:24
 * @修改备注：
 */
@Service(value = "wechatUserService")
public class WechatUserServiceImpl extends WechatServiceImpl implements WechatUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatUserServiceImpl.class);
	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private LoginService loginService;
	@Resource
	private LoginCodeMapper loginCodeMapper;

	/**
	 * 微信扫一扫回调登录
	 */
	@Transactional
    public String scanWebsiteLoginCallback(String code, String loginUuid){

		LoginCodeEntity loginCodeEntity = new LoginCodeEntity();
		loginCodeEntity.setValidCode(loginUuid);
		// 查询登录code码
		loginCodeEntity = loginCodeMapper.queryLoginCodeEntity(loginCodeEntity);
		if (null == loginCodeEntity) {
			return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000042));
		} else {
			// code码只能验证一次
			if (loginCodeEntity.getValidNum() > 0) {
				return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, 
						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000042));
			}

			// 是否过期
			String validTime = loginCodeEntity.getValidTime();
			if (validTime.compareTo(DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss")) < 0) {
				return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, 
						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000042));
			}

		}
		// 根据code获取accessToken
		// String url = "https://open.weixin.qq.com/sns/oauth2/access_token?appid=wl67f9b477b9&secret=6LrmpycrOFweNR&code=CODE&grant_type=authorizaion_code";
		String appId = SystemCacheHolder.getSystemPropertyCache().getMaps().get("wechat.appid");
		String secret = SystemCacheHolder.getSystemPropertyCache().getMaps().get("wechat.appkey");
		String wechatUrl = SystemCacheHolder.getSystemPropertyCache().getMaps().get("wechat.url");
		// String url = "https://open.weixin.qq.com/sns/oauth2/access_token?appid=wl67f9b477b9&secret=6LrmpycrOFweNR&code=CODE&grant_type=authorizaion_code";
		
	    String tranUrl = wechatUrl + "/oauth2/access_token?appid=" + appId + "&secret=" + secret
	    		 + "&code=" + code + "&grant_type=authorizaion_code";
		
	    // 获取微信token
	    String result = super.doWechatGet(tranUrl);
		
	    JSONObject jsonResult = JSONObject.fromObject(result);
    	// 取WechatToken缓存值
	    int expiresIn = 0;
	    
	    if(jsonResult.containsKey("errcode") && !"0".equals(StringUtil.getString(jsonResult.get("errcode")))){
	    	return ResponseUtils.responseErrorStr(ExceptionCode.EC_000035, 
	    			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000035));
	    }
	    
    	// 过期时间
		try{
			expiresIn = Integer.parseInt(StringUtil.getString(jsonResult.get("expires_in")));
		} catch (Exception e) {
			LOGGER.error("微信token过期时间int转化异常：", e);
		}
		
		String openId = StringUtil.getString(jsonResult.get("openid"));
		PeopleLoginEntity peopleLoginEntity = new PeopleLoginEntity();
		peopleLoginEntity.setOpenId(openId);
		peopleLoginEntity.setClientId("100");// 页面微信扫一扫
		// 本系统的唯一用户标识
		String userId = openId;
		
		// 用户微信登录信息
		peopleLoginEntity = this.peopleLoginMapper.queryPeopleLoginEntity(peopleLoginEntity);
		// wechat_user_token表信息
    	if(null == peopleLoginEntity){
    		peopleLoginEntity = new PeopleLoginEntity();
    		peopleLoginEntity.setAccessToken(StringUtil.getString(jsonResult.get("access_token")));
    		peopleLoginEntity.setRefreshToken(StringUtil.getString(jsonResult.get("refresh_token")));
    		peopleLoginEntity.setUserId(openId);
    		peopleLoginEntity.setOpenId(openId);
    		String unionId = StringUtil.getString(jsonResult.get("unionid"));
    		peopleLoginEntity.setUnionId(unionId);
    		peopleLoginEntity.setExpiresIn(expiresIn);
			LOGGER.info("-----------------------新增peopleLoginEntity信息：" + peopleLoginEntity);
			this.peopleLoginMapper.save(peopleLoginEntity);
			
			peopleLoginEntity = new PeopleLoginEntity();
			peopleLoginEntity.setUnionId(unionId);
			List<PeopleLoginEntity> peopleLoginList = this.peopleLoginMapper.queryPeopleLoginList(peopleLoginEntity);
			// 如果unionId不存在，需要新增用户
			if(null != peopleLoginList && peopleLoginList.size() > 0){ //
				PeopleLoginEntity tempPeopleLogin = peopleLoginList.get(0);
				userId = tempPeopleLogin.getUserId();
				
			} else {// 如果unionId不存在，需要新增用户
				if(!this.addPeopleInfo(peopleLoginEntity)){
					return ResponseUtils.responseErrorStr(ExceptionCode.EC_000035, 
			    			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000035));
				}
			}
			
    	} else {
    		userId = peopleLoginEntity.getUserId();
    		peopleLoginEntity.setAccessToken(StringUtil.getString(jsonResult.get("access_token")));
    		peopleLoginEntity.setRefreshToken(StringUtil.getString(jsonResult.get("refresh_token")));
    		peopleLoginEntity.setExpiresIn(expiresIn);
			LOGGER.info("-----------------------更新peopleLoginEntity信息：" + peopleLoginEntity);
			this.peopleLoginMapper.updatePeopleLoginEntity(peopleLoginEntity);
    	}
		
		loginCodeEntity.setUserId(userId);
		
		LOGGER.info("更新H5 微信扫一扫登录回调信息：" + loginCodeEntity);
		loginCodeMapper.updateLoginCodeEntity(loginCodeEntity);
		
		return ResponseUtils.responseWebSuccessJson().toString();
    }

	private boolean addPeopleInfo(PeopleLoginEntity peopleLoginEntity){
		String openId = peopleLoginEntity.getOpenId();
		String acceccToken = peopleLoginEntity.getAccessToken();

		String wechatUrl = SystemCacheHolder.getSystemPropertyCache().getMaps().get("wechat.url");
		// String url = "https://api.weixin.qq.com/sns/userinfo?accecc_token=wl67f9b477b9&openidt=OPENID";
		
	    String tranUrl = wechatUrl + "/userinfo?accecc_token=" + acceccToken + "&openid=" + openId;
	    
	    // 获取微信token
	    String result = super.doWechatGet(tranUrl);
		
	    JSONObject jsonResult = JSONObject.fromObject(result);
    	// 取WechatToken缓存值
	    if(jsonResult.containsKey("errcode") && !"0".equals(StringUtil.getString(jsonResult.get("errcode")))){
	    	return false;
	    }
	    
	    PeopleEntity peopleEntity = new PeopleEntity();
	    peopleEntity.setUserId(openId);
	    peopleEntity.setPeopleName(StringUtil.getString(jsonResult.get("nickname")));
	    peopleEntity.setPeopleSex(StringUtil.getString(jsonResult.get("sex")));
	    peopleEntity.setProvince(StringUtil.getString(jsonResult.get("province")));
	    peopleEntity.setCity(StringUtil.getString(jsonResult.get("city")));
	    peopleEntity.setCountry(StringUtil.getString(jsonResult.get("country")));
	    peopleEntity.setHeadimgUrl(StringUtil.getString(jsonResult.get("headimgurl")));
	    peopleEntity.setPeoplePass(SHA256Utils.encryptSHA256(SystemConstants.PEOPLE_PASS_DEFAULT));
	    peopleEntity.setOrgId(SystemConstants.ORG_ID_DEFAULT);
	    
	    this.peopleMapper.save(peopleEntity);
	    
	    return true;
	}
   
}
