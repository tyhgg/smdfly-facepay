
/* 系统路由查询-超级管理员 */
DELETE FROM `b_testcase` WHERE `case_id` = 'testLoadSystemModule';
INSERT INTO `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	VALUES('testLoadSystemModule','/manager/menu/loadSystemModule','clientid=222,userid=30612380,chnflg=1,trandt=20130129,uuid=test123,languageType=zh',
		'POST','','{"roleId":"1"}','{"menuList":""}','','','');

/* 系统路由查询-超级管理员 */
DELETE FROM `b_testcase` WHERE `case_id` = 'testLoadSystemModuletyhggAdmin';
INSERT INTO `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	VALUES('testLoadSystemModuletyhggAdmin','/manager/menu/loadSystemModule','clientid=222,userid=tyhggadmin,chnflg=1,trandt=20130129,uuid=test123,languageType=zh',
		'POST','','{"roleId":"1"}','{"menuList":""}','','','');

/* 系统路由查询-多角色 */
DELETE FROM `b_testcase` WHERE `case_id` = 'testLoadSystemModule_2';
INSERT INTO `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	VALUES('testLoadSystemModule_2','/manager/menu/loadSystemModule','clientid=222,userid=30612380,chnflg=1,trandt=20130129,uuid=test123,languageType=zh',
		'POST','','{"roleId":"(\'1\',\'2\')"}','{"menuList":""}','','','');
			
		
		
		
		
		