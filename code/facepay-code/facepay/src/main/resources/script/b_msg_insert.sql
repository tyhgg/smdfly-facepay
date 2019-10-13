
/*Data for the table `b_msg` */

delete from `b_msg`;

insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000000','成功','成功');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-999999','后台系统异常','后台系统异常');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000001','操作失败','操作失败');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000002','UUID未上送','UUID未上送');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000003','登录失败，请检查用户名或密码','登录失败，请检查用户名或密码');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000004','用户上送的报文头用户名和报文体用户名信息不匹配','用户上送的报文头用户名和报文体用户名信息不匹配');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000005','请先登录','请先登录');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000006','登录失效，请重新登录','登录失效，请重新登录');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000007','请求参数错误','请求参数错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000008','注销失败','注销失败');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000009','报文头userId不能为空 ','报文头userId不能为空 ');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000010','返回结果错误','返回结果错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000011','请求数据不存在','请求数据不存在');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000012','报文长度超过限制','报文长度超过限制');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000013','通讯超时','通讯超时');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000014','请求时间格式错误','请求时间格式错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000015','原密码不正确','原密码不正确');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000016','新密码和确认密码不一致','新密码和确认密码不一致');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000017','新密码和原密码不能一样','新密码和原密码不能一样');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000018','用户不存在','用户不存在');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000019','密码错误','密码错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000020','登录验证报文头参数缺失','登录验证报文头参数缺失');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000021','端到端加密uuid必须，且长度>=18，clientid必须，且长度为3','端到端加密uuid必须，且长度>=18，clientid必须，且长度为3');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000022','端到端加密异常','端到端加密异常');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000023','该功能暂不可用，接口没权限','该功能暂不可用，接口没权限');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000024','找不到文件模板{0}','找不到文件模板{0}');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000025','文件下载错误{0}','文件下载错误{0}');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000026','手机号不存在 ','手机号不存在 ');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000027','短信验证码错误','短信验证码错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000028','5分钟内短信已超过能发送上限，请稍后重发','5分钟内短信已超过能发送上限，请稍后重发');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000029','登录验证码已失效，请重新发送短信验证码！','登录验证码已失效，请重新发送短信验证码！');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000030','手机号已存在 ','手机号已存在 ');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000031','短信验证码不能重复验证','短信验证码不能重复验证');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000032','发送短信验证码失败','发送短信验证码失败');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000033','短信验证码类型输入错误 ','短信验证码类型输入错误');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000034','手机号和用户不匹配 ','手机号和用户不匹配 ');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000035','人脸识别登录失败！','');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000036','人脸识别登录失败！','');

insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC_P00006','一小时内密码连续输入错误次数已经达到10次，密码登录已锁定，请一小时后再用密码登录或联系管理员解锁密码登录','一小时内密码连续输入错误次数已经达到10次，密码登录已锁定，请一小时后再用密码登录或联系管理员解锁密码登录');

insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000037','当前用户被禁用，请联系管理员','当前用户被禁用，请联系管理员');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000038','code不能为空','code不能为空');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000039','该接口没有配置client权限','该接口没有配置client权限');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000040','密码不合法，必须是6位及以上不连续的字符','密码不合法，必须是6位及以上不连续的字符');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000041','还未扫一扫登录','还未扫一扫登录');
insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000042','扫一扫验证码已失效','扫一扫验证码已失效');

insert  into `b_msg`(`msg_key`,`msg_value`,`rem`) values ('EC-000043','图形验证码不正确','图形验证码不正确');


