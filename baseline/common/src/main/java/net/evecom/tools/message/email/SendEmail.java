package net.evecom.tools.message.email;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @ClassName: SendEmail
 * @Description: 发送邮件工具
 * @author: zhengc
 * @date: 2018年3月14日
 */
public class SendEmail {
	/**
	 * 发件人的邮箱地址和密码
	 */
	private String sendEmailAccount;
	/**
	 * 如果有授权码，此处填写授权码
	 */
	private String sendEmailPassword;
	/**
	 * 端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
	 */
	private String mailSmtpPort = "587";
	/**
	 * 发件人邮箱的 SMTP 服务器地址, 可以登录web邮箱查询
	 */
	private String mailSmtpHost = "smtp.qq.com";
	/**
	 * 设置配置文件
	 */
	private Properties props;
	/**
	 * 构建授权信息，用于进行SMTP进行身份验证
	 */
	private Authenticator authenticator;
	/**
	 * 使用环境属性和授权信息，创建邮件会话
	 */
	private Session mailSession;

	/**
	 * 初始化发送邮件对象的基本信息
	 *
	 * @param sendEmailAccount
	 *            账户
	 * @param sendEmailPassword
	 *            密码
	 * @param mailSmtpPort
	 *            服务端口
	 * @param mailSmtpHost
	 *            服务地址
	 */
	public SendEmail(String sendEmailAccount, String sendEmailPassword, String mailSmtpPort, String mailSmtpHost) {
		super();
		this.sendEmailAccount = sendEmailAccount;
		this.sendEmailPassword = sendEmailPassword;
		this.mailSmtpPort = mailSmtpPort;
		this.mailSmtpHost = mailSmtpHost;
		this.props = new Properties();
		// 表示SMTP发送邮件，必须进行身份验证
		this.props.put("mail.smtp.auth", "true");
		// 此处填写SMTP服务器
		this.props.put("mail.smtp.host", this.mailSmtpHost);
		// 端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
		this.props.put("mail.smtp.port", this.mailSmtpPort);
		// 此处填写你的账号
		this.props.put("mail.user", this.sendEmailAccount);
		// 此处的密码就是前面说的16位STMP口令
		this.props.put("mail.password", this.sendEmailPassword);

		// 构建授权信息，用于进行SMTP进行身份验证
		this.authenticator = new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				String userName = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};

		// 使用环境属性和授权信息，创建邮件会话
		this.mailSession = Session.getInstance(this.props, this.authenticator);
	}

	/**
	 * 发送邮件方法
	 *
	 * @param receiveMailAccount
	 *            收件人邮箱地址, 群发方式:直接用String多个地址有逗号隔开
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param type
	 *            邮件格式 , 默认text/html;charset=UTF-8
	 * @param flag
	 *            是否查看邮件详细的发送 log
	 * @throws Exception
	 */
	public void send(String receiveMailAccount, String subject, String content, String type, boolean flag)
			throws Exception {
		// 创建邮件消息
		MimeMessage message = new MimeMessage(this.mailSession);
		// 设置发件人
		InternetAddress form = new InternetAddress(this.props.getProperty("mail.user"));
		message.setFrom(form);

		// 设置收件人的邮箱
		InternetAddress to = new InternetAddress(receiveMailAccount);
		message.setRecipient(RecipientType.TO, to);

		// 设置邮件标题
		if (subject == null) {
			subject = "";
		}
		message.setSubject(subject);

		// 设置邮件的内容体
		if (content == null) {
			content = "";
		}
		if (type == null) {
			type = "text/html;charset=UTF-8";
		}
		message.setContent(content, type);

		Session session = Session.getDefaultInstance(this.props);

		session.setDebug(flag); // 设置为debug模式, 可以查看详细的发送 log
		// 最后当然就是发送邮件啦
		Transport.send(message);
	}

	public String getSendEmailAccount() {
		return sendEmailAccount;
	}

	public void setSendEmailAccount(String sendEmailAccount) {
		this.sendEmailAccount = sendEmailAccount;
	}

	public String getSendEmailPassword() {
		return sendEmailPassword;
	}

	public void setSendEmailPassword(String sendEmailPassword) {
		this.sendEmailPassword = sendEmailPassword;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public Session getMailSession() {
		return mailSession;
	}

	public void setMailSession(Session mailSession) {
		this.mailSession = mailSession;
	}

}
