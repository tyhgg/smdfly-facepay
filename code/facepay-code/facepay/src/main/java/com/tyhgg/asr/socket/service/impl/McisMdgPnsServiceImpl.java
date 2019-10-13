package com.tyhgg.asr.socket.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tyhgg.core.framework.util.StringUtil;

/**
 * PNS通道短信平台
 * @类名称: McisMdgPnsServiceImpl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年8月27日 下午2:42:18
 * @修改备注：
 */
@Service
public class McisMdgPnsServiceImpl extends AbstractMcisMdgImpl{
	private static final Logger LOGGER = LoggerFactory.getLogger(McisMdgPnsServiceImpl.class);
	//空对象，用于线程安全处理
	private static Object OBJ_PNS = new Object();
	private static int SEQUENCE_COUNT_PNS = 0;

	public McisMdgPnsServiceImpl(){
		
	}
	
	public String processMcisBody(JSONObject jsonObject) throws Exception {
		StringBuilder sbHeader = new StringBuilder(100);
		sbHeader.append(StringUtils.rightPad("0", 4, " ")) //消息类型
		.append("0000000000") //机构代码
		.append("0000000") //终端代码
		.append("000000")  //柜员代码
		.append("BOCHQBGS0001") //业务代码，mcis给的固定值
		.append("0")
		.append("00000000000000")
		.append("0000").append("0000")
		.append(StringUtils.rightPad("", 20, " "))
		.append(StringUtils.rightPad("", 50, " "))
		.append("001")
		.append(StringUtils.rightPad(jsonObject.getString("mobile"), 20, " "));
		LOGGER.info("短信渠道报文头为:" + sbHeader.toString());
		
		String body = "";
		try {
			//body = addPackageLength(StringUtils.rightPad(jsonObject.getString("message"), 1000, " "));
			body = StringUtils.rightPad(jsonObject.getString("message"), 1000, " ");
			LOGGER.info("短信渠道报文体为:" + body);		
		} catch (Exception e) {
			LOGGER.error("组装MCIS报文体失败:", e);
			throw new Exception(e);
		}
		
		String mcisBody = addPackageLength(sbHeader.toString() + body);
		LOGGER.info("Mcis报文体为:" + mcisBody);
		return mcisBody;
	}
	/**
	 * MSG_TYPE:0－请求包 1－回复包请求包上送0
	 * TRAN_ASYNC:0－同步交易1－异步交易请求包上送，目前填写1.
	 * */
	
	public void processMcisResponse(){
		
	}

	/**
	 * 6位时间因子+4位序列数
	 * @return
	 */
	public static String generatePnsSequence(){
		
		StringBuffer sb = new StringBuffer(StringUtil.getSixLengthTime());
		int sequenceTemp = 0;
		synchronized (OBJ_PNS){
			if(SEQUENCE_COUNT_PNS > 9999){
				SEQUENCE_COUNT_PNS = 0;
			}
			sequenceTemp = sequenceTemp++;
		}
		sb.append(StringUtil.appendLeftZero(sequenceTemp, 4));
		return sb.toString();
	}
	
	
}
