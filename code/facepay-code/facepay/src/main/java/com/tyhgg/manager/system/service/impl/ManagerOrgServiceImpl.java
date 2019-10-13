package com.tyhgg.manager.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyhgg.asr.system.entity.OrgInfoEntity;
import com.tyhgg.asr.system.entity.PeoplePageEntity;
import com.tyhgg.asr.system.mapper.OrgInfoMapper;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerOrgService;

/**
 * 组织机构
 * @author zyt5668
 *
 */
@Service(value = "managerOrgService")
public class ManagerOrgServiceImpl implements ManagerOrgService  {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerOrgServiceImpl.class);

	@Resource
	private OrgInfoMapper orgInfoMapper;
	@Resource
	private PeopleMapper peopleMapper;

	/**
	 * 导入execl
	 */
    public JSONObject saveOrgFile(XSSFWorkbook workbook, JSONObject resultJson) {

        int addCount = 0;
        int updateCount = 0;

        List<OrgInfoEntity> entityList = new ArrayList<OrgInfoEntity>();
        // 跳过第一行标题栏取sheet
        // 按顺序引用sheet,sheet2
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowIndex = 1;
        XSSFRow row = sheet.getRow(rowIndex);
        try {
            boolean endFlag = false;

            while (!endFlag && null != row) {
                String sortIdStr = StringUtil.getCellValue(row.getCell(0)).trim();

                if (StringUtil.isEmpty(sortIdStr)) {
                    endFlag = true;
                    break;
                }

                if (!this.setExcelOrgList(entityList, row, resultJson, rowIndex)) {
                    // 有一个格式出错，全部不导入
                    return resultJson;
                }

                // 读取下一行
                rowIndex = rowIndex + 1;
                if (null == sheet.getRow(rowIndex)) {
                    break;
                } else {
                    row = sheet.getRow(rowIndex);
                }
            }

        } catch (Exception e) {
            LOGGER.error("读取Execl文件异常：", e);
            return ResponseUtils.responseWebErrorJson("读取Execl文件异常!");
        }

        // 处理组织机构数据库信息
        StringBuilder messageSb = new StringBuilder();
        for (int i = 0; i < entityList.size(); i++) {
            OrgInfoEntity tempEntity = entityList.get(i);
            String orgId = tempEntity.getOrgId();
            try {
                OrgInfoEntity temp = new OrgInfoEntity();
                temp.setOrgId(orgId);
                temp = this.orgInfoMapper.queryOrgInfo(temp);

                if (null == temp) {
                    LOGGER.info("新增组织机构:" + orgId);
                    this.orgInfoMapper.save(tempEntity);
                    // messageSb.append("新增组织机构:" + orgId + "\n");
                    addCount = addCount + 1;
                } else {
                    temp.setSortId(tempEntity.getSortId());
                    temp.setOrgName(tempEntity.getOrgName());
                    temp.setOrgLevel(tempEntity.getOrgLevel());
                    temp.setIsChild(tempEntity.getIsChild());
                    temp.setPid(tempEntity.getPid());
                    temp.setSuperOrgId(tempEntity.getSuperOrgId());

                    LOGGER.info("修改组织机构:" + orgId);
                    this.orgInfoMapper.updateOrg(temp);
                    // messageSb.append("修改组织机构:" + orgId + "\n");
                    updateCount = updateCount + 1;
                }

            } catch (Exception e) {
                LOGGER.error("处理组织机构信息异常：", e);
                messageSb.append("处理组织机构信息异常，orgId为" + orgId + "\n");
            } finally {

            }
        }

		return ResponseUtils.responseErrorJson("0", "成功新增" + addCount + "个;" + "成功修改" + updateCount + "个;\n" + messageSb.toString());
        
    }

    private boolean setExcelOrgList(List<OrgInfoEntity> entityList, XSSFRow row, JSONObject resultJson, int rowIndex) {

        String sortIdStr = StringUtil.getCellValue(row.getCell(0)).trim();
        String orgId = StringUtil.getCellValue(row.getCell(1)).trim();
        String orgName = StringUtil.getCellValue(row.getCell(2)).trim();
        String orgLevel = StringUtil.getCellValue(row.getCell(3)).trim();
        String pid = StringUtil.getCellValue(row.getCell(4)).trim();
        String isChild = StringUtil.getCellValue(row.getCell(5)).trim();
        String superOrgId = StringUtil.getCellValue(row.getCell(6)).trim();
        
        if (StringUtil.isEmpty(sortIdStr) || StringUtil.isEmpty(orgId) || orgId.length() < 5 || StringUtil.isEmpty(orgName)
                || StringUtil.isEmpty(orgLevel) || StringUtil.isEmpty(isChild) || StringUtil.isEmpty(superOrgId)) {
        	
            ResponseUtils.responseWebErrorJson(resultJson, StringUtil.getString(resultJson.get("msg")) + "\n第" + rowIndex
                    + "行数据有错，请检查必填项及格式后再重新导入!");
            
            return false;
        }
        
        
        if (orgId.length() > 20) {

            ResponseUtils.responseWebErrorJson(resultJson, StringUtil.getString(resultJson.get("msg")) + "\n第" + rowIndex
                    + "行数据长度有错，组织机构编号最多20位，请检查后再重新导入!");
            
            return false;
        }
        if (orgName.length() > 128) {

            ResponseUtils.responseWebErrorJson(resultJson, StringUtil.getString(resultJson.get("msg")) + "\n第" + rowIndex
                    + "行数据长度有错，组织机构名称最多128位，请检查后再重新导入!");
            
            return false;
        }
        
        if (StringUtil.isNotEmpty(orgLevel)) {
            orgLevel = orgLevel.split("-")[0];
        }
        
        if (StringUtil.isNotEmpty(isChild)) {
            isChild = isChild.split("-")[0];
        }

        // 中文
        OrgInfoEntity tempEntity = new OrgInfoEntity();
        tempEntity.setSortId(Integer.parseInt(sortIdStr));
        tempEntity.setOrgId(orgId);
        tempEntity.setSpecificId(orgId);
        tempEntity.setOrgName(orgName);
        tempEntity.setOrgLevel(Integer.parseInt(orgLevel));
        tempEntity.setIsChild(isChild);
        tempEntity.setOrgStatus("1");
        tempEntity.setPid(pid);
        tempEntity.setSuperOrgId(superOrgId);
        
        entityList.add(tempEntity);

        return true;
    }
    
    /**
     * 新增或更新组织机构
     */
    @Override
	public JSONObject insertOrUpdateOrgInfo(OrgInfoEntity orgInfoSource, JSONObject resultJson) {
		if(null == orgInfoSource){
			return ResponseUtils.responseWebSuccessJson();
		}
		// 组织机构id
		String orgId = orgInfoSource.getOrgId();
		OrgInfoEntity orgInfoEntity = new OrgInfoEntity();
		// 判断orgId是否存在
		orgInfoEntity.setOrgId(orgId);
		orgInfoEntity = this.orgInfoMapper.queryOrgInfo(orgInfoEntity);
		if(null == orgInfoEntity){// 新增
			orgInfoEntity = new OrgInfoEntity();
			orgInfoEntity.setOrgId(orgId);
			orgInfoEntity.setPid(orgInfoSource.getPid());
			orgInfoEntity.setSpecificId(orgId);
			
			String superOrgId = orgInfoSource.getSuperOrgId();
			if(StringUtil.isEmpty(superOrgId) || "0".equals(superOrgId)){
				superOrgId = orgId;
			} else {
				superOrgId = orgInfoSource.getSuperOrgId();
			}
			orgInfoEntity.setSuperOrgId(superOrgId);
			orgInfoEntity.setOrgLevel(orgInfoSource.getOrgLevel());
			// 组织机构状态不在此设置
//			orgInfoEntity.setOrgStatus(orgInfoSource.getOrgStatus());
			// 设置排序
			int sortId = orgInfoSource.getSortId();
			if(0 >= sortId){
				OrgInfoEntity sortEntity = new OrgInfoEntity();
				sortEntity.setPid(orgInfoSource.getPid());
				sortId = orgInfoMapper.queryMaxSortId(sortEntity) + 1;
			}
			orgInfoEntity.setSortId(sortId);
    		
			orgInfoEntity.setOrgName(orgInfoSource.getOrgName());
			orgInfoEntity.setIsChild(orgInfoSource.getIsChild());
    		
    		LOGGER.info("新增" + orgInfoEntity);
			this.orgInfoMapper.save(orgInfoEntity);
			
		} else {// 修改
			orgInfoEntity.setPid(orgInfoSource.getPid());
			orgInfoEntity.setSpecificId(orgId);
			String superOrgId = orgInfoSource.getSuperOrgId();
			if(StringUtil.isEmpty(superOrgId) || "0".equals(superOrgId)){
				superOrgId = orgId;
			} else {
				superOrgId = orgInfoSource.getSuperOrgId();
			}
			orgInfoEntity.setSuperOrgId(superOrgId);
			orgInfoEntity.setOrgLevel(orgInfoSource.getOrgLevel());
			// 设置排序
			int sortId = orgInfoSource.getSortId();
			if(0 >= sortId){
				OrgInfoEntity sortEntity = new OrgInfoEntity();
				sortEntity.setPid(orgInfoSource.getPid());
				sortId = orgInfoMapper.queryMaxSortId(sortEntity) + 1;
			}
			orgInfoEntity.setSortId(sortId);
			orgInfoEntity.setOrgName(orgInfoSource.getOrgName());
			orgInfoEntity.setIsChild(orgInfoSource.getIsChild());

    		LOGGER.info("更新" + orgInfoEntity);
			this.orgInfoMapper.updateOrg(orgInfoEntity);
			
		}

		return ResponseUtils.responseWebSuccessJson();
	}
	
	/**
	 * 删除指定组织机构
	 */
	public JSONObject deleteOrgInfo(OrgInfoEntity orgInfoEntity, JSONObject resultJson) {
		OrgInfoEntity temp = new OrgInfoEntity();
		temp.setPid(orgInfoEntity.getOrgId());
		List<OrgInfoEntity> orgList = orgInfoMapper.queryOrgInfoList(temp);
		if(null != orgList && orgList.size() > 0){
			return ResponseUtils.responseWebErrorJson("还有子机构，必须先删除子机构后才能删除！");
		}
		
		PeoplePageEntity peoplePageEntity = new PeoplePageEntity();
		peoplePageEntity.setOrgId(orgInfoEntity.getOrgId());
		int count = this.peopleMapper.queryPeoplePageCount(peoplePageEntity);
		if(count > 0){
			return ResponseUtils.responseWebErrorJson("该机构下还有人员，必须先删除人员后才能删除！");
		}
		
		this.orgInfoMapper.del(orgInfoEntity);
		
		return ResponseUtils.responseWebSuccessJson();
	}

    /**
     * 查询orgId及子机构的in sql语句
     */
	@Override
    public String queryOrgPidIdsByPid(String orgId) {

        Map<String, String> orgMap = new HashMap<String, String>();
        orgMap.put("pid", orgId);
        List<Map<String, String>> orgMapList = this.orgInfoMapper.queryOrgListByPid(orgMap);

        if (null == orgMapList) {
            return "('')";
        }

        StringBuilder sb = new StringBuilder(500);
        sb.append("(");
        int size = orgMapList.size();
        for (int i = 0; i < size; i++) {
            Map<String, String> tmpMap = orgMapList.get(i);

            if (i == size - 1) {

                sb.append("'").append(tmpMap.get("orgId")).append("'");
            } else {
                sb.append("'").append(tmpMap.get("orgId")).append("',");
            }

        }
        sb.append(")");

        if ("()".equals(sb.toString())) {
            return "('" + orgId + "')";
        }

        return sb.toString();
    }
	
	/**
	 * 更新排序顺序
	 */
	@Transactional
	public JSONObject updateOrgSortId(JSONObject jsonObj) {
		if(null == jsonObj){
			return ResponseUtils.responseWebSuccessJson();
		}
		
		OrgInfoEntity entity = new OrgInfoEntity();
		entity.setOrgId(StringUtil.getString(jsonObj.get("orgId")));
		entity.setSortId(Integer.parseInt(StringUtil.getString(jsonObj.get("sortId"))));
		
		this.orgInfoMapper.updateOrgSortId(entity);
		
		entity.setOrgId(StringUtil.getString(jsonObj.get("changeOrgId")));
		entity.setSortId(Integer.parseInt(StringUtil.getString(jsonObj.get("changeSortId"))));
		
		this.orgInfoMapper.updateOrgSortId(entity);
		
		return ResponseUtils.responseWebSuccessJson();
	}
}
