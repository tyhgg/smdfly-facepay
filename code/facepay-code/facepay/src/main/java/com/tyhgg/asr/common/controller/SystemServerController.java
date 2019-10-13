package com.tyhgg.asr.common.controller;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.common.mapper.SystemPropertiesMapper;
import com.tyhgg.core.framework.util.DateUtil;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 系统通用接口
 * @类名称: SystemServerController
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月28日 下午3:39:57
 * @修改备注：
 */
@Controller
public class SystemServerController {
	
	@Resource
    private SystemPropertiesMapper systemPropertiesMapper;
	
	/**
	 * 查询系统日期并返回
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unlogin/querysysdate",method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String querySysDate(HttpServletRequest request){
	    String date = DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss");
	    JSONObject resJson=new JSONObject();
	    resJson.put("sysdate", date);
	    return resJson.toString();
	}
	
	/**
	 * 查询系统url
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unlogin/querySysUrl",method = {RequestMethod.POST})
	@ResponseBody
	public String querySysUrl(@RequestBody String body){

		JSONObject bodyJson =  JSONObject.fromObject(body);

    	JSONObject resultJson =  new JSONObject();
		String type = StringUtil.getString(bodyJson.get("type"));
		if("system.url".equals(type)){
			// 日常会议参会人员名单是否可见
			HashMap<String, String> map = this.systemPropertiesMapper.getSystemProperties("system.url");
			resultJson.put("result", StringUtil.getString(map.get("sysValue")));
		}
		
        return resultJson.toString();
	}
}
