package net.evecom.rd.ie.baseline.utils.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IPUtils
 * @Description: Ip工具类
 * @author： zhengc
 * @date： 2016年6月1日
 */
public class IPUtils {

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 *
	 * 获取IP+[IP所属地址]
	 * @param request
	 * @return
	 */
//	public static String getIpBelongAddress(HttpServletRequest request) {
//		String ip = getIpAddress(request);
//		String belongIp = getIPbelongAddress(ip);
//		return ip + belongIp;
//	}

	/**
	 * 获取Ip所属地址
	 * @param urlStr
	 * @return
	 */
	public static String call(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("GET");
			int code = httpCon.getResponseCode();
			if (code == 200) {
				return streamConvertToSting(httpCon.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将InputStream转换成String
	 * @param is
	 * @return
	 */
	public static String streamConvertToSting(InputStream is) {
		String tempStr = "";
		try {
			if (is == null)
				return null;
			ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = is.read(by)) != -1) {
				arrayOut.write(by, 0, len);
			}
			tempStr = new String(arrayOut.toByteArray());
		} catch (IOException e) {

			e.printStackTrace();
		}
		return tempStr;
	}

	/**
	 *
	 * 获取request访问ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		if (ipValid(ip)) {
			return ip;
		} else {
			return "127.0.0.1";
		}
	}

	/**
	 * @Title:获取本机ip
	 * @Description:
	 * @return
	 * @date 2017年12月25日下午5:34:20
	 * @author gelingqin
	 * @version 1.0
	 */
	public static String getLocalIP() {
		String localIP = "127.0.0.1";
		try {
			Enumeration<?> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				InetAddress ip = ni.getInetAddresses().nextElement();
				if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
					localIP = ip.getHostAddress();
					break;
				}
			}
		} catch (Exception e) {
			try {
				localIP = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}
		return localIP;
	}

	/**
	 * 检查IP是否合法
	 * @param ip
	 * @return
	 */
	public static boolean ipValid(String ip) {
		String regex0 = "(2[0-4]\\d)" + "|(25[0-5])";
		String regex1 = "1\\d{2}";
		String regex2 = "[1-9]\\d";
		String regex3 = "\\d";
		String regex = "(" + regex0 + ")|(" + regex1 + ")|(" + regex2 + ")|(" + regex3 + ")";
		regex = "(" + regex + ").(" + regex + ").(" + regex + ").(" + regex + ")";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		return m.matches();
	}

}
