package net.evecom.rd.ie.baseline.core.mvc.model.service;

import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.core.mvc.model.dao.FileDao;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseService;
import net.evecom.rd.ie.baseline.core.rbac.model.dao.UserDao;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Menu;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Router;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.core.rbac.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ViewService
 * @Description:
 * @author： zhengc
 * @date： 2018/12/18 21:05
 */
@Service("viewService")
public class ViewService extends BaseService {

    @Resource
    private ResourceService resourceService;
    @Resource
    private UserService userService;
    @Resource
    private UserDao userDao;

    public Map<String, Object> showMain(HttpServletRequest request) throws Exception {
        User user = userService.loginUser(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        QueryParam queryParam=new QueryParam();
        //List<Router> routerList = (List<Router>) resourceService.list(Router.class,queryParam).getList();
        //map.put("routerList", routerList);
        List<Menu> menuList = userDao.menuListOracle(user.getTid());
        map.put("menuList", menuList);
        return map;
    }
}
