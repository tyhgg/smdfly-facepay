/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.RoleEntity;
import com.tyhgg.asr.system.entity.RoleMenuRelEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelPageEntity;

/**
 * @author Administrator
 *
 */
@Repository
public interface RoleMapper {
	
	List<RoleEntity> queryRoleList(RoleEntity roleEntity);

	RoleEntity queryRole(String roleId);

	int save(RoleEntity roleEntity);

	int delete(String roleId);

	int updateRole(RoleEntity roleEntity);
	
	/**
	 * 查询人员角色列表
	 * @param rolePeopleRelEntity
	 * @return
	 */
	List<RolePeopleRelEntity> queryPeopleRoleRelList(RolePeopleRelEntity rolePeopleRelEntity);

	/**
	 * 查询角色菜单列表
	 * @param roleId
	 * @return
	 */
	List<String> queryRoleMenuRelStrList(String roleId);
	
	/**
	 * 分页查询角色人员列表
	 * @param rolePeopleRelPageEntity
	 * @return
	 */
	List<RolePeopleRelPageEntity> queryRolePeopleRelPageList(RolePeopleRelPageEntity rolePeopleRelPageEntity);

	int queryRolePeopleRelPageCount(RolePeopleRelPageEntity rolePeopleRelPageEntity);

	/**
	 * 查询未分配该角色的人员列表，前100条
	 * @param rolePeopleRelPageEntity
	 * @return
	 */
	List<RolePeopleRelEntity> getUnselectRolePeopleListByRoleId(RolePeopleRelEntity rolePeopleRelEntity);
	
	/**
	 * 删除角色人员
	 * @方法名: deleteRolePeopleRel
	 * @方法描述: 
	 * @param RolePeopleRelEntity
	 * @return
	 * @return int
	 */
	int deleteRolePeopleRel(RolePeopleRelEntity rolePeopleRelEntity);


	int saveRolePeopleRel(RolePeopleRelEntity rolePeopleRelEntity);
	
	/**
	 * 删除角色菜单
	 * @方法名: deleteRoleMenuRel
	 * @方法描述: 
	 * @param RoleMenuRelEntity
	 * @return
	 * @return int
	 */
	int deleteRoleMenuRel(RoleMenuRelEntity roleMenuRelEntity);

	int saveRoleMenuRel(RoleMenuRelEntity roleMenuRelEntity);
}
