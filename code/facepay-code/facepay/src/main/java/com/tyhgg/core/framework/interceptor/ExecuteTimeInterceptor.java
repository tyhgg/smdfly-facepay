package com.tyhgg.core.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 性能监控拦截器,可用于后期日志扩展
 * 
 * @author zyt5668
 * 
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);
	private static final String start_time = ExecuteTimeInterceptor.class.getName() + ".START_TIME";
	public static final String EXECUTE_TIME_ATTRIBUTE_NAME = ExecuteTimeInterceptor.class.getName() + ".EXECUTE_TIME";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Long localLong = (Long) request.getAttribute(start_time);
		if (localLong == null) {
			localLong = Long.valueOf(System.currentTimeMillis());
			request.setAttribute(start_time, localLong);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		Long localLong1 = (Long) request.getAttribute(EXECUTE_TIME_ATTRIBUTE_NAME);
		Object localObject;
		if (localLong1 == null) {
			localObject = (Long) request.getAttribute(start_time);
			Long localLong2 = Long.valueOf(System.currentTimeMillis());
			localLong1 = Long.valueOf(localLong2.longValue() - ((Long) localObject).longValue());
			request.setAttribute(start_time, localObject);
		}
		// Object 转换
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		if (modelAndView != null) {
			localObject = modelAndView.getViewName();
			if (!StringUtils.startsWith((String) localObject, "redirect:"))
				modelAndView.addObject(EXECUTE_TIME_ATTRIBUTE_NAME, localLong1);
		}
		request.getSession().setAttribute("times", "页面总计加载时间:" + localLong1);
		LOGGER.debug("[Class：{} == Method：{}]  页面总计加载时间: {} ms", handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName(), localLong1);
	}
}