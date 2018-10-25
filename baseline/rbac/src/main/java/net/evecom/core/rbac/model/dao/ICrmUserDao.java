package net.evecom.core.rbac.model.dao;

import net.evecom.core.rbac.model.entity.UiRouter;
import net.evecom.core.rbac.model.entity.CrmPower;
import net.evecom.core.rbac.model.entity.CrmRole;
import net.evecom.core.rbac.model.entity.CrmUser;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface ICrmUserDao extends BaseMapper<CrmUser> {
	int batchdelete(@Param("ids") Long[] ids, @Param("status") int status);

	List<CrmRole> getRoleList(@Param("userId") Long userId);

	List<CrmPower> getPowerList(@Param("userId") Long userId);

	List<UiRouter> getMenuList(@Param("userId") Long userId);

	public CrmUser queryUsers(@Param("userId") Long userId);
}
