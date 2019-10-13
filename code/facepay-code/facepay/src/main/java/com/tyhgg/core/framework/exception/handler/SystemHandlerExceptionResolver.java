
package com.tyhgg.core.framework.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.exception.SystemException;
import com.tyhgg.core.framework.util.ResponseUtils;

/**
 * 全局异常处理类
 * @类名称: SystemHandlerExceptionResolver
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午3:59:58
 * @修改备注：
 */
@ControllerAdvice
public class SystemHandlerExceptionResolver extends
		DefaultHandlerExceptionResolver {

	private static final Log LOGGER = LogFactory.getLog(SystemHandlerExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(ex.getMessage(), ex);
		}
		ModelAndView mav = super.doResolveException(request, response, handler,
				ex);// 先交由父类处理常见的servlet异常，比如401 402
		if (null == mav) {
			try {
				mav = this.handleException(request, response, ex);
			} catch (Exception e) {
				LOGGER.warn("Handling of [" + ex.getClass().getName()
						+ "] resulted in Exception", e);
			}
		}
		return mav;
	}

	@ExceptionHandler(Exception.class)
	private ModelAndView handleException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		
		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		PrintWriter write = null;
		try {
			String str = null;
			if(ex instanceof NullPointerException){
				// 空指针异常
				String msg = SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_FAIL);
				str = ResponseUtils.responseErrorJson(ExceptionCode.EC_FAIL, msg).toString();
			} else if(ex instanceof SystemException){
				String msg = SystemCacheHolder.getMsgCache().getMaps().get(((SystemException)ex).getCode());
				str = ResponseUtils.responseErrorJson(((SystemException)ex).getCode(), msg).toString();
			} else {
				String msg = SystemCacheHolder.getMsgCache().getMaps().get(ExceptionCode.EC_FAIL);
				str = ResponseUtils.responseErrorJson(ExceptionCode.EC_FAIL, msg).toString();
			}

			LOGGER.info("to APP:" + str);
			if(null == str){
				return null;
			}
			write = response.getWriter();
			write.write(str);
			write.flush();
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("", e);
			}
		} finally {
			// HttpServletResponse由j2ee容器自动关闭，HttpServletResponse不能在后台代码中直接关闭，关闭后前端不能正常获取数据
			// write.close();
		}
		
		return null;
	}
	
	private void writeExceptionResponse(HttpServletRequest request,
			HttpServletResponse response, String errorCode, String errorMsg) {

		PrintWriter write = null;
		try {
			String str = ResponseUtils.responseErrorJson(errorCode, errorMsg).toString();
			if(null == str){
				return;
			}
			write = response.getWriter();
			write.write(str);
			write.flush();
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("", e);
			}
		} finally {
			// write.close();
		}
	}
	

	
	/**
	 * 重写 父类方法<br/>
	 * 处理 500错误
	 */
	@Override
	protected ModelAndView handleConversionNotSupported(
			ConversionNotSupportedException ex, HttpServletRequest request,
			HttpServletResponse response, Object handlerr) throws IOException {
		this.writeExceptionResponse(request, response, 
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "",
				ex.getMessage());

		return new ModelAndView();
	}

	/**
	 * 处理405错误
	 */
	@Override
	protected ModelAndView handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpServletRequest request, HttpServletResponse response,
			Object handlerr) throws IOException {
		this.writeExceptionResponse(request, response, 
						HttpServletResponse.SC_METHOD_NOT_ALLOWED + "",
						ex.getMessage());
		return new ModelAndView();
	}


}
