package com.tyhgg.core.framework.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 数据库日志类
 * @类名称: SystemLog
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午3:57:26
 * @修改备注：
 */
public class SystemLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MAX_LENGTH = 1000;

	private static final int FIVE_HUNDRED = 500;

	public static final String SEPATATE_CHAR = "|";

	public static final String NEW_LINE = "[\\r\\n]";
	
	private int id;
    private Date sysDate;
    private String uuid;
    private String userId;
    private String clientId;
    private Timestamp beginTime;
    private Timestamp endTime;
    private int duration;
    private String serviceIp;
    private String clientIp;
    private String tranUrl;
    private String tranCode;
    private String tranName;
    private Timestamp backsysBeginTime;
    private Timestamp backsysEndTime;
    private int backsysDuration;
    private String errorCode;
    private String errorMessage;
    private String httpHeader;
    private String httpBody;
    private String respMessage;
    private int logNum;
    
    private String systemDate;
    private String startTime;
    private String finallyTime;

    private String backSystemStartTime;
    private String backSystemFinallyTime;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBackSystemStartTime() {
		return backSystemStartTime;
	}

	public void setBackSystemStartTime(String backSystemStartTime) {
		this.backSystemStartTime = backSystemStartTime;
	}

	public String getBackSystemFinallyTime() {
		return backSystemFinallyTime;
	}

	public void setBackSystemFinallyTime(String backSystemFinallyTime) {
		this.backSystemFinallyTime = backSystemFinallyTime;
	}

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinallyTime() {
		return finallyTime;
	}

	public void setFinallyTime(String finallyTime) {
		this.finallyTime = finallyTime;
	}

	public int getLogNum() {
		return logNum;
	}

	public void setLogNum(int logNum) {
		this.logNum = logNum;
	}

	public SystemLog() {
		super();
	}

	
	

	@Override
	public String toString() {
		return "SystemLog [sysDate=" + sysDate + ", uuid=" + uuid + ", userId="
				+ userId + ", clientId=" + clientId + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", duration=" + duration
				+ ", serviceIp=" + serviceIp + ", clientIp=" + clientIp
				+ ", tranUrl=" + tranUrl + ", tranCode=" + tranCode
				+ ", tranName=" + tranName + ", backsysBeginTime="
				+ backsysBeginTime + ", backsysEndTime=" + backsysEndTime
				+ ", backsysDuration=" + backsysDuration + ", errorCode="
				+ errorCode + ", errorMessage=" + errorMessage
				+ ", httpHeader=" + httpHeader + ", httpBody=" + httpBody
				+ ", respMessage=" + respMessage + ", logNum=" + logNum
				+ ", systemDate=" + systemDate + ", startTime=" + startTime
				+ ", finallyTime=" + finallyTime + "]";
	}

	/**
	 * @return the sysDate
	 */
	public Date getSysDate() {
		if (sysDate != null) {
			return (Date) sysDate.clone();
		}
		return null;
	}

	/**
	 * @param sysDate
	 *            the sysDate to set
	 */
	public void setSysDate(Date sysDate) {
		if (sysDate != null) {
			this.sysDate = (Date) sysDate.clone();
		} else {
			this.sysDate = null;
		}
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the tranUrl
	 */
	public String getTranUrl() {
		if (tranUrl != null && tranUrl.length() > 255) {
			return tranUrl.substring(0, 255);
		} else {
			return tranUrl;
		}
	}

	/**
	 * @param tranUrl
	 *            the tranUrl to set
	 */
	public void setTranUrl(String tranUrl) {
		this.tranUrl = tranUrl;
	}

	/**
	 * @return the beginTime
	 */
	public Timestamp getBeginTime() {
		if (beginTime != null) {
			return (Timestamp) beginTime.clone();
		}
		return null;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(Timestamp beginTime) {
		if (beginTime != null) {
			this.beginTime = (Timestamp) beginTime.clone();
		} else {
			this.beginTime = null;
		}
	}

	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime() {
		if (endTime != null) {
			return (Timestamp) endTime.clone();
		}
		return null;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Timestamp endTime) {
		if (endTime != null) {
			this.endTime = (Timestamp) endTime.clone();
		} else {
			this.endTime = null;
		}
	}

	/**
	 * @return the httpBody
	 */
	public String getHttpBody() {

		if (httpBody != null && httpBody.length() > MAX_LENGTH) {
			return httpBody.substring(0, MAX_LENGTH);
		} else {
			return httpBody;
		}

	}

	/**
	 * @param httpBody
	 *            the httpBody to set
	 */
	public void setHttpBody(String httpBody) {
		if (httpBody != null) {
			this.httpBody = httpBody.replaceAll(NEW_LINE, " ");
		} else {
			this.httpBody = null;
		}
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		if (uuid != null && uuid.length() > 35) {
			return uuid.substring(0, 35);
		} else {
			return uuid;
		}
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the serviceIp
	 */
	public String getServiceIp() {
		return serviceIp;
	}

	/**
	 * @param serviceIp
	 *            the serviceIp to set
	 */
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		if (errorCode != null && errorCode.length() > 32) {
			return errorCode.substring(0, 32);
		} else {
			return errorCode;
		}
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		if (errorMessage != null && errorMessage.length() > FIVE_HUNDRED) {
			return errorMessage.substring(0, FIVE_HUNDRED);
		} else {
			return errorMessage;
		}
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		if (errorMessage != null) {
			this.errorMessage = errorMessage.replaceAll(NEW_LINE, " ");
		} else {
			this.errorMessage = null;
		}
	}

	/**
	 * @return the respMessage
	 */
	public String getRespMessage() {
		if (respMessage != null && respMessage.length() > MAX_LENGTH) {
			return respMessage.substring(0, MAX_LENGTH);
		} else {
			return respMessage;
		}
	}

	/**
	 * @param respMessage
	 *            the respMessage to set
	 */
	public void setRespMessage(String respMessage) {
		if (respMessage != null) {
			this.respMessage = respMessage.replaceAll(NEW_LINE, " ");
		} else {
			this.respMessage = null;
		}
	}

	/**
	 * @return the httpHeader
	 */
	public String getHttpHeader() {
		if (httpHeader != null && httpHeader.length() > FIVE_HUNDRED) {
			return httpHeader.substring(0, FIVE_HUNDRED);
		} else {
			return httpHeader;
		}
	}

	/**
	 * @param httpHeader
	 *            the httpHeader to set
	 */
	public void setHttpHeader(String httpHeader) {
		if (httpHeader != null) {
			this.httpHeader = httpHeader.replaceAll(NEW_LINE, " ");
		} else {
			this.httpHeader = null;
		}

	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranName() {
		return tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public Timestamp getBacksysBeginTime() {
		if (backsysBeginTime != null) {
			return (Timestamp) backsysBeginTime.clone();
		}
		return null;
	}

	public void setBacksysBeginTime(Timestamp backsysBeginTime) {
		if (backsysBeginTime != null) {
			this.backsysBeginTime = (Timestamp) backsysBeginTime.clone();
		} else {
			this.backsysBeginTime = null;
		}
	}

	public Timestamp getBacksysEndTime() {
		if (backsysEndTime != null) {
			return (Timestamp) backsysEndTime.clone();
		}
		return null;
	}

	public void setBacksysEndTime(Timestamp backsysEndTime) {
		if (backsysEndTime != null) {
			this.backsysEndTime = (Timestamp) backsysEndTime.clone();
		} else {
			this.backsysEndTime = null;
		}
	}

	public int getBacksysDuration() {
		return backsysDuration;
	}

	public void setBacksysDuration(int backsysDuration) {
		this.backsysDuration = backsysDuration;
	}
	
}
