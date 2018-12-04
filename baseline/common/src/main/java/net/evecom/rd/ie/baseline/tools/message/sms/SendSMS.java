package net.evecom.rd.ie.baseline.tools.message.sms;

import cn.emay.test.performance.Emay;

/**
 * @ClassName: SendSMS
 * @Description: 发送短信工具
 * @author: zhengc
 * @date: 2018年3月14日
 */
public class SendSMS {
	private static String host = "shmtn.b2m.cn:80";
	private static String secretKey = "A6C6976D480E9617";
	private static String appId = "EUCP-EMY-SMS1-1YWPD";// 104855 营销
	private static String appId2 = "EUCP-EMY-SMS1-02WD0";// 245908通知
	private static String secretKey2 = "3EBCF6224CF48E1C";//

	/**
	 * 短信发送
	 *
	 * @param text
	 *            文本内容
	 * @param tel
	 *            接收的电话号码
	 * @return
	 */
	public static boolean send_sms2(String text, String tel) {
		Emay emay = new Emay(host, appId2, secretKey2);
		return Emay.setSingleSmsBoolean(text, tel);
	}

	/**
	 * 短信发送
	 *
	 * @param text
	 *            文本内容
	 * @param tel
	 *            接收的电话号码
	 * @return
	 */
	public static boolean send_sms1(String text, String tel) {
		Emay emay = new Emay(host, appId, secretKey);
		return Emay.setSingleSmsBoolean(text, tel);
	}
}
