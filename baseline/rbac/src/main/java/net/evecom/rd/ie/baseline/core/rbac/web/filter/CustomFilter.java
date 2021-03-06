package net.evecom.rd.ie.baseline.core.rbac.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.rd.ie.baseline.core.rbac.config.MessageConfig;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Priv;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.core.rbac.model.service.AuthService;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisClient;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.service.Result;
import net.evecom.rd.ie.baseline.tools.request.ActionUtil;
import net.evecom.rd.ie.baseline.utils.file.PropertiesUtils;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @ClassName: CustomFilter
 * @Description: 用户权限过滤器
 * @author： zhengc
 * @date： 2018年10月27日
 */
public class CustomFilter implements Filter {

    protected RedisClient redisClient;

    protected ObjectMapper objectMapper;

    private AuthService authCertService;

    private Set<String> excludeUrl = new HashSet<String>();

    private PropertiesUtils global = new PropertiesUtils(MessageConfig.class.getClassLoader().getResourceAsStream("app.properties"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();// 这里获取applicationContext
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        redisClient = ctx.getBean(RedisClient.class);
        objectMapper = ctx.getBean(ObjectMapper.class);
        authCertService = ctx.getBean(AuthService.class);
        excludeUrl.clear();
        String exclude = global.getKey("filter.exclude.url");
        if (CheckUtil.isNotNull(exclude)) {
            for (String url : exclude.split(",")) {
                excludeUrl.add(url);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            request.setCharacterEncoding("utf-8");
            String stp_url = request.getRequestURI();
            String stp_method = request.getMethod();
            String main_url = request.getContextPath();
            main_url = (main_url == null ? "" : main_url);
            stp_url = stp_url.substring(main_url.length());
            if (isExcludeUrl(excludeUrl, stp_url)) {
                filterChain.doFilter(request, response);
                return;
            }
            String sid = authCertService.getSID(request);
            if (CheckUtil.isNull(sid)) {
                noPower(request, response, CommonException.NO_LOGIN);
                return;
            }
            Object obj = null;
            try {
                obj = authCertService.getUser(sid);
            } catch (Exception e) {
                noPower(request, response, e.getMessage());
            }
            if (CheckUtil.isNull(obj)) {
                noPower(request, response, CommonException.NO_LOGIN);
                return;
            }
            User user = (User) obj;
            objectMapper.writeValueAsString(user);
//            if (user.getTid() != 1) {// TODO 测试使用
                List<Priv> powerList = user.getPowerList();
                boolean bo = hasPower(powerList, stp_url, stp_method);
                if (!bo) {
                    noPower(request, response, CommonException.NO_POWER);
                    return;
                }
//            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 验证是否含有权限
     *
     * @param powerList 登录用户权限集合
     * @param url       验证路径
     * @param method    请求类型
     * @return
     */
    private static boolean hasPower(List<Priv> powerList, String url, String method) {
        if (powerList == null || powerList.size() == 0) {
            return false;
        }
        for (Priv power : powerList) {
            if(CheckUtil.isNull(power.getUrl())||CheckUtil.isNull(power.getMethod())){
                continue;
            }
            String regex = "^" + power.getUrl().replaceAll("\\*", "\\.\\*") + "$";
            if (Pattern.matches(regex, url) && power.getMethod().toUpperCase().equals(method)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证不通过时操作 ajax 返回json字符,普通请求跳转至登录页
     *
     * @param request
     * @param response
     * @param message
     * @throws IOException
     * @throws ServletException
     */
    private void noPower(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException, ServletException {
//        boolean bo = ActionUtil.isAjax(request);
//        if (bo) {
//            String url = global.getKey("server.servlet.context-path");
//            response.sendRedirect(url + "?message=" + message);
//        } else {
            String str = objectMapper.writeValueAsString(Result.failed(message));
            ActionUtil.renderString(response, str, "application/json");
//        }
    }

    @Override
    public void destroy() {

    }

    private boolean isExcludeUrl(Set<String> excludeUrl, String stp_url) {
        for (String url : excludeUrl) {
            if (url.startsWith("*") && url.endsWith("*")) {
                if (stp_url.indexOf(url.substring(1, url.length() - 1)) != -1)
                    return true;
            }
            if (url.startsWith("*")) {
                if (stp_url.endsWith(url.substring(1)))
                    return true;
            } else if (url.endsWith("*")) {
                if (stp_url.startsWith(url.substring(0, url.length() - 1)))
                    return true;
            } else {
                if (stp_url.indexOf(url) != -1)
                    return true;
            }
        }
        return false;
    }
}
