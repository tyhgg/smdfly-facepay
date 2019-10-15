package com.tyhgg.core.framework.constants;

import java.nio.charset.Charset;

/**
 * 系统常量类
 * @类名称: SystemConstants
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午3:58:19
 * @修改备注：
 */
public final class SystemConstants {
	/** 用户session  */
	public final static String CURRENT_USER_SESSION = "CURRENT_USER_SESSION";

	/** 系统超级管理员角色ID  */
	public final static String ROLE_ADMINSTRATOR = "1";
	/** 系统普通管理员角色ID  */
	public final static String ROLE_ADMIN_NORMAL = "2";
	/** 普通角色  */
	public final static String ROLE_NORMAL = "9";

	/** 未签到  */
	public final static String PEOPLE_NOT_SIGN_IN = "1";
	/** 已签到  */
	public final static String PEOPLE_SIGN_IN = "2";

	/** 已登录  */
	public final static String PEOPLE_LOGINSTATUS_LOGIN = "1";
	/** 已退出  */
	public final static String PEOPLE_LOGINSTATUS_LOGOUT = "2";
	
	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static String CHARSET_GBK = "GBK";
    // 检查必须验证登录的url
	public final static String NOT_CHECK_LOGIN_URL = "not.check.session.url";
    // PC页面需要验登录
	public final static String PCWEB_NOT_CHECK_LOGIN_URL = "pcweb.not.check.session.url";
    // 不需要WrapperHttpServletFilter过滤器的url
	public final static String NOT_WRAPPER_FILTER_URL = "not.wrapper.filter.url";
    // 不需要EncryptionFilter过滤器的url
	public final static String NOT_ENCRYPT_FILTER_URL = "not.encrypt.filter.url";
	// 后台http系统配置的url
	public final static String SAP_ACCESS_URI = "access.uri";

	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	/** 部分后台交易成功返回码 */
	public final static String RTN_SUCCESS_CODE = "0000000";

	/** result 返回参数 */
	public final static String RESULT_PARAM = "result";
	
	public final static String REQUEST_PARAM_LOG = " 请求报文:";

	public final static String RESPONSE_PARAM_LOG = " 返回报文:";

	public final static String HEADER_AUTHORIZATION = "authorization";
	public final static String HEADER_USER_ID = "userId";
	public final static String HEADER_ROLE_ID = "roleId";

	public final static String HEADER_CLIENT_ID = "clientid";
	
	// 是否加密 header_key_type：0，不加密；1，AES加密；
	public final static String ENCRYPT_TYPE = "encryptType";

	public final static String HEADER_ACCESS_TOKEN = "acton";
	/** 日志记录用uuid */
	public final static String HEADER_UUID = "uuid";

	/** 渠道标识 */
	public final static String HEADER_CHANNEL_FLAG = "chnflg";

	/** 交易日期 */
	public final static String HEADER_TRANSACTION_DATE = "trantime";
	
	/** 默认密码 */
	public final static String PEOPLE_PASS_DEFAULT = "password01";
	/** 默认机构 */
	public final static String ORG_ID_DEFAULT = "wechat";
	/** 密码正则表达式  */
	public final static String PASS_REGEX = ".*(?!^\\d+$).{6,20}*";
	//  "(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[!@#$%^&*`~]+$).{8,20}";	
	/** mcis请求tcp_ip */
	public final static String SOCKET_MCIS_TCP_IP = "socket.mcis.tcp.ip";
	
	/** mcis请求tcp_port */
	public final static String SOCKET_MCIS_TCP_PORT = "socket.mcis.tcp.port";

	// 01-登录，02-找回密码，03-修改手机号，04-修改密码，05-注册，06-支付，07-转账
	public static final String MESSAGE_BUSI_TYPE_LOGIN = "01"; //发送登录验证码
	public static final String MESSAGE_BUSI_TYPE_FINDPASS = "02"; //发送找回密码验证码
	public static final String MESSAGE_BUSI_TYPE_UPDATEPEOPLETEL = "03"; //发送修改手机号验证码
	public static final String MESSAGE_BUSI_TYPE_UPDATEPASS = "04"; // 发送修改登录密码验证码
	public static final String MESSAGE_BUSI_TYPE_REGISTER = "05"; // 发送注册验证码
	
	// 日志级别
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;
	public static final int FATAL = 5;
	
//	// 日志数据源jndi
//	public static final String LOG_JNDI_DATASOURCE = "java:/comp/env/jdbc/ecsDS";

	public static final String FROM_STR = " FROM ";
	public static final String AND_STR = " AND ";
	public static final String EQUAL_QUES_STR = " = ? ";
	public static final String UNCHECKED = "unchecked";
	public static final char XML_QUES = '?';
	public static final String XML_STARTNODE_BEGIN = "<";
	public static final String XML_ENDNODE_BEGIN = "</";
	public static final String XML_NODE_END = ">";
	public static final String XML_DELIMITER = "<|>";
	public static final String XML_REGEX = "<\\|>";
	public static final String XML_BIGSPACE = " ";
	public static final String XML_QUOTE = "\"";
	public static final String XML_EQUAL = "=";
	public static final String XML_COMMA = ",";
	public static final String XML_NEW_LINE = "\n";
	
	public static final String WECHAT_PAY_SUCCESS = "SUCCESS";
	
}
