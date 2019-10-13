package com.tyhgg.core.framework.util;

import org.springframework.context.ApplicationContext;

public final class ContextUtil {

    private ContextUtil() {}

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(
            ApplicationContext context) {
        applicationContext = context;
    }
    
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
     } 
    
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }
   
    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

}
