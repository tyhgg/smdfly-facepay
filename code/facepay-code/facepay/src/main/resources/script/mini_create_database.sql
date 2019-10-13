/*
* 创建mini数据库
**/
drop user 'mini'@'%';
create user 'mini'@'%' identified by 'Mini123!';

DROP DATABASE IF EXISTS `mini_boot`;
CREATE DATABASE `mini_boot`;

GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, ALTER, DROP, CREATE TEMPORARY TABLES,CREATE views, INDEX, FILE ON mini_boot.* TO 'mini'@'%' IDENTIFIED BY 'Mini123!';

GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, ALTER, DROP, CREATE TEMPORARY TABLES,INDEX ON mini_boot.* TO 'mini'@'%' with grant option;

GRANT all privileges ON mini_boot.* TO 'mini'@'%' with grant option;
FLUSH PRIVILEGES;
