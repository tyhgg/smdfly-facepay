package com.tyhgg.manager.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.system.entity.OrgInfoEntity;
import com.tyhgg.asr.system.entity.OrgInfoPageEntity;
import com.tyhgg.asr.system.mapper.OrgInfoMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.JsonXmlObjConvertUtils;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerOrgService;
import com.tyhgg.manager.system.service.ManagerPeopleService;

/**
 * 
 * @author zyt5668
 *
 */
@Controller
public class ManagerOrgController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerOrgController.class);

    @Resource
    private OrgInfoMapper orgInfoMapper;
    @Resource
    private ManagerOrgService managerOrgService;
    @Resource
    private ManagerPeopleService managerPeopleService;
    
    /**
     * 分页查询组织机构列表
     * @param header
     * @param body
     * @return
     */
    @RequestMapping(value = "/manager/orginfo/queryOrgInfoPageList", method = { RequestMethod.POST })
    @ResponseBody
    public Object queryOrgInfoPageList(@RequestHeader HttpHeaders header, @RequestBody String body) {

		// 返回结果
        JSONObject resultJson = new JSONObject();
        
        OrgInfoPageEntity orgInfoPageEntity = JsonXmlObjConvertUtils.jsonToObj(body, OrgInfoPageEntity.class);
        int pageSize = orgInfoPageEntity.getPageSize();
        if (0 == pageSize) {
            pageSize = 20;
        }
        int pageNum = orgInfoPageEntity.getPageNum();
        if (0 == pageNum) {
        	pageNum = 1;
        }
        orgInfoPageEntity.setPageNum(pageNum);
        orgInfoPageEntity.setPageSize(pageSize);
        orgInfoPageEntity.setOrgStatus("1");
        
        int totalCount = this.orgInfoMapper.queryOrgInfoPageCount(orgInfoPageEntity);
        LOGGER.info("机构总数为：" + totalCount);
        List<OrgInfoPageEntity> orgList = new ArrayList<OrgInfoPageEntity>();
        if (totalCount > 0) {
            orgList = this.orgInfoMapper.queryOrgInfoPageList(orgInfoPageEntity);
        }

        resultJson.put("total", totalCount);
        resultJson.put("rows", orgList);

        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }

    /**
     * 查询组织机构详情
     * @param header
     * @param body
     * @return
     */
    @RequestMapping(value = "/manager/orginfo/queryOrgInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Object queryOrgInfo(@RequestHeader HttpHeaders header, @RequestBody String body) {

        JSONObject resultJson = new JSONObject();
        JSONObject jsonBody = JSONObject.fromObject(body);

        OrgInfoEntity orgInfoEntity = new OrgInfoEntity();
        orgInfoEntity.setOrgId(StringUtil.getString(jsonBody.get("orgId")));
        
        orgInfoEntity = this.orgInfoMapper.queryOrgInfo(orgInfoEntity);
        
        resultJson.put("orginfo", orgInfoEntity);
        
        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }

    /**
     * 根据组织机构ID删除组织机构
     * @param header
     * @param body
     * @return
     */
    @RequestMapping(value = "/manager/orginfo/delOrgInfoById", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delOrgInfoById(@RequestHeader HttpHeaders header, @RequestBody String body) {

        JSONObject jsonBody = JSONObject.fromObject(body);
        String delOrgId = StringUtil.getString(jsonBody.get("orgId"));
        if (StringUtil.isEmpty(delOrgId)) {
            return ResponseUtils.responseWebErrorJson("请输入待删除的机构ID！");
        }

        OrgInfoEntity orgInfoEntity = new OrgInfoEntity();
        orgInfoEntity.setOrgId(delOrgId);
        JSONObject resultJson = new JSONObject();
        
        return this.managerOrgService.deleteOrgInfo(orgInfoEntity, resultJson);
        
    }

    /**
     * 新增或修改组织机构信息
     * @param header
     * @param body
     * @return
     */
    @RequestMapping(value = "/manager/orginfo/saveOrgInfo", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrgInfo(@RequestHeader HttpHeaders header, @RequestBody String body) {

    	OrgInfoEntity orgInfoEntity = JsonXmlObjConvertUtils.jsonToObj(body, OrgInfoEntity.class);
        JSONObject resultJson = new JSONObject();
        return this.managerOrgService.insertOrUpdateOrgInfo(orgInfoEntity, resultJson).toString();
        
    }

    /**
     * 根据机构号查询本机构和下级机构，如果是超级管理员用户可以查询所有的机构
     * 
     * @param header
     * @param body
     * @return
     */
    @RequestMapping(value = "/manager/orginfo/queryOrgListByPid", method = { RequestMethod.POST })
    @ResponseBody
    public String queryOrgListByPid(@RequestHeader HttpHeaders header, @RequestBody String body) {

        JSONObject resultJson = new JSONObject();

        JSONObject jsonBody = JSONObject.fromObject(body);
        String roleId = StringUtil.getString(jsonBody.get("roleId"));
        String orgId = StringUtil.getString(jsonBody.get("orgId"));

        // 系统错误码
        Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();
        if (StringUtil.isEmpty(orgId)) {
            return ResponseUtils.responseWebErrorJson(msgMap.get(ExceptionCode.EC_000007)).toString();
        }

        Map<String, String> orgMap = new HashMap<String, String>();
        // 管理员机构可以查看所有机构
        if (this.managerPeopleService.peopleIsAdmin(roleId, "")) {// 是管理员
            
        } else {
            orgMap.put("pid", orgId);
        }

        List<Map<String, String>> orgMapList = this.orgInfoMapper.queryOrgListByPid(orgMap);

        resultJson.put("orgList", orgMapList);

        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }
    
    /**
     * 根据组织机构级别取本级机构和一级机构
     * @方法名: getOrgByLevel
     * @方法描述: 
     * @param body
     * @return
     * @return Object
     */
    @RequestMapping(value = "/manager/orginfo/queryOrgListByLevel", method = { RequestMethod.POST })
    @ResponseBody
    public Object queryOrgListByLevel(@RequestHeader HttpHeaders header, @RequestBody String body) {
        JSONObject jsonBody = JSONObject.fromObject(body);
        String orgLevel = StringUtil.getString(jsonBody.get("orgLevel"));
        if (StringUtil.isEmpty(orgLevel)) {
            return ResponseUtils.responseWebErrorJson("请输入组织机构级别！");
        }
        // 返回结果
        JSONObject resultJson = new JSONObject();
        // 上级机构
        OrgInfoEntity orgInfoEntity = new OrgInfoEntity();
        orgInfoEntity.setOrgLevel(Integer.parseInt(orgLevel));
//        orgInfoEntity.setIsChild("2");
        List<OrgInfoEntity> upOrgList = orgInfoMapper.queryOrgInfoList(orgInfoEntity);
        resultJson.put("upOrgList", upOrgList);
        // 一级机构
        orgInfoEntity = new OrgInfoEntity();
        orgInfoEntity.setOrgLevel(1);
        List<OrgInfoEntity> orgList = orgInfoMapper.queryOrgInfoList(orgInfoEntity);
        resultJson.put("firstOrgList", orgList);
        
        return ResponseUtils.responseSuccessJson(resultJson);
    }
   
}
