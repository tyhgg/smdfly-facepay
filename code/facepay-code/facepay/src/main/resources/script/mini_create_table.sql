
tee source_sql.log;
set names utf8;
/*Table structure for table `b_country` */

DROP TABLE IF EXISTS `b_country`;

CREATE TABLE `b_country` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `country_id` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '国家ID',
  `country_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '国家名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='国家参数表';


DROP TABLE IF EXISTS `b_module_menu`;

CREATE TABLE `b_module_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mod_id` int(6) NOT NULL COMMENT '菜单编号',
  `mod_name` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '菜单名称',
  `mod_url` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '菜单地址',
  `mod_pid` int(6) DEFAULT 0 COMMENT '父菜单编号',
  `is_child` varchar(1) COLLATE utf8_bin DEFAULT '' COMMENT '是否是叶子节点，1-是，2-不是',
  `sort_id` int(11) DEFAULT 0 COMMENT '菜单排序编号',
  `mod_status` varchar(1) COLLATE utf8_bin DEFAULT '' COMMENT '菜单状态',
  `rem` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '备注说明',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `menu_idx` (`mod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统模块表';


/*Table structure for table `b_nation` */

DROP TABLE IF EXISTS `b_nation`;

CREATE TABLE `b_nation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nation_id` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '民族ID',
  `nation_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '民族名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='民族参数表';

/*Table structure for table `b_org` */

DROP TABLE IF EXISTS `b_org`;

CREATE TABLE `b_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '组织机构编码,主键',
  `org_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '机构名称',
  `org_full_name` varchar(512) COLLATE utf8_bin DEFAULT '' COMMENT '机构名称',
  `pid` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '父机构编号',
  `org_level` int(3) DEFAULT '1' COMMENT '机构层级：1-一级，2-二级，3-三级',
  `is_child` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否有子机构，1-没有，2-有',
  `sort_id` int(7) DEFAULT '0' COMMENT '部门排序用',
  `org_status` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '1、正常，2、不显示，3、逻辑删除',
  `specific_id` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '组织机构编码，对应业务要求的组织机构编码，通过页面维护时和org_id一致',
  `super_org_id` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '最上上级组织机构编码，目前维护成一级的，通过页面维护时选最上一级',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_idx` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `b_role` */

