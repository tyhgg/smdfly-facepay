
package com.tyhgg.core.framework.filter;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.entity.SystemLogEntity;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.exception.SystemException;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.JwtUtils;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.Slf4jMDCUtil;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.core.framework.util.SystemLogUtil;
import com.tyhgg.core.framework.wrapper.SystemHttpServletRequestWrapper;
import com.tyhgg.core.framework.wrapper.SystemHttpServletResponseWrapper;

/**
 * 作用：
 * 1、记日志
 * 2、检查是否登录
 * 3、从部分交易从报文头中取userId放到报文体中，会改变报文体内容
 * @类名称: WrapperHttpServletFilter
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月28日 下午4:07:29
 * @修改备注：
 */
@WebFilter(urlPatterns="/*",filterName="WrapperHttpServletFilter")
@Order(value=1)
public class WrapperHttpServletFilter extends AbstractSystemFilter {

    private static boolean saveLog = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperHttpServletFilter.class);

    /**
     * @param 
     * 
     */
    public static void setSaveLogStatus(boolean status) {
        saveLog = status;
        LOGGER.info("System log status is :" + saveLog);
    }

    @Override
    public void destroy() {
    }
   
    @Override
    protected void filter(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws IOException, SystemException, ServletException {
        SystemHttpServletRequestWrapper wreq = null;
    	SystemHttpServletResponseWrapper wres = null;

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 设置返回数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("Content-Language", "zh-CN");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");

        final String tranUrl = StringUtil.getShortUri(request);
        // 记录日志
        SystemLogEntity logEntity = SystemLogUtil.LOG_ENTITY.get();
        String uuid = this.getUuid(request, response);
        Slf4jMDCUtil.initMdc(uuid);
        Date date = new Date();
        logEntity.setBeginTime(new java.sql.Timestamp(date.getTime()));
        logEntity.setSysDate(new java.sql.Date(date.getTime()));
        logEntity.setUuid(uuid);
        logEntity.setTranUrl(tranUrl);
        
        // 需要登录后才能做的交易验证报文头中jwt-token,同时把userId和roleId设置到logEntity中
		if (!tranUrl.matches(SystemCacheHolder.getSystemPropertyCache().getMaps().get(SystemConstants.NOT_CHECK_LOGIN_URL))) {
			if(!this.validJwtToken(request, response, logEntity, new JSONObject())){
				return;
			}
		}

		// 不需要走WrapperHttpServletFilter记日志的交易，如文件之类
		if (tranUrl.matches(SystemCacheHolder.getSystemPropertyCache().getMaps().get(SystemConstants.NOT_WRAPPER_FILTER_URL))) {
			chain.doFilter(req, res);
            return;
		}

        boolean isResponse = false;
        try {

            wreq = new SystemHttpServletRequestWrapper(request);
            wres = new SystemHttpServletResponseWrapper(response);

            String body = FileCopyUtils.copyToString(new InputStreamReader(wreq.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();
            if(StringUtil.isNotEmpty(body)){
            	jsonObject = JSONObject.fromObject(body);
                // 需要从报文头中获取userid和roleid放到报文体中的交易
                if(SystemCacheHolder.getTranUseridCheckCache().getMaps().containsKey(tranUrl)){
        			wreq.setBody(this.setHeaderUserIdToBody(logEntity, jsonObject));
                }
            }
            
            // 记录日志
            before(wreq, response, logEntity, jsonObject);
            
            chain.doFilter(wreq, wres);
        } catch (SystemException e) {
        	isResponse = true;
        	throw e;
        } catch (Exception e) {
        	isResponse = true;
        	LOGGER.error("处理请求异常：", e);
        	throw e;
        } finally {
            if (wres != null && !isResponse) {
                byte[] byteStream = wres.getReponseOutputStream();
                // 将报文输出到客户端
                res.getOutputStream().write(byteStream);
            }
            after(wres, logEntity);
        }

    }

    private byte[] setHeaderUserIdToBody(SystemLogEntity logEntity, JSONObject jsonObject) throws UnsupportedEncodingException{
        // 登录接口验证报文头和报文体中的用户名信息是否统一
		String bodyField = SystemCacheHolder.getTranUseridCheckCache().getMaps().get(logEntity.getTranUrl());
		if(StringUtil.isNotEmpty(bodyField)){
			jsonObject.put(bodyField, logEntity.getUserId());
		} else {
			jsonObject.put(SystemConstants.HEADER_USER_ID, logEntity.getUserId());
		}
		jsonObject.put(SystemConstants.HEADER_ROLE_ID, logEntity.getRoleId());
        
		return jsonObject.toString().getBytes("UTF-8");
    }
    
    /**
     * 验证报文头jwt token, 同时把userId和roleId设置到logEntity中
     * @方法名: validJwtToken
     * @方法描述: 
     * @param request
     * @param response
     * @return
     * @return boolean
     * @throws IOException 
     */
    private boolean validJwtToken(HttpServletRequest request, HttpServletResponse response, 
    		SystemLogEntity logEntity, JSONObject jsonObject) throws IOException {
    	// jwt头
		String authorization = request.getHeader(SystemConstants.HEADER_AUTHORIZATION);
		LOGGER.info("header authentication = " + authorization);
		if (authorization != null && !"".equals(authorization.trim())) {
			Claims claims = JwtUtils.getClaimsFromToken(authorization);
			if(null == claims){
				this.writeJwtErrorRes(request, response, logEntity, jsonObject, ExceptionCode.EC_000005);
				return false;
			}
			// 获取token
			LOGGER.info("checking  claims " + claims);
			String userId = StringUtil.getString(claims.get("userId"));
			String roleId = StringUtil.getString(claims.get("roleId"));
			// 根据jwt的token获取jwt中用户id
			LOGGER.info("checking userId " + userId);
			LOGGER.info("checking roleId " + roleId);
			logEntity.setUserId(userId);
			logEntity.setRoleId(roleId);
			if (!"".equals(userId)) {
				// 根据userid获取用户信息
				Date expiration = claims.getExpiration();
				
				LOGGER.error("expiration：", DateUtil.dateFormat(expiration, "yyyy-MM-dd HH:mm:ss"));
				
				if(!expiration.before(new Date())){
					Map<String, String> peopleMap = new HashMap<String, String>();
					peopleMap.put("userId", userId);
					peopleMap.put("roleId", StringUtil.getString(claims.get("roleId")));
					
					request.setAttribute("jwtPeopleMap", peopleMap);
					
				} else {
					
					this.writeJwtErrorRes(request, response, logEntity, jsonObject, ExceptionCode.EC_000006);
					
					return false;
				}
			} else {
				
				this.writeJwtErrorRes(request, response, logEntity, jsonObject, ExceptionCode.EC_000009);
				
				return false;
			}
		} else {
			
			this.writeJwtErrorRes(request, response, logEntity, jsonObject, ExceptionCode.EC_000005);
			return false;
			
		}
		
		return true;
    }
    
    private void writeJwtErrorRes(HttpServletRequest request, HttpServletResponse response, 
    		SystemLogEntity logEntity, JSONObject jsonObject, String errorCode) throws IOException{

		JSONObject resultJson = ResponseUtils.responseErrorJson(errorCode, 
				SystemCacheHolder.getMsgCache().getMaps().get(errorCode));
		
        // 记录日志
        this.beforeJwt(request, response, logEntity, jsonObject);
        this.afterJwt(resultJson, logEntity);
		
		LOGGER.info("Too App : " + resultJson);
		response.getWriter().write(resultJson.toString());
		
    }
    
    /**
     * 1:from app
     */
    private void beforeJwt(HttpServletRequest request, HttpServletResponse response, 
    		SystemLogEntity logEntity, JSONObject jsonObject) {

        String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);

        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer header = new StringBuffer("$header:");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            header.append(headerName).append("-")
                    .append(request.getHeader(headerName)).append(";");
        }
        
        LOGGER.info(header.toString());
        
        StringBuilder bodyBuffer = new StringBuilder();
        bodyBuffer.append("请求报文：").append(logEntity.getUserId()).append("|")
				.append(logEntity.getUuid()).append("|").append("1").append("|")
				.append(logEntity.getTranUrl())
				.append("$queryParam:").append(request.getQueryString())
                .append(";$from APP:").append(jsonObject.toString());

        LOGGER.info(bodyBuffer.toString());

        logEntity.setClientId(clientId);
        logEntity.setTranCode("");
        logEntity.setTranName("");
        logEntity.setServiceIp(System.getProperty("node"));
        logEntity.setClientIp(getRemoteHost(request));
        logEntity.setHttpHeader(header.toString());
        logEntity.setHttpBody(jsonObject.toString());

    }
    
    /**
     * 1:from app
     */
    private void before(HttpServletRequest request, HttpServletResponse response, 
    		SystemLogEntity logEntity, JSONObject jsonObject) throws IOException, SystemException {

        Assert.notNull(logEntity, "'SystemLogEntity' is not null ");
        // 从request中获取登录用户信息
        @SuppressWarnings("unchecked")
		Map<String, String> jwtPeopleMap = (HashMap<String, String>)request.getAttribute("jwtPeopleMap");
        String userId = "";
        if(null != jwtPeopleMap){
        	userId = jwtPeopleMap.get(SystemConstants.HEADER_USER_ID);
        }
        
        String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);

        // 登录接口从报文体中取userid
        if("/unlogin/login".equals(logEntity.getTranUrl())){
        	try{
        		userId = StringUtil.getString(jsonObject.get("userId"));
        		if(StringUtil.isEmpty(userId)){
        			userId = StringUtil.getString(jsonObject.get("peopleTel"));
        		}
        	} catch (Exception e) {
        		LOGGER.debug("", e);
        	}
        }
        
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer header = new StringBuffer("$header:");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            header.append(headerName).append("-")
                    .append(request.getHeader(headerName)).append(";");
        }
        
        LOGGER.info(header.toString());
        
        StringBuilder bodyBuffer = new StringBuilder();
        bodyBuffer.append("请求报文：").append(userId).append("|")
				.append(logEntity.getUuid()).append("|").append("1").append("|")
				.append(logEntity.getTranUrl())
				.append("$queryParam:").append(request.getQueryString())
                .append(";$from APP:");

        if(null != jsonObject){
        	bodyBuffer.append(jsonObject.toString());
        }
        
        LOGGER.info(bodyBuffer.toString());

        logEntity.setUuid(logEntity.getUuid());
        logEntity.setUserId(userId);
        logEntity.setClientId(clientId);
        logEntity.setTranUrl(logEntity.getTranUrl());
        logEntity.setTranCode("");
        logEntity.setTranName("");
        logEntity.setServiceIp(System.getProperty("node"));
        logEntity.setClientIp(getRemoteHost(request));
        logEntity.setHttpHeader(header.toString());
        if(null != jsonObject){
        	logEntity.setHttpBody(request.getQueryString() + jsonObject.toString());
        }
        
    }
    
    /**
     * 获取客户端的IP地址
     * 
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ipx = request.getHeader("x-forwarded-for");
        if (!StringUtil.isEmpty(ipx) && !"unkonown".equalsIgnoreCase(ipx)) {
            return ipx.split(",")[0];
        }
        String ipp = request.getHeader("Proxy-Client-IP");
        if (!StringUtil.isEmpty(ipp) && !"unkonown".equalsIgnoreCase(ipp)) {
            return ipp;
        }
        String ipw = request.getHeader("WL-Proxy-Client-IP");
        if (!StringUtil.isEmpty(ipw) && !"unkonown".equalsIgnoreCase(ipw)) {
            return ipw;
        }
        return request.getRemoteAddr();
    }

    /**
     * 处理请求后的报文
     * 
     * @param request
     * @param response
     * @param logEntity
     * @throws IOException
     */
    private void afterJwt(JSONObject resultJson, SystemLogEntity logEntity) {
    	
        logEntity.setErrorCode(StringUtil.getString(resultJson.get(ResponseUtils.ERRCODE)));
        logEntity.setErrorMessage(StringUtil.getString(resultJson.get(ResponseUtils.ERRMSG)));
        // 设置结束时间
        logEntity.setEndTime(new java.sql.Timestamp(new Date().getTime()));

        logEntity.setDuration(DateUtil.dateBetweenMillisecond(logEntity.getBeginTime(), logEntity.getEndTime()));
        
        LOGGER.info(new StringBuffer().append("返回报文：").append(logEntity.getUserId()).append("|")
                .append(logEntity.getUuid()).append("|")
                .append(logEntity.getTranUrl()).append("|to APP:").append(resultJson.toString())
                .toString());
        
        logEntity.setRespMessage(resultJson.toString());
        
        // 记录数据库日志
        if (saveLog) {
            Logger dbLoger = LoggerFactory.getLogger("DBLogger");
            dbLoger.info(logEntity.toString());
        }

    }

    /**
     * 处理请求后的报文
     * 
     * @param request
     * @param response
     * @param logEntity
     * @throws IOException
     */
    private void after(SystemHttpServletResponseWrapper response,
            SystemLogEntity logEntity) throws IOException {
        String body = null;
        if (response != null) {
            body = response.getResponseData();
            JSONObject jsonObject = JSONObject.fromObject(body);
            logEntity.setErrorCode(StringUtil.getString(jsonObject.get(ResponseUtils.ERRCODE)));
            logEntity.setErrorMessage(StringUtil.getString(jsonObject.get(ResponseUtils.ERRMSG)));
        }
        // 设置结束时间
        logEntity.setEndTime(new java.sql.Timestamp(new Date().getTime()));
        logEntity.setDuration(DateUtil.dateBetweenMillisecond(logEntity.getBeginTime(), logEntity.getEndTime()));
        
        // 系统配置参数
 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
        String notPrintFileLogUrls = StringUtil.getString(systemPropertyMap.get("not.print.file.log.urls"));
        String tranUrl = StringUtil.getString(logEntity.getTranUrl());
        
        if(StringUtil.isNotEmpty(tranUrl) && notPrintFileLogUrls.contains(tranUrl)){
        	LOGGER.info(new StringBuffer().append("返回报文：").append(logEntity.getUserId()).append("|")
              .append(logEntity.getUuid()).append("|")
              .append(logEntity.getTranUrl()).append("|to APP:notPrintFileLogUrls")
              .toString());
        } else {
            LOGGER.info(new StringBuffer().append("返回报文：").append(logEntity.getUserId()).append("|")
                    .append(logEntity.getUuid()).append("|")
                    .append(logEntity.getTranUrl()).append("|to APP:").append(body)
                    .toString());
        }
        logEntity.setRespMessage(body);
        
        // 记录数据库日志
        if (saveLog) {
            Logger dbLoger = LoggerFactory.getLogger("DBLogger");
            dbLoger.info(logEntity.toString());
        }

    }

	private String getUuid(HttpServletRequest request, HttpServletResponse response) {
		String uuid = request.getHeader(SystemConstants.HEADER_UUID);
		if (StringUtils.isEmpty(uuid) || uuid.equals("null")) {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
			response.addHeader(SystemConstants.HEADER_UUID, uuid);
		}
		// 设置线程名称，便于查日志
		if (StringUtil.isNotEmpty(uuid)) {
			Thread.currentThread().setName(uuid);
		}

		request.setAttribute(SystemConstants.HEADER_UUID, uuid);

		return uuid;
	}
	
}
