//package com.tyhgg.core.framework.aspect;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class TestAspect {
//	
//	private Logger logger = LoggerFactory.getLogger(TestAspect.class);
//	
//	@Pointcut("execution(public * com.tyhgg.kindling.controller.NewsController.add*(..))")
//	public void addAspect(){}
//
//	
//	@Before("addAspect()")
//	public void beforeMethod(){
//		logger.info("切面逻辑处理之前。。。");
//	}
////	@Around("addAspect()")
////	public void interceptor(ProceedingJoinPoint point){
////		logger.info("切面逻辑处理中。。。");
////	}
//	@After("addAspect()")
//	public void afterMethod(){
//		logger.info("切面逻辑处理之后。。。");
//	}
//	
//	
//}
