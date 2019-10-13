package com.tyhgg.manager.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyhgg.asr.system.entity.ModuleMenuEntity;
import com.tyhgg.asr.system.entity.RoleEntity;
import com.tyhgg.asr.system.entity.RoleMenuRelEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelEntity;
import com.tyhgg.asr.system.entity.RolePeopleRelPageEntity;
import com.tyhgg.asr.system.entity.RoleRelEntity;
import com.tyhgg.asr.system.mapper.ModuleMenuMapper;
import com.tyhgg.asr.system.mapper.OrgInfoMapper;
import com.tyhgg.asr.system.mapper.RoleMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerRoleService;

/**
 * 角色管理
 * @author zyt5668
 *
 */
@Controller
public class ManagerRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerRoleController.class);

	@Resource
    private RoleMapper roleMapper;
	
	@Resource
    private OrgInfoMapper orgInfoMapper;
	
	@Resource
    private ModuleMenuMapper moduleMenuMapper;
	
	@Resource
    private ManagerRoleService managerRoleService;

	/**
	 * 查询角色列表
	 * @方法名: queryRoleList
	 * @方法描述: 
	 * @param header
	 * @param request
	 * @param body
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/manager/role/queryRoleList", produces = { "application/json;charset=utf-8" }, method = { RequestMethod.POST })
	@ResponseBody
    public Object queryRoleList(HttpServletRequest request, @RequestBody String body) {
		// 返回结果
		JSONObject resultJson = new JSONObject();
		JSONObject bodyJson =  JSONObject.fromObject(body);
		
	    RoleEntity roleEntity = new RoleEntity();
	    roleEntity.setRoleId(StringUtil.getString(bodyJson.get("roleId")));
	    roleEntity.setRoleName(StringUtil.getString(bodyJson.get("roleName")));
	    roleEntity.setRoleIds(StringUtil.getString(bodyJson.get("loginRoleId")));
	    
		List<RoleEntity> roleList = roleMapper.queryRoleList(roleEntity);
	    if(null != roleList){
	    	resultJson.put("total", roleList.size());
	    	resultJson.put("rows", roleList);
	    } else {
	    	resultJson.put("total", 0);
	    	resultJson.put("rows", "[]");
	    }
		
        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }
	
	/**
	 * 查询角色详情
	 * @方法名: queryRoleInfo
	 * @方法描述: 
	 * @param header
	 * @param request
	 * @param body
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/manager/role/toUpdateRole", produces = { "application/json;charset=utf-8" }, method = {RequestMethod.POST })
	@ResponseBody
    public Object queryRoleInfo(HttpServletRequest request, @RequestBody String body) {
		// 返回结果
		JSONObject resultJson = new JSONObject();
		JSONObject bodyJson =  JSONObject.fromObject(body);
    	String roleId = StringUtil.getString(bodyJson.get("roleId"));
        RoleEntity roleEntity = roleMapper.queryRole(roleId);
        resultJson.put("roleInfo", roleEntity);

        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }
	
	/**
	 * 查询角色菜单
	 * @方法名: queryRoleMenuList
	 * @方法描述: 
	 * @param header
	 * @param request
	 * @param body
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/manager/role/queryRoleMenuList", produces = { "application/json;charset=utf-8" }, method = { RequestMethod.POST })
	@ResponseBody
    public Object queryRoleMenuList(HttpServletRequest request, @RequestBody String body) {
		// 返回结果
		JSONObject resultJson = new JSONObject();
		JSONObject bodyJson =  JSONObject.fromObject(body);

		// 系统错误码
		Map<String, String> msgMap = SystemCacheHolder.getMsgCache().getMaps();
		
    	String roleId = StringUtil.getString(bodyJson.getString("roleId"));
    	if(StringUtil.isEmpty(roleId)){
    		return ResponseUtils.responseWebErrorJson(msgMap.get(ExceptionCode.EC_000007));
    	}

        // 所有一级菜单
    	ModuleMenuEntity moduleMenuEntity = new ModuleMenuEntity();
    	// 一级菜单的pid是-1
    	moduleMenuEntity.setModPid(-1);
    	// 查询所有
    	moduleMenuEntity.setRoleIds("1");
    	moduleMenuEntity.setModStatus("0");
    	LOGGER.info("查询所有一级菜单：" + moduleMenuEntity);
    	List<ModuleMenuEntity>  moduleMenuList = this.moduleMenuMapper.queryModuleMenuList(moduleMenuEntity);
    	
    	// 查询角色已分配的菜单权限
    	RoleMenuRelEntity roleMenuRelEntity = new RoleMenuRelEntity();
    	roleMenuRelEntity.setRoleId(roleId);
    	LOGGER.info("查询角色已分配的菜单权限：" + roleId);
    	List<String> roleMenuRelStrList = this.roleMapper.queryRoleMenuRelStrList(roleId);
    	LOGGER.info("查询角色已分配的菜单权限结果：" + roleMenuRelStrList);
    	
    	List<ModuleMenuEntity> menuFirstList = new ArrayList<ModuleMenuEntity>();
        // 查询二级菜单
    	int size = moduleMenuList.size();
        for(int i = 0; i < size; i++){
        	ModuleMenuEntity menuEntity = moduleMenuList.get(i);

        	// 查询二级菜单
        	ModuleMenuEntity secondMenu = new ModuleMenuEntity();
        	secondMenu.setModPid(menuEntity.getModId());
        	secondMenu.setRoleIds("1");
        	secondMenu.setModStatus("0");

        	LOGGER.info("查询所有二级菜单：" + secondMenu);
        	List<ModuleMenuEntity> secondModuleMenuList = this.moduleMenuMapper.queryModuleMenuList(secondMenu);  
        	
        	// 设置已分配权限的菜单为选中状态
        	if(null != roleMenuRelStrList && roleMenuRelStrList.size() > 0 && null != secondModuleMenuList){
        		int secondSize = secondModuleMenuList.size();
        		for(int j = 0; j < secondSize; j++){
        			ModuleMenuEntity tempEntity = secondModuleMenuList.get(j);
        			// 设置选中状态
        			if(roleMenuRelStrList.contains(String.valueOf(tempEntity.getModId()))){
        				tempEntity.setIsChecked("1");
        				tempEntity.setVueChecked(true);
        			} else {
        				tempEntity.setIsChecked("0");
        				tempEntity.setVueChecked(false);
        			}
        		}
        	}
        	
        	menuEntity.setModuleMenuList(secondModuleMenuList);
        	menuFirstList.add(menuEntity);
        }
        
        resultJson.put("menuFirstList", menuFirstList);
        
        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }
	
	/**
	 * 分页查询角色人员列表
	 * @方法名: queryRolePeopleRelInList
	 * @方法描述: 
	 * @param header
	 * @param request
	 * @param body
	 * @return
	 * @return Object
	 */
    @RequestMapping(value = "/manager/role/queryRolePeopleList", produces = { "application/json;charset=utf-8" }, method = {RequestMethod.POST })
	@ResponseBody
    public Object  queryRolePeopleRelPageList(HttpServletRequest request, @RequestBody String body){

		JSONObject bodyJson =  JSONObject.fromObject(body);
    	String roleId = StringUtil.getString(bodyJson.get("roleId"));
    	int pageSize = Integer.parseInt(StringUtil.getString(bodyJson.get("pageSize")));
        if (0 == pageSize) {
            pageSize = 20;
        }
        int page = Integer.parseInt(StringUtil.getString(bodyJson.get("page")));
        if (0 == page) {
            page = 1;
        }

    	RolePeopleRelPageEntity rolePeopleRelPageEntity = new RolePeopleRelPageEntity();
    	rolePeopleRelPageEntity.setPageNum(page);
    	rolePeopleRelPageEntity.setPageSize(pageSize);
    	rolePeopleRelPageEntity.setRoleId(roleId);
		int totalCount = this.roleMapper.queryRolePeopleRelPageCount(rolePeopleRelPageEntity);
		// 分页查询角色人员列表
		List<RolePeopleRelPageEntity> rolePeopleRelPageList = null;
		if (totalCount > 0) {
			rolePeopleRelPageList = this.roleMapper.queryRolePeopleRelPageList(rolePeopleRelPageEntity);
		} else {
			rolePeopleRelPageList = new ArrayList<RolePeopleRelPageEntity>();
		}

    	// 返回结果
        JSONObject resultJson = new JSONObject();
        resultJson.put("total", totalCount);
        resultJson.put("rows", rolePeopleRelPageList);
        
        return ResponseUtils.responseSuccessJson(resultJson).toString();
    }

    @RequestMapping(value = "/manager/role/deleteRolePeopleRel",produces = { "application/json;charset=utf-8" }, method={RequestMethod.POST})
	@ResponseBody
    public String deleteRolePeopleRel(HttpServletRequest request, @RequestBody String body){
    	// 返回结果
		JSONObject bodyJson =  JSONObject.fromObject(body);
    	String roleId = StringUtil.getString(bodyJson.get("roleId"));
    	String userId = StringUtil.getString(bodyJson.get("userId"));
    	RolePeopleRelEntity rolePeopleRelTemp = new RolePeopleRelEntity();
    	rolePeopleRelTemp.setRoleId(roleId);
    	rolePeopleRelTemp.setUserId(userId);
    	
        this.roleMapper.deleteRolePeopleRel(rolePeopleRelTemp);
    	
    	return ResponseUtils.responseSuccessJson(null).toString();

    }
    
    /**
	 * 保存角色及角色菜单信息
	 */
    @RequestMapping(value = "/manager/role/saveRole", produces = { "application/json;charset=utf-8" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveRole(HttpServletRequest request, @RequestBody String body) {
    	// 返回结果
		JSONObject bodyJson =  JSONObject.fromObject(body);
    	String roleId = StringUtil.getString(bodyJson.get("roleId"));
    	String roleName = StringUtil.getString(bodyJson.get("roleName"));
    	String roleRem = StringUtil.getString(bodyJson.get("roleRem"));
    	String menus = StringUtil.getString(bodyJson.get("menuList"));
    	List<ModuleMenuEntity> menuList = new ArrayList<ModuleMenuEntity>();
    	if(StringUtil.isNotEmpty(menus) && !"null".equals(menus)){
    		// 角色菜单信息
	    	JSONArray firstMenuListJsonArray = bodyJson.getJSONArray("menuList");
	    	JSONArray.toCollection(firstMenuListJsonArray);
	    	if(firstMenuListJsonArray != null && firstMenuListJsonArray.size()>0){
	    		for(int i = 0; i < firstMenuListJsonArray.size(); i++){
	    			JSONObject json = firstMenuListJsonArray.getJSONObject(i);
	    			JSONArray roleMenuListJson = json.getJSONArray("moduleMenuList");
	    			@SuppressWarnings("unchecked")
					List<ModuleMenuEntity> roleMenuRelList = (List<ModuleMenuEntity>)JSONArray.toCollection(roleMenuListJson, ModuleMenuEntity.class);
	    			menuList.addAll(roleMenuRelList);
	    		}
	    	}
    	}
    	RoleRelEntity roleRelEntity = new RoleRelEntity();
    	roleRelEntity.setRoleId(roleId);
    	roleRelEntity.setRoleName(roleName);
    	roleRelEntity.setRoleRem(roleRem);
    	roleRelEntity.setModuleMenuList(menuList);
    	
    	this.managerRoleService.saveRoleAndMenuRel(roleRelEntity);
    	
    	return ResponseUtils.responseSuccessJson(null).toString();
	}
    
    /**单独添加角色人员:将选择的用户添加到编辑的角色中
     * @param model
     * @param request
     * @param selectedPeos
     * @param selectedRoleId
     * @return
     */
    @RequestMapping(value = "/manager/role/saveSelectRolePeople", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public Object saveSelectRolePeople(HttpServletRequest request, @RequestBody String body) {

		// 导入参会人员
		String[] peolist = null;
		JSONObject bodyJson = JSONObject.fromObject(body);
		String selectedRoleId = StringUtil.getString(bodyJson.get("selectedRoleId"));
		String selectedPeos = StringUtil.getString(bodyJson.get("selectedPeos"));
		if (selectedPeos != null) {
			peolist = selectedPeos.split(",");
		}
		// JSONArray jsonArray = JSONArray.fromObject(selectedPeos);
		if (StringUtil.isEmpty(selectedPeos)) {
			return ResponseUtils.responseWebErrorJson("人员不能为空!");
		}
		this.managerRoleService.addSelectedRolePeo(selectedRoleId, peolist);

		return ResponseUtils.responseWebSuccessJson();

	}
    
    /**
	 * 获取所有未选择人的菜单树
	 * 1.未选择一级节点时加载一级节点，选择了一级节点时加载二级节点，逐级加载
	 * 2.加载一级节点时先过滤用户权限机构
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/manager/role/getUnselectRolePeopleList", method = { RequestMethod.POST })
    @ResponseBody
    public Object getUnselectRolePeopleList(HttpServletRequest request, @RequestBody String body) {

    	// 返回结果
    	JSONObject resultJson = new JSONObject();

		JSONObject bodyJson =  JSONObject.fromObject(body);
    	String roleId = StringUtil.getString(bodyJson.get("roleId"));
    	String peopleName = StringUtil.getString(bodyJson.get("peopleName"));
    	
    	RolePeopleRelEntity rolePeopleRelEntity = new RolePeopleRelEntity();
    	rolePeopleRelEntity.setRoleId(roleId);
    	// peopleName有值时是按姓名模糊搜索，没值时查询全部
    	rolePeopleRelEntity.setPeopleName(peopleName);
    	
    	List<RolePeopleRelEntity> unselectRolePeopleList = this.roleMapper.getUnselectRolePeopleListByRoleId(rolePeopleRelEntity); 
    	
    	resultJson.put("unselectRolePeopleList", unselectRolePeopleList);
    	
    	return ResponseUtils.responseSuccessJson(resultJson).toString();
        
    }
    
    /**获取匹配人员与机构树（默认支持三级机构及下属人员，可递归扩展）
     * 支持会议人员，角色人员搜索
     * @param model
     * @param request
     * @return
     *//*
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/manager/role/getMatchedRolePeopleOrgTree", method = { 
            RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public Object getMatchedRolePeopleOrgTree(@RequestHeader HttpHeaders header,
            HttpServletRequest request, @RequestBody String body,
            @RequestParam Map<String, List<MeetTypeEntity>> uriVariables) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
       JSONObject reObject = new JSONObject();
       
        JSONObject bodyJson = JSONObject.fromObject(body);
                 
        String peopleName = StringUtil
                .getString(bodyJson.get("peopleName"));
        //获取当前登录ehrid
        String curEhrId = StringUtil
                .getString(bodyJson.get("ehrId"));
        //获取当前登录orgid
        String curOrgId = StringUtil
                .getString(bodyJson.get("orgId"));
        //获取当前编辑的roleid
        String roleId = StringUtil
        		.getString(bodyJson.get("roleId"));
        
        if (StringUtil.isEmpty(peopleName)) {
            return null;
        } else {
            try {
                String peopleNameS = URLDecoder.decode(peopleName, "UTF-8");
                peopleNameS=peopleNameS.replaceAll("%", "\\\\%");
                //list = meetingMapper.getFatherOrgInfos();
                boolean isAdmin = peopleService.peopleIsAdmin("", curEhrId);
                if(isAdmin){
					list = this.orgInfoMapper.getFirstOrgTreeOpenList();
				}else{
					list = this.orgInfoMapper.getSelfOrgTreeList("("+curOrgId+")");//过滤用户权限
				}

                Iterator<Map<String, Object>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map a = iterator.next();
                    List<Map<String, Object>> pList = this.roleMapper.getUnselectRolePeople(a.get("orgId").toString(), roleId, peopleNameS);
                    for(Map m:pList){
                    	m.put("isLeaf", true);
                    }
                    List<Map<String, Object>> sonList = this.orgInfoMapper.getSonOrgTreeOpenList(a.get("orgId").toString());
                    if (sonList != null && sonList.size() > 0) {
                        Iterator<Map<String, Object>> siterator = sonList.iterator();
                        while (siterator.hasNext()) {
                            Map s = siterator.next();
                            List<Map<String, Object>> psList = roleMapper.getUnselectRolePeople(s.get("orgId").toString(), roleId,peopleNameS);
                            
                            for(Map m:psList){
                            	m.put("isLeaf", true);
                            }
                            
                            List<Map<String, Object>> sonsList = orgInfoMapper.getSonOrgTreeOpenList(s.get("orgId").toString());
                            if (sonsList != null && sonsList.size() > 0) {
                                Iterator<Map<String, Object>> ssiterator = sonsList.iterator();
                                while (ssiterator.hasNext()) {
                                    Map ss = ssiterator.next();
                                    List<Map<String, Object>> pssList = roleMapper.getUnselectRolePeople(ss.get("orgId").toString(),
                                                    roleId,peopleNameS);
                                    if (pssList != null && pssList.size() > 0) {
                                    	
                                    	 for(Map m:pssList){
                                         	m.put("isLeaf", true);
                                         }
                                    	
                                        ss.put("children", pssList);
                                    } else {
                                        ssiterator.remove();
                                    }
                                }
                                if (sonsList != null && sonsList.size() > 0) {
                                    psList.addAll(sonsList);
                                }
                            }
                            if (psList != null && psList.size() > 0) {
                                s.put("children", psList);
                            } else {
                                siterator.remove();
                            }
                        }

                        if (sonList != null && sonList.size() > 0) {
                            pList.addAll(sonList);
                        }
                    }

                    if (pList != null && pList.size() > 0) {
                        a.put("children", pList);
                    } else {
                        iterator.remove();
                    }

                }
                reObject.put("data", JSONArray.fromObject(list));
              //  return JSONArray.fromObject(list);
                return reObject;
            } catch (Exception e) {
             //   logger.error("获取机构树列表出错！", e);
            	 reObject.put("data", JSONArray.fromObject(list));
            	 return reObject;
               // return JSONArray.fromObject(list);
            }
        }
           
    }
    */
}
