package com.tyhgg.asr.wechat.service.impl;

import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tyhgg.asr.system.entity.PeoplePageEntity;
import com.tyhgg.asr.system.entity.MessageCodeEntity;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.system.mapper.MessageCodeMapper;
import com.tyhgg.asr.wechat.service.MessageService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.HttpClientUtil;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;

@Service
public class MessageServiceImpl implements MessageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Resource
	private MessageCodeMapper validateCodeMapper;
	
	@Resource
	private PeopleMapper peopleMapper;

	@Override
	public JSONObject insertValidCodeAndSendMessage(MessageCodeEntity validCodeEntity) {
		// 短信有效时间或间隔发送时间
		int validMinutes = Integer.parseInt(SystemCacheHolder.
				getSystemPropertyCache().getMaps().get("message.valid.minutes"));
		// 有效时间或间隔发送次数
		int maxAllowNum = Integer.parseInt(SystemCacheHolder.
				getSystemPropertyCache().getMaps().get("message.max.allow.num"));
		
		PeoplePageEntity peopleEntity = new PeoplePageEntity();
		peopleEntity.setPeopleTel(validCodeEntity.getPeopleTel());
		peopleEntity.setPeopleStatus("1");
		int count = this.peopleMapper.queryPeoplePageCount(peopleEntity);
		// 01-登录，02-找回密码，03-修改手机号，04-修改密码，05-注册，06-支付，07-转账
		if("03".equals(validCodeEntity.getValidType()) && count > 0){// 修改的手机号系统不能存在
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000030, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000030));
		} else if(count < 1) {
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000026, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000026));
		}
		
		validCodeEntity.setMinutes(validMinutes);
		
		// 查询最近时间段内发送短信次数
		int recentValidCodeCount = this.validateCodeMapper.queryRecentMessageCodeCount(validCodeEntity.getPeopleTel());
		// 5分钟内最多只能发送5次短信,如果超限，直接报错
		if(recentValidCodeCount >= maxAllowNum){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000028, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000028));
		}
		
		// 删除已失效的短信验证码
		this.validateCodeMapper.deleteMessageCode(validCodeEntity.getPeopleTel());

		// 生成6位短信验证码
		String validCode = StringUtil.getVerifyCode();
		validCodeEntity.setValidCode(validCode);
		
		// 发送短信验证码
		if(!this.sendMessageCodeToQCloud(validCodeEntity.getPeopleTel(), validCode)){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000032, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000032));
		}
		
		// 新增短信验证码
		this.validateCodeMapper.insertMessageCode(validCodeEntity);
		
		return ResponseUtils.responseSuccessJson(null);
		
	}

	@Override
	public String checkValidCode(MessageCodeEntity validCodeEntity) {
		
		//查询是否存在有效的验证码信息
		validCodeEntity = this.validateCodeMapper.queryMessageCode(validCodeEntity);
		if(validCodeEntity==null){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000027, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000027)).toString();
		}
		if(validCodeEntity.getValidNum() > 0){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000031, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000031)).toString();
		}

		if(validCodeEntity.getValidDate().compareTo(DateUtil.getNowDateStr()) < 0){
			return ResponseUtils.responseErrorJson(ExceptionCode.EC_000029, 
					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000029)).toString();
		}
		
		// 更新短信验证码验证
		this.validateCodeMapper.updateMessageNum(validCodeEntity);

		return ResponseUtils.responseSuccessJson(null).toString();
	}

	/**
	 * 调用腾讯云发送短信验证码
	 * @param mobileNo
	 * @param verifyCode
	 * @return
	 */
	private boolean sendMessageCodeToQCloud(String mobileNo, String verifyCode){
		
		try{
			Map<String, String> propertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
			// 短信验证码失效时间分钟
			String validTime = SystemCacheHolder.getSystemPropertyCache().getMaps().get("message.max.allow.send.minutes");
			
			JSONObject jsonObject = new JSONObject();
			String[] params = new String[2];
			params[0] = verifyCode;
			params[1] = validTime;
			
			jsonObject.put("params", params);
			
			String strAppKey = propertyMap.get("tencent.yun.sms.appkey");
			long strTime = System.currentTimeMillis()/1000;
			long strRand = new Random(strTime).nextInt(900000) + 100000;
			StringBuilder sb = new StringBuilder("appkey=")
				.append(strAppKey)
				.append("&random=")
				.append(strRand)
				.append("&time=")
				.append(strTime)
				.append("&mobile=")
				.append(mobileNo);
			
			String sig = DigestUtils.sha256Hex(sb.toString());
						
			jsonObject.put("sig", sig);
			jsonObject.put("sign", propertyMap.get("tencent.yun.sms.sign"));
			
			JSONObject telJson = new JSONObject();
			telJson.put("mobile", mobileNo);
			telJson.put("nationcode", "86");
			jsonObject.put("tel", telJson);
			
			jsonObject.put("time", strTime);
			jsonObject.put("tpl_id", propertyMap.get("tencent.yun.sms.templateid"));
			
			StringBuffer urlSb = new StringBuffer(propertyMap.get("tencent.yun.sms.uri"))
				.append("?sdkappid=").append(propertyMap.get("tencent.yun.sms.sdkappid"))
				.append("&random=").append(strRand);
			
			LOGGER.info("请求weChat报文：" + jsonObject.toString());
			String weChatRslt = HttpClientUtil.httpPost(urlSb.toString(), jsonObject.toString());
			LOGGER.info("weChat返回报文：" + weChatRslt);
			
			JSONObject weChat = JSONObject.fromObject(weChatRslt);
			if(!"0".equals(weChat.get("result").toString())){
				return false;
			}
		}catch(Exception e){
			LOGGER.error("调用腾讯云发送短信验证码异常！", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 优化中英文短信内容
	 * 给Mcis系统发送手机短信验证码
	 * @param header
	 * @param mobileNo
	 * @param verifyCode
	 * @return
	 */
	public boolean sendVerifyCodeToQCloud(Map<String,String> smsParams){
		try{
			String mobileNo = smsParams.get("mobileNo");
			String verifyCode = smsParams.get("verifyCode");
//			String busiType = smsParams.get("busiType");
			Map<String, String> propertyMap =SystemCacheHolder.getSystemPropertyCache().getMaps();
			String validTime = propertyMap.get("sms.max.valid.time");
			/*if(SMS_BUSI_TYPE_LOGIN.equals(busiType)){
				if("zh".equalsIgnoreCase(languageType)){
					sendMessage = propertyMap.get("sms.send.login.validcode.context");
				}else{
					sendMessage = propertyMap.get("sms.send.login.validcode.context.en");
				}
			}else if(SMS_BUSI_TYPE_FINDPASS.equals(busiType)){
				if("zh".equalsIgnoreCase(languageType)){
					sendMessage = propertyMap.get("sms.send.find.pass.context");
				}else{
					sendMessage = propertyMap.get("sms.send.find.pass.context.en");
				}
			}else if(SMS_BUSI_TYPE_UPDATEPEOPLETEL.equals(busiType)){
				if("zh".equalsIgnoreCase(languageType)){
					sendMessage = propertyMap.get("sms.send.update.tel.validcode.context");
				}else{
					sendMessage = propertyMap.get("sms.send.update.tel.validcode.context.en");
				}	
			}else if(SMS_BUSI_TYPE_UPDATEPASS.equals(busiType)){
				if("zh".equalsIgnoreCase(languageType)){
					sendMessage = propertyMap.get("sms.send.update.pass.validcode.context");
				}else{
					sendMessage = propertyMap.get("sms.send.update.pass.validcode.context.en");
				}
			}else{
				LOGGER.debug("发送短信验证码业务类型是空的busiType="+busiType);
				sendMessage = propertyMap.get("sms.send.login.validcode.context");
			}
			if(StringUtil.isNotEmpty(sendMessage)){
				sendMessage = sendMessage.replaceAll("X", verifyCode);
			}*/
			JSONObject jsonObject = new JSONObject();
			String[] params = new String[2];
			params[0] = verifyCode;
			params[1] = validTime;
			
			jsonObject.put("params", params);
			
			String strAppKey = propertyMap.get("tencent.yun.sms.appkey");
			long strTime = System.currentTimeMillis()/1000;
			long strRand = new Random(strTime).nextInt(900000) + 100000;
			StringBuilder sb = new StringBuilder("appkey=")
				.append(strAppKey)
				.append("&random=")
				.append(strRand)
				.append("&time=")
				.append(strTime)
				.append("&mobile=")
				.append(mobileNo);
			
			String sig = DigestUtils.sha256Hex(sb.toString());
						
			jsonObject.put("sig", sig);
			jsonObject.put("sign", propertyMap.get("tencent.yun.sms.sign"));
			
			JSONObject telJson = new JSONObject();
			telJson.put("mobile", mobileNo);
			telJson.put("nationcode", "86");
			jsonObject.put("tel", telJson);
			
			jsonObject.put("time", strTime);
			jsonObject.put("tpl_id", propertyMap.get("tencent.yun.sms.templateid"));
			
			StringBuffer urlSb = new StringBuffer(propertyMap.get("tencent.yun.sms.uri"))
				.append("?sdkappid=").append(propertyMap.get("tencent.yun.sms.sdkappid"))
				.append("&random=").append(strRand);
			
			LOGGER.info("请求weChat报文：" + jsonObject.toString());
			String weChatRslt = HttpClientUtil.httpPost(urlSb.toString(), jsonObject.toString());
			LOGGER.info("weChat返回报文：" + weChatRslt);
			
			JSONObject weChat = JSONObject.fromObject(weChatRslt);
			if(Integer.parseInt(weChat.get("result").toString()) != 0){
				return false;
			}
		}catch(Exception e){
			LOGGER.error("调用腾讯云发送短信验证码异常！", e);
			return false;
		}
		return true;
	}
	
}
