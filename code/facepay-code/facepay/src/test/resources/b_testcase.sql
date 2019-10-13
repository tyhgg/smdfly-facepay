/* 查询系统时间  */
delete from `b_testcase` where `case_id` = 'testQuerySysDate';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQuerySysDate','/unlogin/querysysdate','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'GET','','','{"sysdate":""}','','','');
/* 查询系统url  */
delete from `b_testcase` where `case_id` = 'testQuerySysUrl';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQuerySysUrl','/unlogin/querySysUrl','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"type":"system.url"}','{"result":"http://22.188.14.160:7788/book/"}','','','');								
								
/* 生成服务器端随机数 */
delete from `b_testcase` where `case_id` = 'testGetRdnum';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testGetRdnum','/unlogin/getrdnum','clientid=111,userid=autotestuser1,chnflg=1,trandt=20130129,uuid=test123',
		'GET','','{}','{"random":"","randomid":""}','','','');
/* 明文密码登录 */
delete from `b_testcase` where `case_id` = 'testLoginByClearPass';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testLoginByClearPass','/unlogin/login','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser1","loginType":"1","password":"111111"}','{"peopleName":"自动化测试用户1","userId":"autotestuser1","orgId":"000102","updatePass":""}','','','');
/* 退出登录 */
delete from `b_testcase` where `case_id` = 'testLoginout';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testLoginout','/people/loginout','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{}','{"result":"0"}','','','');

/* 根据角色查询系统路由-vue */
delete from `b_testcase` where `case_id` = 'testLoadSystemModule';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testLoadSystemModule','/manager/menu/loadSystemModule','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"roleId":"1"}','{"menuList":[]}','','','');

/* 查询组织机构列表 */
delete from `b_testcase` where `case_id` = 'testQueryOrgInfoPageList';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryOrgInfoPageList','/manager/orginfo/queryOrgInfoPageList','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"000101","orgName":"二级机构"}','{"total":1,"rows":[]}','','','');

/* 查询组织机构 */
delete from `b_testcase` where `case_id` = 'testQueryOrgInfo';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryOrgInfo','/manager/orginfo/queryOrgInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"000101"}','{"orginfo":{"isChild":"1","orgId":"000101","orgName":"","orgStatus":"","pid":"","superOrgId":""}}','','','');

/* 删除组织机构 */
delete from `b_testcase` where `case_id` = 'testDelOrgInfoById';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testDelOrgInfoById','/manager/orginfo/delOrgInfoById','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"000101"}','{"result":"true"}','','','');
/* 删除组织机构-失败 */
delete from `b_testcase` where `case_id` = 'testDelOrgInfoById1';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testDelOrgInfoById1','/manager/orginfo/delOrgInfoById','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"000001"}','{"result":"false"}','','','');

/* 修改组织机构 */
delete from `b_testcase` where `case_id` = 'testSaveOrgInfoUpdate';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testSaveOrgInfoUpdate','/manager/orginfo/saveOrgInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"isChild":"2","orgId":"000101","orgName":"二级机构（一）","orgLevel":2,"pid":"000001","superOrgId":"000001"}','{"result":"true"}','','','');

/* 新增组织机构 */
delete from `b_testcase` where `case_id` = 'testSaveOrgInfoAdd';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testSaveOrgInfoAdd','/manager/orginfo/saveOrgInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"100101","orgName":"二级机构（一）","orgLevel":2,"isChild":"2","pid":"000001","superOrgId":"000001"}','{"result":"true"}','','','');

/* 根据机构号查询本机构和下级机构，如果是超级管理员用户可以查询所有的机构 */
delete from `b_testcase` where `case_id` = 'testQueryOrgListByPid';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryOrgListByPid','/manager/orginfo/queryOrgListByPid','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgId":"000001","roleId":"1"}','{"orgList":[]}','','','');

/* 根据组织机构级别查询组织机构列表 */
delete from `b_testcase` where `case_id` = 'testQueryOrgListByLevel';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryOrgListByLevel','/manager/orginfo/queryOrgListByLevel','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"orgLevel":1}','{"orgList":[]}','','','');
