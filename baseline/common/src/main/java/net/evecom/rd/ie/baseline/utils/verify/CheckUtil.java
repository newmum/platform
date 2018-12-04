package net.evecom.rd.ie.baseline.utils.verify;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CheckUtil
 * @Description: 验证组件 @author： zhengc @date： 2015年5月21日
 */
public class CheckUtil {
	/**
	 * 验证账号
	 *
	 * @param account
	 *            账号，格式：a458797 中aagds
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isAccount(String account) {
		if (isNull(account)) {
			return false;
		}
		String regex = "^[0-9a-zA-Z\u4E00-\u9FA5]{3,20}$";
		return Pattern.matches(regex, account);
	}

	/**
	 * 字符串长度判断
	 *
	 * @param text
	 *            字符串
	 * @param min
	 *            最小长度(>=)
	 * @param max
	 *            最大长度(<=)
	 * @return
	 */
	public static boolean stringLengthCheck(String text, int min, int max) {
		if (text != null && text.length() >= 3 && text.length() <= 20) {
			return true;
		}
		return false;
	}

	/**
	 * 验证Email
	 *
	 * @param email
	 *            email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isEmail(String email) {
		if (isNull(email)) {
			return false;
		}
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 验证身份证号码
	 *
	 * @param idCard
	 *            居民身份证号码18位，第一位不能为0，最后一位可能是数字或字母，中间16位为数字 \d同[0-9]
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isIdCard(String idCard) {
		String regex = "[1-9]\\d{16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 *
	 * @param mobile
	 *            移动、联通、电信运营商的号码段
	 *            <p>
	 *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            </p>
	 *            <p>
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            </p>
	 *            <p>
	 *            电信的号段：133、153、180（未启用）、189
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isMobile(String mobile) {
		// String regex = "(\\+\\d+)?1[346758]\\d{9}$";
		if (isNull(mobile)) {
			return false;
		}
		String regex = "^1[0-9]{10}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码
	 *
	 * @param telephone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isTelephone(String telephone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, telephone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 *
	 * @param digit
	 *            一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 *
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符
	 *
	 * @param blankSpace
	 *            空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文
	 *
	 * @param chinese
	 *            中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）
	 *
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 *
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * <pre>
	 * 获取网址 URL 的一级域名
	 * http://detail.tmall.com/item.htm?spm=a230r.1.10.44.1xpDSH&id=15453106243&_u=f4ve1uq1092 ->> tmall.com
	 * </pre>
	 *
	 * @param url
	 * @return
	 */
	public static String isDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		// 获取完整的域名Pattern
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	/**
	 * 匹配中国邮政编码
	 *
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 *
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * 邮政编码验证
	 *
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isZipCode(String text) {
		return match(text, "^[0-9]{6}$");
	}

	/**
	 * 验证字符串长度
	 *
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static String checkLen(String str, int min, int max) {
		if (!CheckUtil.isNotNull(str))
			return "不能为空！";
		if (str.length() > max)
			return "不能大于" + max + "个字符！";
		if (str.length() < min)
			return "不能小于" + min + "个字符！";
		return "";
	}

	/**
	 * 验证数字长度
	 *
	 * @param num
	 * @param min
	 * @param max
	 * @return
	 */
	public static String checkNumLen(int num, int min, int max) {
		if (num > max)
			return "不能大于" + max + "！";
		if (num < min)
			return "不能小于" + min + "！";
		return "";
	}

	/**
	 * 验证是否日期型
	 *
	 * @param sDate
	 * @return
	 */
	public static boolean checkDate(String sDate) {
		String datePattern = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern p = Pattern.compile(datePattern);
			Matcher m = p.matcher(sDate);
			boolean b = m.matches();
			if (b) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 验证是否数字型
	 *
	 * @param num
	 * @return
	 */
	public static boolean isNum(String num) {
		if (!isNotNull(num))
			return false;
		if (num.matches("\\d+"))
			return true;
		else
			return false;
	}

	/**
	 * 验证是否非空
	 *
	 * @param sourseStr
	 * @return
	 */
	public static boolean isNotNull(String sourseStr) {
		if ((sourseStr != null) && !sourseStr.equals("") && !sourseStr.toLowerCase().equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证是否为空
	 *
	 * @param sourseStr
	 * @return
	 */
	public static boolean isNull(String sourseStr) {
		if ((sourseStr == null) || (sourseStr.trim() == null) || sourseStr.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断对象是否为空！(null,"", "null")
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null || "".equals(obj) || "null".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符是否相同
	 *
	 * @param sourseStr
	 * @param targetStr
	 * @return
	 */
	public static boolean isSameStr(String sourseStr, String targetStr) {
		if (isNotNull(sourseStr) && sourseStr.equals(targetStr)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断子字符是否是完整字符串中的单元
	 *
	 * @param all
	 * @param sub
	 * @return
	 */
	public static boolean isInArry(String all, String sub) {
		if (CheckUtil.isNull(all))
			return false;
		String[] ary = all.split(",");
		for (String s : ary) {
			if (!CheckUtil.isNull(s.trim()) && s.trim().equals(sub))
				return true;
		}
		return false;
	}

	/**
	 * 是否为测试服务
	 *
	 * @return
	 */
	public static boolean isWindowsOrTestService() {
		try {
			if (System.getProperty("os.name").indexOf("Windows") != -1) {
				return true;
			}

			if (InetAddress.getLocalHost().getHostAddress().indexOf("192.168") != -1) {
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 正则表达式匹配
	 *
	 * @param text
	 *            待匹配的文本
	 * @param reg
	 *            正则表达式
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean match(String text, String reg) {
		if (!isNotNull(text) || !isNotNull(reg))
			return false;
		return Pattern.compile(reg).matcher(text).matches();
	}
}
