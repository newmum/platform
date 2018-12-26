package net.evecom.rd.ie.baseline.core.mvc.model.dao;

import net.evecom.rd.ie.baseline.core.mvc.model.entity.SysFile;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface FileDao extends BaseMapper<SysFile> {

	List<SysFile> getFileParentList(@Param("fileId") Long fileId);

	List<SysFile> getFileChildList(@Param("fileId") Long fileId);
}
