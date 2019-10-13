package com.tyhgg.asr.system.controller;

import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.MessageCodeEntity;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.wechat.service.MessageService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 短信发送
 * @RestController = @Controller + @ResponseBody
 * @author zyt5668
 *
 */
@RestController
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
		
	@Resource
	private MessageService messageService;
	@Resource
	private PeopleMapper peopleMapper;

	/**
	 * 非登录发送短信验证码，调用腾讯云发送短信验证码
	 * @方法名: sentMessageUnlogin
	 * @方法描述: 
	 * @param request
	 * @param header
	 * @param body
	 * @return
	 * @return String
	 */
	@ApiOperation(value="非登录发送短信验证码", notes="根据手机号发送短信，返回带*号的手机号,报文体格式json")
	@RequestMapping(value = "/unlogin/sentMessage", method = {RequestMethod.POST})
	public String sentMessageUnlogin(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {

		JSONObject bodyJson = JSONObject.fromObject(body);

		String peopleTel = StringUtil.getString(bodyJson.get("peopleTel"));
		if("".equals(peopleTel)){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000007, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000007)).toString();
		}
		// 01-登录，02-找回密码，03-修改手机号，04-修改密码，05-注册，06-支付，07-转账
		String busiType = StringUtil.getString(bodyJson.get("busiType"));
		if(!"01".equals(busiType) && !"02".equals(busiType) && !"05".equals(busiType)){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000033, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000033)).toString();
		}
		
		MessageCodeEntity validCodeEntity = new MessageCodeEntity();
		validCodeEntity.setPeopleTel(peopleTel);
		validCodeEntity.setValidType(busiType);
        
		return this.messageService.insertValidCodeAndSendMessage(validCodeEntity).toString();
	}

	/**
	 * 非登录发送短信验证码，调用腾讯云发送短信验证码
	 * @方法名: sentMessageUnlogin
	 * @方法描述: 
	 * @param request
	 * @param header
	 * @param body
	 * @return
	 * @return String
	 */
	@ApiOperation(value="登录后发送短信验证码", notes="根据手机号发送短信，返回带*号的手机号,报文体格式{\"userId\":\"\",\"peopleTel\":\"\"}")
	@RequestMapping(value = "/people/sentMessage", method = {RequestMethod.POST})
	public String sentMessage(HttpServletRequest request, 
			@RequestHeader HttpHeaders header, @RequestBody String body) {

		JSONObject bodyJson = JSONObject.fromObject(body);

		String peopleTel = StringUtil.getString(bodyJson.get("peopleTel"));
		if("".equals(peopleTel)){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000007, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000007)).toString();
		}
		
		String userId = StringUtil.getString(bodyJson.get("userId"));
		if(!"".equals(userId)){
			PeopleEntity peopleEntity = new PeopleEntity();
			peopleEntity.setUserId(userId);
			LOGGER.info("查询用户手机号信息：" + userId);
			peopleEntity = this.peopleMapper.queryPeople(peopleEntity);
			if(null == peopleEntity || !peopleTel.equals(peopleEntity.getPeopleTel())){
				return ResponseUtils.responseErrorJson(ExceptionCode.EC_000034, 
						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000034)).toString();
			}
		}
		
		// 01-登录，02-找回密码，03-修改手机号，04-修改密码，05-注册，06-支付，07-转账
		String busiType = StringUtil.getString(bodyJson.get("busiType"));
		if(!"01".equals(busiType) && !"02".equals(busiType) && !"05".equals(busiType)){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000033, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000033)).toString();
		}
		
		MessageCodeEntity validCodeEntity = new MessageCodeEntity();
		validCodeEntity.setPeopleTel(peopleTel);
		validCodeEntity.setValidType(busiType);
        
		return this.messageService.insertValidCodeAndSendMessage(validCodeEntity).toString();
	}
}
