package net.evecom.rd.ie.baseline.utils.string;

import java.util.Random;
import java.util.UUID;

/**
 * @ClassName: RandomUtil
 * @Description: 随机字符数组件 @author： zhengc @date： 2008年5月27日
 */
public class RandomUtil {

	/** 设置随机变量*/
	private static Random random = new Random();
	/** 用于随机选的数字 */
	private static final String BASE_NUMBER = "0123456789";
	/** 用于随机选的字符 */
	private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
	/** 用于随机选的字符和数字 */
	private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

	/**
	 * 获得一个随机的字符串（只包含数字和字符）
	 *
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(int length) {
		return randomString(BASE_CHAR_NUMBER, length);
	}

	/**
	 * 获得一个只包含数字的字符串
	 *
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomNumbers(int length) {
		return randomString(BASE_NUMBER, length);
	}

	/**
	 * 获得一个随机的字符串
	 *
	 * @param baseString
	 *            随机字符选取的样本
	 * @param length
	 *            字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(String baseString, int length) {
		StringBuffer sb = new StringBuffer();

		if (length < 1) {
			length = 1;
		}
		int baseLength = baseString.length();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}
		return sb.toString();
	}

    /**
     * 生成UUID
     * @return uuid
     */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

}
