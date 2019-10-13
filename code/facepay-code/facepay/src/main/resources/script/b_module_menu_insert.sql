/*Data for the table `b_module_menu` */
delete from `b_module_menu`;

insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (136,1,'系统管理','',-1,'2',1,'0','系统管理');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (140,11,'角色管理','toRoleManager',1,'1',11,'0','角色管理');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (141,12,'用户管理','toPeopleManager',1,'1',12,'0','用户管理');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (143,13,'组织机构管理','toOrgManage',1,'1',130,'0','组织机构管理');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (154,199,'系统日志查询','toQuerySystemLog',1,'1',195,'1','系统日志查询');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (155,196,'文件日志查询','queryServerLog',1,'1',196,'1','文件日志查询');
insert  into `b_module_menu`(`id`,`mod_id`,`mod_name`,`mod_url`,`mod_pid`,`is_child`,`sort_id`,`mod_status`,`rem`) 
	values (156,197,'执行sql','getActiveSqlResult',1,'1',197,'1','执行sql');

