package net.evecom.utils.string;

import net.evecom.utils.verify.CheckUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StringUtil
 * @Description: 字符串通用操作组件 @author： zhengc @date： 2017年1月10日
 */
public class StringUtil {
	/**
	 * 生成模板内容
	 *
	 * @param text
	 *            模板
	 * @param map
	 *            根据key值替换模板中内容
	 * @return
	 */
	public static String messageTemplate(String text, Map<String, String> map) {
		if (CheckUtil.isNotNull(text)) {
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				String value = map.get(key);
				text = text.replaceAll("#" + key + "#", value);
			}
		}
		return text;
	}

	/**
	 * 字符串为空替换为默认字符串
	 *
	 * @param str
	 * @param def
	 * @return
	 */
	public static String defString(String str, String def) {
		if (str == null || str.trim().length() == 0) {
			if (def != null && def.trim().length() > 0) {
				str = def;
			} else {
				str = "";
			}
		}
		return str;
	}

	/**
	 * 字符串位数太多用...补充
	 *
	 * @param str
	 * @param num
	 * @return
	 */
	public static String cutString(String str, int num) {
		if (CheckUtil.isNull(str))
			return "";
		if (str.length() < num)
			return str;
		else
			return str.substring(0, num) + "...";
	}

	/**
	 * 第一个字符改为大写
	 *
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str) {
		String f = str.substring(0, 1);
		String tmp = str.substring(1);
		return f.toUpperCase() + tmp;
	}

	/**
	 * 切分字符串
	 *
	 * @param str
	 *            被切分的字符串
	 * @param separator
	 *            分隔符字符
	 * @return 切分后的集合
	 */
	public static String[] splitToArray(String str, char separator) {
		List<String> result = split(str, separator);
		return result.toArray(new String[result.size()]);
	}

	/**
	 * 切分字符串<br>
	 * a#b#c -> [a,b,c] <br>
	 * a##b#c -> [a,"",b,c]
	 *
	 * @param str
	 *            被切分的字符串
	 * @param separator
	 *            分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0);
	}

	/**
	 * 切分字符串
	 *
	 * @param str
	 *            被切分的字符串
	 * @param separator
	 *            分隔符字符
	 * @param limit
	 *            限制分片数
	 * @return 切分后的集合
	 */
	public static String[] splitToArray(String str, char separator, int limit) {
		List<String> result = split(str, separator, limit);
		return result.toArray(new String[result.size()]);
	}

	/**
	 * 切分字符串
	 *
	 * @param str
	 *            被切分的字符串
	 * @param separator
	 *            分隔符字符
	 * @param limit
	 *            限制分片数
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator, int limit) {
		if (str == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(limit == 0 ? 16 : limit);
		if (limit == 1) {
			list.add(str);
			return list;
		}
		boolean isNotEnd = true; // 未结束切分的标志
		int strLen = str.length();
		StringBuilder sb = new StringBuilder(strLen);
		for (int i = 0; i < strLen; i++) {
			char c = str.charAt(i);
			if (isNotEnd && c == separator) {
				list.add(sb.toString());
				// 清空StringBuilder
				sb.delete(0, sb.length());

				// 当达到切分上限-1的量时，将所剩字符全部作为最后一个串
				if (limit != 0 && list.size() == limit - 1) {
					isNotEnd = false;
				}
			} else {
				sb.append(c);
			}
		}
		list.add(sb.toString());// 加入尾串
		return list;
	}

	/**
	 * 切分字符串
	 *
	 * @param str
	 *            被切分的字符串
	 * @param delimiter
	 *            分隔符
	 * @return 字符串
	 */
	public static String[] split(String str, String delimiter) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return new String[] { str };
		}
		int dellen = delimiter.length(); // del length
		int maxparts = (str.length() / dellen) + 2; // one more for the last
		int[] positions = new int[maxparts];

		int i, j = 0;
		int count = 0;
		positions[0] = -dellen;
		while ((i = str.indexOf(delimiter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = str.length();
		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	/**
	 * 简单字符串匹配方法，支持匹配类型为： *what *what* what*
	 *
	 * @param pattern
	 *            匹配模式
	 * @param str
	 *            字符串
	 * @return 是否匹配
	 */
	public static boolean simpleMatch(String pattern, String str) {
		if (pattern == null || str == null) {
			return false;
		}
		int firstIndex = pattern.indexOf('*');
		if (firstIndex == -1) {
			return pattern.equals(str);
		}
		if (firstIndex == 0) {
			if (pattern.length() == 1) {
				return true;
			}
			int nextIndex = pattern.indexOf('*', firstIndex + 1);
			if (nextIndex == -1) {
				return str.endsWith(pattern.substring(1));
			}
			String part = pattern.substring(1, nextIndex);
			int partIndex = str.indexOf(part);
			while (partIndex != -1) {
				if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
					return true;
				}
				partIndex = str.indexOf(part, partIndex + 1);
			}
			return false;
		}
		return (str.length() >= firstIndex && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex))
				&& simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
	}
    /**
     * 获取名称后缀
     * @param name
     * @return
     */
    public static String getExt(String name){
        if(name == null || "".equals(name) || !name.contains("."))
            return "";
        return name.substring(name.lastIndexOf(".")+1);
    }
}
