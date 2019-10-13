package com.tyhgg.manager.system.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tyhgg.asr.system.entity.OrgInfoPageEntity;
import com.tyhgg.asr.system.mapper.OrgInfoMapper;
import com.tyhgg.core.framework.cache.SystemCacheHolder;
import com.tyhgg.core.framework.exception.ExceptionCode;
import com.tyhgg.core.framework.exception.SystemException;
import com.tyhgg.core.framework.util.ResponseUtils;
import com.tyhgg.core.framework.util.StringUtil;
import com.tyhgg.manager.system.service.ManagerOrgService;
import com.tyhgg.manager.system.service.ManagerPeopleService;

/**
 * 文件导入导出
 * @类名称: ManagerOrgImportControl
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年6月26日 上午9:24:22
 * @修改备注：
 */
@Controller
public class ManagerOrgImportControl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerOrgImportControl.class);

	@Resource
	private ManagerOrgService managerOrgService;
    @Resource
    private OrgInfoMapper orgInfoMapper;
    @Resource
    private ManagerPeopleService peopleService;

	@RequestMapping(value = "/manager/sysfile/importOrginfo", method = {RequestMethod.POST })
	@ResponseBody
	public Object orginfoImport(HttpServletRequest request) {

		JSONObject resultJson = new JSONObject();
		MultipartHttpServletRequest upload = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> fileMap = upload.getMultiFileMap();
		List<MultipartFile> allFiles = new ArrayList<MultipartFile>();
		for (List<MultipartFile> files : fileMap.values()) {
			allFiles.addAll(files);
		}
		if (allFiles.size() != 1) {
			return ResponseUtils.responseWebErrorJson("未选择导入文件或请上传单个文件!");
		}
		MultipartFile uploadFile = allFiles.get(0);
		String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1);
		LOGGER.info("文件格式-------------------------------------------------" + suffix);
		if (!"xlsx".equalsIgnoreCase(suffix)) {
			return ResponseUtils.responseWebErrorJson("只允许上传.xlsx类型EXCEL文件!");
		}

		// 处理文件上传，获取当前上传的文件
		XSSFWorkbook workbook = null;
		// 创建对Excel工作簿文件的引用
		InputStream is = null;
		try {
			is = uploadFile.getInputStream();
			workbook = new XSSFWorkbook(uploadFile.getInputStream());
			
			// 导入execl
			this.managerOrgService.saveOrgFile(workbook, resultJson);
		} catch (Exception e) {
			LOGGER.error("", e);
			return ResponseUtils.responseWebErrorJson("导入组织机构信息错误!");
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					LOGGER.debug("", e);
				}
			}
			if (null != workbook) {
				try {
					workbook.close();
				} catch (Exception e) {
					LOGGER.debug("", e);
				}
			}
		}
		return ResponseUtils.responseSuccessJson(resultJson);
	}

	/**
	 * Excel模板下载,GET方式下载
	 * 
	 * @param header
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/manager/getsysfile/downloadSysTemplate", method = {RequestMethod.GET})
	public void downSysExeclTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String templateName = StringUtil.getString(StringUtil.getXssParameterValue(request, "templateName"));
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename=\"" + templateName + "\"");
		Map<String, String> systemPropertyMap = SystemCacheHolder.getSystemPropertyCache().getMaps();
		String templatePath = systemPropertyMap.get("sys.template.path");

		if (StringUtil.isEmpty(templatePath)) {
			templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "template/"
					+ templateName;
		} else {
			templatePath = templatePath + templateName;
		}

		long fileLength = new File(templatePath).length();
		response.setHeader("Content-Length", String.valueOf(fileLength));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(templatePath));

			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (FileNotFoundException e1) {
			LOGGER.error("找不到文件模板：" + templatePath, e1);
			throw new SystemException(e1, ExceptionCode.EC_000024, null, new String[]{templateName});
		} catch (Exception e) {
			LOGGER.error("下载文件出错：", e);
			throw new SystemException(e, ExceptionCode.EC_000025, null, new String[]{templateName});
		} finally {
			if (null != bis) {
				bis.close();
			}
			if (null != bos) {
				bos.close();
			}
		}

	}

	/**
	 * 导出组织机构信息，POST方式下载文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/manager/sysfile/exportOrgInfo", method = {RequestMethod.POST})
	public void exportOrgInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		try {

			// 请求的报文体信息
//			String prama = StringUtil.getString(StringUtil.getXssParameterValue(request, "prama"));
			LOGGER.info("请求参数:" + body);
			JSONObject bodyJson = JSONObject.fromObject(body);

//			String userId = StringUtil.getString(bodyJson.get("userId"));
//			String oneOrgId = StringUtil.getString(bodyJson.get("oneOrgId"));
			String queryOrgId = StringUtil.getString(bodyJson.get("queryOrgId"));
			String orgName = StringUtil.getString(bodyJson.get("orgName"));
//	        String orgLevel = StringUtil.getString(bodyJson.get("orgLevel"));

			response.setContentType("application/vnd-openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + StringUtil.toUft8String("组织机构导出列表.xlsx"));

			// 查询当前登录人信息，get方式请求，没有 header，前端head如何设置？？？
			// final String headloginUserId =
			// request.getHeader(SystemConstants.HEADER_USER_ID);

//	        OrgInfoPageEntity orgInfoPageEntity = JsonXmlObjConvertUtils.jsonToObj(prama, OrgInfoPageEntity.class);
			OrgInfoPageEntity orgInfoPageEntity = new OrgInfoPageEntity();
	        orgInfoPageEntity.setPageNum(1);
	        orgInfoPageEntity.setPageSize(65535);
	        orgInfoPageEntity.setOrgStatus("1");
	        orgInfoPageEntity.setOrgId(queryOrgId);
	        orgInfoPageEntity.setOrgName(orgName);
	        
	        List<OrgInfoPageEntity> orgList = this.orgInfoMapper.queryOrgInfoPageList(orgInfoPageEntity);
			
			OutputStream bos = new BufferedOutputStream(response.getOutputStream());
			String[] headers = { "全局显示顺序（必输项）", "组织机构编号（必输项）", "组织机构名称（必输项）", "组织机构名称（英文）", 
					"组织机构层级（必输项）", "上级机构编号（必输项）", "是否有子机构（必输项）", "最上级机构编号（必输项）"};
			// 导出excel
			this.export2Excel("组织机构信息", headers, orgList, bos, "yyyy-MM-dd");
		} catch (Exception e) {
			LOGGER.error("导出组织机构列表Excel出错！", e);
			throw new SystemException(e, ExceptionCode.EC_FAIL, null, null);
		}
	}

	/**
	 * 导出excel
	 * 
	 * @param title
	 * @param headers
	 * @param peopleList
	 * @param out
	 * @param pattern
	 */
	private void export2Excel(String title, String[] headers, List<OrgInfoPageEntity> orgList, OutputStream out,
			String pattern) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);

		// 定制化列宽
		sheet.setColumnWidth(0, 2560);
		sheet.setColumnWidth(1, 2560);

		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setWrapText(true);
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		int size = orgList.size();
		if (null != orgList && size > 0) {
			// 遍历集合数据，产生数据行
			// row为0时为标题行
			int rowNum = 1;
			for (int i = 0; i < size; i++) {
				row = sheet.createRow(rowNum++);
				OrgInfoPageEntity entity = orgList.get(i);
				
				XSSFCell cell = row.createCell(0);
				cell.setCellValue(entity.getSortId());
				cell = row.createCell(1);
				cell.setCellValue(entity.getOrgId());
				cell = row.createCell(2);
				cell.setCellValue(entity.getOrgName());
				cell = row.createCell(3);
				cell.setCellValue(entity.getOrgNameEn());
				cell = row.createCell(4);
				
				String orgLevel = StringUtil.getString(entity.getOrgLevel());
				if("1".equals(orgLevel)){
					cell.setCellValue("1-一级");
				}else if("2".equals(orgLevel)){
					cell.setCellValue("2-二级");
				} else if("3".equals(orgLevel)){
					cell.setCellValue("3-三级");
				} else if("4".equals(orgLevel)){
					cell.setCellValue("4-四级");
				} else if("5".equals(orgLevel)){
					cell.setCellValue("5-五级");
				} else {
					cell.setCellValue("其他");
				}
				
				cell = row.createCell(5);
				cell.setCellValue(entity.getPid());
				cell = row.createCell(6);
				String isChild = StringUtil.getString(entity.getIsChild());
				if("2".equals(isChild)){
					cell.setCellValue("2-有");
				} else {
					cell.setCellValue("1-没有");
				}
				cell = row.createCell(7);
				cell.setCellValue(entity.getSuperOrgId());
				cell = row.createCell(8);
			}
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			LOGGER.error("导出组织机构列表Excel出错！", e);
			throw new SystemException(e, ExceptionCode.EC_FAIL, null, null);
		} finally {
			if(null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOGGER.error("关闭资源异常：", e);
				}
			}
		}
	}

}
