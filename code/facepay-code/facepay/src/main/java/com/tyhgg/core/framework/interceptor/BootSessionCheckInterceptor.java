//package com.tyhgg.core.framework.interceptor;
//
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import com.tyhgg.asr.system.entity.PeopleLoginEntity;
//import com.tyhgg.asr.system.mapper.PeopleLoginMapper;
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.constants.SystemConstants;
//import com.tyhgg.core.framework.exception.ExceptionCode;
//import com.tyhgg.core.framework.util.DateUtil;
//import com.tyhgg.core.framework.util.SpringUtil;
//import com.tyhgg.core.framework.util.StringUtil;
//
///**
// * 登录校验
// * @类名称: SessionCheckInterceptor
// * @类描述: 
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2019年4月28日 下午4:24:22
// * @修改备注：
// */
//@Configuration
//public class BootSessionCheckInterceptor extends WebMvcConfigurationSupport {
//
//    private static Logger LOGGER = LoggerFactory.getLogger(BootSessionCheckInterceptor.class);
//
//    private PeopleLoginMapper peopleLoginMapper;
//
//    {
//        if (peopleLoginMapper == null) {
//        	peopleLoginMapper = SpringUtil.getBean(PeopleLoginMapper.class);
//		}
//    }
//    
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//            HttpServletResponse response, Object handler) {
//    	
//        final String url = StringUtil.getShortUri(request);
//
//        final String notCheckLoginUrl = SystemCacheHolder.getSystemPropertyCache().getMaps().get(SystemConstants.NOT_CHECK_LOGIN_URL);
//        // 接口登录验证
//        // 增加例外，如单点登录服务，用户登录
//        if (url.matches(notCheckLoginUrl)) {
//            return true;
//        }
//        // 报文头里存放的sessionid
//        final String sessionId = request.getRequestedSessionId();
//        final String userid = request.getHeader(SystemConstants.HEADER_USER_ID);
//        final String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);
//        LOGGER.debug("---request.getRemoteAddr()=[" + request.getRemoteAddr() + "]");
//        LOGGER.debug("---request.getHeader(cookie)=[" + request.getHeader("cookie") + "]");
//        LOGGER.debug("---request.getRequestedSessionId()=[" + sessionId + "]");
//        if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(userid) || StringUtils.isEmpty(clientId)) {
//        	StringUtil.responseJson(response, ExceptionCode.EC_000020, 
//        			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000020));
//            return false;
//        }
//
//        PeopleLoginEntity peopleLoginEntity = new PeopleLoginEntity();
//        peopleLoginEntity.setUserId(userid);;
//        peopleLoginEntity.setClientId(clientId);
//        peopleLoginEntity = peopleLoginMapper.queryPeopleLoginEntity(peopleLoginEntity);
//        if (peopleLoginEntity == null) {
//        	StringUtil.responseJson(response, ExceptionCode.EC_000005, 
//        			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000005));
//            return false;
//        }
//
//        LOGGER.info("---usrToken.getSessionid()=["  + peopleLoginEntity.getSessionId() + "]");
//        // session检验
//        if (!peopleLoginEntity.getSessionId().equals(sessionId)) {
//        	StringUtil.responseJson(response, ExceptionCode.EC_000006, 
//        			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000006));
//            return false;
//        }
//        // 登录超时时间检验
//        String timeoutTime = peopleLoginEntity.getTimeoutTime();
//        String nowTime = DateUtil.dateFormat(new Date(), DateUtil.YY_MM_DD_HH_MM_SS);
//        if(nowTime.compareTo(timeoutTime) > 0){
//        	StringUtil.responseJson(response, ExceptionCode.EC_000006, 
//        			SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000006));
//            return false;
//        }
//        
//        return true;
//
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request,
//            HttpServletResponse response, Object handler,
//            ModelAndView modelAndView) {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request,
//            HttpServletResponse response, Object handler, Exception ex) {
//        
//    }
//
//}
