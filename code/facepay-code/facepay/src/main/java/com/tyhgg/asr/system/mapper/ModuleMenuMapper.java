/**
 * 
 */
package com.tyhgg.asr.system.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tyhgg.asr.system.entity.ModuleMenuEntity;

/**
 * @author Administrator
 *
 */
@Repository
public interface ModuleMenuMapper {
	
	List<ModuleMenuEntity> queryModuleMenuList(ModuleMenuEntity moduleMenuEntity);
	
}
