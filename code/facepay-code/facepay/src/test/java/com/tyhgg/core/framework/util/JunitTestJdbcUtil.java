package com.tyhgg.core.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取数据库案例配置
 * 
 * @类名称: JunitTestJdbcUtil
 * @类描述:
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年2月6日 下午4:46:46
 * @修改备注：
 */
public class JunitTestJdbcUtil {

	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;

	private static Logger LOGGER = LoggerFactory.getLogger(JunitTestJdbcUtil.class);

	private Connection conn = null;
	{
		Properties jdbcProperties = PropertiesFileUtils.getSystemProperties();

		String driver = jdbcProperties.getProperty("spring.datasource.driver-class-name");
		String url = jdbcProperties.getProperty("spring.datasource.url");
		String username = jdbcProperties.getProperty("spring.datasource.username");
		String password = jdbcProperties.getProperty("spring.datasource.password");

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		} finally {
			
		}
	}

	/**
	 * 查询b_testcase表里数据
	 */
	public HashMap<String, TestCaseVO> loadTestCaseMap() {
		HashMap<String, TestCaseVO> map = new HashMap<>();
		try {
			String sq1 = "SELECT * FROM b_testcase";
			this.pstat = this.conn.prepareStatement(sq1);
			ResultSet rs = this.pstat.executeQuery();

			while (rs.next()) {
				TestCaseVO testCase = new TestCaseVO();
				testCase.setCaseId(rs.getString("case_id") == null ? "" : rs.getString("case_id"));
				testCase.setUrl(rs.getString("url") == null ? "" : rs.getString("url"));
				testCase.setHeaders(rs.getString("headers") == null ? "" : rs.getString("headers"));
				testCase.setMethod(rs.getString("method") == null ? "" : rs.getString("method"));
				testCase.setParameter(rs.getString("parameter") == null ? "" : rs.getString("parameter"));
				testCase.setRequest(rs.getString("request") == null ? "" : rs.getString("request"));
				testCase.setResponse(rs.getString("response") == null ? "" : rs.getString("response"));
				testCase.setRemark1(rs.getString("remark1") == null ? "" : rs.getString("remark1"));
				testCase.setRemark2(rs.getString("remark2") == null ? "" : rs.getString("remark2"));
				testCase.setRemark3(rs.getString("remark3") == null ? "" : rs.getString("remark3"));
			}
		} catch (Exception e) {
			LOGGER.error("查询b_testcase表失败", e);
		} finally {
			this.close();
		}
		return map;
	}

	/**
	 * 查询b_testcase表里数据
	 */
	public TestCaseVO getTestCaseByCaseId(String caseId) {
		TestCaseVO testCase = new TestCaseVO();
		try {
			String sq1 = "SELECT * FROM b_testcase where case_id = '" + caseId + "'";
			this.pstat = this.conn.prepareStatement(sq1);
			ResultSet rs = this.pstat.executeQuery();

			while (rs.next()) {
				testCase.setCaseId(rs.getString("case_id") == null ? "" : rs.getString("case_id"));
				testCase.setUrl(rs.getString("url") == null ? "" : rs.getString("url"));
				testCase.setHeaders(rs.getString("headers") == null ? "" : rs.getString("headers"));
				testCase.setMethod(rs.getString("method") == null ? "" : rs.getString("method"));
				testCase.setParameter(rs.getString("parameter") == null ? "" : rs.getString("parameter"));
				testCase.setRequest(rs.getString("request") == null ? "" : rs.getString("request"));
				testCase.setResponse(rs.getString("response") == null ? "" : rs.getString("response"));
				testCase.setRemark1(rs.getString("remark1") == null ? "" : rs.getString("remark1"));
				testCase.setRemark2(rs.getString("remark2") == null ? "" : rs.getString("remark2"));
				testCase.setRemark3(rs.getString("remark3") == null ? "" : rs.getString("remark3"));

				break;
			}
		} catch (Exception e) {
			LOGGER.error("查询b_testcase表失败", e);
		} finally {
			this.close();
		}
		return testCase;
	}

	/**
	 * 关闭所有连接
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("关闭资源失败！", e);
		}
	}

}
