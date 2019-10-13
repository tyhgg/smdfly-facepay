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
////@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false) //默认是回滚的,false 不回滚
//public class ManagerModuleMenuControllerTest extends CommonControllerTest {
//	private static Logger LOGGER = LoggerFactory.getLogger(ManagerModuleMenuControllerTest.class);
//
//	/**
//	 * 根据角色查询系统路由-vue,超级管理员返回所有菜单
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testLoadSystemModule() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testLoadSystemModule");
//		LOGGER.info("-------" + testCase.getUrl() + "测试开始");
//		LOGGER.info(testCase);
//		System.out.println("-------" + testCase);
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
//}
