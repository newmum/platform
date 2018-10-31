package net.evecom.core.rbac.model.dao;

import net.evecom.core.rbac.model.entity.Power;
import net.evecom.core.rbac.model.entity.UiRouter;
import net.evecom.core.rbac.model.entity.Role;
import net.evecom.core.rbac.model.entity.User;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface ICrmUserDao extends BaseMapper<User> {
	int batchdelete(@Param("ids") Long[] ids, @Param("status") int status);

	List<Role> getRoleList(@Param("userId") Long userId);

	List<Power> getPowerList(@Param("userId") Long userId);

	List<UiRouter> getMenuList(@Param("userId") Long userId);

	public User queryUsers(@Param("userId") Long userId);
}
