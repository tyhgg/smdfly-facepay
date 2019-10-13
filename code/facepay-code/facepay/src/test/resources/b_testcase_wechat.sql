/* 微信扫一扫二维码url接口  */
delete from `b_testcase` where `case_id` = 'testGetOauthRedirectUrl';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testGetOauthRedirectUrl','/unlogin/website/getOauthRedirectUrl','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'GET','scanType=1','','{"oauth2Url":"","loginUuid":""}','','','');
/* 查询系统url  */
delete from `b_testcase` where `case_id` = 'testQuerySysUrl';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQuerySysUrl','/unlogin/querySysUrl','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"type":"system.url"}','{"result":"http://22.188.14.160:7788/book/"}','','','');								
