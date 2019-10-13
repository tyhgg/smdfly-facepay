package com.tyhgg.asr.socket.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.util.StringUtil;

/**
 * MCIS定长报文，Socket短连接
 * @类名称: AbstractMcisImpl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年8月27日 下午2:43:34
 * @修改备注：
 */
public abstract class AbstractMcisImpl  {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMcisImpl.class);

	public static final String UTF8_PARAM = "GBK";
	public static final int MCIS_PACKAGE_LENGTH_SIZE = 5;
	public static final DateFormat DATEFORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public static final DateFormat DATEFORMAT_HHMMSS = new SimpleDateFormat("HHmmss");
	public static final String SYS_ID = "BG";
	public static final String MCIS_ID = "BG01";
	
	//空对象，用于线程安全处理
	private static Object obj = new Object();
	private static int SEQUENCE_COUNT = 0;

	public AbstractMcisImpl(){
		
	}
	
	public JSONObject process(JSONObject jsonObject)throws Exception {
		
		try {
			// 处理MCIS定长报文头
			String header  = this.processMcisHeader(jsonObject);
			// 处理MCIS报文体
			String body = this.processMcisBody(jsonObject);
			// 发送MCIS请求
			// 需要增加5位长度
			String message = addPackageLength(header + body);
			return this.processMcisRequest(message);
		} catch (Exception e) {
			LOGGER.error("MICS调用异常", e);
			throw new Exception(e);
		} 
		
	}

	public abstract String processMcisHeader(JSONObject jsonObject);
	
	public abstract String processMcisBody(JSONObject jsonObject) throws Exception;

	public abstract JSONObject processMcisRequest(String message) throws Exception;
	
	
	/**
	 * 生成8位Mcis流水号
	 * @方法名: generateMCISSequence
	 * @方法描述: 
	 * @param sysCode
	 * @return
	 * @return String
	 */
	public String generateMCISSequence(String sysCode){
		int sequenceTemp = 0;
		StringBuffer sb = new StringBuffer(sysCode);//系统编码+短信渠道代码
		String currentTime = StringUtil.getString(System.currentTimeMillis());
		sb.append(currentTime.substring(currentTime.length()-8,currentTime.length()));
		synchronized (obj){
			if(SEQUENCE_COUNT > 9999){
				SEQUENCE_COUNT = 0;
			}
			sequenceTemp = sequenceTemp++;
		}
		sb.append(StringUtil.appendLeftZero(sequenceTemp, 8));
		LOGGER.info("msgId长度"+sb.length()+",msgId:"+sb.toString());
		return sb.toString();
	}

    /**
     * 增加报文头长度
     * @param message
     * @return
     */
    public String addPackageLength(String message) {
		String messageLength = String.valueOf(StringUtil.getStrLength(message,"GBK"));
		messageLength = StringUtil.appendLeftZero(messageLength, MCIS_PACKAGE_LENGTH_SIZE);
		message = messageLength + message;
    	return message;
    }
	
}
