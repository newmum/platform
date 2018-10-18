package net.evecom.utils.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CookieUtil
 * @Description: Cookie操作组件 @author： zhengc @date： 2017年5月15日
 */
public class CookieUtil {

	/**
	 * 删除域中cookie
	 *
	 * @param response
	 * @param domain
	 * @param key
	 */
	public static void delCookieByDomain(HttpServletResponse response, String domain, String key) {
		Cookie cookieToken = new Cookie(key, "");
		if (domain != null) {
			cookieToken.setDomain(domain);
		}
		cookieToken.setPath("/");
		cookieToken.setMaxAge(0);
		response.addCookie(cookieToken);
	}

	/**
	 * 设置cookie
	 *
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse res, String name, String value, String domain, int maxAge,
                                 boolean isHttpOnly) {
		try {
			value = URLEncoder.encode("" + value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value + "");
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setHttpOnly(isHttpOnly);
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		res.addCookie(cookie);
	}

	/**
	 * 将cookie封装到Map里面
	 *
	 * @param req
	 * @return
	 */
	private static Map<String, Cookie> getCookieMap(HttpServletRequest req) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = req.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 删除cookie
	 *
	 * @param req
	 * @param res
	 * @param cookieName
	 * @return
	 */
	public static boolean delCookie(HttpServletRequest req, HttpServletResponse res, String cookieName) {
		if (cookieName != null) {
			Cookie cookie = getCookie(req, cookieName);
			if (cookie != null) {
				cookie.setMaxAge(0);// 0，就立即删除
				cookie.setPath("/");// 不要漏掉
				cookie.setDomain(req.getServerName());
				res.addCookie(cookie);
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据名字取值
	 *
	 * @param req
	 * @param cookieName
	 * @return
	 */
	public static String getValue(HttpServletRequest req, String cookieName) {
		Cookie cookie = getCookie(req, cookieName);
		return getValue(cookie);
	}

	/**
	 * 从cookie取值
	 *
	 * @param cookie
	 * @return
	 */
	public static String getValue(Cookie cookie) {
		if (null == cookie)
			return null;
		try {
			return URLDecoder.decode(cookie.getValue(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据名字取cookie
	 *
	 * @param req
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest req, String cookieName) {
		Cookie[] cookies = req.getCookies();
		Cookie cookie = null;
		try {
			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals(cookieName)) {
						return cookie;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
