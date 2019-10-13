/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.OrgInfoEntity;
import com.tyhgg.asr.system.entity.OrgInfoPageEntity;

/**
 * 组织机构
 * @author zyt5668
 *
 */
@Repository
public interface OrgInfoMapper {

    /**
     * 查询单个
     * 
     * @param entry
     */
	void save(OrgInfoEntity entry);

    /**
     * 删除单个
     * 
     * @param entry
     */
	int del(OrgInfoEntity entry);
	
    /**
     * 更新单个
     * 
     * @param entry
     */
	int updateOrg(OrgInfoEntity entry);

    /**
     * 查询单个
     * 
     * @param entry
     */
	OrgInfoEntity queryOrgInfo(OrgInfoEntity entry);
	  
    /**
     * 查询列表
     * 
     * @param entry
     */
    List<OrgInfoEntity> queryOrgInfoList(OrgInfoEntity entry);

    /**
     * 查询个数
     * 
     * @param entry
     */
    int queryOrgInfoPageCount(OrgInfoPageEntity orgInfoPageVo);

    /**
     * 查询列表
     * 
     * @param entry
     */
    List<OrgInfoPageEntity> queryOrgInfoPageList(OrgInfoPageEntity orgInfoPageVo);

    /**
     * 更新sortId
     * 
     * @param entry
     */
	int updateOrgSortId(OrgInfoEntity entry);
	
    /**
     * 查询最大orgId
     * 
     * @param entry
     */
	int queryMaxSortId(OrgInfoEntity entry);

    /**
     * 根据机构id查询当前机构和子机构
     * */
    List<Map<String,String>> queryOrgListByPid(Map<String,String> paramMap);
    
    /**
     * 查询列表
     * 
     * @param entry
     */
    List<OrgInfoEntity> queryOrgInfoListForRole(OrgInfoEntity entry);


    /**
     * 查询树列表
     * 
     * @param entry
     */
    List<Map<String, Object>> getFirstOrgTreeList();

    /**
     * 查询树列表
     * 
     * @param entry
     */
    List<Map<String, Object>> getFirstOrgTreeOpenList();
    
    /**
     * 查询树列表
     * 
     * @param entry
     */
    List<Map<String, Object>> getSonOrgTreeList(String pid);

    /**
     * 查询树列表
     * 
     * @param entry
     */
    List<Map<String, Object>> getSonOrgTreeOpenList(String pid);
	
}
