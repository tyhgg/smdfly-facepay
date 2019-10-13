package com.tyhgg.core.framework.cache;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.cache.SystemCacheHolder;

/**
 * 
 * @类名称: SystemCacheHolderTest
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年2月6日 上午9:08:06
 * @修改备注：
 */

public class SystemCacheHolderTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SystemCacheHolderTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	public void test() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void testGetSystemPropertyCache() {
		LOGGER.info("执行testGetSystemPropertyCache案例");
		Map<String, String> msgMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		
		Assert.assertTrue(msgMap.containsKey("not.check.session.url"));
	}

}
