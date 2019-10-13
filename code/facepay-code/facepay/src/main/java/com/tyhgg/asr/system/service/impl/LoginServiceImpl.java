package com.tyhgg.asr.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.PeopleLoginEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelEntity;
import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.asr.system.mapper.RoleMapper;
import com.tyhgg.asr.system.service.LoginService;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.JwtUtils;
import com.tyhgg.core.framework.util.StringUtil;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService  {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Resource
	private PeopleLoginMapper peopleLoginMapper;
	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private RoleMapper roleMapper;

	/**
	 * 获取登录信息
	 */
	@Transactional
	public JSONObject handleLoginInfo(HttpServletRequest request, JSONObject bodyJson, PeopleEntity peopleEntity, 
			Map<String, String> msgMap, Map<String, String> systemPropertyMap){

		String userId = peopleEntity.getUserId();
		
		Calendar calendar = Calendar.getInstance();
		Date nowTime = new Date();
		calendar.setTime(nowTime);
//		calendar.add(Calendar.DATE, -meetingBetweenDays);
		// 查询角色
		RolePeopleRelEntity rolePeopleRelEntityTemp = new RolePeopleRelEntity();
		rolePeopleRelEntityTemp.setUserId(userId);
		List<RolePeopleRelEntity> rolePeopleRelLsit = this.roleMapper.queryPeopleRoleRelList(rolePeopleRelEntityTemp);
        StringBuilder sb = new StringBuilder();
        // in需要的角色('','')
        if(null != rolePeopleRelLsit && rolePeopleRelLsit.size() > 0){
            sb.append("(");
            int size = rolePeopleRelLsit.size();
            for(int i = 0; i < size; i++){
            	RolePeopleRelEntity tempRel = rolePeopleRelLsit.get(i);
                if(i == 0){
                    sb.append("'").append(tempRel.getRoleId()).append("'");
                } else {
                    sb.append(",'").append(tempRel.getRoleId()).append("'");
                }
            }
            sb.append(")");
            peopleEntity.setRoleId(sb.toString());
        } else if(userId.endsWith("admin")) {
        	peopleEntity.setRoleId(SystemConstants.ROLE_ADMINSTRATOR);
        } else {
        	peopleEntity.setRoleId("('99')");
        }
        // 只是系统管理员身份设置为1
        if(("('1')").equals(sb.toString())){
        	peopleEntity.setRoleId(SystemConstants.ROLE_ADMINSTRATOR);
        }
        // 返回报文
		JSONObject resultJson = new JSONObject();

		Date nowDate = new Date();
        // jwt token
    	Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", userId);
        claims.put("roleId", peopleEntity.getRoleId());
        claims.put("orgId", peopleEntity.getOrgId());
        claims.put("created", nowDate);
		
        int expiration = Integer.parseInt(systemPropertyMap.get("jwt.timeout.minutes"));
        String jwtToken = JwtUtils.generateToken(claims, expiration);
        resultJson.put("authorization", jwtToken);
        
		// 处理登录信息
		this.handlePeopleLogin(request, bodyJson, resultJson, systemPropertyMap, peopleEntity);
		
		resultJson.put("peopleName", peopleEntity.getPeopleName());
		resultJson.put("userId", peopleEntity.getUserId());
		resultJson.put("orgId", peopleEntity.getOrgId());
		resultJson.put("roleId", peopleEntity.getRoleId());
		resultJson.put("orgName", peopleEntity.getOrgName());
		resultJson.put("updatePass", peopleEntity.getUpdatePass());
		
		resultJson.put("currentDate", DateUtil.dateFormat(nowDate, "yyyy-MM-dd HH:mm:ss"));
		
		String peopleTel = peopleEntity.getPeopleTel();
		if(StringUtil.isNotEmpty(peopleTel) && peopleTel.length() > 7){
			peopleTel = peopleTel.substring(0, 3) + "****" + peopleTel.substring(peopleTel.length() - 4);
		}
		resultJson.put("peopleTel", peopleTel);
		
		return resultJson;
	}

	private void handlePeopleLogin(HttpServletRequest request, JSONObject bodyObject, JSONObject resultJson,
			Map<String, String> systemPropertyMap, PeopleEntity peopleEntity){

		// 保存Session
//		String sessionId = request.getSession().getId();
		String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);
		String userId = StringUtil.getString(peopleEntity.getUserId());
		
		PeopleLoginEntity peopleLoginEntity = new PeopleLoginEntity();
		peopleLoginEntity.setUserId(userId);
		peopleLoginEntity.setClientId(clientId);
		LOGGER.info("查询用户登录信息：" + peopleLoginEntity);
		peopleLoginEntity = peopleLoginMapper.queryPeopleLoginEntity(peopleLoginEntity);
		LOGGER.info("查询用户登录信息结果：" + peopleLoginEntity);

        Calendar calendar = Calendar.getInstance();
        Date loginTime = new Date();
        calendar.setTime(loginTime);
        String timeoutTimeStr = "";
        String minutes = systemPropertyMap.get("login.timeout.minutes");
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, Integer.parseInt(minutes));
        timeoutTimeStr = DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		
		if(null == peopleLoginEntity){
			// 新增
			peopleLoginEntity = new PeopleLoginEntity();
			peopleLoginEntity.setUserId(userId);
			peopleLoginEntity.setClientId(clientId);
			peopleLoginEntity.setLoginType(StringUtil.getString(bodyObject.get("loginType")));
			peopleLoginEntity.setSessionId(StringUtil.getString(resultJson.get("authorization")));
			peopleLoginEntity.setLoginStatus(SystemConstants.PEOPLE_LOGINSTATUS_LOGIN);
			peopleLoginEntity.setLoginCount(1);
			
			peopleLoginEntity.setLoginTime(DateUtil.dateFormat(loginTime, DateUtil.YY_MM_DD_HH_MM_SS));
			
			peopleLoginEntity.setTimeoutTime(timeoutTimeStr);
			LOGGER.info("新增用户登录信息：" + peopleLoginEntity);
			this.peopleLoginMapper.save(peopleLoginEntity);
		} else {
			// 修改
			peopleLoginEntity.setLoginType(StringUtil.getString(bodyObject.get("loginType")));
			peopleLoginEntity.setSessionId(StringUtil.getString(resultJson.get("authorization")));
			peopleLoginEntity.setLoginStatus(SystemConstants.PEOPLE_LOGINSTATUS_LOGIN);
			peopleLoginEntity.setLoginCount(peopleLoginEntity.getLoginCount() + 1);
			peopleLoginEntity.setLoginTime(DateUtil.dateFormat(loginTime, DateUtil.YY_MM_DD_HH_MM_SS));
			peopleLoginEntity.setTimeoutTime(timeoutTimeStr);
			LOGGER.info("修改用户登录信息：" + peopleLoginEntity);
			this.peopleLoginMapper.updatePeopleLoginEntity(peopleLoginEntity);
		}
	}
	
}
