package com.tyhgg.core.framework.filter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 后台管理页面过滤器
 * 
 * @author maliang
 *
 */
public class AdminLoginFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	    ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	// HttpServletResponse httpResponse = (HttpServletResponse) response;

	String url = StringUtil.getShortUri(httpRequest);

	// 系统配置参数
	Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();

	String uuid = UUID.randomUUID().toString();

	LOGGER.info("后台管理端交易" + url + " uuid为：" + uuid);

	Thread.currentThread().setName(uuid);

	// PC页面需要验登录
	final String pcwebNotCheckLoginUrl = systemPropertyMap.get(SystemConstants.PCWEB_NOT_CHECK_LOGIN_URL);
	if (url.startsWith("/pcweb/") && !url.matches(pcwebNotCheckLoginUrl)) {

	    HttpSession session = httpRequest.getSession();
	    if (null == session.getAttribute(SystemConstants.CURRENT_USER_SESSION)) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {

		    // httpResponse.sendRedirect("../index.jsp");//
		    // 主页面跳转，整个页面跳转需要用下面的方法

		    response.getWriter().write(
			    "<script>parent.window.top.location='" + httpRequest.getContextPath() + "/pcweb/toLogin"
				    + "';</script>");
		} catch (IOException e) {
		}
		session.setAttribute("msg", "登录超时,请重新登录！");
		return;
	    } else {
		chain.doFilter(request, response);
		session.removeAttribute("msg");
	    }
	} else {
	    chain.doFilter(request, response);
	}
    }

    @Override
    public void destroy() {
    	
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	
    }

}
