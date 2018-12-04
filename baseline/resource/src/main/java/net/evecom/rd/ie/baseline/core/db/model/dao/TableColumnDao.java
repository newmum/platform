package net.evecom.rd.ie.baseline.core.db.model.dao;

import net.evecom.rd.ie.baseline.core.db.model.entity.ResProp;
import net.evecom.rd.ie.baseline.core.db.model.entity.TableColumn;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface TableColumnDao extends BaseMapper<TableColumn> {
	List<ResProp> getList(@Param("tableName") String tableName);
}
