package com.tyhgg.core.framework;

import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.tyhgg.MiniBootApplication;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.core.framework.util.TestCaseVO;

/**
 * @author zyt5668 创建时间：2017年5月8日 下午3:10:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:application.properties"})
@SpringBootTest(classes = MiniBootApplication.class)// 指定spring-boot的启动类   
//@SpringApplicationConfiguration(classes = MiniBootApplication.class)// 1.4.0 前版本  注意启动类不要搞错了
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@WebAppConfiguration
public class CommonControllerTest {
	private static Logger LOGGER = LoggerFactory.getLogger(CommonControllerTest.class);
	protected MockMvc mockMvc;

	@Resource
    protected WebApplicationContext webApplicationContext;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  //初始化MockMvc对象
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void test() {
		Assert.assertTrue(true);
	}

	/**
	 * 获取GET请求Controller执行结果报文
	 * @param testCase
	 */
	public String getMethodControllerReturn(TestCaseVO testCase) {
		System.out.println("--------" + testCase);
		// 报文头
		HttpHeaders httpHeaders = this.getHttpHeaders(testCase);
		
		// ?号参数处理
		MultiValueMap<String, String> paramMap = this.getParamsMap(testCase);
		// 返回报文
		String result = "";
		try {
	        System.out.println("--------" + testCase.getUrl() + "测试start-----------");
			result = mockMvc.perform(
	        		MockMvcRequestBuilders.get(testCase.getUrl())    //请求的url,请求的方法是get
	                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
	                        .headers(httpHeaders)
	                        .params(paramMap)         //添加参数
	        ).andExpect(MockMvcResultMatchers.status().isOk())    //返回的状态是200
	                .andDo(MockMvcResultHandlers.print())         //打印出请求和相应的内容
	                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
	        System.out.println("--------" + testCase.getUrl() + "返回的json = " + result);
	        
	        if ("".equals(testCase.getResponse())) {
				Assert.assertTrue(true);
			} else {
				this.assertJsonStrResult(result, testCase.getResponse());
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * 获取POST请求Controller执行结果报文
	 * @param testCase
	 */
	public String postMethodControllerReturn(TestCaseVO testCase) {
		System.out.println("--------" + testCase);
		// 报文头
		HttpHeaders httpHeaders = this.getHttpHeaders(testCase);
		
		// ?号参数处理
		MultiValueMap<String, String> paramMap = this.getParamsMap(testCase);
		// 返回报文
		String result = "";
		try {
	        System.out.println("--------" + testCase.getUrl() + "测试start-----------");
	    	result = mockMvc.perform(MockMvcRequestBuilders.post("/unlogin/querySysUrl")
	    			.contentType(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders)
                    .params(paramMap)         //添加参数
	    			.content(testCase.getRequest()))
	    			.andDo(MockMvcResultHandlers.print())
	    	        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
	    	        .getResponse().getContentAsString();
	        System.out.println("--------" + testCase.getUrl() + "返回的json = " + result);
			
	        if ("".equals(testCase.getResponse())) {
				Assert.assertTrue(true);
			} else {
				this.assertJsonStrResult(result, testCase.getResponse());
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	private HttpHeaders getHttpHeaders(TestCaseVO testCase){
		// 报文头
		HttpHeaders httpHeaders = new HttpHeaders();
		if ((testCase.getHeaders() != null) && (testCase.getHeaders().trim().length() > 0)) {
			for (String header : testCase.getHeaders().split(",")) {
				String[] pair = header.split("=");
				if ("cookie".equals(pair[0])) {
					httpHeaders.add(pair[0], "JSESSIONID=" + pair[1]);
				} else {
					httpHeaders.add(pair[0], pair[1]);
				}
			}
		}
		return httpHeaders;
	}
	
	private MultiValueMap<String, String> getParamsMap(TestCaseVO testCase){
		// ?号参数处理
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		if ((testCase.getParameter() != null) && (testCase.getParameter().trim().length() > 0)) {
			for (String para : testCase.getParameter().split(",")) {
				String[] pair = para.split("=");
				if (pair.length > 1) {
					paramMap.add(pair[0], pair[1]);
				} else {
					paramMap.add(pair[0], "");
				}
			}
		}
		return paramMap;
	}
	
	public void assertJsonResult(String result, TestCaseVO testCase) {
		if (StringUtil.isNotEmpty(result)) {
			// controller返回json
			JSONObject resultJson = JSONObject.fromObject(result);

			// b_testcase中配置返回json
			JSONObject responseJson = JSONObject.fromObject(testCase.getResponse());

			@SuppressWarnings("unchecked")
			Iterator<String> its = responseJson.keys();
			while (its.hasNext()) {
				String key = its.next();
				String value = StringUtil.getString(responseJson.get(key));
				// 如果值是空的话，只需要判断返回报文中是否包含该字段
				if (StringUtil.isEmpty(value)) {
					Assert.assertTrue(resultJson.containsKey(key));
				} else {
					Assert.assertTrue(value.equals(resultJson.get(key)));
				}
			}
		}
	}

	/**
	 * 可嵌套循环检查JSONObject结果
	 * 
	 * @param result
	 * @param responseStr
	 */
	public void assertJsonStrResult(String result, String responseStr) {
		if (StringUtil.isNotEmpty(result)) {
			// controller返回json
			JSONObject resultJson = JSONObject.fromObject(result);

			// b_testcase中配置返回json
			JSONObject responseJson = JSONObject.fromObject(responseStr);

			@SuppressWarnings("unchecked")
			Iterator<String> responseIts = responseJson.keys();
			while (responseIts.hasNext()) {
				String key = responseIts.next();

				if (responseJson.get(key) instanceof JSONObject) {
					System.out.println("JSONObject:" + key);

					assertJsonStrResult(resultJson.get(key).toString(), responseJson.get(key).toString());

				} else if (responseJson.get(key) instanceof JSONArray) {
					System.out.println("JSONArray:" + key);
					this.assertArrayStrResult(resultJson.getJSONArray(key), responseJson.getJSONArray(key));
				} else if (responseJson.get(key) instanceof String) {
					String value = StringUtil.getString(responseJson.get(key));
					// 如果值是空的话，只需要判断返回报文中是否包含该字段
					if (StringUtil.isEmpty(value)) {
						Assert.assertTrue(resultJson.containsKey(key));
					} else {
						Assert.assertTrue(value.equals(resultJson.get(key)));
					}
					// System.out.println("String:" + key);
				} else if (responseJson.get(key) instanceof Integer) {
					System.out.println("Integer:" + key);
					String value = StringUtil.getString(responseJson.get(key));
					// 如果值是空的话，只需要判断返回报文中是否包含该字段
					if (StringUtil.isEmpty(value)) {
						Assert.assertTrue(resultJson.containsKey(key));
					} else {
						Assert.assertTrue(value.equals(StringUtil.getString(resultJson.get(key))));
					}
				} else {
					System.out.println("else:" + key);
				}

			}
		}
	}

	/**
	 * 可嵌套循环检查JSONObject结果
	 * 
	 * @param result
	 * @param responseStr
	 */
	public void assertArrayStrResult(JSONArray resultArray, JSONArray responseArray) {

		@SuppressWarnings("rawtypes")
		Iterator resultIter = resultArray.iterator();
		@SuppressWarnings("rawtypes")
		Iterator responseIter = responseArray.iterator();
		while (responseIter.hasNext()) {
			JSONObject innerJson = (JSONObject) responseIter.next();

			@SuppressWarnings("unchecked")
			Iterator<String> innerJsonIts = innerJson.keys();
			while (innerJsonIts.hasNext()) {
				String key = innerJsonIts.next();
				if (innerJson.get(key) instanceof String) {
					while (resultIter.hasNext()) {
						JSONObject resultInnerJson = (JSONObject) resultIter.next();
						if (innerJson.getString(key).equals(resultInnerJson.getString(key))) {
							assertJsonStrResult(resultInnerJson.toString(), innerJson.toString());
							break;
						}
					}

				} else if (innerJson.get(key) instanceof Integer) {
					System.out.println("Integer:" + key);
				}
			}

		}

	}

}
