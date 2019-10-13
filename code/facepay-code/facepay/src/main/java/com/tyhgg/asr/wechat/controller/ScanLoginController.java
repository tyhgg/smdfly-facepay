package com.tyhgg.asr.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.system.entity.LoginCodeEntity;
import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.mapper.LoginCodeMapper;
import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.system.service.LoginService;
import com.tyhgg.asr.wechat.service.WechatService;
import com.tyhgg.asr.wechat.service.WechatUserService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 扫一扫登录，使用于PC端
 * @author zyt5668
 *
 */
@Controller
public class ScanLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScanLoginController.class);

	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private LoginService loginService;
	@Resource
	private LoginCodeMapper loginCodeMapper;
	@Resource
	private WechatService wechatService;
	@Resource
	private WechatUserService wechatUserService;

	/**
	 * 微信扫一扫二维码url接口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unlogin/website/getOauthRedirectUrl", method = { RequestMethod.GET })
	@ResponseBody
	public String getOauthRedirectUrl(HttpServletRequest request) {

		LoginCodeEntity loginCodeEntity = new LoginCodeEntity();
		// 扫一扫登录code码
		String uuid = UUID.randomUUID().toString();
		String scanType = StringUtil.getString(StringUtil.getXssParameterValue(request, "scanType"));
		if("".equals(scanType)){
			scanType = "1";
		}
		
		uuid = uuid.replaceAll("-", "");
		loginCodeEntity.setValidCode(uuid);
		loginCodeEntity.setValidNum(0);
		
		// 登录码失效时间
		int loginCodeValidateTime = Integer.parseInt(SystemCacheHolder.getSystemPropertyCache().getMaps()
				.get("logincode.validate.time"));
		Calendar calendar = Calendar.getInstance();
		Date loginTime = new Date();
		calendar.setTime(loginTime);
		calendar.add(Calendar.SECOND, loginCodeValidateTime);
		String validTime = DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");;
		loginCodeEntity.setValidTime(validTime);
		
		// https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
		String hangxinOauth2Url = SystemCacheHolder.getSystemPropertyCache().getMaps()
				.get("wechat.oauth2.url");
		
		String scanLoginUrl = SystemCacheHolder.getSystemPropertyCache().getMaps()
				.get("wechat.scan.login.url");

		String redirectUrl = scanLoginUrl + "?loginUuid=" + uuid + "&scanType=" + scanType;
		LOGGER.info("扫一扫登录redirectUrl：" + redirectUrl);
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn("redirectUrl URLEncoder异常：", e);
		}
		
		String state = StringUtil.getVerifyCode();
		
		String appid = SystemCacheHolder.getSystemPropertyCache().getMaps()
				.get("wechat.appid");
//		String agentid = systemPropertyMap.get("wechat.agentid");
		String oauth2Url = hangxinOauth2Url + "?appid=" + appid + "&redirect_uri=" + redirectUrl 
				+ "&response_type=code&scope=snsapi_login&state="+state+"#wechat_redirect";
		LOGGER.info("扫一扫登录oauth2Url：" + oauth2Url);

		LOGGER.info("新增扫一扫登录loginCodeEntity信息：" + loginCodeEntity);
		loginCodeMapper.save(loginCodeEntity);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("oauth2Url", redirectUrl);
		resultJson.put("loginUuid", uuid);
		
		return resultJson.toString();
	}

	/**
	 * 微信扫一扫轮询登录接口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unlogin/website/scanLogin", method = { RequestMethod.POST })
	@ResponseBody
	public String scanWebsiteLogin(HttpServletRequest request, @RequestHeader HttpHeaders header, 
			@RequestBody String body) {

		LOGGER.info("微信扫一扫定时登录开始");
		JSONObject resultJson = new JSONObject();
		JSONObject bodyJson = JSONObject.fromObject(body);

		// 系统错误码
		Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();
		// 系统配置参数
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		LOGGER.info("微信扫一扫定时登录参数:" + body);
		String loginUuid = StringUtil.getString(bodyJson.get("loginUuid"));
		// 登录方式
		String loginType = StringUtil.getString(bodyJson.get("loginType"));		
		String clientId = header.get(SystemConstants.HEADER_CLIENT_ID).get(0);

		if ("".equals(loginType) || "".equals(clientId) || "".equals(loginUuid)) {
			return ResponseUtils.responseErrorStr(ExceptionCode.EC_000007, msgMap.get(ExceptionCode.EC_000007));
		}
		LOGGER.info("微信扫一扫定时登录loginUuid=" + loginUuid);
		LoginCodeEntity loginCodeEntity = new LoginCodeEntity();
		loginCodeEntity.setValidCode(loginUuid);
		// 查询登录code码
		loginCodeEntity = loginCodeMapper.queryLoginCodeEntity(loginCodeEntity);
		if (null == loginCodeEntity) {
			return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, msgMap.get(ExceptionCode.EC_000042));
		} else {
			LOGGER.info("H5 微信扫一扫定时登录信息：" + loginCodeEntity);
			// code码只能验证一次
			if (loginCodeEntity.getValidNum() > 0) {
				return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, msgMap.get(ExceptionCode.EC_000042));
			}
			loginCodeEntity.setValidNum(1);

			// 是否过期
			String validTime = loginCodeEntity.getValidTime();
			if (validTime.compareTo(DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss")) < 0) {
				return ResponseUtils.responseErrorStr(ExceptionCode.EC_000042, msgMap.get(ExceptionCode.EC_000042));
			}

			
			if (StringUtil.isEmpty(loginCodeEntity.getUserId())) {// 还未扫一扫登录就直接返回
				resultJson.put("result", "1");
				return ResponseUtils.responseSuccessJson(resultJson).toString();
			} else {
				LOGGER.info("更新H5 微信扫一扫定时登录信息：" + loginCodeEntity);
				loginCodeMapper.updateLoginCodeEntity(loginCodeEntity);
			}
		}
		// 查询人员信息
		PeopleEntity peopleEntity = new PeopleEntity();

		peopleEntity.setUserId(loginCodeEntity.getUserId());
		LOGGER.info("查询用户信息：" + peopleEntity);
		peopleEntity = peopleMapper.queryPeopleAndPass(peopleEntity);
		if (null == peopleEntity) {
			return ResponseUtils.responseErrorStr(ExceptionCode.EC_000018, msgMap.get(ExceptionCode.EC_000018));
		}
		LOGGER.info("查询用户信息结果：" + peopleEntity);
		
		if ("100".equals(clientId)) {// pad扫一扫登录
			return this.loginService.handleLoginInfo(request, bodyJson, peopleEntity, msgMap, systemPropertyMap).toString();
		} else {
//			return this.managerLoginService.handleLoginInfo(request, bodyJson, header, peopleEntity).toString();
		}
		
		return ResponseUtils.responseSuccessJson(resultJson).toString();

	}

	/**
	 * 微信扫一扫回调接口
	 * @方法名: scanLoginCallbacke
	 * @方法描述: 
	 * @param request
	 * @param header
	 * @param body
	 * @return
	 * @return String
	 */
	@RequestMapping(value = "/unlogin/website/scanLoginCallback", method = { RequestMethod.GET })
	@ResponseBody
	public String scanWebsiteLoginCallback(HttpServletRequest request) {

		LOGGER.info("微信扫一扫登录回调开始");
		String code = StringUtil.getString(StringUtil.getXssParameterValue(request, "code"));
		
		String loginUuid = StringUtil.getString(StringUtil.getXssParameterValue(request, "loginUuid"));
		
		LOGGER.info("code=" + code + ",loginUuid=" + loginUuid);
		
		if ("".equals(code) || "".equals(loginUuid)) {// 
			return ResponseUtils.responseErrorStr(ExceptionCode.EC_000007, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000007));
		}
		
		return this.wechatUserService.scanWebsiteLoginCallback(code, loginUuid);
		
	}
	
}
