package net.evecom.rd.ie.baseline.utils.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RegexUtil
 * @Description: 正则组件 @author： zhengc @date： 2015年5月21日
 */
public class RegexUtil {

	/**
	 * IP
	 */
	public static final String IP = "^((25[0-5]|2[0-4]\\d|[1]{1}\\d{1}\\d{1}|[1-9]{1}\\d{1}|\\d{1})($|(?!\\.$)\\.)){4}$ ";
	/**
	 * 手机号码
	 */
	public static final String MOBILE = "^1[3-9]\\d{9}$";

	/**
	 * 电话号码
	 */
	public static final String PHONE = "^(\\d{3,4}-?)?\\d{7,8}$";

	/**
	 * QQ
	 */
	public static final String QQ = "^\\d{6,20}$";

	/**
	 * 正数
	 */
	public static final String NUMBER = "^(0|[1-9]\\d*)(\\.\\d+)?$";

	/**
	 * 正两位及两位以下小数
	 */
	public static final String DECIMAL_2 = "^(0|[1-9]\\d*)(\\.\\d{1,2})?$";

	/**
	 * 正两位及四位以下小数
	 */
	public static final String DECIMAL_4 = "^(0|[1-9]\\d*)(\\.\\d{1,4})?$";

	/**
	 * 仅中文
	 */
	public static final String CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

	/**
	 * E-mail
	 */
	public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-_]+\\.[A-Za-z]{2,6}$";

	/**
	 * 统一社会信用代码(统一代码由十八位的阿拉伯数字或大写英文字母（不使用I、O、Z、S、V）组成)
	 * http://blog.sina.com.cn/s/blog_540316260102x352.html
	 */
	public static final String USCID = "^(1[1239]|5[1239]|9[123]|Y1)[1-9]{6}[0-9A-HJ-NPQRTUWXY]{10}$";

	/**
	 * 邮政编码
	 */
	public static final String ZIPCODE = "^\\d{6}$";

	/**
	 * 地区编码12位
	 */
	public static final String AREACODE_12 = "^\\d{12}$";

	/**
	 * 日期 yyyy-MM-dd
	 */
	public static final String DATE_SHORT = "^\\d{4}-\\d{2}-\\d{2}$";

	/**
	 * 日期 yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_LONG = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";

	/**
	 * ID串。多个ID间以英文逗号隔开
	 */
	public static final String IDS = "^\\d+(,\\d+)*$";

	/**
	 *
	 */
	private RegexUtil() {
	}

	/***/
	private static ThreadLocal<Map<String, Pattern>> threadLocal = new ThreadLocal<>();

	/**
	 *
	 * @param regex
	 * @param src
	 * @return
	 */
	public static boolean isMatch(String regex, String src) {
		return match(regex, src).find();
	}

	/**
	 *
	 * @param regex
	 * @param src
	 * @return
	 */
	public static Matcher match(String regex, String src) {
		Pattern p = getPattern(regex);
		return p.matcher(src);
	}

	/**
	 *
	 * @param regex
	 * @return
	 */
	private static Pattern getPattern(String regex) {
		Map<String, Pattern> patterns = getPatterns();
		if (!patterns.containsKey(regex)) {
			Pattern p = Pattern.compile(regex);
			patterns.put(regex, p);
		}
		return patterns.get(regex);
	}

	/**
	 *
	 * @return
	 */
	private static Map<String, Pattern> getPatterns() {
		if (null == threadLocal.get()) {
			Map<String, Pattern> patterns = new HashMap<>();
			threadLocal.set(patterns);
		}
		return threadLocal.get();
	}

}
