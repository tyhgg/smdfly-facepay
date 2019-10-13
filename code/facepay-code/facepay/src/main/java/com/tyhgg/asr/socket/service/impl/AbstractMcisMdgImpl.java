package com.tyhgg.asr.socket.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.asr.socket.service.SocketService;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.util.StringUtil;

/**
 * 
 * @类名称: AbstractMcisMdgImpl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年8月27日 下午3:00:11
 * @修改备注：
 */
public abstract class AbstractMcisMdgImpl extends AbstractMcisImpl implements SocketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMcisMdgImpl.class);

	
	public AbstractMcisMdgImpl(){
		
	}
	
	public String processMcisHeader(JSONObject jsonObject){
		// 系统配置参数
 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
 		String mcisSysId = systemPropertyMap.get("BG");
 		
		Date date = new Date();
		StringBuilder sb = new StringBuilder(100);
		String msgId = StringUtils.rightPad(generateMCISSequence(mcisSysId+"01"), 32);
		// MCIS交易码
		String mcisTranCode = jsonObject.getString("mcisTranCode");
		sb.append("0") //交易类型
			.append("1")	//异步标识
			.append(StringUtils.rightPad(StringUtil.getString(mcisTranCode), 10, " ")) //交易码
			.append("01") //推送渠道
			.append(StringUtils.rightPad("0000000000", 20, " ")) //模板编号 固定0
			.append(mcisSysId) //外围系统标识
			.append("05")
			.append("N")
			.append("00").append("00")
			.append("00000").append(DATEFORMAT_YYYYMMDD.format(date)).append(DATEFORMAT_HHMMSS.format(date))
			.append(msgId)//消息id
			.append(StringUtils.rightPad(StringUtil.getUUID(), 32))
			.append(StringUtils.rightPad("", 7, " "))
			.append(StringUtils.rightPad("", 5, " "))
			.append(StringUtils.rightPad("", 100, " ")); //备用域
		LOGGER.info("MCIS报文头为：" + sb.toString());
		LOGGER.info("MCIS报文头  消息id ：" + msgId);
		// 报文体长度
		return sb.toString();
	}
	
	/**
	 * 发送socket短连接请求
	 */
	public JSONObject processMcisRequest(String message) throws Exception {
		
		JSONObject resultJSONObject = new JSONObject();
        //发送TCP客户端请求   
    	Socket socket = null;
    	// 无返回报文
		OutputStream outputStream = null;
		try {
			// 系统配置参数
	 		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
	 		String tcpIp = systemPropertyMap.get(SystemConstants.SOCKET_MCIS_TCP_IP);
	 		String tcpPort = systemPropertyMap.get(SystemConstants.SOCKET_MCIS_TCP_PORT);
			socket = new Socket(tcpIp, Integer.parseInt(tcpPort));
			socket.setSoTimeout(30000);
			outputStream = socket.getOutputStream();
			
			LOGGER.info("MCIS发送报文为：" + message);
			outputStream.write(message.getBytes("GBK"));
			outputStream.flush();
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String reply = null;
			if(is==null){
				LOGGER.info("==返回信息为空====");
				resultJSONObject.put("result", "0");
			}
			while((reply=br.readLine())!=null){
				LOGGER.info("==响应信息===="+reply);
			}
			LOGGER.info("--------------------------------发送报文结束----------------------------------");
			// 只有发送，没有回复
		} catch (Exception e) {
			LOGGER.error("系统出错", e);
			return resultJSONObject;
		} finally {
			try {
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				LOGGER.error("系统出错", e);
			}
		}
		LOGGER.info("通讯结束 ");
		resultJSONObject.put("result", "1");
		return resultJSONObject;
    }
}
