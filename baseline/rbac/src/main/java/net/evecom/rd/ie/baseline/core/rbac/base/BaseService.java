package net.evecom.rd.ie.baseline.core.rbac.base;

import net.evecom.rd.ie.baseline.core.rbac.exception.UserException;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.core.rbac.model.service.AuthService;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * service服务基类
 * @author zhengc
 *
 */
@Transactional
@Service("baseService")
public class BaseService {

    protected static Logger log = LoggerFactory.getLogger(BaseService.class);
	/**
	 * 描述
	 */
	@Resource
	private SQLManager sqlManager;

    @Resource
	protected AuthService authService;

	/**
	 * 描述 检测某值是否存在
	 * @author Klaus Zhuang
	 * @created 2018/12/29 10:24
	 * @return
	 * @param
	 */
	public boolean checkExist(String tableName, String idName, String idValue) {
		String sql = "select "+idName+" from " +tableName +" where " + idName +"=?";
		List list = sqlManager.execute(new SQLReady(sql,idValue),Map.class);
		if(list.size()>0){
			return true;
		}
		return false;

	}

    /**
     * 更新会话中的用户对象
     * @param user
     * @param request
     * @throws Exception
     */
	public void updateLoginUser(User user, HttpServletRequest request) throws Exception {
		authService.updateUser(user, request);
	}

    /**
     * 保存用户到会话中
     * @param user
     * @param response
     * @throws Exception
     */
	public String saveLoginUser(User user, HttpServletResponse response) throws Exception {
        return authService.saveUser(user, response);
	}

    /**
     * 从会话中获取登录的用户对象
     * @param request
     * @return
     * @throws Exception
     */
	public User loginUser(HttpServletRequest request) throws Exception {
		return authService.getUser(request);
	}

	/**
	 * 删除缓存会话中的用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public boolean delUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return authService.delUser(request, response);
	}

	/**
	 * 根据请求获取会话SID
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getSID(HttpServletRequest request) throws Exception {
		String sid = authService.getSID(request);
		if (CheckUtil.isNull(sid)) {
			throw new UserException(UserException.USER_NO_LOGIN);
		}
		return sid;
	}
}
