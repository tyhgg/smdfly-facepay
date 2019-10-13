//package com.tyhgg.manager.system.control;
//
//import org.apache.log4j.Logger;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
//import com.tyhgg.core.framework.CommonControllerTest;
//import com.tyhgg.core.framework.util.TestCaseVO;
//
///**
// * @author zyt5668 创建时间：2018年5月30日
// */
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false) //默认是回滚的,false 不回滚
//public class ManagerPeopleControlTest extends CommonControllerTest {
//	private static Logger LOGGER = LoggerFactory.getLogger(ManagerPeopleControlTest.class);
//
//	/**
//	 * 新增用户
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testSavePeopleInfoAdd() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testSavePeopleInfoAdd");
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
//	 * 用户列表查询
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryPeopleList() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryPeopleList");
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
//	 * 修改用户
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testSavePeopleInfoUpdate() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testSavePeopleInfoUpdate");
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
//	 * 查询用户详细信息
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testQueryPeopleByUserId() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testQueryPeopleByUserId");
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
//	 * 修改密码
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testManagerUpdatePas() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testManagerUpdatePass");
//		LOGGER.info("-------" + testCase.getUrl() + "测试开始");
//		LOGGER.info(testCase);
//		String response = super.getControllerReturn(testCase);
//		LOGGER.info("-------" + testCase.getUrl() + "----response:" + response);
//		System.out.println("-------" + testCase.getUrl() + "----response:" + response);
//		// response:{"total":1,"rows":[{"countryId":"","createTime":"","dataSource":"","ehrId":"autotestzhangsan","esmFlag":"","groupName":"","groupNameList":[],"id":38804,"inDate":"","languageType":"zh","meetingId":0,"modTime":"","nationId":"","orgId":"other01","orgLevel":0,"orgName":"","peopleCountry":"","peopleIdno":"","peopleIdtype":"","peopleName":"自动化测试行外张三","peopleNation":"","peoplePass":"","peoplePhone":"021-38971296","peopleSex":"男","peopleStatus":"1","peopleTel":"15921218887","peopleTitle":"银监会主席","peopleType":"","queryKey":"","rem":"","roleId":"","roleName":"","roomNo":"","sortId":0,"titleDate":"","titleId":"","peopleTitle":"","updatePass":"","usPeopleCountry":"","usPeopleTitle":""}]}
//		if ("".equals(testCase.getResponse())) {
//			Assert.assertTrue(true);
//		} else {
//			super.assertJsonStrResult(response, testCase.getResponse());
//		}
//	}
//
//	/**
//	 * 新增用户
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testDelPeopleInfo() throws Exception {
//
//		TestCaseVO testCase = junitTestJdbcUtil.getTestCaseByCaseId("testDelPeopleInfo");
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
//}
