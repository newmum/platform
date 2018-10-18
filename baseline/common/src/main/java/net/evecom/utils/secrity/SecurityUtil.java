package net.evecom.utils.secrity;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;

/**
 * @ClassName: SecurityUtil
 * @Description: 平台通用加密组件 @author： zhengc @date： 2009年11月20日
 */
public class SecurityUtil {
	/**
	 * Passport 加密函数
	 *
	 * @param txt
	 *            等待加密的原字串
	 * @param key
	 *            私有密匙(用于解密和加密)
	 * @return 原字串经过私有密匙加密后的结果
	 */
	public static String encrypt(String txt, String key, boolean timestamp) {
		int j = (int) (Math.random() * 32000);
		String encrypt_key = MD5Util.getMD5(Integer.toString(j));
		int ctr = 0;
		String tmp = "";

		if (timestamp) {
			long dateTime = System.currentTimeMillis() / 1000;
			txt = txt + "." + String.valueOf(dateTime);
		}
		for (int i = 0; i < txt.length(); i++) {
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			char a1 = encrypt_key.charAt(ctr);
			char t1 = txt.charAt(i);
			char t2 = encrypt_key.charAt(ctr++);
			char a2 = (char) (t1 ^ t2);
			tmp += String.valueOf(a1) + a2;
		}
		return Base64.encode((passportKey(tmp, key)).getBytes());
	}

	/**
	 * Passport 解密函数
	 *
	 * @param txt1
	 *            加密后的字串
	 * @param key
	 *            私有密匙(用于解密和加密)
	 * @return 字串经过私有密匙解密后的结果
	 * @throws Base64DecodingException
	 */
	public static String decrypt(String txt1, String key) throws Base64DecodingException {
		String txt = passportKey(new String(Base64.decode(txt1)), key);
		String tmp = "";
		for (int i = 0; i < txt.length(); i++) {
			tmp += (char) (txt.charAt(i) ^ txt.charAt(++i));
		}
		return tmp;
	}

	/**
	 * Passport 密匙处理函数
	 *
	 * @param txt
	 *            待加密或待解密的字串
	 * @param encrypt_key1
	 *            私有密匙(用于解密和加密)
	 * @return 处理后的密匙
	 */
	private static String passportKey(String txt, String encrypt_key1) {
		// 将 $encrypt_key 赋为 $encrypt_key 经 md5() 后的值
		String encrypt_key = MD5Util.getMD5(encrypt_key1);
		// 变量初始化
		int ctr = 0;
		String tmp = "";
		for (int i = 0; i < txt.length(); i++) {
			// 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			// $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
			// 与 $encrypt_key 的第 $ctr + 1 位取异或。然后 $ctr = $ctr + 1
			tmp += (char) (txt.charAt(i) ^ encrypt_key.charAt(ctr++));
		}
		return tmp;
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * 生成客户端识别码
	 *
	 * @param info
	 *            信息
	 * @param id
	 *            ID
	 * @param authkey
	 *            校验码
	 * @param limit
	 *            是否设置有效期
	 * @return 识别码
	 */
	public static String createClientSn(String info, String id, String authkey, boolean limit) {
		String str = encrypt(info, authkey, limit);
		int length = id.length();
		String newString = "";
		for (int i = 0; i < length; i++) {
			newString += str.substring(i + 0, i + 1) + id.charAt(i);
		}
		return length + newString + str.substring(length);
	}

	/**
	 * 根据识别码获取信息
	 *
	 * @param clientSn
	 * @param authkey
	 * @return
	 */
	public static String getInfo(String clientSn, String authkey) {
		String input = "";
		String str = clientSn.substring(1);
		String number = "";
		int length = Integer.valueOf(clientSn.substring(0, 1));
		for (int i = 0; i < length; i++) {
			number += str.substring(2 * i, 2 * i + 1);
		}
		try {
			input = decrypt(number + str.substring(length * 2), authkey);
			input = input.substring(0, input.indexOf("."));
			if (!input.matches("^((00)|(0))[1-9]\\d{5,20}$") && !input.matches("^[1-9](\\d{4,5}|\\d{7,9})$"))// 国际
																												// 国内
																												// uucall
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * 根据识别码获取时间
	 *
	 * @param clientSn
	 * @param authkey
	 * @return
	 */
	public static String getTime(String clientSn, String authkey) {
		String input = "";
		String str = clientSn.substring(1);
		String number = "";
		int length = Integer.valueOf(clientSn.substring(0, 1));
		for (int i = 0; i < length; i++) {
			number += str.substring(2 * i, 2 * i + 1);
		}
		try {
			input = decrypt(number + str.substring(length * 2), authkey);
			if (input.indexOf(".") == -1)
				return null;
			input = input.substring(input.indexOf(".") + 1, input.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * 根据识别码获取ID
	 *
	 * @param clientSn
	 * @return
	 */
	public static String getId(String clientSn) {
		int length = Integer.valueOf(clientSn.substring(0, 1));
		String str = clientSn.substring(1);
		String merchartId = "";
		for (int i = 0; i < length; i++) {
			merchartId += str.substring(2 * i + 1, 2 * i + 2);
		}
		return merchartId;
	}

	/**
	 * 示例
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		String info = System.currentTimeMillis() + "";
		String id = "zhengchao";
		String authkey = "key";
		// 生成客户端认证码(每次都可能不同)
		String clientSn = SecurityUtil.createClientSn(info, id, authkey, false);
		System.out.println("clientSn    :" + clientSn);
		System.out.println("calleeNumber:" + SecurityUtil.getInfo(clientSn, authkey));
		System.out.println("userId:     :" + SecurityUtil.getId(clientSn));
		System.out.println("Time        :" + SecurityUtil.getTime(clientSn, authkey));
		String s = SecurityUtil.encrypt("hello today", "7b4ad0ebee046c647b31d2b50fa1635f", false);
		System.out.println(s);
		try {
			System.out.println(SecurityUtil.decrypt(s, "7b4ad0ebee046c647b31d2b50fa1635f"));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}
}
