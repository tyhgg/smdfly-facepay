package com.tyhgg.asr.common.controller;

import org.junit.Test;

import com.tyhgg.core.framework.CommonControllerTest;
import com.tyhgg.core.framework.util.JunitTestJdbcUtil;
import com.tyhgg.core.framework.util.TestCaseVO;

public class SystemServerControllerTest extends CommonControllerTest {

    @Test
    public void testQuerySysDate() throws Exception {
    	TestCaseVO testCase = new JunitTestJdbcUtil().getTestCaseByCaseId("testQuerySysDate");
    	
    	super.getMethodControllerReturn(testCase);
    }
    
    @Test
    public void testQuerySysUrl() throws Exception {
    	TestCaseVO testCase = new JunitTestJdbcUtil().getTestCaseByCaseId("testQuerySysUrl");
    	super.postMethodControllerReturn(testCase);
    }

}
