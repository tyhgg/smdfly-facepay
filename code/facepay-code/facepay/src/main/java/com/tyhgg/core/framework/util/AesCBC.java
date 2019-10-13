package com.tyhgg.core.framework.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @类名称: AesCBC
 * @类描述: AES加密CBC模式
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2017年7月2日 下午7:59:47
 * @修改备注：
 */
public class AesCBC {

	private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
	// 加密方式
	private static final String KEY_ALGORITHM = "AES";

	private static final Logger LOGGER = LoggerFactory.getLogger(AesCBC.class);

	private static String KEY_VALUE = "TR75O2E740UE58F9";

	// 固定秘钥长度为16位

	/**
	 * 生成AES密钥,密钥大于16位截取16位，小于16位末尾补0
	 * 
	 * @param 密钥种子
	 * @return SecretKeySpec AES密钥
	 */
	private static SecretKeySpec createSecretKey(String key) {
		byte[] data = null;
		if (null == key) {
			key = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		while (sb.length() < 16) {
			sb.append("0");
		}
		if (sb.length() > 16) {
			sb.setLength(16);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("AES密钥种子:" + sb.toString());
		}
		try {
			data = sb.toString().getBytes("utf-8");
		} catch (Exception ex) {
			LOGGER.error("生成报文头密钥错误！", ex);
			return null;
		}
		return new SecretKeySpec(data, KEY_ALGORITHM);
	}

	/**
	 * 生成AES加密器初始化向量，偏移量参数大于16位截取16位，小于16位末尾补0
	 * 
	 * @param password
	 *            偏移量
	 * @return IvParameterSpec AES加密器初始化向量
	 */
	private static IvParameterSpec createIVSpec(String iv) {
		byte[] data = null;
		if (iv == null) {
			iv = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(iv);
		while (sb.length() < 16) {
			sb.append("0");
		}
		if (sb.length() > 16) {
			sb.setLength(16);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("AES偏移量:" + sb.toString());
		}
		try {
			data = sb.toString().getBytes("utf-8");
		} catch (Exception ex) {
			LOGGER.error("生成密钥初始化向量错误！", ex);
			return null;
		}
		return new IvParameterSpec(data);
	}

	/**
	 * 将要加密的字节数组经过AES加密后，再由Base64编码成字符串返回
	 * 
	 * @param content
	 *            要加密内容
	 * @param iv
	 *            初始化向量偏移量
	 * @return String 加密后的字符串
	 */
	public static String encryptAES(byte[] content, String iv) {
		String result = null;
		try {
			SecretKeySpec secretKeySpec = createSecretKey(KEY_VALUE);
			Cipher cipher = Cipher.getInstance(CIPHER_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, createIVSpec(iv));
			byte[] bytes = cipher.doFinal(content);
			result = encodeBase64(bytes);
//			LOGGER.debug(result);
			return result;
		} catch (Exception ex) {
			LOGGER.error("AES加密错误！", ex);
		}
		return null;
	}

	/**
	 * 将Base64编码的字符串去掉回车换行后解码，进行AES解密
	 * 
	 * @param conent
	 *            要解密的Base64编码的字符串
	 * @param iv
	 *            初始化向量偏移量
	 * @return byte[] 解密后的字节数组
	 */
	public static byte[] decryptAES(String content, String iv) {
		byte[] data = null;
		try {
			content = StringUtil.replaceBaseStr(content);
			data = decodeBase64(content);
			SecretKeySpec secretKeySpec = createSecretKey(KEY_VALUE);
			Cipher cipher = Cipher.getInstance(CIPHER_MODE);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, createIVSpec(iv));
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception ex) {
			LOGGER.error("AES解密错误！", ex);
		}
		return null;
	}

	/**
	 * Base64编码
	 * 
	 * @param conent
	 *            要编码的字节数组
	 * @return String 编码后的字符串
	 */
	public static String encodeBase64(byte[] conent) {
		Base64 base64 = new Base64();
		byte[] bytes = base64.encode(conent);
		String s = null;
		try {
			s = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("不支持UTF-8字符集，使用系统默认字符集生成String", e);
			s = new String(bytes, Charset.defaultCharset());
		}
		return s;
	}

	/**
	 * Base64解码
	 * 
	 * @param conent
	 *            要解码的字符串
	 * @return byte[] 解码后的字节数组
	 */
	public static byte[] decodeBase64(String conent) {
		byte[] bytes = null;
		try {
			bytes = conent.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("不支持UTF-8字符集，使用系统默认字符集生成byte数组", e);
			bytes = conent.getBytes(Charset.defaultCharset());
		}
		Base64 base64 = new Base64();
		bytes = base64.decode(bytes);
		return bytes;
	}

	/**
	 * 用clientId和uuid拼接成明文偏移量然后进行MD5加密转换为16进制字符串偏移量
	 * 
	 * @param clientId
	 *            app应用clientKey
	 * @param uuid
	 *            app上送的uuid
	 * @return String MD5加密后的偏移量
	 */
	public static String createKey(String clientId, String uuid) {
		String orgKey = clientId + uuid;
		orgKey = SHA256Utils.genSHA256Str(orgKey);
		return orgKey;
	}

//	public static void main(String[] args) throws UnsupportedEncodingException {
//
//		String result = createKey("100", "damnfts238kclcbbeg3joj3undefineddzgrntc3".substring(5, 18));
//		System.out.println("MD5转16进制后的数据（偏移量）：" + result);
//		/* String data = "{\\"meetingId\": 600053}"; */
//		String data = "{}";
//		String encryptResult = encryptAES(data.getBytes("utf-8"),
//				createKey("111", "damnfts238kclcbbeg3joj3undefineddzgrntc3".substring(5, 18)));
//		System.out.println("加密后的结果是：" + encryptResult);
//		String clientid = "111", uuid = "damnfts238kclcbbeg3joj3undefineddzgrntc3".substring(5, 18);
//		System.out.println("解密后的数据：" + decryptAES(encryptResult, AesCBC.createKey(clientid, uuid)));
//		System.out.println("解密后的数据2：" + new String(decryptAES(encryptResult, AesCBC.createKey(clientid, uuid))));
//	}

}
