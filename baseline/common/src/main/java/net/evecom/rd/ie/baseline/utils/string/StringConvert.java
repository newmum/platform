package net.evecom.rd.ie.baseline.utils.string;

import java.text.DecimalFormat;

/**
 * @ClassName: StringUtil
 * @Description: 字符串类型转换组件 @author： zhengc @date： 2010年7月30日
 */
public class StringConvert {
	/**
	 * 将String转换为long
	 *
	 * @param str
	 * @return
	 */
	public static long string2long(String str) {
		long l = 0;
		try {
			if (str != null && str.trim().length() > 0) {
				l = Long.parseLong(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 将String转换为double
	 *
	 * @param str
	 * @return
	 */
	public static double string2double(String str) {
		double d = 0;
		try {
			if (str != null && str.trim().length() > 0) {
				d = Double.parseDouble(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 将String转换为int
	 *
	 * @param str
	 * @return
	 */
	public static int string2Int(String str) {
		int k = 0;
		try {
			if (str != null && str.trim().length() > 0) {
				k = Integer.parseInt(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k;
	}

	/**
	 * 将int转换维string
	 *
	 * @param i
	 * @return
	 */
	public static String int2String(int i) {
		String str = "";
		try {
			str = String.valueOf(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将long转换维string
	 *
	 * @param l
	 * @return
	 */
	public static String long2String(long l) {
		String str = "";
		try {
			str = String.valueOf(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将double转换维string
	 *
	 * @param d
	 * @return
	 */
	public static String double2String(double d) {
		String str = "";
		try {
			str = String.valueOf(d);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 规范浮点字符串的显示格式
	 *
	 * @param d
	 * @param format
	 * @return
	 */
	public static String double2String(double d, String format) {
		String str = "";
		try {
			if (format != null && format.trim().length() > 0) {
				DecimalFormat myFormatter = new DecimalFormat(format);
				str = myFormatter.format(d);
			} else {
				str = String.valueOf(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
