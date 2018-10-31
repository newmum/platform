package net.evecom.core.mvc.model.service;

import net.evecom.core.mvc.model.dao.SysFileDao;
import net.evecom.core.mvc.model.entity.SysFile;
import net.evecom.core.rbac.base.BaseService;
import net.evecom.core.rbac.model.entity.User;
import net.evecom.core.db.model.entity.Resources;
import net.evecom.core.db.exception.ResourceException;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.core.db.untis.ValidtorUtil;
import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.tools.exception.CommonException;
import net.evecom.tools.service.Page;
import net.evecom.utils.database.redis.RedisCacheAnno;
import net.evecom.utils.file.PropertiesUtils;
import net.evecom.utils.string.RandomUtil;
import net.evecom.utils.string.StringUtil;
import net.evecom.utils.verify.CheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("sysFileService")
public class SysFileService extends BaseService {
	@Resource
	private ResourceService resourceService;
	@Resource
	private SysFileDao sysFileDao;

	PropertiesUtils global = new PropertiesUtils(SysFileService.class.getClassLoader().getResourceAsStream("app.properties"));

	public void baseCheck(SysFile sysFile) throws Exception {
		String str = ValidtorUtil.validbean(sysFile);
		if (CheckUtil.isNotNull(str)) {
			throw new ResourceException(str);
		}
	}

