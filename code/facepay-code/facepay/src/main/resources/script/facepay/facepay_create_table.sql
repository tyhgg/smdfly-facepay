
DROP TABLE IF EXISTS `f_module_menu`;
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

