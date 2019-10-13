package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.CountryInfoEntity;
import com.tyhgg.asr.system.entity.NationInfoEntity;

/**
 * 数据字典类
 * @author zyt5668
 *
 */
@Repository
public interface SystemDataDictMapper {

	List<CountryInfoEntity> queryCountryInfoList();
	
	List<NationInfoEntity> queryNationInfoList();
}
