package net.evecom.tools.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求返回数据工具类
 *
 * @author xiej
 * @date 2017-9-11 14:02:13
 * @since 2.0
 */
public class ActionUtil {

	public static final String AJAX_ACCEPT_CONTENT_TYPE = "text/html;type=ajax";
	public static final String AJAX_SOURCE_PARAM = "ajaxSource";

	/**
	 * 判断请求是否为ajax
	 *
	 * @param request
	 * @param response
	 * @return true ajax false 传统请求
	 */
	public static boolean isAjax(HttpServletRequest request) {
		String header = request.getHeader("content-type");
		// boolean isAjax = "XMLHttpRequest".equals(requestType) ? true : false;
		return header != null && header.contains("json");
	}

	/**
	 * 客户端返回字符串
	 *
	 * @param response
	 * @param string
	 * @return
	 */
	public static String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static void sendContendClink(HttpServletResponse response, Object object) throws IOException {
		response.getWriter().print(object.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

}
