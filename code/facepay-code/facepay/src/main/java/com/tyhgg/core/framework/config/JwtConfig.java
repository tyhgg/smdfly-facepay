package com.tyhgg.core.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public class JwtConfig {

//    @Bean
//    public JwtFilter getJwtFilterFilterBean(){
//        return new JwtFilter();
//    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(getJwtFilterFilterBean());
        // 添加需要拦截的url
        List<String> urlPatterns =new ArrayList();
//        urlPatterns.add("/user/*");
//        urlPatterns.add("/manager/orginfo/*");
        
//        urlPatterns.add("/news/do*");
//        urlPatterns.add("/news/add*");
//        urlPatterns.add("/news/getFavorites");

        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }
}
