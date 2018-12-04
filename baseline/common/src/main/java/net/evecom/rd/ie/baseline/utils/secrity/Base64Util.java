package net.evecom.rd.ie.baseline.utils.secrity;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: Base64Util
 * @Description: Base64加密组件
 * @author： zhengc
 * @date： 2009年12月19日
 */
public class Base64Util {
    /**
     * 解密
     * @param s
     * @return
     */
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {

			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

    /**
     * 加密
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * 实例
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Base64Util.getBase64("123321"));
		System.out.println(Base64Util.getFromBase64("MTIzMzIx"));
	}

}
