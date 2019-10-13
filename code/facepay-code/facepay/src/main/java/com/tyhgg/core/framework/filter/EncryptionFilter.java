//package com.tyhgg.core.framework.filter;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Map;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.FileCopyUtils;
//
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.constants.SystemConstants;
//import com.tyhgg.core.framework.exception.ExceptionCode;
//import com.tyhgg.core.framework.exception.SystemException;
//import com.tyhgg.core.framework.util.AesCBC;
//import com.tyhgg.core.framework.util.StringUtil;
//import com.tyhgg.core.framework.wrapper.EncryptionHttpServletRequestWrapper;
//import com.tyhgg.core.framework.wrapper.EncryptionHttpServletResponseWrapper;
//
///**
// * @类名称: EncryptionFilter
// * @类描述: 涉及到加密、解密，报文头必须上送3个字段：uuid、clientid、header_key_type、
// * 			uuid:交易流水号，长度必须大于或等于18位,clientid请上送3位字符串
// * 			clientid：客户端标识，3位字符串
// * 			header_key_type=0：报文体加密标识，0或者不上送表示不加密，1表示是加密数据
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2017年7月2日 下午7:17:21
// * @修改备注：
// */
//public class EncryptionFilter extends AbstractSystemFilter {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionFilter.class);
//
//
//	@Override
//	public void destroy() {
//
//	}
//
//	@Override
//	protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
//			SystemException, ServletException {
//
//		LOGGER.info("----------------进入EncryptionFilter开始------------------------");
//
//		// 系统配置参数
//		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
//		// 获取交易url
//		final String url = StringUtil.getShortUri(request);
//		// 不需要端到端加密的交易url正则表达式
//		final String notFilterUrl = systemPropertyMap.get(SystemConstants.NOT_ENCRYPT_FILTER_URL);
//		// 不需要端到端加密的交易跳过此过滤器
//		if (url.matches(notFilterUrl)) {
//			chain.doFilter(request, response);
//			return;
//		}
//
////		HttpServletRequest request = (HttpServletRequest) req;
////		HttpServletResponse response = (HttpServletResponse) res;
//
//		// 设置返回数据
//		response.setHeader("Content-Type", "application/json;charset=UTF-8");
//		response.setHeader("Content-Language", "zh-CN");
//		response.setHeader("Cache-Control", "no-store");
//		response.setHeader("Pragma", "no-cache");
//
//		String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);
//		String uuid = request.getHeader(SystemConstants.HEADER_UUID);
//		if (null != uuid && uuid != "" && uuid.length() > 18) {
//			uuid = uuid.substring(5, 18);// 5-17,不包括17
//		}
//		// header_key_type=0或者不上送表示不加密，=1表示是加密数据，提醒后台需先解密
//		String keyType = request.getHeader(SystemConstants.HEADER_KEY_TYPE);
//		
//		if (StringUtil.isEmpty(keyType) || "0".equals(keyType)) {
//			chain.doFilter(request, response);
//		} else if (StringUtil.isEmpty(keyType) || uuid.length() < 19 || StringUtil.isEmpty(clientId) || clientId.length() != 3) {
//			LOGGER.error("加解密接收到的clientId=" + clientId + ",uuid=" + uuid);
//			throw new SystemException(ExceptionCode.EC_000021);
//		} else {
//			LOGGER.info("加解密接收到的clientId=" + clientId + ",uuid=" + uuid);
//			EncryptionHttpServletRequestWrapper wreq = null;
//			EncryptionHttpServletResponseWrapper wres = null;
//			try {
//				wreq = new EncryptionHttpServletRequestWrapper(request);
//				wres = new EncryptionHttpServletResponseWrapper(response);
//
//				String body = FileCopyUtils.copyToString(new InputStreamReader(wreq.getInputStream(), "UTF-8"));
//
//				byte[] keyDataBytes = AesCBC.decryptAES(body, AesCBC.createKey(clientId, uuid));
//				// LOGGER.info(Arrays.toString(keyDataBytes));
//
//				wreq.setBody(keyDataBytes);
//
//				chain.doFilter(wreq, wres);
//			} catch (SystemException e) {
//				throw e;
//			} catch (Exception e) {
//				LOGGER.error("处理请求异常：", e);
//				throw new SystemException(e, ExceptionCode.EC_000022, null, null);
//			} finally {
//				if (wres != null) {
//					byte[] byteStream = wres.getReponseOutputStream();
//					// 将报文输出到客户端
//					String rekeyDataBytes = AesCBC.encryptAES(byteStream, AesCBC.createKey(clientId, uuid));
//
//					response.setContentLength(rekeyDataBytes.getBytes().length);
//					/*
//					 * res.setHeader("header_key_type", keyType);
//					 * res.setHeader("uuid", uuidReturn);
//					 */
//					response.getOutputStream().write(rekeyDataBytes.getBytes());
//					response.getOutputStream().flush();
//				}
//
//			}
//
//		}
//
//		LOGGER.info("----------------进入EncryptionFilter结束------------------------");
//	}
//}
