package com.tyhgg.core.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 引入interceptor
 * @类名称: InterceptorConfig
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月29日 下午4:52:59
 * @修改备注：
 */
@SuppressWarnings("deprecation")
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new URLInterceptor()).addPathPatterns("/**");
//		registry.addInterceptor(new ClientTranInterceptor()).addPathPatterns("/**");
//		registry.addInterceptor(new SessionCheckInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
