//package com.tyhgg.manager.system.control;
//
//import org.apache.log4j.Logger;
//import org.junit.Assert;
//import org.junit.Test;
//
//import com.tyhgg.core.framework.CommonControllerTest;
//import com.tyhgg.core.framework.util.TestCaseVO;
//
///**
// * @author zyt5668 创建时间：2018年5月30日
// */
//public class ManagerOrgControlTest extends CommonControllerTest {
//	private static Logger LOGGER = LoggerFactory.getLogger(ManagerOrgControlTest.class);
//
//	/**
//	 * 查询组织机构列表
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryOrgInfoPageList() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryOrgInfoPageList");
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
//	/**
//	 * 查询组织机构
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryOrgInfo() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryOrgInfo");
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
//	/**
//	 * 查询组织机构
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testDelOrgInfoById() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testDelOrgInfoById");
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
//	/**
//	 * 查询组织机构
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testDelOrgInfoById1() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testDelOrgInfoById1");
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
//	/**
//	 * 修改组织机构
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testSaveOrgInfoUpdate() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testSaveOrgInfoUpdate");
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
//	/**
//	 * 新增组织机构
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testSaveOrgInfoAdd() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testSaveOrgInfoAdd");
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
//	/**
//	 * 根据机构号查询本机构和下级机构，如果是超级管理员用户可以查询所有的机构 
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryOrgListByPid() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryOrgListByPid");
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
//	/**
//	 * 根据组织机构级别查询组织机构列表
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryOrgListByLevel() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryOrgListByLevel");
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
