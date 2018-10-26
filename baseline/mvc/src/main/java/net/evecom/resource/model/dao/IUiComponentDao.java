package net.evecom.resource.model.dao;

import net.evecom.resource.model.entity.UiComponent;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface IUiComponentDao extends BaseMapper<UiComponent> {
	List<UiComponent> getChildListById(@Param("componentId") Long componentId);
}
