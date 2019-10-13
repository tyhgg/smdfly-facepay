package com.tyhgg.core.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.tyhgg.core.framework.exception.SystemException;

/**
 * 过滤器父类
 * @类名称: AbstractSystemFilter
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月28日 下午4:06:24
 * @修改备注：
 */
public abstract class AbstractSystemFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			this.filter(request, response, chain);
			
		} catch (Exception e) {
			
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
			((HandlerExceptionResolver)webApplicationContext.getBean("handlerExceptionResolver")).resolveException(request, response,
					null, e);
			return;
		}

	}

	protected abstract void filter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws IOException, SystemException, ServletException;

}
