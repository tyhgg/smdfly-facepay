		
/* 新增用户 */
delete from `b_testcase` where `case_id` = 'testSavePeopleInfoAdd';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testSavePeopleInfoAdd','/manager/people/savePeopleInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16","orgId":"000001","peopleName":"自动化测试用户16","peopleSex":"1","peopleTel":"12345678901"}','{"result":"true"}','','','');
					
/* 用户列表查询 */
delete from `b_testcase` where `case_id` = 'testQueryPeopleList';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryPeopleList','/manager/people/queryPeopleList','clientid=111,userid=autotestuser16,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16","peopleName":"自动化测试用户","orgId":"000001"}','{"total":1,"rows":[{"peopleEntity":{"orgId":"000001","peopleName":"自动化测试用户16","peopleSex":"1","peopleStatus":"1","peopleTel":"12345678901","updatePass":"1","userId":"autotestuser16"}}]}','','','');
		
/* 修改用户 */
delete from `b_testcase` where `case_id` = 'testSavePeopleInfoUpdate';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testSavePeopleInfoUpdate','/manager/people/savePeopleInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16","orgId":"000001","peopleName":"自动化测试用户6","peopleSex":"2","peopleTel":"12345678902"}','{"result":"true"}','','','');
						
/* 查询用户详细信息 */
delete from `b_testcase` where `case_id` = 'testQueryPeopleByUserId';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testQueryPeopleByUserId','/manager/people/queryPeopleByUserId','clientid=111,userid=autotestuser16,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16"}','{"peopleEntity":{"orgId":"","peopleName":"自动化测试用户6","peopleSex":"","peopleStatus":"","peopleTel":"","updatePass":"","userId":"autotestuser16"}}','','','');

/* 修改密码 */
delete from `b_testcase` where `case_id` = 'testManagerUpdatePass';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testManagerUpdatePass','/manager/people/updatePass','clientid=111,userid=autotestuser16,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16","peoplePass":"222222","newPass":"222222","oldPass":"111111"}','{"msg":"操作成功!","result":"true"}','','','');
		
/* 删除用户 */
delete from `b_testcase` where `case_id` = 'testDelPeopleInfo';
insert into `b_testcase` (`case_id`, `url`, `headers`, `method`, `parameter`, 
		`request`, `response`, `remark1`, `remark2`, `remark3`) 
	values('testDelPeopleInfo','/manager/people/delPeopleInfo','clientid=111,userid=autotestuser1,chnflg=2,trandt=20130129,uuid=test123',
		'POST','','{"userId":"autotestuser16"}','{"result":"true"}','','','');
	
		
		
		
		
		
		