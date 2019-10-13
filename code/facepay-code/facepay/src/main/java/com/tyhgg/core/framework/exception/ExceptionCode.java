package com.tyhgg.core.framework.exception;

/**
 * 错误码
 * @类名称: ExceptionCode
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2018年7月13日 下午4:00:18
 * @修改备注：
 */
public final class ExceptionCode {

	/** 成功 */
	public final static String EC_SUCCESS = "EC-000000";
	/** 后台系统异常-本后台系统异常 */
	public final static String EC_FAIL = "EC-999999";
	/** 操作失败 */
	public final static String EC_000001 = "EC-000001";
	/** UUID未上送 */
	public final static String EC_000002 = "EC-000002";
	/** 登录失败，请检查用户名或密码 */
	public final static String EC_000003 = "EC-000003";
	/** 用户上送的报文头用户名和报文体用户名信息不匹配 */
	public final static String EC_000004 = "EC-000004";
	/** 请先登录 */
	public final static String EC_000005 = "EC-000005";
	/** 登录失效，请重新登录 */
	public final static String EC_000006 = "EC-000006";
	/** 请求参数错误！ */
	public final static String EC_000007 = "EC-000007";
	/** 注销失败 */
	public final static String EC_000008 = "EC-000008";
	/** 报文头userId不能为空 */
	public final static String EC_000009 = "EC-000009";
	/** 返回结果错误 */
	public final static String EC_000010 = "EC-000010";
	/** 请求数据不存在 */
	public final static String EC_000011 = "EC-000011";
	/** 报文长度超过限制 */
	public final static String EC_000012 = "EC-000012";
	/** 通讯超时 */
	public final static String EC_000013 = "EC-000013";
	/** 请求时间格式错误 */
	public final static String EC_000014 = "EC-000014";
	/** 原密码不正确 */
	public final static String EC_000015 = "EC-000015";
	/** 新密码和确认密码不一致！ */
	public final static String EC_000016 = "EC-000016";
	/** 新密码和原密码不能一样！ */
	public final static String EC_000017 = "EC-000017";
	/** 用户不存在 */
	public final static String EC_000018 = "EC-000018";
	/** 密码错误 */
	public final static String EC_000019 = "EC-000019";
	/** 登录验证报文头参数缺失 */
	public final static String EC_000020 = "EC-000020";
	/** 端到端加密uuid必须，且长度>=18，clientid必须，且长度为3 */
	public final static String EC_000021 = "EC-000021";
	/** 端到端加密异常 */
	public final static String EC_000022 = "EC-000022";
	/** 该功能暂不可用，接口没权限 */
	public final static String EC_000023 = "EC-000023";
	/** 找不到文件模板{0} */
	public final static String EC_000024 = "EC-000024";
	/** 文件下载错误{0} */
	public final static String EC_000025 = "EC-000025";
	/** 手机号不存在 */
	public final static String EC_000026 = "EC-000026";
	/** 短信验证码错误 */
	public final static String EC_000027 = "EC-000027";
	/** 5分钟内短信已超过能发送上限，请稍后重发 */
	public final static String EC_000028 = "EC-000028";
	/** 登录验证码已失效，请重新发送短信验证码！ */
	public final static String EC_000029 = "EC-000029";
	/** 手机号已存在 */
	public final static String EC_000030 = "EC-000030";
	/** 短信验证码不能重复验证 */
	public final static String EC_000031 = "EC-000031";
	/** 发送短信验证码失败  */
	public final static String EC_000032 = "EC-000032";
	/** 短信验证码类型输入错误  */
	public final static String EC_000033 = "EC-000033";
	/** 手机号和用户不匹配  */
	public final static String EC_000034 = "EC-000034";
	/** 调用微信接口出错!*/
	public final static String EC_000035 = "EC-000035";
	/** 一小时内密码连续输入错误次数已经达到10次，密码登录已锁定，请一小时后再用密码登录或联系管理员解锁密码登录，您也可以尝试手机号码或人脸识别登录 */
	public final static String EC_P00006 = "EC-P00006";
	/** 当前用户被禁用，请联系管理员 */
    public final static String EC_000037 = "EC-000037";
	/** code不能为空 */
	public final static String EC_000038 = "EC-000038";
	/** 该接口没有配置client权限 */
	public final static String EC_000039 = "EC-000039";
	/** 密码不合法，必须是6位及以上不连续的字符 */
	public final static String EC_000040 = "EC-000040";
	/** 还未扫一扫登录 */
	public final static String EC_000041 = "EC-000041";
	/** 扫一扫验证码已失效！ */
	public final static String EC_000042 = "EC-000042";
	/** 图形验证码不正确 */
	public final static String EC_000043 = "EC-000043";
	

	/** 原密码不正确 */
	public final static String EC_000036 = "EC-000036";
	
	private ExceptionCode() {
	}

}
