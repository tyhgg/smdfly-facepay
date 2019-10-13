package com.tyhgg.asr.wechat.controller;

import org.junit.Test;

import com.tyhgg.core.framework.CommonControllerTest;
import com.tyhgg.core.framework.util.JunitTestJdbcUtil;
import com.tyhgg.core.framework.util.TestCaseVO;

public class ScanLoginControllerTest extends CommonControllerTest {

    @Test
    public void testGetOauthRedirectUrl() throws Exception {
    	TestCaseVO testCase = new JunitTestJdbcUtil().getTestCaseByCaseId("testGetOauthRedirectUrl");
    	
    	super.getMethodControllerReturn(testCase);
    }
    
}
