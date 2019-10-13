/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.entity.PeoplePageEntity;

@Repository
public interface PeopleMapper {
    /**
     * 查询单个
     * 
     * @param entry
     */
    PeopleEntity queryPeople(PeopleEntity entry);
    /**
     * 查询单个
     * 
     * @param entry
     */
    PeopleEntity queryPeopleByUserId(String userId);
    
    /**
     * 查询用户及密码信息
     * 
     * @param entry
     */
    PeopleEntity queryPeopleAndPass(PeopleEntity entry);
    
    /**
     * 查询列表
     * 
     * @param entry
     */
    List<PeopleEntity> queryPeopleList(PeopleEntity entry);
    
    /**
     * 查询列表
     * 
     * @param entry
     */
    List<PeopleEntity> queryPeoplePageList(PeoplePageEntity entry);
    
    /**
     * 查询个数
     * 
     * @param entry
     */
    int queryPeoplePageCount(PeoplePageEntity entry);
    
    
    /**
     * 根据组织查询列表
     * 
     * @param entry
     */
    List<PeopleEntity> queryPeopleListByOrg(PeopleEntity entry);
    
    /**
     * 保存
     * 
     * @param entry
     */
    int save(PeopleEntity entry);

    /**
     * 删除
     * 
     * @param entry
     */
    int delete(PeopleEntity entry);
    
    /**
     * 更新
     * 
     * @param entry
     */
    int updatePeopleByPeople(PeopleEntity entry);

    /**
     * 更新密码
     * 
     * @param entry
     */
    int updatePeoplePass(PeopleEntity entry);

    /**
     * 更新peopleStatus
     * 
     * @param entry
     */
    int updatePeopleStatus(PeopleEntity entry);
    
    /**
     * 更新密码
     * 
     * @param entry
     */
    int resetPeoplePass(PeopleEntity entry);

    List<String> queryPeopleNoTelList();

    List<String> queryPeopleTelList();
    
    /**
     * 批量更新手机号
     * @param list
     * @return
     */
    int updatePeopleByIdAndPhoneIsNullBatch(List<PeopleEntity> list);
}
