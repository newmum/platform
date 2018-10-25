package net.evecom.tools.request;

import net.evecom.utils.secrity.MD5Util;

/**
 * @ClassName: SessionTool
 * @Description: 会话操作组件 @author： zhengc @date： 2014年3月17日
 */
public class SessionTool {

    public static String getSid() {
        return SessionTool.getSid("pc");
    }

	public static String getSid(String clientSys) {
		int j = (int) (Math.random() * 32000);
		String encrypt_key = MD5Util.getMD5(Integer.toString(j));
		encrypt_key = MD5Util.getMD5(encrypt_key + System.currentTimeMillis());
		StringBuilder sb = new StringBuilder(encrypt_key);
		int r = (int) (Math.random() * 10);
		sb = sb.replace(7, 8, r + "");
		String s1 = clientSys.subSequence(0, 1).toString();
		String s2 = clientSys.subSequence(1, 2).toString();
		sb.replace(Integer.valueOf("1" + r), Integer.valueOf("1" + r) + 1, s1);
		sb.replace(Integer.valueOf("2" + r), Integer.valueOf("2" + r) + 1, s2);
		return sb.toString();
	}

	public static String getClientSys(String sid) {
		StringBuilder sb = new StringBuilder(sid);
		String r = sb.substring(7, 8);
		String s1 = sb.substring(Integer.valueOf("1" + r), Integer.valueOf("1" + r) + 1);
		String s2 = sb.substring(Integer.valueOf("2" + r), Integer.valueOf("2" + r) + 1);
		return s1 + s2;
	}
}
