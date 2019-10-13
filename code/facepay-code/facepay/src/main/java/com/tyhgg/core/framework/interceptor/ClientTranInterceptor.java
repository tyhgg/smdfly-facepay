//package com.tyhgg.core.framework.interceptor;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tyhgg.asr.system.mapper.ClientTranMapper;
//import com.tyhgg.core.framework.cache.SystemCacheHolder;
//import com.tyhgg.core.framework.constants.SystemConstants;
//import com.tyhgg.core.framework.exception.ExceptionCode;
//import com.tyhgg.core.framework.util.ResponseUtils;
//import com.tyhgg.core.framework.util.SpringUtil;
//import com.tyhgg.core.framework.util.StringUtil;
//
///**
// * 接口权限控制，client可以访问的接口
// * 
// * @类名称: ClientTranInterceptor
// * @类描述:
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2019年4月28日 下午4:13:20
// * @修改备注：
// */
//public class ClientTranInterceptor implements HandlerInterceptor {
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	private ClientTranMapper clientTranMapper;
//
//	{
//		if (clientTranMapper == null) {
//			clientTranMapper = SpringUtil.getBean(ClientTranMapper.class);
//		}
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		// 能匹配到的url没有权限，很多url没有配置就匹配不到，匹配不到的url有权限
//		if (checkClientTran(request)) {
//			ResponseUtils.responseWriterJson(response, ExceptionCode.EC_000023,
//					SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_000023));
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 检查clientId与url权限
//	 * */
//	private boolean checkClientTran(HttpServletRequest request) {
//		boolean checkFlag = false;
//		final String tranUrl = StringUtil.getShortUri(request);
//		String clientId = request.getHeader(SystemConstants.HEADER_CLIENT_ID);
//		String checkUrl = tranUrl + clientId;
//
//		Map<String, String> tranUrlClients = SystemCacheHolder.getClientTranCache().getMaps();
//		logger.info(" 接口clientid：" + clientId + " 接口url：" + tranUrl);
//		if (tranUrlClients != null) {
//			for (Map.Entry<String, String> entity : tranUrlClients.entrySet()) {
//				// b_tran表中配置的url有可能省略了manager等前缀，所以判断以表url结尾
//				if (checkUrl.endsWith(entity.getKey())) {
//					logger.info(clientId + " 接口没有权限：" + tranUrl);
//					checkFlag = true;
//					break;
//				}
//			}
//		}
//		return checkFlag;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//
//	}
//
//}
