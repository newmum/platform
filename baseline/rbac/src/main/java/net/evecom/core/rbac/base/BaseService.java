package net.evecom.core.rbac.base;

import net.evecom.core.rbac.exception.UserException;
import net.evecom.core.rbac.model.entity.User;
import net.evecom.core.rbac.model.service.AuthCertService;
import net.evecom.utils.verify.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * service服务基类
 * @author zhengc
 *
 */
public class BaseService {

    protected static Logger log = LoggerFactory.getLogger(BaseService.class);

    @Resource
	AuthCertService authCertService;
    /**
     * 更新会话中的用户对象
     * @param user
     * @param request
     * @throws Exception
     */
	public void updateLoginUser(User user, HttpServletRequest request) throws Exception {
		authCertService.updateUser(user, request);
	}

    /**
     * 保存用户到会话中
     * @param user
     * @param response
     * @throws Exception
     */
	public String saveLoginUser(User user, HttpServletResponse response) throws Exception {
        return authCertService.saveUser(user, response);
	}

    /**
     * 从会话中获取登录的用户对象
     * @param request
     * @return
     * @throws Exception
     */
	public User loginUser(HttpServletRequest request) throws Exception {
		return authCertService.getUser(request);
	}

	/**
	 * 删除缓存会话中的用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public boolean delUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return authCertService.delUser(request, response);
	}

	/**
	 * 根据请求获取会话SID
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getSID(HttpServletRequest request) throws Exception {
		String sid = authCertService.getSID(request);
		if (CheckUtil.isNull(sid)) {
			throw new UserException(UserException.USER_NO_LOGIN);
		}
		return sid;
	}
}
