/*
* mini用户赋权限。
**/

GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, ALTER, DROP, CREATE TEMPORARY TABLES,INDEX ON mini_boot.* TO 'mini'@'%' with grant option;
FLUSH PRIVILEGES;