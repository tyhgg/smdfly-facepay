package com.tyhgg.core.framework.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * 返回码统一处理
 * @类名称: ResponseUtils
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年7月11日 下午4:06:44
 * @修改备注：
 */
public class ResponseUtils {
    public static final String ERRCODE = "errcode";
    public static final String ERRMSG = "errmsg";
	/** 交易成功返回码 */
	public final static String RTN_SUCCESS_CODE = "0";
	/** 后台管理端统一错误码 */
	public final static String RTN_ERROR_WEB = "ERR_WEB";
	/** 后台管理端返回统一成功提示信息 */
	public final static String RTN_SUCCESS_WEB = "操作成功！";
    
	public static void responseWriterJson(HttpServletResponse response,
			String statusCode, String msg) {
		PrintWriter write = null;
		try {
			write = response.getWriter();
			write.write(responseErrorJson(statusCode, msg).toString());
			write.flush();
		} catch (Exception e) {
			
		} finally {
			// 不能关闭write
		}
	}

	public static JSONObject responseSuccessJson(JSONObject resultJson) {
		if(null == resultJson){
			resultJson = new JSONObject();
		}
		
		resultJson.put(ERRCODE, RTN_SUCCESS_CODE);
		resultJson.put(ERRMSG, RTN_SUCCESS_WEB);
		
		return resultJson;
	}
	
	public static JSONObject responseErrorJson(String statusCode, String msg) {
		JSONObject resultJson = new JSONObject();
		
		resultJson.put(ERRCODE, statusCode);
		resultJson.put(ERRMSG, msg);
		
		return resultJson;
		
	}

	public static String responseErrorStr(String statusCode, String msg) {
		JSONObject resultJson = new JSONObject();
		
		resultJson.put(ERRCODE, statusCode);
		resultJson.put(ERRMSG, msg);
		
		return resultJson.toString();
		
	}
	/*
	 * WEB端错误返回信息,后台管理端不需要太精细
	 */
	public static JSONObject responseWebErrorJson(String msg) {
		JSONObject resultJson = new JSONObject();
		
		resultJson.put(ERRCODE, RTN_ERROR_WEB);
		resultJson.put(ERRMSG, msg);
		
		return resultJson;
		
	}

	public static JSONObject responseWebErrorJson(JSONObject resultJson, String errmsg) {
		if(null == resultJson){
			resultJson = new JSONObject();
		}
		
		resultJson.put(ERRCODE, RTN_ERROR_WEB);
		resultJson.put(ERRMSG, errmsg);
		
		return resultJson;
	}
	
	/*
	 * WEB端错误返回信息,后台管理端不需要太精细
	 */
	public static JSONObject responseWebSuccessJson() {
		JSONObject resultJson = new JSONObject();
		
		resultJson.put(ERRCODE, RTN_SUCCESS_CODE);
		resultJson.put(ERRMSG, RTN_SUCCESS_WEB);
		
		return resultJson;
		
	}
	

//	public static void main(String[] args) throws UnsupportedEncodingException{
//		String dd = generateSequence(16);
//		byte[] tys = dd.getBytes();
//		for(byte hh:tys){
//			System.out.println(hh);	
//		}
//		
//		System.out.println("01234567890".substring(4));
//		
//		String ss = "100002";
//		byte[] byyy = ss.getBytes("UTF-8");
//		
//		String hex = parseByte2HexStr(byyy);
//		System.out.println(hex);
//		byte[] byt = parseHexStr2Byte("313233717765");
//		System.out.println(new String(byt, "UTF-8"));
//		
//		
//	}
	
}
