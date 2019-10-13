package com.tyhgg.manager.system.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.mapper.OrgInfoMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.constants.SystemConstants;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.SHA256Utils;
import com.tyhgg.manager.system.service.ManagerPeopleService;

@Service(value = "managerPeopleService")
public class ManagerPeopleServiceImpl implements ManagerPeopleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerPeopleServiceImpl.class);

	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	public OrgInfoMapper orgInfoMapper;

	@Transactional
	public JSONObject savePeople(PeopleEntity peopleEntity, JSONObject resultJson) {
		if (null == peopleEntity) {
			return ResponseUtils.responseWebSuccessJson();
		}

		// 判断userId
		String userId = peopleEntity.getUserId();
		PeopleEntity tempPeople = new PeopleEntity();
		tempPeople.setUserId(userId);
		tempPeople = this.peopleMapper.queryPeopleAndPass(tempPeople);
		if (null == tempPeople) {
			// 新增
			peopleEntity.setPeoplePass(SHA256Utils.encryptSHA256(SystemConstants.PEOPLE_PASS_DEFAULT));

			LOGGER.info("新增：" + peopleEntity);
			this.peopleMapper.save(peopleEntity);
		} else {
			// 修改
			LOGGER.info("修改：" + peopleEntity);
			this.peopleMapper.updatePeopleByPeople(peopleEntity);
		}

		return ResponseUtils.responseWebSuccessJson();
	}


	@Transactional
	public JSONObject deletePeople(PeopleEntity peopleEntity) {
		// 删除
		this.peopleMapper.delete(peopleEntity);
		
		return ResponseUtils.responseWebSuccessJson();
	}

	/**
	 * 登录用户是否是系统超级管理员
	 * true 是超级管理员
	 * false 不是超级管理员
	 */
	@Override
	public boolean peopleIsAdmin(String orleId, String ehrId) {
		// int count = orgInfoMapper.queryOrgInfoPageCount(vo);
		if (orleId.equals("1") || orleId.contains("'1'")) {
			return true;
		}
		return false;
	}

}
