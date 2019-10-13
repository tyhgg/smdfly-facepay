package com.tyhgg.asr.system.controller;

import io.swagger.annotations.ApiOperation;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.PeopleLoginEntity;
import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.system.mapper.RoleMapper;
import com.tyhgg.asr.system.service.LoginService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.SHA256Utils;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 登录
 * @RestController = @Controller + @ResponseBody
 * @author zyt5668
 *
 */
@RestController
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
		
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private LoginService loginService;

	/**
	 * 提供容器登陆结果
	 * 
	 * @param header
	 *            http报文头
	 * @param body
	 *            http 报文体
	 * @param request
	 *            请求对象
	 * @return
	 */
	@ApiOperation(value="登录", notes="根据用户名密码设置请求头token，返回用户信息,报文体格式{\"username\":\"\",\"password\":\"\"}")
	@RequestMapping(value = "/unlogin/login", method = { RequestMethod.POST})
	public String login(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {

		// 系统错误码
		Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();
		// 系统配置参数
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		JSONObject bodyJson = JSONObject.fromObject(body);
		// 返回参数
		JSONObject resultJson = new JSONObject();

		if(!this.validateLoginParam(header, bodyJson, resultJson)){
			return resultJson.toString();
		}

        // 1-移动终端 2-PC终端
//		String chnflg = header.get(SystemConstants.HEADER_CHANNEL_FLAG).get(0);
		// 1-明文密码方式登录
		String loginType = StringUtil.getString(bodyJson.get("loginType"));
		if("1".equals(loginType) || "".equals(loginType)){// UserId，明文密码登录
			resultJson = this.loginByClearPass(request, bodyJson, msgMap, systemPropertyMap);
		}
		
		return resultJson.toString();
	}
	
	private boolean validateLoginParam(HttpHeaders header, JSONObject bodyJson, JSONObject resultJson){
		String userId = StringUtil.getString(bodyJson.get("userId"));
		// 1-EHR CFCA密码方式，2-人脸识别方式，3-padid直接登录，4-手机号验证码方式，5-明文密码方式登录
//		String loginType = StringUtil.getString(bodyJson.get("loginType"));

        String clientId = header.get(SystemConstants.HEADER_CLIENT_ID).get(0);
        // 1-移动终端 2-PC终端
//		String chnflg = header.get(SystemConstants.HEADER_CHANNEL_FLAG).get(0);
		
		if("".equals(clientId) || "".equals(userId)){
			
			resultJson.put(ResponseUtils.ERRCODE, ExceptionCode.EC_000007);
			resultJson.put(ResponseUtils.ERRMSG, SystemCacheHolder.getMsgCache().getMaps());
			
			return false;
		}
		
		return true;
	}

	private JSONObject loginByClearPass(HttpServletRequest request, JSONObject bodyJson, 
			Map<String, String> msgMap, Map<String, String> systemPropertyMap){
		
		String userId = StringUtil.getString(bodyJson.get("userId"));
		String password = StringUtil.getString(bodyJson.get("password"));
		
		// 查询人员信息
		PeopleEntity peopleEntity = new PeopleEntity();
		
		peopleEntity.setUserId(userId);
		LOGGER.info("查询用户信息：" + peopleEntity);
		peopleEntity = peopleMapper.queryPeopleAndPass(peopleEntity);
		if(null == peopleEntity){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000003, msgMap.get(ExceptionCode.EC_000003));
		}
		LOGGER.info("查询用户信息结果：" + peopleEntity);
		
		// SHA1加密密文
		password = SHA256Utils.encryptSHA256(password);
		
		if(!password.equals(peopleEntity.getPeoplePass())){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000003, msgMap.get(ExceptionCode.EC_000003));
		}
		
		return ResponseUtils.responseSuccessJson(this.loginService.handleLoginInfo(request, bodyJson, 
				peopleEntity, msgMap, systemPropertyMap));
		
	}
	
	/**
	 * 提供容器退出功能
	 * 
	 * @param header
	 *            http报文头
	 * @return
	 */
	@RequestMapping(value = "/people/loginout", method = {RequestMethod.POST})
	public String loginout(HttpServletRequest request, @RequestHeader HttpHeaders header, 
			@RequestBody String body) {
		JSONObject jsonBody = JSONObject.fromObject(body);
		Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();// 系统错误码
		
		String headerUserId = StringUtil.getString(jsonBody.get("userId"));
		String headerClientId = header.get(SystemConstants.HEADER_CLIENT_ID).get(0);
		
		PeopleLoginEntity peopleLoginEntity = new PeopleLoginEntity();
		peopleLoginEntity.setUserId(headerUserId);
		peopleLoginEntity.setClientId(headerClientId);
		LOGGER.info("查询用户登录信息：" + peopleLoginEntity);
		peopleLoginEntity = peopleLoginMapper.queryPeopleLoginEntity(peopleLoginEntity);
		
		if(null == peopleLoginEntity){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000011, 
					msgMap.get(ExceptionCode.EC_000011)).toString();
		}
		
		peopleLoginEntity.setLoginStatus(SystemConstants.PEOPLE_LOGINSTATUS_LOGOUT);
		LOGGER.info("更新用户登录信息：" + peopleLoginEntity);
		this.peopleLoginMapper.updatePeopleLoginEntity(peopleLoginEntity);
		
		return (ResponseUtils.responseSuccessJson(null)).toString();
	}

	/**
	 * 获取密码控件需要的随机数
	 * 
	 * @param header
	 *            http报文头
	 * @return
	 */
	@RequestMapping(value = "/unlogin/getrdnum", method = {RequestMethod.GET, RequestMethod.POST})
	public String getRdnum(HttpServletRequest request, @RequestHeader HttpHeaders header) {

		JSONObject resultJson = new JSONObject();
		String rdom = StringUtil.generateSequence(16);
		resultJson.put("random", rdom);
		rdom = new String(Base64.encodeBase64(rdom.getBytes()));
		resultJson.put("randomid", rdom);
		
		return ResponseUtils.responseSuccessJson(resultJson).toString();
	}

}
