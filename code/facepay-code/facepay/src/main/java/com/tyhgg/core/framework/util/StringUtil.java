package com.tyhgg.core.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.filter.WrapperHttpServletFilter;

public class StringUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperHttpServletFilter.class);
    
    private static List<Object[]> xssPatternList = new ArrayList<Object[]>();
    static{
    	xssPatternList.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
    	xssPatternList.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
    	xssPatternList.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
    	xssPatternList.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
    	xssPatternList.add(new Object[]{"<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
    	xssPatternList.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
    	xssPatternList.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|0nerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|0nmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|0nclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
    }

	/**
	 * 从request对象中获得交易路径（如：/rate/search）
	 * 
	 * @param request
	 * 
	 * @return shortUri
	 */
	public static String getShortUri(HttpServletRequest request) {
		String shortUri = request.getServletPath();
		int index = shortUri.indexOf(SystemConstants.XML_QUES);
		if (index > 0) {
			shortUri = shortUri.substring(0, index);
		}
		return shortUri;
	}

	/**
	 * 从request流中获取请求的json信息
	 * 
	 * @param request
	 *            Http请求
	 * @return json对象
	 * @throws IOException
	 */
	public static JSONObject getJsonObjFromRequest(HttpServletRequest request)
			throws IOException {
		try {
			StringBuffer sb = new StringBuffer();
			String line;
			BufferedReader reader = request.getReader();
			if (reader != null) {
				while (true) {
					line = reader.readLine();
					if (line != null) {
						sb.append(line);
					} else {
						break;
					}
				}
				reader.close();
			}
			return JSONObject.fromObject(sb.toString());
		} catch (Exception ex) {
			return null;
		}

	}
	
	/**将二进制转换成16进制  
	 * @param buf  
	 * @return  
	 */   
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			byte b = buf[i];
			String hex = Integer.toHexString(b & 255);
			if (1 == hex.length()) {
				hex = '0' + hex;
			}
			res.append(hex.toUpperCase());
//			if (0 == ((i + 4) % 16)) {
//				res.append(hex.toUpperCase()).append(
//						SystemConstants.XML_NEW_LINE);
//			} else if (buf.length == (i + 1)) {
//				res.append(hex.toUpperCase());
//			} else {
//				res.append(hex.toUpperCase()).append(
//						SystemConstants.XML_BIGSPACE);
//			}
		}
		return res.toString();
	} 

	/**将16进制转换为二进制  
	 * @param hexStr  
	 * @return  
	 */   
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	} 
	

	/**
	 * 如果参数为null，返回""，否则原样返回
	 * 
	 * @param str
	 * @return
	 */
	public static String getString(String str) {
		return str == null ? "" : str;
	}

	// 返回obj对象字符串
	public static String getString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * 左补0到length位数
	 * @param obj
	 * @param length
	 * @return
	 */
	public static String appendLeftZero(Object obj, int length) {
		String str = getString(obj);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < (length - str.length()); i++) {
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}

	// 去掉左边的0
	public static String leftZeroOff(String str) {
        if (str == null || "".equals(str.trim())) {
            return "";
        }
        String newStr = str.trim();
        int strLen = newStr.length();
        for(int i = 0; i < strLen; i++){
            if ("0".equals(String.valueOf(newStr.charAt(i)))) {
            	newStr = newStr.substring(1, strLen);
            } else {
            	break;
            }
            strLen = newStr.length();
            i--;
        }
		
		return newStr;
	}
	
	/**
	 * 字符串为空判断
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(String arg){
		if(null == arg || "".equals(arg.trim())){
			return true;
		}
		return false;
	}

	/**
	 * 字符串非为空判断
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg){
		if(null == arg || "".equals(arg.trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * 如果字符串中包含了回车、制表符和换行符，则替换成空字符
	 * @param arg
	 * @return
	 */
	public static String replaceSpecificStr(String arg){
		return getString(arg).replaceAll("[\\t\\n\\r]","");
	}
	
	/**
	 * 替换字符串中的{x}
	 * x可以是字符串或数字
	 * 替换是按照{}出现的顺序，与{}中的内容无关
	 * @param value
	 * @param args
	 * @return
	 */
	public static String fillingValue(String value, Object[] args) {
		String result = value;
		if(args == null || args.length == 0) {
			return result;
		}
		
		for (Object arg : args) {
			if(arg instanceof CharSequence) {
				result = result.replaceFirst("\\{[^\\{]*\\}", arg.toString());
			}
		}
		return result;
	}

	/**
	 * 判断请求参数是否是标准的JSON格式字符串
	 * @param jsonBody
	 * @return
     */
	public static Boolean isParsedJson(String jsonBody){
		JSONObject bodyJson = null;
		try {
			bodyJson = JSONObject.fromObject(jsonBody);
		} catch (Exception e) {
			return false;
		}
		if(bodyJson.isNullObject()||bodyJson.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * 用户判断接口请求参数是否完整
	 * @param jsonObject
	 * @param params
     * @return
     */
	public static Boolean isExistsSpecialedParams(JSONObject jsonObject,List<String> params){
		for(String param:params){
			if(!jsonObject.containsKey(param)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 计算字符串长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStrUtf8Length(String str) {
		if (null == str) {
			return 0;
		}
		int length = 0;
		try {
			length = str.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
		}
		return length;
	}
	
	/**
	 * 计算字符串长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str,String charSet) {
		if (null == str) {
			return 0;
		}
		int length = 0;
		try {
			length = str.getBytes(charSet).length;
		} catch (UnsupportedEncodingException e) {
		}
		return length;
	}

	/**
	 * 字符串截串，汉字算两个，其他一个
	 * 
	 * @param str
	 * @return
	 */
	public static String subString(String str, int length) {
		if (null == str || length < 1) {
			return "";
		}

		int actLength = getStrUtf8Length(str);

		if (actLength <= length) {
			return str;
		}
		StringBuilder sb = new StringBuilder(100);
		String chinese = "[\u4e00-\u9fa5]";
		actLength = 0;
		for (int i = 0; i < length && actLength < length; i++) {
			String temp = str.substring(i, i + 1);
			if (temp.matches(chinese)) {
				actLength += 2;
				if (actLength <= length) {
					sb.append(temp);
				}
			} else {
				actLength++;
				sb.append(temp);
			}
		}

		return sb.toString();
	}

	/**
	 * 手机号合法性检查
	 * @方法名: isMobilePhone
	 * @方法描述: 
	 * @param str
	 * @return
	 * @return boolean
	 */
	public static boolean isMobilePhone(String str) {
		if(isEmpty(str)){
			return true;
		}
		String regex = "[+]?[\\d]{1,20}";
		if(str.matches(regex) && str.length()<21){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 导出文件中文文件名处理方法
	 * 
	 * @param hexStr
	 * @return
	 */
	public static String toUft8String(String str) {
		StringBuilder sb = new StringBuilder(10);
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c >= 0 && c <= 255){
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					b = new byte[0];
				}
				for(int j = 0; j < b.length; j++){
					int k = b[j];
					if(k < 0){
						k+=256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	public static String getUUID(){
	    UUID uuid=UUID.randomUUID();
	    String str=uuid.toString();
	    //去掉-
	    String temp=str.replaceAll("-", "");
	    return temp;
	}
	 
	public static String getCellValue(HSSFCell cell) {
		if (null == cell) {
			return "";
		}
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellValue = df.format(cell.getNumericCellValue()).toString();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	public static String getCellValue(XSSFCell cell) {
		if (null == cell) {
			return "";
		}
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			cellValue = df.format(cell.getNumericCellValue()).toString();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}
	  
	/**获取日期格式化数据
	 * @param cell
	 * @return
	 */
	public static String getDateCellValue(XSSFCell cell) {  
	      String result = "";  
	      try {  
	          int cellType = cell.getCellType();  
	          if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {  
	              Date date = cell.getDateCellValue();
	              DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	              result= formater.format(date);
	          } else if (cellType == XSSFCell.CELL_TYPE_STRING) {  
	              String date = getCellValue(cell);  
	              result = date.replaceAll("[年月]", "-").replace("日", "").trim();  
	          } else if (cellType == XSSFCell.CELL_TYPE_BLANK) {  
	              result = "";  
	          }  
	      } catch (Exception e) {  
	          System.out.println("日期格式不正确!");  
	          e.printStackTrace();  
	      }  
	      return result;  
	  } 

	/**获取时间格式化数据
	 * @param cell
	 * @return
	 */
	public static String getDatetimeCellValue(XSSFCell cell) {  
	     String result = "";  
	     try {  
	         int cellType = cell.getCellType();  
	         if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {  
	             
	             if(HSSFDateUtil.isCellDateFormatted(cell)){
	               //用于转化为日期格式
	               Date d = cell.getDateCellValue();
	               DateFormat formater = new SimpleDateFormat("HH:mm");
	               result= formater.format(d);
	             }
	         } else if (cellType == XSSFCell.CELL_TYPE_STRING) {  
	             String date = getCellValue(cell);  
	             result = date.replaceAll("[年月]", "-").replace("日", "").trim();  
	         } else if (cellType == XSSFCell.CELL_TYPE_BLANK) {  
	             result = "";  
	         }  
	     } catch (Exception e) {  
	    	 LOGGER.error("", e);;  
	     }  
	     return result;  
	   }  

	// 6位时间因子
	public static String getSixLengthTime() {
		// 用毫秒取秒
		long timeMillis = System.currentTimeMillis() / 1000;
		// 864000为10天的秒数235959
		long millis = timeMillis % 864000;
		return appendLeftZero(String.valueOf(millis), 6);
	}

	/**
	 * 右补空格到length位数,不适用于有汉字的
	 * @param obj
	 * @param length
	 * @return
	 */
	public static String appendRightBlank(Object obj, int length) {
		String str = getString(obj);
		
		return StringUtils.rightPad(str, length, " ");
	}
	
	/**
	 * 右补空格到length位数,适用于UFT-8，包括汉字，length为字节数
	 * @param str
	 * @param length
	 * @return
	 */
	public static String appendRightBlankUft8(String str, int length) {
		try {
			byte[] btyes = str.getBytes("UTF-8");
			if(btyes.length < length){
				btyes = ByteUtil.rightPad(btyes, length);
			}
			str = new String(btyes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return str;
	}
	
	/**
	 * 左补空格到length位数,不适用于有汉字的
	 * @param obj
	 * @param length
	 * @return
	 */
	public static String appendLeftBlank(Object obj, int length) {
		String str = getString(obj);
		
		return StringUtils.leftPad(str, length, " ");
	}
	
	/**
	 * 不足指定位数左补0，多余的部分去掉左边的
	 * 
	 * @param param
	 *            被处理字符串
	 * @param length
	 *            长度
	 * @return 被处理后的字符串
	 */
	public static String lpadZero(String param, int length) {
		if (isEmpty(param)) {
			param = "";
		}
		param = param.trim();
		int paramLength = param.length();
		if (paramLength > length) {
			return param.substring(paramLength - length, paramLength);
		}
		StringBuilder sb = new StringBuilder(16);
		for (int i = paramLength; i < length; i++) {
			sb.append("0");
		}
		sb.append(param);
		return sb.toString();
	}

	/** 
	 * UNICODE编码的字段还原原始字符
	 * @param s 源str 
	 * @return  修改后的str 
	 */  
	public static String unicode2(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '\\' && chars[i + 1] == 'u') {
				char cc = 0;
				for (int j = 0; j < 4; j++) {
					char ch = Character.toLowerCase(chars[i + 2 + j]);
					if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
						cc |= (Character.digit(ch, 16) << (3 - j) * 4);
					} else {
						cc = 0;
						break;
					}
				}
				if (cc > 0) {
					i += 5;
					sb.append(cc);
					continue;
				}
			}
			sb.append(c);
		}
		return sb.toString();
	} 

	/**
	 * UNICODE编码的字段还原原始字符
	 * 
	 * @param str
	 * @return
	 */
	public static String unicode2Original(String str) {
		String ret = str;
		Pattern p = Pattern.compile("(\\\\u.{4})");
		Matcher m = p.matcher(ret);
		while (m.find()) {
			String xxx = m.group(0);
			ret = str.replaceAll("\\" + xxx, unicode2(xxx));
		}
		return ret;
	}

    private static List<Pattern> getPatterns() {
        List<Pattern> list = new ArrayList<Pattern>();
        String regex = null;
        Integer flag = null;
        int arrLength = 0;
        for(Object[] arr : xssPatternList) {
            arrLength = arr.length;
            for(int i = 0; i < arrLength; i++) {
                regex = (String)arr[0];
                flag = (Integer)arr[1];
                list.add(Pattern.compile(regex, flag));
            }
        }
        return list;
    }
    
    public static String stripXss(String value) {
        if(StringUtils.isNotBlank(value)) {
            Matcher matcher = null;
            for(Pattern pattern : getPatterns()) {
                matcher = pattern.matcher(value);
                // 匹配
                if(matcher.find()) {
                    // 删除相关字符串
                    value = matcher.replaceAll("");
                }
            }
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return value;
    }

	public static String getXssParameterValue(HttpServletRequest request,String name) {
		String value = request.getParameter(name);
        name = stripXss(name);
        value = stripXss(value);
        // 返回值之前 先进行过滤
        //value = stripXss(StringUtil.getXssParameterValue(request, name));
        return value;
    }

	/**
	 * 获取本机IP
	 */
	public static String getLocalIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			
			String localIp = ia.getHostAddress();
			
			return localIp;
		} catch (Exception e) {
			LOGGER.error("获取本地IP异常：", e);
		}
		return "";
	}
	
	/**
	 * 获取客户端的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(HttpServletRequest request) {
		String ipx = request.getHeader("x-forwarded-for");
		LOGGER.info("报文头记录的x-forwarded-for为:" + ipx);
		if (!StringUtil.isEmpty(ipx) && !"unkonown".equalsIgnoreCase(ipx)) {
			return ipx.split(",")[0];
		}
		String ipp = request.getHeader("Proxy-Client-IP");
		if (!StringUtil.isEmpty(ipp) && !"unkonown".equalsIgnoreCase(ipp)) {
			return ipp;
		}
		String ipw = request.getHeader("WL-Proxy-Client-IP");
		if (!StringUtil.isEmpty(ipw) && !"unkonown".equalsIgnoreCase(ipw)) {
			return ipw;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 生成6位验证码
	 * @return
	 */
	public static String getVerifyCode(){
		double rad = 0;
		while(rad < 0.01){
			rad = new Random().nextDouble();
		}
		
		String code = String.valueOf(rad);
		code= code.substring(2);
		code = StringUtil.appendLeftZero(code, 6);
		
		return code.substring(0, 6);
	}
	
	//空对象，用于线程安全处理
	private static Object obj = new Object();
	private static int SEQUENCE_COUNT = 0;

	public static String generateSequence(int length){
		int sequenceTemp = 0;
		StringBuffer sb = new StringBuffer();
		String currentTime = getString(System.currentTimeMillis());
		sb.append(currentTime.substring(currentTime.length()-length+4, currentTime.length()));
		synchronized (obj){
			if(SEQUENCE_COUNT > 9999){
				SEQUENCE_COUNT = 0;
			}
			sequenceTemp = sequenceTemp++;
		}
		sb.append(StringUtil.appendLeftZero(sequenceTemp, 4));
		return sb.toString();
	}

	public static String getSysFileUrl(String fileUrl, Map<String, String> systemPropertyMap) {
		String suffix = "";
		String newFileUrl = "";
		if (StringUtil.isNotEmpty(fileUrl) && fileUrl.indexOf(".") != -1) {
			suffix = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
			// 返回视频地址
			if ("MP4".equalsIgnoreCase(suffix)) {
				if(isNotEmpty(systemPropertyMap.get("mms.mp4.url"))){
					newFileUrl = systemPropertyMap.get("mms.mp4.url") + fileUrl;
				} else {
					newFileUrl = fileUrl;
				}
			} else if ("PDF".equalsIgnoreCase(suffix)) {// 返回文件地址
				newFileUrl = fileUrl;
			} else {// 返回图片地址
				if(isNotEmpty(systemPropertyMap.get("mms.image.url"))){
					newFileUrl = systemPropertyMap.get("mms.image.url") + fileUrl;
				} else {
					newFileUrl = fileUrl;
				}
			}
		}
		
		return newFileUrl;
	}

	/**
	 * 如果字符串中包含了回车、和换行符，则替换成空字符
	 * 去掉base64编码时出现的回车和换行符
	 * @param arg
	 * @return
	 */
	public static String replaceBaseStr(String arg) {
		return getString(arg).replaceAll("[\\n\\r]", "");
	} 

//	public static void main(String[] args) throws UnsupportedEncodingException{
//		String dd = generateSequence(16);
//		byte[] tys = dd.getBytes();
//		for(byte hh:tys){
//			System.out.println(hh);	
//		}
//		
//		System.out.println("01234567890".substring(4));
//		
//		String ss = "100002";
//		byte[] byyy = ss.getBytes("UTF-8");
//		
//		String hex = parseByte2HexStr(byyy);
//		System.out.println(hex);
//		byte[] byt = parseHexStr2Byte("313233717765");
//		System.out.println(new String(byt, "UTF-8"));
//		
//		
//	}
	
}
