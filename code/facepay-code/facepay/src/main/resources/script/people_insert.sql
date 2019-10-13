delete from `people` where user_id = 'admin';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('admin', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');

delete from `people` where user_id = 'miniadmin';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('miniadmin', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');

delete from `people` where user_id = 'sysadmin';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('sysadmin', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');

delete from `people` where user_id = 'superadmin';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('superadmin', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');

delete from `people` where user_id = 'autotestadmin';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('autotestadmin', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');

delete from `people` where user_id = 'autotestuser1';
INSERT INTO `people` (`user_id`, `people_name`, `people_pass`, org_id) 
	VALUES ('autotestuser1', '系统超级管理员', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'admin');



