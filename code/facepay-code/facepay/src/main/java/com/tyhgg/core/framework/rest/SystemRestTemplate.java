//package com.tyhgg.core.framework.rest;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.web.client.RequestCallback;
//import org.springframework.web.client.ResponseExtractor;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriUtils;
//
///**
// * 覆盖父类的方法 重新拼接字符串
// * 
// * @创建人:zyt5668
// * @修改时间:
// * @修改内容:
// */
//public class SystemRestTemplate extends RestTemplate {
//	private final Logger logger = LoggerFactory.getLogger(SystemRestTemplate.class);
//	
//	public SystemRestTemplate() {
//		super();
//	}
//	
//	public SystemRestTemplate(ClientHttpRequestFactory requestFactory) {
//		super(requestFactory);
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public <T> T execute(String url, HttpMethod method,
//			RequestCallback requestCallback,
//			ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) {
//		URI expanded = null;
//		try {
//			String encoded = UriUtils.encodeHttpUrl(url, "UTF-8");
//			String expandedStr = this.expandURI(encoded, urlVariables);
//			expanded = new URI(expandedStr);
//			return this.doExecute(expanded, method, requestCallback,
//					responseExtractor);
//		} catch (UnsupportedEncodingException e) {
//			logger.error(e.getMessage(), e);
//		} catch (URISyntaxException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//
//	}
//
//	/**
//	 * 拼接url字符串
//	 * 
//	 * @param encoded
//	 *            原始url字符串
//	 * @param urlVariables
//	 *            url变量
//	 */
//	private String expandURI(String encoded, Map<String, ?> urlVariables) {
//		StringBuffer sb = new StringBuffer(encoded);
//		if ((null != urlVariables) && !urlVariables.isEmpty()) {
//			if (!encoded.contains("?")) {
//				sb.append("?");
//			} else {
//				sb.append("&");
//			}
//			for (Entry<String, ?> entry : urlVariables.entrySet()) {
//				sb.append(entry.getKey()).append("=").append(entry.getValue())
//						.append("&");
//			}
//		}
//		return sb.toString();
//	}
//}
