package net.evecom.rd.ie.baseline.core.rbac.model.service;

import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.tools.constant.consts.CacheGroupConst;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisClient;
import net.evecom.rd.ie.baseline.utils.request.CookieUtil;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName: AuthCertService
 * @Description: 用户授权认证操作 @author： zhengc @date： 2018年8月1日
 */
@Service("authCertService")
public class AuthCertService {

    @Resource(name = "redisClient")
    protected RedisClient redisClient;

    /**
     * 保存用户到缓存会话中
     *
     * @param user
     * @param response
     * @return
     * @throws Exception
     */
    public String saveUser(User user, HttpServletResponse response) throws Exception {
        int size = 0;//要改成从数据库配置表读
        int time = (size == 0 ? 30 * 60 : Integer.valueOf(size));
        String sid = UUID.randomUUID().toString();
        redisClient.set(CacheGroupConst.SESSION_REDIS, sid, user, time);
        CookieUtil.addCookie(response, CacheGroupConst.COOKIE_USER_ID, sid, "", time, true);
        response.addHeader(CacheGroupConst.TICKET_USER_ID, sid);
        return sid;
    }

    /**
     * 删除缓存会话中的用户
     *
     * @param request
     * @return
     */
    public boolean delUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sid = getSID(request);
        redisClient.del(CacheGroupConst.SESSION_REDIS, sid);
        boolean rs = CookieUtil.delCookie(request, response, CacheGroupConst.COOKIE_USER_ID);
        return rs;
    }

    /**
     * 从缓存会话中获取登录的用户对象
     *
     * @param request
     * @return
     * @throws Exception
     */
    public User getUser(HttpServletRequest request) throws Exception {
        String sid = getSID(request);
        return getUser(request, sid);
    }

    public User getUser(HttpServletRequest request, String sid) throws Exception {
        Object obj = redisClient.get(CacheGroupConst.SESSION_REDIS, sid);
        return (User) obj;
    }


    /**
     * 更新缓存会话中用户对象
     *
     * @param user
     * @param request
     * @throws Exception
     */
    public void updateUser(User user, HttpServletRequest request) throws Exception {
        String sid = getSID(request);
        Long time = redisClient.ttl(CacheGroupConst.SESSION_REDIS, sid);
        redisClient.set(CacheGroupConst.SESSION_REDIS, sid, user, time.intValue());
    }

    /**
     * 获取用户会话ID
     *
     * @param request
     * @return
     */
    public String getSID(HttpServletRequest request) {
        String sid = CookieUtil.getValue(request, CacheGroupConst.COOKIE_USER_ID);
        if (CheckUtil.isNull(sid)) {
            Object obj = request.getHeader(CacheGroupConst.TICKET_USER_ID);
            if (obj != null) {
                sid = obj.toString();
            }
        }
        return sid;
    }
}
