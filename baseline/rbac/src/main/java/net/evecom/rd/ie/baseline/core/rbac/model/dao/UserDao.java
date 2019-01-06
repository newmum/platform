package net.evecom.rd.ie.baseline.core.rbac.model.dao;

import net.evecom.rd.ie.baseline.core.rbac.model.entity.Priv;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Menu;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Role;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

	int batchdelete(@Param("ids") String[] ids, @Param("status") int status);

	List<Role> getRoleList(@Param("userId") String userId);

	List<Priv> privList(@Param("userId") String userId);

	List<Menu> menuListMysql(@Param("userId") String userId);

    List<Menu> menuListOracle(@Param("userId") String userId);

	User queryUsers(@Param("userId") String userId);
}