DROP TABLE IF EXISTS `b_role`;
CREATE TABLE `b_role` (
  `role_id` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '角色编号',
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_rem` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色表';

/*Table structure for table `b_role_menu_rel` */

DROP TABLE IF EXISTS `b_role_menu_rel`;
CREATE TABLE `b_role_menu_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '角色编号',
  `mod_id` int(6) DEFAULT 0 COMMENT '菜单编号',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mod_idx` (`role_id`,`mod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色菜单表';

/*Table structure for table `b_role_people_rel` */

DROP TABLE IF EXISTS `b_role_people_rel`;
CREATE TABLE `b_role_people_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '角色编号',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '用户ID',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_idx` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色人员表';


/*Table structure for table `b_tran_userid_check` */

DROP TABLE IF EXISTS `b_tran_userid_check`;
CREATE TABLE `b_tran_userid_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tran_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '交易url',
  `body_field` varchar(50) COLLATE utf8_bin DEFAULT 'userId' COMMENT '报文体字段',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='需要检查报文体和报文头userid一致表';

/*Table structure for table `people` */

DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `people_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '人员姓名',
  `people_pass` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '用户密码',
  `people_sex` varchar(2) COLLATE utf8_bin DEFAULT '' COMMENT '参会人员性别 0-男 1-女',
  `sort_id` int(7) DEFAULT '0' COMMENT '排序',
  `people_idtype` varchar(16) COLLATE utf8_bin DEFAULT '' COMMENT '证件类型',
  `people_idno` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '身份证号',
  `country` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '国家',
  `province` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '省份',
  `city` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '城市',
  `address` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '详细地址',
  `headimg_url` VARCHAR(200) COLLATE utf8_bin DEFAULT '' COMMENT '用户头像地址',
  `title_id` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '对应标准职位代码',
  `title_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '标准职称名称',
  `birthday` varchar(10) COLLATE utf8_bin DEFAULT '' COMMENT '出生日期',
  `people_tel` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '手机',
  `people_phone` varchar(40) COLLATE utf8_bin DEFAULT '' COMMENT '座机',
  `org_id` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT 'b_org表的org_id',
  `people_status` varchar(20) COLLATE utf8_bin DEFAULT '1' COMMENT '1-什么情况都正常查询，2-隐藏',
  `update_pass` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否更改密码：1-未修改，2-已修改',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `rem` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_idx` (`user_id`),
  KEY `tel_idx` (`people_tel`),
  KEY `name_idx` (`people_name`),
  KEY `org_idx` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户表';

/*Table structure for table `people_login` */

DROP TABLE IF EXISTS `people_login`;
CREATE TABLE `people_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '用户ID',
  `client_id` varchar(5) COLLATE utf8_bin NOT NULL COMMENT 'clientid',
  `login_type` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '1-密码方式，2-微信扫一扫登录',
  `open_id` VARCHAR(50) COLLATE utf8_bin DEFAULT '' COMMENT '微信的openID',
  `union_id` VARCHAR(50) COLLATE utf8_bin DEFAULT '' COMMENT '微信的用户统一标识',
  `session_id` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '返给前端的登录校验串',
  `access_token` VARCHAR(512) COLLATE utf8_bin DEFAULT '' COMMENT 'accecssToke,最长为512字节',
  `refresh_token` VARCHAR(512) COLLATE utf8_bin DEFAULT '' COMMENT '刷新token,最长为512字节',
  `expires_in` int DEFAULT 0 COMMENT 'token的有效时间（秒）',
  `fail_count` int(11) DEFAULT 0 COMMENT '连续失败次数',
  `login_count` int(11) DEFAULT 0 COMMENT '登录次数',
  `login_status` varchar(2) COLLATE utf8_bin DEFAULT '1' COMMENT '登录状态：1-登录，2-退出',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `timeout_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_idx` (`user_id`,`client_id`,`open_id`),
  KEY `open_idx` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户登录信息表';

/*Table structure for table `b_msg` */

DROP TABLE IF EXISTS `b_msg`;
CREATE TABLE `b_msg` (
  `msg_key` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '键值对key值',
  `msg_value` varchar(1000) COLLATE utf8_bin DEFAULT '' COMMENT '键值对value值',
  `rem` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='错误码表';

/*Table structure for table `b_tran` */

DROP TABLE IF EXISTS `b_tran`;
CREATE TABLE `b_tran` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tran_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '交易url',
  `tran_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '交易名称',
  `tran_status` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '状态',
  `remark` varchar(256) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易url信息';

DROP TABLE IF EXISTS `b_client`;
CREATE TABLE `b_client` (
  `client_id` varchar(3) COLLATE utf8_bin NOT NULL COMMENT 'clientid',
  `client_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '终端名称',
  `mod_time` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '修改时间',
  `rem` varchar(256) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='终端信息';

DROP TABLE IF EXISTS `b_client_tran_rel`;
CREATE TABLE `b_client_tran_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(3) COLLATE utf8_bin NOT NULL COMMENT 'clientid',
  `tran_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '终端名称',
  `mod_time` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='终端信息';

/*Table structure for table `system_log` */
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sys_date` date NOT NULL COMMENT '交易日期',
  `uuid` char(35) DEFAULT '' COMMENT '交易唯一标识',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '用户ID',
  `client_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'clientID',
  `begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '接收时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `duration` int(11) DEFAULT 0 COMMENT '用时ms',
  `service_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '服务器IP',
  `client_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '客户端IP',
  `tran_url` varchar(256) DEFAULT '' COMMENT '交易URL',
  `tran_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '交易名称',
  `tran_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '交易码',
  `backsys_begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '后台系统发送报文时间',
  `backsys_end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '后台系统返回时间',
  `backsys_duration` int(11) DEFAULT 0 COMMENT '后台系统用时ms',
  `error_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '错误码',
  `error_message` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '错误信息',
  `http_header` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '报文头',
  `http_body` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '请求报文',
  `resp_message` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '返回报文',
  PRIMARY KEY (`id`),
  KEY `idx_sys_date` (`sys_date`,`user_id`),
  KEY `idx_sys_uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

/*Table structure for table `system_properties` */

DROP TABLE IF EXISTS `system_properties`;
CREATE TABLE `system_properties` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_key` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '键值对key值',
  `sys_value` varchar(1000) COLLATE utf8_bin DEFAULT '' COMMENT '键值对value值',
  `rem` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_key` (`sys_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数表';

DROP TABLE IF EXISTS `b_testcase`;
CREATE TABLE `b_testcase` (
  `case_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '案例ID',
  `url` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '请求url',
  `headers` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '报文头',
  `method` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '请求方法',
  `parameter` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '？号方式请求参数',
  `request` varchar(2000) COLLATE utf8_bin DEFAULT '' COMMENT '请求报文',
  `response` varchar(2000) COLLATE utf8_bin DEFAULT '' COMMENT '返回报文',
  `remark1` varchar(1000) COLLATE utf8_bin DEFAULT '' COMMENT '备用字段',
  `remark2` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '备用字段',
  `remark3` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '备用字段',
  PRIMARY KEY (`case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自动化单元测试案例表';

DROP TABLE IF EXISTS `b_message_code`;
CREATE TABLE `b_message_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(15) DEFAULT '' COMMENT '用户ID',
  `people_tel` varchar(15) DEFAULT '' COMMENT '手机号',
  `valid_code` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '验证码',
  `valid_num` int(2) COLLATE utf8_bin DEFAULT 0 COMMENT '验证次数',
  `valid_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
  `code_type` varchar(2) DEFAULT '' COMMENT '类型，01-登录，02-找回密码，03-修改手机号，04-修改密码，05-注册，06-支付，07-转账',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `people_tel_idx` (`people_tel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信验证码表';

DROP TABLE IF EXISTS `login_code`;
CREATE TABLE `login_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT '' COMMENT '用户ID',
  `valid_code` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '验证码',
  `valid_num` int(2) COLLATE utf8_bin DEFAULT 0 COMMENT '验证次数',
  `valid_time` varchar(19) DEFAULT '' COMMENT '失效时间',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `valid_code_idx` (`valid_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='一次性验证码表';

/* 微信token信息表 */   
DROP TABLE IF EXISTS `wechat_token`;
CREATE TABLE `wechat_token` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corp_id` VARCHAR(50) COLLATE utf8_bin not NULL COMMENT '企业CorpID',
  `access_token` VARCHAR(512) COLLATE utf8_bin DEFAULT '' COMMENT '获取到的凭证,最长为512字节',
  `expires_in` int DEFAULT 0 COMMENT 'token的有效时间（秒）',
  `mod_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='微信token信息表';
