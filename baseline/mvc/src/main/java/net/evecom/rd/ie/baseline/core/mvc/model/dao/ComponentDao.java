package net.evecom.rd.ie.baseline.core.mvc.model.dao;

import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiComponent;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface ComponentDao extends BaseMapper<UiComponent> {
	List<UiComponent> getChildListById(@Param("componentId") Long componentId);
}
