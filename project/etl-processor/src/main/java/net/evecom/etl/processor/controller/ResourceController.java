package net.evecom.etl.processor.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.core.db.annotatoin.Token;
import net.evecom.core.rbac.base.BaseController;
import net.evecom.core.db.model.entity.Resources;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.resource.model.service.SysFileService;
import net.evecom.tools.constant.consts.SuccessConst;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.utils.report.exl.ImportExcel;
import net.evecom.tools.exception.CommonException;
import net.evecom.tools.service.Page;
import net.evecom.tools.service.Result;
import net.evecom.utils.file.PropertiesUtils;
import net.evecom.utils.string.RandomUtil;
import net.evecom.utils.string.StringUtil;
import net.evecom.utils.verify.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resources")
@Api(tags = "资源管理模块")
public class ResourceController extends BaseController {
    @Resource
	private ResourceService resourceService;

    @Resource
	private ObjectMapper objectMapper;

	@ApiOperation(value = "新增指定资源的数据", notes = "对指定资源新增数据")
	@Token(remove = true)
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "data", value = "数据(json格式对象)", dataType = "string", paramType = "query", required = true),
			@ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
	public Result<?> add(@PathVariable(value = "resourceName") String name, String data) throws Exception {
		if (CheckUtil.isNull(data)) {
			throw new CommonException(CommonException.DATA_NULL);
		}
		Resources resource = resourceService.get(name);
		Class<?> itemBean = Class.forName(resource.getClasspath());
		Object entity = null;
		try {
			entity = objectMapper.readValue(data, itemBean);
		} catch (Exception e) {
			throw new CommonException(CommonException.JSON_FORMAT_ERROR);
		}
		return Result.success(SuccessConst.OPERATE_SUCCESS, resourceService.add(entity));
	}

	@ApiOperation(value = "根据动态条件查询数据列表", notes = "根据动态条件查询数据列表")
	@RequestMapping(value = "/{resourceName}/list", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "动态条件(json格式QueryParam对象)", dataType = "string", paramType = "query", required = true) })
	public Result<Page<?>> list(@PathVariable(value = "resourceName") String name, String param) throws Exception {
		if (CheckUtil.isNull(param)) {
			throw new CommonException(CommonException.PARAM_NULL);
		}
		QueryParam<?> queryParam = null;
		try {
			queryParam = objectMapper.readValue(param, QueryParam.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(CommonException.JSON_FORMAT_ERROR);
		}
		Resources resources = resourceService.get(name);
		return Result.success(SuccessConst.OPERATE_SUCCESS, resourceService.list(resources, queryParam));
	}

	@ApiOperation(value = "删除指定资源的数据", notes = "删除指定资源的数据")
	@Token(remove = true)
	@RequestMapping(value = "/{resourceName}/{id}", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
	public Result<?> delete(@PathVariable(value = "resourceName") String name, @PathVariable(value = "id") Long id)
			throws Exception {
		Resources resource = resourceService.get(name);
		resourceService.delete(resource, id);
		return Result.success(SuccessConst.OPERATE_SUCCESS);
	}

	@ApiOperation(value = "批量删除指定资源的数据", notes = "批量删除指定资源的数据")
	@Token(remove = true)
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "删除id集合(json格式int数组)", dataType = "string", paramType = "query", required = true),
			@ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
	public Result<?> deletes(@PathVariable(value = "resourceName") String name, String ids) throws Exception {
		Resources resource = resourceService.get(name);
		if (CheckUtil.isNull(ids)) {
			throw new CommonException(CommonException.PARAM_NULL);
		}
		List<Long> idsArray = objectMapper.readValue(ids, new TypeReference<List<Long>>() {
		});
		Long[] array = new Long[idsArray.size()];
		idsArray.toArray(array);
		return Result.success(SuccessConst.OPERATE_SUCCESS, resourceService.delete(resource, array));
	}

	@ApiOperation(value = "更新指定资源的数据", notes = "更新指定资源的数据")
	@Token(remove = true)
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.PUT)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "data", value = "数据(json格式对象)", dataType = "string", paramType = "query", required = true),
			@ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
	public Result<?> edit(@PathVariable(value = "resourceName") String name, String data) throws Exception {
		Resources resource = resourceService.get(name);
		Class<?> itemBean = Class.forName(resource.getClasspath());
		if (CheckUtil.isNull(data)) {
			throw new CommonException(CommonException.DATA_NULL);
		}
		Object entity = null;
		try {
			entity = objectMapper.readValue(data, itemBean);
		} catch (Exception e) {
			throw new CommonException(CommonException.JSON_FORMAT_ERROR);
		}
		return Result.success(SuccessConst.OPERATE_SUCCESS, resourceService.update(entity));
	}

	@ApiOperation(value = "获取指定资源单条数据", notes = "获取指定资源单条数据")
	@RequestMapping(value = "/{resourceName}/{id}", method = RequestMethod.GET)
	public Result<?> get(@PathVariable(value = "resourceName") String name, @PathVariable(value = "id") Long id)
			throws Exception {
		Resources resources = resourceService.get(name);
		return Result.success(SuccessConst.OPERATE_SUCCESS, resourceService.get(resources, id));
	}

