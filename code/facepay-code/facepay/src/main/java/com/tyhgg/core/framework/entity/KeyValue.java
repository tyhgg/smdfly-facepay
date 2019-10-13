package com.tyhgg.core.framework.entity;

import java.io.Serializable;

/**
 * 数据字典
 * @类名称: KeyValue
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午3:56:07
 * @修改备注：
 */
public class KeyValue implements Serializable {
	
	private static final long serialVersionUID = -3092385889488910067L;
	private String keyValue;
	private String keyDes;
	
	public KeyValue(String keyValue, String keyDes){
		this.keyValue = keyValue;
		this.keyDes = keyDes;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getKeyDes() {
		return keyDes;
	}

	public void setKeyDes(String keyDes) {
		this.keyDes = keyDes;
	}

	@Override
	public String toString() {
		return "KeyValue [keyValue=" + keyValue + ", keyDes=" + keyDes + "]";
	}
	
}
