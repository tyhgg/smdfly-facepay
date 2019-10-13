package com.tyhgg.core.framework.util;
//package com.tyhgg.core.framework.utils;
//
//import java.util.Iterator;
//
//import net.sf.json.JSONObject;
//
//import org.junit.Assert;
//
//import com.tyhgg.core.framework.util.StringUtil;
//
//public class JunitAssertUtils {
//	
//	private void assertJsonResult(String result){
//		
//		String className = testCase.getResponse();
//				
//		if("java.lang.Integer".equals(className)){
//			Assert.assertTrue(result.contains(testCase.getSipresponse()));
//			return ;
//		} else if("java.lang.String".equals(className)){
//			Assert.assertTrue(result.contains(testCase.getSipresponse()));
//			return ;
//		}
//		
//		if(StringUtil.isNotEmpty(result)){
//			// controller返回json
//			JSONObject resultJson = JSONObject.fromObject(result);
//			
//			// b_testcase中配置返回json
//			JSONObject responseJson = JSONObject.fromObject(testCase.getSipresponse());
//			
//			@SuppressWarnings("unchecked")
//			Iterator<String> its = resultJson.keys();
//			while (its.hasNext()) {
//				String key = its.next();
//				Assert.assertTrue(resultJson.get(key).equals(responseJson.get(key)));
//			}
//		}
//	}
//	
//	private void assertJsonResult(String result, TestCaseVO testCase){
//		
//		String className = testCase.getResponse();
//				
//		if("java.lang.Integer".equals(className)){
//			Assert.assertTrue(result.contains(testCase.getSipresponse()));
//			return ;
//		} else if("java.lang.String".equals(className)){
//			Assert.assertTrue(result.contains(testCase.getSipresponse()));
//			return ;
//		}
//		
//		if(StringUtil.isNotEmpty(result)){
//			// controller返回json
//			JSONObject resultJson = JSONObject.fromObject(result);
//			
//			// b_testcase中配置返回json
//			JSONObject responseJson = JSONObject.fromObject(testCase.getSipresponse());
//			
//			@SuppressWarnings("unchecked")
//			Iterator<String> its = resultJson.keys();
//			while (its.hasNext()) {
//				String key = its.next();
//				Assert.assertTrue(resultJson.get(key).equals(responseJson.get(key)));
//			}
//		}
//	}
//}
