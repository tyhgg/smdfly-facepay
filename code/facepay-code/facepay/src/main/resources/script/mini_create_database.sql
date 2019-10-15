/*
* 创建mini数据库
**/
drop user 'facepay'@'%';
create user 'facepay'@'%' identified by 'Facepay123!';

DROP DATABASE IF EXISTS `facepay`;
CREATE DATABASE `facepay`;

GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, ALTER, DROP, CREATE TEMPORARY TABLES,CREATE views, INDEX, FILE ON mini_boot.* TO 'mini'@'%' IDENTIFIED BY 'Mini123!';

GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, ALTER, DROP, CREATE TEMPORARY TABLES,INDEX ON mini_boot.* TO 'mini'@'%' with grant option;

GRANT all privileges ON facepay.* TO 'facepay'@'%' with grant option;
FLUSH PRIVILEGES;