	public void editCheck(SysFile sysFile,Long userId) throws Exception {
        SysFile temp = (SysFile) resourceService.get(SysFile.class, sysFile.getID());
        QueryParam<SysFile> param = new QueryParam<>();
		param.setNeedPage(true);
		param.setPage(1);
		param.setPageSize(1);
		param.append(SysFile::getName, sysFile.getName());
		param.append(SysFile::getParentId, temp.getParentId());
		param.append(SysFile::getUserId, userId);
		param.append(SysFile::getID, sysFile.getID(), SqlConst.NOT_EQ, SqlConst.AND);
		Boolean bo = resourceService.list(SysFile.class, param).getList().size() > 0;
		if (bo) {
			throw new ResourceException(ResourceException.NAME_HAS_EXIST);
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@RedisCacheAnno(type = "edit")
	public void update(SysFile sysFile) throws Exception {
		resourceService.update(sysFile);

	}

	public void addCheck(SysFile sysFile, Long userId) throws Exception {
		baseCheck(sysFile);
		QueryParam<SysFile> param = new QueryParam<>();
		param.setNeedPage(true);
		param.setPage(1);
		param.setPageSize(1);
		param.append(SysFile::getName, sysFile.getName());
		param.append(SysFile::getParentId, sysFile.getParentId());
		param.append(SysFile::getUserId, userId);
		Boolean bo = resourceService.list(SysFile.class, param).getList().size() > 0;
		if (bo) {
			throw new ResourceException(ResourceException.NAME_HAS_EXIST);
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@RedisCacheAnno(type = "add")
	public void add(SysFile sysFile, Long userId, String upload, HttpServletRequest request) throws Exception {
		String parent_path = "";
		if (sysFile.getParentId() != 0) {
			SysFile pFile = (SysFile) resourceService.get(SysFile.class, sysFile.getParentId());
			if (pFile.getType() == 1) {
				throw new ResourceException(ResourceException.NOT_FOLDER);
			}
			parent_path = getParentPath(sysFile.getParentId());
		}

		String path = RandomUtil.getUUID();
		sysFile.setPath("/" + path);
		sysFile.setTrueName(path);
		sysFile.setUserId(userId);

		// 真实路径创建目录
		String basePath = global.getKey("file.upload.dir") + upload + parent_path + "/" + path;
		File file = new File(basePath);
		if (!file.exists()) {
			file.mkdirs();
			System.out.println(file.getAbsoluteFile());
		}

		resourceService.add(sysFile);
	}
	@Transactional(rollbackFor = Exception.class)
	@RedisCacheAnno(type = "add")
	public Map<String, Object> addfiles(MultipartFile file, String upload, Long pathId, Long userId,
                                        HttpServletRequest request) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		SysFile sysFile = new SysFile();
		sysFile.setUserId(userId);
		sysFile.setType(1);
		sysFile.setName(file.getOriginalFilename());
		sysFile.setParentId(pathId);
		addCheck(sysFile, userId);
		String parent_path = "";
		if (sysFile.getParentId() != 0) {
			SysFile pFile = (SysFile) resourceService.get(SysFile.class, sysFile.getParentId());
			if (pFile.getType() == 1) {
				throw new ResourceException(ResourceException.NOT_FOLDER);
			}
			parent_path = getParentPath(pathId);
		}
		// 文件保存目录路径
		String basePath = global.getKey("file.upload.dir") + upload + parent_path;
		// 文件保存目录URL
		String visitUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/" + upload + parent_path + "/";

		String ext = StringUtil.getExt(file.getOriginalFilename());
		String fileName = RandomUtil.getUUID().concat(".").concat(ext);
		StringBuilder sb = new StringBuilder();
		// 拼接保存路径
		sb.append(basePath).append("/").append(fileName);
		String url = "/" + upload + parent_path + "/" + fileName;
		visitUrl = visitUrl.concat(fileName);
		try {
			File f = new File(sb.toString());
			if (!f.exists()) {
				f.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(f);
			FileCopyUtils.copy(file.getInputStream(), out);
		} catch (Exception e) {
			params.put("state", "ERROR");
			throw new CommonException(CommonException.OPERATE_FAILED);
		}
		sysFile.setTrueName(fileName);
		sysFile.setPath("/" + fileName);
		resourceService.add(sysFile);
		params.put("url","/sys_file/download/"+sysFile.getID());
		return params;
	}
	@Transactional(rollbackFor = Exception.class)
	@RedisCacheAnno(type = "del")
	public void delete(Long id) throws Exception {
		List<SysFile> list = getChildFile(id);
		Long[] ids = new Long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getID();
		}
		Resources resource = resourceService.get("sys_file");
		resourceService.delete(resource, ids);
	}

	public List<SysFile> getParentFile(Long sysFileId) {
		List<SysFile> fileParentList = sysFileDao.getFileParentList(sysFileId);
		return fileParentList;
	}

	public List<SysFile> getChildFile(Long sysFileId) {
		List<SysFile> fileChildList = sysFileDao.getFileChildList(sysFileId);
		return fileChildList;
	}

	public String getParentPath(Long parentId) {
		List<SysFile> list = getParentFile(parentId);
		StringBuffer sb = new StringBuffer();
		Map<Long, SysFile> map = new HashMap<Long, SysFile>();
		for (SysFile sysFile : list) {
			map.put(sysFile.getID(), sysFile);
		}
		while (parentId != 0L) {
			SysFile sysFile = map.get(parentId);
			sb.insert(0, sysFile.getPath());
			parentId = sysFile.getParentId();
		}

		return sb.toString();
	}

	public Map<String, String> getPath(Long id, HttpServletRequest request) throws Exception {
		SysFile sysFile = (SysFile) resourceService.get(SysFile.class, id);
		String upload = global.getKey("file.upload.dir");
		if (sysFile.getUserId() == 0L) {
			upload += "common_file";
		} else {
			User user = loginUser(request);
			upload += "upload" + "/" + user.getID();
		}
		String path = upload + getParentPath(sysFile.getParentId()) + sysFile.getPath();
		Map<String, String> result = new HashMap<String, String>();
		result.put("path", path);
		result.put("fileName",sysFile.getName());
		return result;
	}

	public Page<?> list(QueryParam<SysFile> queryParam) throws Exception {
        Page<?> page = resourceService.list(SysFile.class, queryParam);
        return page;
    }
}
