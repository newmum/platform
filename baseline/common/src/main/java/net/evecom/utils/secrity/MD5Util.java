package net.evecom.utils.secrity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * @ClassName: MD5Util
 * @Description: MD5加密组件
 * @author： zhengc
 * @date： 2009年12月19日
 */
public class MD5Util {
    /**
     * 通过键值对获取校验码
     * @param data 请求参数集合
     * @return
     */
    public static String getCheckKey(Map<String, Object> data) {
        String result = null;
        StringBuffer buf = new StringBuffer();
        if (data != null && data.size() > 0) {
            ArrayList<String> keys = new ArrayList<String>(data.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                if (buf.length() > 0) {
                    buf.append("&");
                }
                buf.append(key);
                buf.append("=");
                buf.append(data.get(key));
            }
            result = getMD5(buf.toString().toUpperCase());
        }
        return result;
    }

    /**
     * 获取MD5
     * @param s
     * @return
     */
    public static String getMD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
