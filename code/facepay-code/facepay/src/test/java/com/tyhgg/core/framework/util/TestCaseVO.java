package com.tyhgg.core.framework.util;

/**
 * 
 * @类名称: TestCaseVO
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年2月7日 上午8:50:55
 * @修改备注：
 */
public class TestCaseVO {
	private String caseId;
	private String url;
	private String headers;
	private String method;
	private String parameter;
	// request报文体
	private String request;
	// 返回结果
	private String response;
	private String remark1;
	private String remark2;
	private String remark3;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	@Override
	public String toString() {
		return "TestCaseVO [caseId=" + caseId + ", url=" + url + ", headers=" + headers + ", method=" + method + ", parameter=" + parameter + ", request=" + request + ", response=" + response
				+ ", remark1=" + remark1 + ", remark2=" + remark2 + ", remark3=" + remark3 + "]";
	}

}
