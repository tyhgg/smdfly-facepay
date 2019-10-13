//package com.tyhgg.core.framework.filter;
//
//import io.jsonwebtoken.Claims;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.tyhgg.asr.system.mapper.PeopleMapper;
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.exception.ExceptionCode;
//import com.tyhgg.core.framework.util.JwtUtils;
//import com.tyhgg.core.framework.util.ResponseUtils;
//import com.tyhgg.core.framework.util.StringUtil;
//
///**
// * Jwt - json web token cookie不支持跨域访问，jwt支持跨域访问，无状态
// * jwt组成：1、header，2、payload载荷，3、签证 signature
// * @类名称: JwtFilter
// * @类描述:
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2019年7月1日 上午9:31:38
// * @修改备注：
// */
////@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//	private static Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
//
//	@Resource
//	private PeopleMapper peopleMapper;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//		// jwt头
//		String authorization = request.getHeader("authorization");
//		LOGGER.info("checking authentication header = " + authorization);
//		if (authorization != null && "".equals(authorization.trim())) {
//			Claims claims = JwtUtils.getClaimsFromToken(authorization);
//			if(null == claims){
//				LOGGER.info("Too App : " + ResponseUtils.responseErrorJson(ExceptionCode.EC_000006, 
//						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000006)));
//				response.getWriter().write(ResponseUtils.responseErrorJson(ExceptionCode.EC_000006, 
//						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000006)).toString());
//				return ;
//			}
//			// 获取token
//			LOGGER.info("checking  claims " + claims);
//			String userId = StringUtil.getString(claims.get("userId"));
//			// 根据jwt的token获取jwt中用户id
//			LOGGER.info("checking userId " + userId);
//			if (userId != null) {
//				// 根据userid获取用户信息
//				Date expiration = claims.getExpiration();
//				if(expiration.before(new Date())){
//					Map<String, String> peopleMap = new HashMap<String, String>();
//					peopleMap.put("userId", userId);
//					peopleMap.put("roleId", StringUtil.getString(claims.get("roleId")));
//					
//					request.setAttribute("jwtPeopleMap", peopleMap);
//					
//					chain.doFilter(request, response);
//				} else {
//					response.getWriter().write(ResponseUtils.responseErrorJson(ExceptionCode.EC_000006, 
//							SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000006)).toString());
//				}
//			} else {
//				LOGGER.info("Too App : " + ResponseUtils.responseErrorJson(ExceptionCode.EC_000009, 
//						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000009)));
//				response.getWriter().write(ResponseUtils.responseErrorJson(ExceptionCode.EC_000009, 
//						SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000009)).toString());
//			}
//		} else {
//			LOGGER.info("Too App : " + ResponseUtils.responseErrorJson(ExceptionCode.EC_000005, 
//					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000005)));
//
//			response.getWriter().write(ResponseUtils.responseErrorJson(ExceptionCode.EC_000005, 
//					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000005)).toString());
//		}
//
//	}
//}
