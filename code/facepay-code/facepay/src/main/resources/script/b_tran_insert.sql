/*Data for the table `b_tran` */
delete from `b_tran`;

insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (1,'/unlogin/querysysdate','查询系统时间','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (2,'/unlogin/getrdnum','获取随机数接口','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (3,'/unlogin/login','登录验密接口','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (5,'/people/loginout','退出登录接口','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (6,'/people/updatePass','修改密码','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (7,'/unlogin/findPass','找回密码','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (8,'/unlogin/sentMessage','非登录发送短信验证码','1',NULL);
insert  into `b_tran`(`id`,`tran_url`,`tran_name`,`tran_status`,`remark`) 
	values (9,'/people/sentMessage','登录后发送短信验证码','1',NULL);


