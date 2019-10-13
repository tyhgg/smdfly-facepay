package com.tyhgg.manager.system.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tyhgg.asr.system.entity.PeopleEntity;
import com.tyhgg.asr.system.mapper.PeopleMapper;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerOrgService;
import com.tyhgg.manager.system.service.ManagerPeopleService;

/**
 * 行内用户导入和导出
 * 
 * @author zyt5668
 *
 */
@Controller
public class ManagerPeopleImportExportControl {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerPeopleImportExportControl.class);
	@Resource
	private PeopleMapper peopleMapper;
	@Resource
	private ManagerPeopleService managerPeopleService;
	@Resource
	private ManagerOrgService managerOrgService;

//	/**
//	 * 导入行内用户信息
//	 * 
//	 * @param header
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/manager/sysfile/importPeople", method = { RequestMethod.POST })
//	@ResponseBody
//	public String importPeople(HttpServletRequest request) {
//		JSONObject resultJson = new JSONObject();
//		MultipartHttpServletRequest requestParam = ((MultipartHttpServletRequest) request);
//
//		final String curEhrId = request.getHeader(SystemConstants.HEADER_USER_ID);
//		String loginOrgId = StringUtil.getString(StringUtil.getXssParameterValue(request, "loginOrgId"));
//		String importType = StringUtil.getString(StringUtil.getXssParameterValue(request, "importType"));
//		PeopleVo curPeopleVo = new PeopleVo();
//		curPeopleVo.setOrgId(loginOrgId);
//		curPeopleVo.setEhrId(curEhrId);
//
//		try {
//			// excel上传
//			MultiValueMap<String, MultipartFile> fileMap = requestParam.getMultiFileMap();
//
//			XSSFWorkbook workbook = null;
//			InputStream is = null;
//			for (List<MultipartFile> files : fileMap.values()) {
//
//				if (files.size() == 0) {
//					return StringUtil.createWebErrorMessage("未选择文件").toString();
//				}
//
//				for (MultipartFile file : files) {
//					String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//					LOGGER.info("文件格式-------------------------------------------------" + suffix);
//					if (!"xlsx".equalsIgnoreCase(suffix)) {
//
//						return StringUtil.createWebErrorMessage("只允许上传xlsx类型EXCEL文件!").toString();
//					}
//
//					// 处理文件上传，获取当前上传的文件
//					// 创建对Excel工作簿文件的引用
//					is = file.getInputStream();
//					workbook = new XSSFWorkbook(is);
//
//					if("2".equals(importType)){
//						this.peopleService.saveExcelPeopleByUserAuthorityAndExport(resultJson, workbook, curPeopleVo);
//					}else{
//						this.peopleService.saveExcelPeopleByUserAuthority(resultJson, workbook, curPeopleVo);
//					}
//					
//				}
//			}
//
//		} catch (Exception e) {
//			LOGGER.error("导入行内用户列表Excel出错！", e);
//			return StringUtil.createWebErrorMessage("导入行内用户出错!").toString();
//		}
//
//		return resultJson.toString();
//	}
//
//
//	/**
//	 * 导出行内用户信息
//	 * 
//	 * @param header
//	 * @param request
//	 * @param response
//	 * @param uriVariables
//	 */
//	@RequestMapping(value = "/manager/sysfile/exportPeople", method = { RequestMethod.POST, RequestMethod.GET })
//	public void exportPeople(HttpServletRequest request, HttpServletResponse response) {
//		try {
//
//			// 请求的报文体信息
//			String prama = StringUtil.getString(StringUtil.getXssParameterValue(request, "prama"));
//
//			JSONObject bodyJson = JSONObject.fromObject(prama);
//
//			String loginOrgId = StringUtil.getString(bodyJson.get("loginOrgId"));
//			String loginUserId = StringUtil.getString(bodyJson.get("loginUserId"));
//			String ehrId = StringUtil.getString(bodyJson.get("ehrId"));
//			String orgId = StringUtil.getString(bodyJson.get("orgId"));
//			String peopleName = StringUtil.getString(bodyJson.get("peopleName"));
//			String languageType = StringUtil.getString(bodyJson.get("languageType"));
//
//			response.setContentType("application/vnd-openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
//			response.setHeader("Content-Disposition", "attachment; filename=" + StringUtil.toUft8String("行内用户导出列表.xlsx"));
//
//			// 查询当前登录人信息，get方式请求，没有 header，前端head如何设置？？？
//			// final String headloginUserId =
//			// request.getHeader(SystemConstants.HEADER_USER_ID);
//
//			// 查询参数
//			PeoplePageVo peoplePageVo = new PeoplePageVo();
//			peoplePageVo.setEhrId(ehrId);
//			// peoplePageVo.setOrgId(orgId);
//			peoplePageVo.setPeopleName(peopleName);
//			if (StringUtil.isEmpty(languageType)) {
//				peoplePageVo.setLanguageType("zh");
//			} else {
//				peoplePageVo.setLanguageType(languageType);
//			}
//
//			peoplePageVo.setPageNum(1);
//			peoplePageVo.setPageSize(65535);
//
//			if (StringUtil.isNotEmpty(orgId)) {
//				String orgPidIds = this.managerOrgService.queryAllLevelOrgIdsByPid(orgId);
//				peoplePageVo.setOrgPidIds(orgPidIds);
//				// peoplePageVo.setOrgId(orgId);
//			} else {
//				if (!peopleService.peopleIsAdmin(loginUserId)) {
//					// 非管理员用户查询本机构及所有下级机构用户
//					String orgPidIds = this.managerOrgService.queryAllLevelOrgIdsByPid(loginOrgId);
//					peoplePageVo.setOrgPidIds(orgPidIds);
//				}
//			}
//
//			LOGGER.info("查询用户列表信息：" + peoplePageVo);
//			List<PeopleEntity> peopleList = this.peopleMapper.queryPeoplePageList(peoplePageVo);
//
//			OutputStream bos = new BufferedOutputStream(response.getOutputStream());
//			String[] headers = { "姓名", "EHR号", "所在机构", "机构ID", "性别", "标准职称", "标准职称ID", "显示职称", "当前职位聘用日期", "入行日期", "手机号",
//					"座机", "启用/禁用" };
//			// 导出excel
//			this.exportPeople2Excel("行内用户人员信息", headers, peopleList, bos, "yyyy-MM-dd");
//		} catch (Exception e) {
//			LOGGER.error("导出行内用户列表Excel出错！", e);
//			throw new SystemException(e, ExceptionCode.EC_000064, null, null);
//			
//		}
//	}
//
//	/**
//	 * 导出excel
//	 * 
//	 * @param title
//	 * @param headers
//	 * @param peopleList
//	 * @param out
//	 * @param pattern
//	 */
//	private void exportPeople2Excel(String title, String[] headers, List<PeopleEntity> peopleList, OutputStream out,
//			String pattern) {
//		// 声明一个工作薄
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		// 生成一个表格
//		XSSFSheet sheet = workbook.createSheet(title);
//		// 设置表格默认列宽度为15个字节
//		sheet.setDefaultColumnWidth(20);
//
//		// 定制化列宽
//		sheet.setColumnWidth(0, 2560);
//		sheet.setColumnWidth(1, 2560);
//
//		// 生成一个样式
//		XSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		XSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//		// 生成并设置另一个样式
//		XSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setWrapText(true);
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		XSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//
//		// 产生表格标题行
//		XSSFRow row = sheet.createRow(0);
//		for (short i = 0; i < headers.length; i++) {
//			XSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		int size = peopleList.size();
//		if (null != peopleList && size > 0) {
//			// 遍历集合数据，产生数据行
//			// row为0时为标题行
//			int rowNum = 1;
//			for (int i = 0; i < size; i++) {
//				row = sheet.createRow(rowNum++);
//				PeopleEntity entity = peopleList.get(i);
//				XSSFCell cell = row.createCell(0);
//				cell.setCellValue(entity.getPeopleName());
//				cell = row.createCell(1);
//				cell.setCellValue(entity.getEhrId());
//				cell = row.createCell(2);
//				cell.setCellValue(entity.getOrgName());
//				cell = row.createCell(3);
//				cell.setCellValue(entity.getOrgId());
//				cell = row.createCell(4);
//				cell.setCellValue(entity.getPeopleSex());
//				cell = row.createCell(5);
//				cell.setCellValue(entity.getTitleName());
//				cell = row.createCell(6);
//				cell.setCellValue(entity.getTitleId());
//				cell = row.createCell(7);
//				cell.setCellValue(entity.getPeopleTitle());
//				cell = row.createCell(8);
//				cell.setCellValue(entity.getTitleDate());
//				cell = row.createCell(9);
//				cell.setCellValue(entity.getInDate());
//				cell = row.createCell(10);
//				cell.setCellValue(entity.getPeopleTel());
//				cell = row.createCell(11);
//				cell.setCellValue(entity.getPeoplePhone());
//				cell = row.createCell(12);
//
//				if ("2".equals(entity.getPeopleStatus())) {
//					cell.setCellValue("禁用");
//				} else {
//					cell.setCellValue("启用");
//				}
//
//			}
//		}
//
//		try {
//			workbook.write(out);
//		} catch (IOException e) {
//			LOGGER.error("导出行内用户列表Excel出错！", e);
//			throw new SystemException(e, ExceptionCode.EC_000064, null, null);
//		}
//	}

	/**
	 * 批量导入用户手机号信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/manager/sysfile/importPeopleTel", method = { RequestMethod.POST })
	@ResponseBody
	public String importPeopleTel(HttpServletRequest request) {
		JSONObject resultJson = new JSONObject();
		MultipartHttpServletRequest requestParam = ((MultipartHttpServletRequest) request);
		List<PeopleEntity> peopleList = new ArrayList<PeopleEntity>(); 
		int totalSize = 0; //文件总数据
		int intSuccess = 0;//成功处理总数
		int failCount = 0;//失败总数

		// 文件中的ehrId
		List<String> ehrIdTxtList = new ArrayList<String>(1000);
		// 文件中的手机号
		List<String> peopleTelTxtList = new ArrayList<String>(1000);
		// 没有手机号的用户
		List<String> peopleEhrIdNoTelList = this.peopleMapper.queryPeopleNoTelList();
		// 已存在的手机号
		List<String> peopleTelList = this.peopleMapper.queryPeopleTelList();

		// 不需要处理的用户
		List<String> noDoEhrIdList = new ArrayList<String>(1000);
		
		BufferedReader br = null;
		try {
			// txt上传
			MultiValueMap<String, MultipartFile> fileMap = requestParam.getMultiFileMap();
			InputStream is = null;
			for (List<MultipartFile> files : fileMap.values()) {
				if (files.size() == 0) {
					return ResponseUtils.responseWebErrorJson("未选择文件").toString();
				}
				
				
				for (MultipartFile file : files) {
					String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
					LOGGER.info("处理文件---------" + file.getOriginalFilename());
					if (!"txt".equalsIgnoreCase(suffix)) {
						return ResponseUtils.responseWebErrorJson("只允许上传txt类型文本文件!").toString();
					}
					// 处理文件上传，获取当前上传的文件
					is = file.getInputStream();
					String readLine = null;
					br = new BufferedReader(new InputStreamReader(is));
					while((readLine=br.readLine()) != null){
						String [] line = readLine.split("\\|");
						if(line.length != 2 || StringUtil.isEmpty(line[0]) || StringUtil.isEmpty(line[1])){
							continue;
						}
						totalSize++;
						// 不需要处理的ehrId跳过
						if(ehrIdTxtList.contains(line[0]) || !peopleEhrIdNoTelList.contains(line[0])){
							LOGGER.info(line[0] + "-ehrId重复或不存在或者已有手机号");
							noDoEhrIdList.add(line[0] + "-ehrId重复或不存在或者已有手机号");
							continue;
						}

						// 不需要处理的手机号跳过
						if(peopleTelTxtList.contains(line[1]) || peopleTelList.contains(line[1]) || !StringUtil.isMobilePhone(line[1])){
							LOGGER.info(line[0] + "-手机号重复或者手机号不合法");
							noDoEhrIdList.add(line[0] + "-手机号重复或者手机号不合法");
							continue;
						}
						
						ehrIdTxtList.add(line[0]);
						peopleTelTxtList.add(line[1]);
						
						// 需要批量更新手机号
						PeopleEntity entry = new PeopleEntity();
						entry.setUserId(line[0]);
						entry.setPeopleTel(line[1]);
						peopleList.add(entry);
					}
				}
			}
			// 批量更新数据库，500条更新一次
			int size = peopleList.size();
			int pageSize = 500;
			int page = size / pageSize;
			int mod = size % pageSize;
			
			LOGGER.info("共需要处理" + size + "个手机号");
			
			if(size <= pageSize && size > 0){
				LOGGER.info("批量新增手机号:" + size);
				this.peopleMapper.updatePeopleByIdAndPhoneIsNullBatch(peopleList);
				intSuccess = size;
			} else {
				for(int i = 0; i < page; i++){
					try{
						LOGGER.info("批量新增手机号:第几次" + i);
						this.peopleMapper.updatePeopleByIdAndPhoneIsNullBatch(peopleList.subList(i * pageSize, (i + 1) * pageSize));
						intSuccess = intSuccess + pageSize;
						
						
						if(i == page - 1 && mod != 0){
							LOGGER.info("批量新增手机号:第几次" + i);
							this.peopleMapper.updatePeopleByIdAndPhoneIsNullBatch(peopleList.subList((i + 1) * pageSize, size));
							intSuccess = intSuccess + size - (i + 1) * pageSize ;
							
						}
						
					} catch (Exception e) {
						failCount = failCount + pageSize;
						LOGGER.error("批量新增手机号出错！", e);
						LOGGER.error(peopleList.subList(i * pageSize, (i + 1) * pageSize).toString());
					}
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("批量导入手机号出错！", e);
			return ResponseUtils.responseWebErrorJson("批量导入手机号出错!").toString();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
					LOGGER.error("文件流关闭失败！", e);
				}
			}
		}

		
		LOGGER.info("不需要处理的:" + noDoEhrIdList.toString());
		resultJson.put("result", "true");
		resultJson.put("msg", "需处理手机号" + totalSize + "条，成功新增" + intSuccess + "条，不需要处理" + noDoEhrIdList.size() + "条，处理失败" + failCount + "条");
		
		return ResponseUtils.responseSuccessJson(resultJson).toString();
	}
}
