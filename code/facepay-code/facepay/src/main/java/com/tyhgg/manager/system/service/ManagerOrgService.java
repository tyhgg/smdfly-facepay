package com.tyhgg.manager.system.service;

import net.sf.json.JSONObject;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tyhgg.asr.system.entity.OrgInfoEntity;

public interface ManagerOrgService  {

	JSONObject deleteOrgInfo(OrgInfoEntity orgInfoEntity, JSONObject resultJson);

	JSONObject insertOrUpdateOrgInfo(OrgInfoEntity orgInfoEntity, JSONObject resultJson);

    String queryOrgPidIdsByPid(String orgId);
	
    JSONObject updateOrgSortId(JSONObject jsonObj);

    JSONObject saveOrgFile(XSSFWorkbook workbook, JSONObject resultJson);
	
}
