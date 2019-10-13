package com.tyhgg.core.framework.util;

import org.slf4j.MDC;

public class Slf4jMDCUtil {
	
	public static final String PRODUCT = "product";

	public static void initMdc() {

		if (System.getProperty(PRODUCT) != null) {
			MDC.put(PRODUCT, System.getProperty(PRODUCT));
			MDC.put("module", System.getProperty("module"));
			MDC.put("node", System.getProperty("node"));
		}else{
			
			MDC.put(PRODUCT, "ras");
			MDC.put("module", "aas");
			MDC.put("node", "access");
		}
		
		
	}

	public static void initMdc(String uuid) {
		initMdc();
		MDC.put("uuid", uuid);
	}
}
