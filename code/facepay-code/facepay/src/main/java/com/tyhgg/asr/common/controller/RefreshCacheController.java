package com.tyhgg.asr.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.util.HttpClientUtil;

/**
 * 刷新缓存
 * @类名称: RefreshCacheController
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月28日 下午4:26:40
 * @修改备注：
 */
@Controller
public class RefreshCacheController {

	/**
	 * 刷新单机缓存
	 * @param type 表名
	 * @param cred 简单的凭证：admin+20190226（当天日期）
	 * @return
	 */
	// http://127.0.0.1:8080/mini/unlogin/cache?cred=admin20190515&type=system_properties
	@RequestMapping(method = RequestMethod.GET, value = "/unlogin/cache")
	@ResponseBody
	public String refreshOneServerCacheByUrl(@RequestParam(value = "type", required = false) String cacheType,
			@RequestParam(value = "cred", required = false) String cred) {

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String dateStr = sdf.format(date);

		if (null == cred || !cred.equals("admin" + dateStr)) {
			return "刷新缓存失败[无效的用户凭证]";
		}
		
		switch (cacheType) {
			case "system_properties":
				SystemCacheHolder.getSystemPropertyCache().refreshCache();;
				break;
			case "b_msg":
				SystemCacheHolder.getMsgCache().refreshCache();;
				break;
			case "b_tran":
				SystemCacheHolder.getTranCache().refreshCache();;
				break;
			case "b_tran_userid_check":
				SystemCacheHolder.getTranUseridCheckCache().refreshCache();;
				break;
//			case "b_client_tran":
//				SystemCacheHolder.getClientTranCache().refreshCache();;
//				break;
			default:
				;
		}
		
		return "刷新" + cacheType + "缓存成功";
	}
	
	/**
	 * 刷新集群环境缓存，服务器所有地址需要在系统配置jboss.url中配置
	 * @param type 表名
	 * @param cred 简单的凭证：admin+20190226（当天日期）
	 * @return
	 */
	// http://22.188.14.76:8080/ecs/unlogin/refreshCache?cred=admin20190226&type=system_properties
	@RequestMapping(method = RequestMethod.GET, value = "/unlogin/refreshCache")
	@ResponseBody
	public String refreshAllServerCacheByUrl(@RequestParam(value = "type", required = false) String cacheType,
			@RequestParam(value = "cred", required = false) String cred) {

		Date date = new Date();
		String url = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String dateStr = sdf.format(date);

		if (null == cred || !cred.equals("admin" + dateStr)) {
			return "刷新缓存失败[无效的用户凭证]";
		}
		
		//获取jboss地址
		String ips = SystemCacheHolder.getSystemPropertyCache().getMaps().get("server.ip");
		String ip[] = ips.split(",");
		for (String oneIp : ip) {
			url = oneIp + "/ecs/unlogin/cache?cred=" + cred + "&type=" +cacheType;
			HttpClientUtil.httpGet(url);
		}
				
		return "刷新" + cacheType + "缓存成功";
	}

}
