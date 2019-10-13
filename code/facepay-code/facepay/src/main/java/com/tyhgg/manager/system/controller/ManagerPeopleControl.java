package com.tyhgg.manager.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.PeoplePageEntity;
import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.JsonXmlObjConvertUtils;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.SHA256Utils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerPeopleService;

@Controller
public class ManagerPeopleControl {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerPeopleControl.class);
	@Resource
	private ManagerPeopleService managerPeopleService;
	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private PeopleLoginMapper peopleLoginMapper;

	/**
	 * 用户列表查询
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/queryPeoplePageList", method = { RequestMethod.POST })
	@ResponseBody
	public String queryPeopleList(@RequestHeader HttpHeaders header, HttpServletRequest request, @RequestBody String body) {

		// 返回结果
        JSONObject resultJson = new JSONObject();
        
        PeoplePageEntity peoplePageVo = JsonXmlObjConvertUtils.jsonToObj(body, PeoplePageEntity.class);
        int pageSize = peoplePageVo.getPageSize();
        if (0 == pageSize) {
            pageSize = 20;
        }
        int pageNum = peoplePageVo.getPageNum();
        if (0 == pageNum) {
        	pageNum = 1;
        }
        peoplePageVo.setPageNum(pageNum);
        peoplePageVo.setPageSize(pageSize);

		LOGGER.info("查询用户列表信息：" + peoplePageVo);
		int totalCount = this.peopleMapper.queryPeoplePageCount(peoplePageVo);
		List<PeopleEntity> peopleList = null;
		if (totalCount > 0) {
			peopleList = this.peopleMapper.queryPeoplePageList(peoplePageVo);
		} else {
			peopleList = new ArrayList<PeopleEntity>();
		}

		resultJson.put("total", totalCount);
		resultJson.put("rows", peopleList);

		return ResponseUtils.responseSuccessJson(resultJson).toString();
	}

	/**
	 * 查询用户详细信息
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/queryPeopleByUserId", method = { RequestMethod.POST })
	@ResponseBody
	public String queryPeopleByUserId(@RequestHeader HttpHeaders header,
			HttpServletRequest request, @RequestBody String body,
			@RequestParam Map<String, String> uriVariables) {

		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);
		// 返回报文
		JSONObject resultJson = new JSONObject();

		// 系统错误码
		Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();
		
		String userId = StringUtil.getString(bodyJson.get("userId"));
		
		if ("".equals(userId) || null == userId) {
			return ResponseUtils.responseWebErrorJson(msgMap.get(ExceptionCode.EC_000007)).toString();	
		} 

		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setUserId(userId);
		//查询用户信息
		peopleEntity = this.peopleMapper.queryPeople(peopleEntity);
		
		LOGGER.info("" + peopleEntity);
		
		resultJson.put("peopleEntity", peopleEntity);
		
		return ResponseUtils.responseSuccessJson(resultJson).toString();
	}

	/**
	 * 新增或修改用户信息
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/savePeopleInfo", produces = { "application/json;charset=utf-8" }, method = { RequestMethod.POST })
	@ResponseBody
	public String savePeopleInfo(@RequestHeader HttpHeaders header, HttpServletRequest request, @RequestBody String body) {

		// 请求的报文体信息
		// JSONObject bodyJson = JSONObject.fromObject(body);
		JSONObject resultJson = new JSONObject();

		PeopleEntity peopleEntity = JsonXmlObjConvertUtils.jsonToObj(body, PeopleEntity.class);
		
		return this.managerPeopleService.savePeople(peopleEntity, resultJson).toString();

	}

	/**
	 * 删除用户信息
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/delPeopleInfo", method = { RequestMethod.POST })
	@ResponseBody
	public String delPeopleInfo(@RequestBody String body) {

		// 请求的报文体信息
		PeopleEntity peopleEntity = JsonXmlObjConvertUtils.jsonToObj(body, PeopleEntity.class);
		
		return this.managerPeopleService.deletePeople(peopleEntity).toString();

	}

	/**
	 * 修改密码
	 * @param header
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/manager/people/updatePass", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePass(@RequestHeader HttpHeaders header, HttpServletRequest request, @RequestBody String body) {
		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);
		String userId = StringUtil.getString(bodyJson.get("userId"));

		String peoplePass = StringUtil.getString(bodyJson.get("peoplePass"));
		String newPass = StringUtil.getString(bodyJson.get("newPass"));
		String oldPass = StringUtil.getString(bodyJson.get("oldPass"));
		if (StringUtil.isEmpty(oldPass)) {
			return ResponseUtils.responseWebErrorJson("原始密码不能为空，请重新输入！").toString();
		}
		if (StringUtil.isEmpty(peoplePass) || StringUtil.isEmpty(newPass)) {
			return ResponseUtils.responseWebErrorJson("新密码不能为空，请重新输入！").toString();
		}
		if (!peoplePass.equals(newPass)) {
			return ResponseUtils.responseWebErrorJson("两次输入的密码不一致，请重新输入！").toString();
		}

		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setUserId(userId);

		peopleEntity = this.peopleMapper.queryPeopleAndPass(peopleEntity);

		oldPass = SHA256Utils.encryptSHA256(oldPass);

		if (!oldPass.equals(peopleEntity.getPeoplePass())) {
			return ResponseUtils.responseWebErrorJson("原始密码不正确，请重新输入！").toString();
		}

		peoplePass = SHA256Utils.encryptSHA256(peoplePass);

		peopleEntity.setPeoplePass(peoplePass);

		this.peopleMapper.updatePeoplePass(peopleEntity);

		return ResponseUtils.responseWebSuccessJson().toString();
	}

	/**
	 * 解除密码锁定，更新peopleLogin表联系失败次数fail_count信息
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/unlockPeoplePass", method = { RequestMethod.POST })
	@ResponseBody
	public String unlockPeoplePass(@RequestBody String body) {

		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);

		String ehrId = StringUtil.getString(bodyJson.get("ehrId"));

		// 更新peopleLogin表联系失败次数fail_count信息
		this.peopleLoginMapper.unlockPeoplePass(ehrId);

		return ResponseUtils.responseWebSuccessJson().toString();

	}

	/**
	 * 重置用户密码
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/resetPeoplePass", method = { RequestMethod.POST })
	@ResponseBody
	public String resetPeoplePass(@RequestBody String body) {

		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);

		String userId = StringUtil.getString(bodyJson.get("userId"));
		//重置系统密码
		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setUserId(userId);
		peopleEntity.setPeoplePass(SHA256Utils.encryptSHA256(SystemConstants.PEOPLE_PASS_DEFAULT));
		this.peopleMapper.resetPeoplePass(peopleEntity);
		// 发送密码重置成功短信
		peopleEntity = peopleMapper.queryPeople(peopleEntity);
		// 系统配置参数
// 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
// 		String repassMsg = systemPropertyMap.get(SystemConstants.PEOPLE_REPASS_MSG);
//		//根据手机号发送短信
//		if(peopleEntity!=null && StringUtil.isNotEmpty(peopleEntity.getPeopleTel())){
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("mobile", peopleEntity.getPeopleTel());
//			jsonObject.put("message", repassMsg);
//			try {
//				mcisService.process(jsonObject);
//			} catch (Exception e) {
//				LOGGER.error("重置密码手机号:"+peopleEntity.getPeopleTel()+"重置密码发送短信失败!");
//			}
//		}
		return ResponseUtils.responseWebSuccessJson().toString();

	}

	/**
	 * 改变用户状态，1-启用，2-禁用
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/manager/people/updatePeopleStatus", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePeopleStatus(@RequestBody String body) {

		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);
		String userId = StringUtil.getString(bodyJson.get("userId"));
		String peopleStatus = StringUtil.getString(bodyJson.get("peopleStatus"));
		// peopleStatus 1-正常，2-逻辑删除

		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setUserId(userId);
		peopleEntity.setPeopleStatus(peopleStatus);
		this.peopleMapper.updatePeopleStatus(peopleEntity);

		return ResponseUtils.responseWebSuccessJson().toString();
	}
	
	/**
	 * 检查用户userId是否重复
	 * 
	 * @param header
	 * @param request
	 * @param body
	 * @param uriVariables
	 * @return
	 */
	@RequestMapping(value = "/manager/people/checkUserId", produces = { "application/json;charset=utf-8" }, 
			method = { RequestMethod.POST })
	@ResponseBody
	public String checkUserId(@RequestHeader HttpHeaders header, HttpServletRequest request, @RequestBody String body) {

		// 请求的报文体信息
		JSONObject bodyJson = JSONObject.fromObject(body);
		String userId = StringUtil.getString(bodyJson.get("userId"));

		if (StringUtil.isEmpty(userId)) {
			return ResponseUtils.responseWebSuccessJson().toString();
		}

		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setUserId(userId);
		peopleEntity = peopleMapper.queryPeople(peopleEntity);
		
		if (null != peopleEntity) {
			return ResponseUtils.responseWebErrorJson(userId + "已存在，请重新填写！").toString();
		} else {
			return ResponseUtils.responseWebSuccessJson().toString();
		}

	}
}
