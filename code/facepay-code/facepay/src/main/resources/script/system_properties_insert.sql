/*Data for the table `system_properties` */
delete from `system_properties`;

insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('login.timeout.minutes','1440','app端session超时时间,分钟');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`)     
	values ('datagram.maxsize','10000','最大报文长度');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('thread.pool.count','5','线程池默认数');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('systemlog.pool.count','3','线程池默认数');  
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sys.upload.file.type','apk|txt|jpg|jpeg|gif|png|html|htm|doc|xls|xlsx|ppt|pdf|mp4','文件格式');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sys.upload.file.size','200','单位M');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sys.ftp.url','','ftp服务器地址');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sys.server.url','http://22.188.14.160:7788,http://22.188.14.76:8080','后台服务的地址');
delete from system_properties where sys_key = 'system.url';
insert into system_properties (sys_key, sys_value, rem) values('system.url','http://22.188.14.160:7788/book/','系统地址');

insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('not.check.session.url','.*.jsp|.*(/unlogin/).*|.*(/resources).*|.*.png|.*.jpg|.*.gif|.*.htm|.*.woff|.*.woff2|.*.ttf|.*(/getsysfile/).*','不需要验登录的接口');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('not.encrypt.filter.url','.*.jsp|.*(/resources).*|.*.png|.*.jpg|.*.gif|.*.htm|.*.woff|.*.woff2|.*.ttf|.*(/sysfile/).*|.*(/getsysfile/).*','不能走encryption(加密)过滤器的交易');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('not.wrapper.filter.url','.*.jsp|.*(/resources).*|.*.png|.*.jpg|.*.gif|.*.htm|.*.woff|.*.woff2|.*.ttf|.*(/sysfile/).*|.*(/getsysfile/).*','不能走WrapperHttpServletFilter过滤器的交易');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('not.print.file.log.urls','.*(/sysfile/).*','不需要记文件日志的交易');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('not.print.db.log.urls','.*(/sysfile/).*','不需要记文件日志交易');
   
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sys.template.path','/tyhgg/tgbook/template/','模板文件存放地址');
    
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('jwt.timeout.minutes','30','jwt token超时时间');
    

insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('message.valid.minutes','5','短信验证码有效时间，单位分钟');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('message.max.allow.num','5','在一定时间内运行发送短信验证码的次数');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('tencent.yun.sms.appkey','','微信发送短信key');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('tencent.yun.sms.sign','','微信发送短信sign');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('tencent.yun.sms.templateid','','微信发送短信模板');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('tencent.yun.sms.uri','','微信发送短信uri');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('tencent.yun.sms.sdkappid','','微信发送短信appid');
insert into `system_properties`(`sys_key`,`sys_value`,`rem`) 
    values ('sms.max.valid.time','','微信短信有效期');
    
DELETE FROM system_properties WHERE sys_key = 'wechat.url';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.url','https://api.weixin.qq.com/sns','微信地址，不需要最后的/');
DELETE FROM system_properties WHERE sys_key = 'wechat.appid';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.appid','1109775598','微信appid');
DELETE FROM system_properties WHERE sys_key = 'wechat.appkey';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.appkey','UuRbYpYruG6WSvzS','微信appkey');
DELETE FROM system_properties WHERE sys_key = 'wechat.agentid';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.agentid','1000005','agentid');
DELETE FROM system_properties WHERE sys_key = 'wechat.book.h5url';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.book.h5url','http://tyhgg.iicp.net:11702/tgbook/wechat','H5页面地址，由微信H5提供，不需要后面的/');
DELETE FROM system_properties WHERE sys_key = 'logincode.validate.time';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('logincode.validate.time','300','登录验证码失效时间，单位秒');
DELETE FROM system_properties WHERE sys_key = 'wechat.oauth2.url';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.oauth2.url','https://open.weixin.qq.com/connect/qrconnect','微信oauth2登录地址，不需要最后的/');
DELETE FROM system_properties WHERE sys_key = 'wechat.scan.login.url';
INSERT INTO `system_properties` (`sys_key`, `sys_value`, `rem`) 
	VALUES('wechat.scan.login.url','http://tyhgg.iicp.net:11702/tgbook/#/saoma','微信扫一扫登录回调地址，不需要最后的/');
    
    