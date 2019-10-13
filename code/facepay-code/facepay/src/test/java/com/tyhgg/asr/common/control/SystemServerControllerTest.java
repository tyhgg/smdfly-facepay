package com.tyhgg.asr.common.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.tyhgg.MiniBootApplication;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:application.properties"})

@SpringBootTest(classes = MiniBootApplication.class)// 指定spring-boot的启动类   
//@SpringApplicationConfiguration(classes = MiniBootApplication.class)// 1.4.0 前版本  注意启动类不要搞错了

//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional

@WebAppConfiguration
public class SystemServerControllerTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SystemServerControllerTest.class);
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }


    @Test
    public void testQuerySysDate() throws Exception {
        String responseString = mockMvc.perform(
        		MockMvcRequestBuilders.get("/unlogin/querysysdate")    //请求的url,请求的方法是get
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                        .param("pcode","root")         //添加参数
        ).andExpect(MockMvcResultMatchers.status().isOk())    //返回的状态是200
                .andDo(MockMvcResultHandlers.print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);
    }
    
    @Test
    public void testQuerySysUrl() throws Exception {
    	JSONObject requestJson = new JSONObject();
    	requestJson.put("type", "system.url");
    	//。。。设置值
    	String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/unlogin/querySysUrl").contentType(MediaType.APPLICATION_JSON)
    			.content(requestJson.toString())).andDo(MockMvcResultHandlers.print())
    	        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
    	System.out.println("--------返回的json = " + responseString);
    }

}