//    @ApiOperation(value = "获取指定资源单条数据", notes = "获取指定资源单条数据")
//    @RequestMapping(value = "/{resourceName}/count", method = RequestMethod.GET)
//    public Result<?> count(@PathVariable(value = "resourceName") String name) throws Exception {
//
//        return Result.success(SuccessConst.OPERATE_SUCCESS, "[{type:1,}]");
//    }

	@ApiOperation(value = "指定资源的导入模板下载", notes = "指定资源的导入模板下载")
	@RequestMapping(value = "/{resourceName}/importTemplate", method = RequestMethod.POST)
	public Result<?> importTemplate(@PathVariable(value = "resourceName") String name) throws Exception {
		Resources resource = resourceService.get(name);
		resourceService.importTemplate(resource, response);
		return null;
	}

	@ApiOperation(value = "导出指定资源的数据", notes = "导出指定资源数据")
	@Token(remove = true)
	@RequestMapping(value = "/{resourceName}/export", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "动态条件(json格式QueryParam对象)", dataType = "string", paramType = "query", required = true) })
	public Result<?> export(@PathVariable(value = "resourceName") String name, String param) throws Exception {
		if (CheckUtil.isNull(param)) {
			throw new CommonException(CommonException.PARAM_NULL);
		}
		QueryParam<?> queryParam = null;
		try {
			queryParam = objectMapper.readValue(param, QueryParam.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(CommonException.JSON_FORMAT_ERROR);
		}
		Resources resource = resourceService.get(name);
//		resourceService.exportCheck(resource);
		resourceService.export(resource,queryParam, response);
		return null;
	}

	@ApiOperation(value = "导入指定资源的数据", notes = "导入指定资源的数据")
	@ResponseBody
	@RequestMapping(value = "/{resourceName}/import", method = RequestMethod.POST)
	public Result<?> images(@PathVariable(value = "resourceName") String name, MultipartFile file) throws Exception {
        PropertiesUtils global = new PropertiesUtils(SysFileService.class.getClassLoader().getResourceAsStream("application.properties"));
	    String ext = StringUtil.getExt(file.getOriginalFilename());
		String fileName = RandomUtil.getUUID().concat(".").concat(ext);
		String basePath = global.getKey("file.upload.dir") + "/temp";
		StringBuilder sb = new StringBuilder();
		// 拼接保存路径
		sb.append(basePath).append("/").append(fileName);
		File f = new File(sb.toString());
		if (!f.exists()) {
			f.getParentFile().mkdirs();
		}
		OutputStream out = new FileOutputStream(f);
		FileCopyUtils.copy(file.getInputStream(), out);
		List<List<Object>>list=new ArrayList<>();
		try {
			list=getExcelContent(f.getAbsolutePath());
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}finally {
			f.delete();
		}
		Resources resources = resourceService.get(name);
		resourceService.importData(resources,list,request);
		return Result.success(SuccessConst.OPERATE_SUCCESS);
	}

	/**
	 * 导入测试
	 */
	public List<List<Object>> getExcelContent(String filePath) throws Throwable {
		List<List<Object>>result=new ArrayList<>();
		ImportExcel ei = new ImportExcel(filePath, 1);
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			List<Object>list=new ArrayList<>();
			Row row = ei.getRow(i);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				System.out.print(val + ", ");
				list.add(val);
			}
			result.add(list);
		}
		return result;

	}

	// @ApiOperation(value = "获取资源属性", notes = "获取资源属性")
	// @RequestMapping(value = "/{resourceName}/attribute", method =
	// RequestMethod.POST)
	// public Result<?> getAttribute(@PathVariable(value = "resourceName")
	// String name) throws Exception {
	// Resources resources = resourceService.get(name);
	// QueryParam<?> param = new QueryParam<>();
	// param.append("resources_id", resources.getId());
	// param.setNeedPage(false);
	// Page<ResProp> attributeList = (Page<ResProp>)
	// resourceService.list(ResProp.class, param);
	// if (attributeList.getList() == null || attributeList.getList().size() ==
	// 0) {
	// return Result.success(SuccessConst.OPERATE_SUCCESS, new
	// ArrayList<ResPropHandle>());
	// }
	// param.clear();
	// param.setNeedPage(false);
	// Map<Long, String> map = new HashMap<Long, String>();
	// StringBuffer sb = new StringBuffer();
	// for (ResProp attribute : attributeList.getList()) {
	// map.put(attribute.getId(), attribute.getName());
	// sb.append("," + attribute.getId());
	// }
	// param.append("attribute_id", sb.toString().substring(1), SqlConst.IN);
	// param.append("sort", "", SqlConst.ORDERBY, SqlConst.DESC);
	// Page<ResPropHandle> modelList = (Page<ResPropHandle>)
	// resourceService.list(ResPropHandle.class, param);
	// for (ResPropHandle temp : modelList.getList()) {
	// temp.setAttributeName(map.get(temp.getAttributeId()));
	// }
	// return Result.success(SuccessConst.OPERATE_SUCCESS, modelList.getList());
	// }

}
