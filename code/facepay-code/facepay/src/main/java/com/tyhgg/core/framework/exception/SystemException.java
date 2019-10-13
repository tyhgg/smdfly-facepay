package com.tyhgg.core.framework.exception;

import com.tyhgg.core.framework.util.StringUtil;

/**
 * 自定义异常类
 * @类名称: SystemException
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午4:00:28
 * @修改备注：
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1381325479896057076L;

	private String code;

	private String value;

	public SystemException(Throwable cause, String code, String message, Object[] args) {
		
		super(StringUtil.fillingValue(message, args), cause);
		this.code = code;
		this.value = StringUtil.fillingValue(message, args);
	}

	public SystemException(String code) {
		
		super(code);
		this.code = code;
		this.value = value == null ? null : value;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value == null ? null : value;
	}

	public void setValue(String value) {
		this.value = value == null ? null : value;
	}

}
