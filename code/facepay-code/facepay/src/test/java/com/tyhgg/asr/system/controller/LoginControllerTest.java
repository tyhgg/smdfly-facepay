package com.tyhgg.asr.system.controller;
//package com.tyhgg.asr.system.control;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//
//import com.tyhgg.core.framework.CommonControllerTest;
//import com.tyhgg.core.framework.util.TestCaseVO;
//
///**
// * @author zyt5668 创建时间：2018年5月30日
// */
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false) //默认是回滚的,false 不回滚
//public class LoginControllerTest extends CommonControllerTest {
//	private static Logger LOGGER = LoggerFactory.getLogger(LoginControllerTest.class);
//
//	/**
//	 * 登录
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testLoginByClearPass() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testLoginByClearPass");
//		LOGGER.info("-------" + testCase.getUrl() + "测试开始");
//		LOGGER.info(testCase.toString());
//		String response = super.getControllerReturn(testCase);
//		LOGGER.info("-------" + testCase.getUrl() + "----response:" + response);
//		System.out.println("-------" + testCase.getUrl() + "----response:" + response);
//		if ("".equals(testCase.getResponse())) {
//			Assert.assertTrue(true);
//		} else {
//			super.assertJsonStrResult(response, testCase.getResponse());
//		}
//	}
//
//	/**
//	 * 获取随机数
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testGetRdnum() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testGetRdnum");
//		LOGGER.info("-------" + testCase.getUrl() + "测试开始");
//		LOGGER.info(testCase);
//		String response = super.getControllerReturn(testCase);
//		LOGGER.info("-------" + testCase.getUrl() + "----response:" + response);
//		System.out.println("-------" + testCase.getUrl() + "----response:" + response);
//		if ("".equals(testCase.getResponse())) {
//			Assert.assertTrue(true);
//		} else {
//			super.assertJsonStrResult(response, testCase.getResponse());
//		}
//	}
//
//
//	/**
//	 * 退出登录
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testLoginout() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testLoginout");
//		LOGGER.info("-------" + testCase.getUrl() + "测试开始");
//		LOGGER.info(testCase);
//		String response = super.getControllerReturn(testCase);
//		LOGGER.info("-------" + testCase.getUrl() + "----response:" + response);
//		System.out.println("-------" + testCase.getUrl() + "----response:" + response);
//		if ("".equals(testCase.getResponse())) {
//			Assert.assertTrue(true);
//		} else {
//			super.assertJsonStrResult(response, testCase.getResponse());
//		}
//	}
//
//
//}
