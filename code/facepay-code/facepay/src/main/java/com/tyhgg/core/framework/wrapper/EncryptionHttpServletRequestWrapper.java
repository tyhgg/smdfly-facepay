//package com.tyhgg.core.framework.wrapper;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.constants.SystemConstants;
//import com.tyhgg.core.framework.exception.ExceptionCode;
//import com.tyhgg.core.framework.exception.SystemException;
//
///*import com.tyhgg.asr.framework.cache.SystemCacheHolder;
//import com.tyhgg.asr.framework.exception.ExceptionCode;
//import com.tyhgg.asr.framework.exception.SystemException;
//import com.tyhgg.asr.framework.util.SystemConstants;*/
//
///**
// * 
// * @author zyt5668
// *
// */
//public class EncryptionHttpServletRequestWrapper extends HttpServletRequestWrapper {
//	
//	private byte[] body;
//	
//
//	/**
//	 * @param request
//	 * @throws IOException
//	 * @throws ServletException 
//	 * @throws OutofMaxDatagramException 
//	 */
//	public EncryptionHttpServletRequestWrapper(HttpServletRequest request)
//			throws IOException, ServletException, SystemException {
//		super(request);
//		
//		// 系统配置参数
//		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
//		
//		int maxsize=Integer.valueOf(systemPropertyMap.get("datagram.maxsize"));
//		
//		int conentLength = request.getContentLength();
//		byte[] buffer;
////		String shortUri = ProjectStringUtil.getShortUri(request);
//		
//		//判断上送报文长度是否大于最大值
//		if(conentLength <= maxsize*1000){//KB * 1000
//			buffer = this.getBytes(request);
//		} else {
//			throw new SystemException(ExceptionCode.EC_000012);
//		}
//		
//		this.body = buffer;
//	}
//	
//	/**
//	 * 读取请求参数
//	 * @param request
//	 * @return
//	 * @throws IOException
//	 */
//	private byte[] getBytes(HttpServletRequest request) throws IOException{
//		byte[] buffer = new byte[((request.getContentLength() < 0) ? 0
//				: request.getContentLength())];
//		ServletInputStream input =  request.getInputStream();
//		int c;
//		int i=0;
//		while( (c=input.read()) != -1)
//		{
//			buffer[i] = (byte)c;
//			i++;
//		}
//		return buffer;
//	}
//
//	
//	
//	
//	@Override
//	public String getContentType() {
//		
//		return "application/json;charset=UTF-8";
//	}
//
//	@Override
//	public ServletInputStream getInputStream() throws IOException {
//		final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
//		return new ServletInputStream() {
//			@Override
//			public int read() throws IOException {
//				return bais.read();
//			}
//		};
//	}
//
//	@Override
//	public BufferedReader getReader() throws IOException {
//		return new BufferedReader(new InputStreamReader(this.getInputStream(),
//				SystemConstants.DEFAULT_CHARSET));
//	}
//
//	@Override
//	public String getHeader(String name) {
//		if ("Accept".equals(name)) {
//			return this.getContentType();
//		}
//		return super.getHeader(name);
//	}
//	
//	public byte[] getBody() {
//		return body;
//	}
//
//	public void setBody(byte[] body) {
//		this.body = body;
//	}
//}
