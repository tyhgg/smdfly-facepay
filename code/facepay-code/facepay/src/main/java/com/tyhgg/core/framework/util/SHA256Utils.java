package com.tyhgg.core.framework.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256Utils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHA256Utils.class);
	
	/**
	 * SHA256数字签名
	 * 
	 * @param string
	 * @return
	 */
	public static String encryptSHA256(String string) {
		return Base64.getEncoder().encodeToString(digestSHA256(string));
	}

	/**
	 * SHA-256散列
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] digestSHA256(String key) {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
			byte[] strByte;
			try {
				strByte = key.getBytes("UTF-8");
			} catch (UnsupportedEncodingException ex) {
				strByte = key.getBytes();
			}
			return messagedigest.digest(strByte);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 返回16进制字符串
	 * @方法名: genSHA256Str
	 * @方法描述: 
	 * @param iv 向量
	 * @return
	 * @return String
	 */
	public static String genSHA256Str(String key) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("SHA-256加密前的偏移量:" + key);
		}
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			LOGGER.error("生成SHA-256加密器失败！", e);
			return null;
		}
		byte[] byteArray = null;
		String res = null;
		try {
			byteArray = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("不支持UTF-8字符集，使用系统默认字符集生成byte数组", e);
			byteArray = key.getBytes(Charset.defaultCharset());
		}
		if (null != byteArray) {
			messagedigest.update(byteArray);
			res = HexConver.BinToHex(messagedigest.digest());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("SHA-256加密后的16进位偏移量:"+res);
			}
		}
		return res;
	}
	
}
