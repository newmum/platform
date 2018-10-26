package net.evecom.core.rbac.config;

import net.evecom.tools.message.email.SendEmail;
import net.evecom.utils.file.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 短信及邮件发送配置
 * @author： zhengc
 * @date： 2018年5月30日17:13:38
 */
@Configuration
public class MessageConfig {
	@Bean(name = "sendEmail", value = "sendEmail")
	public SendEmail emailTool() {
        PropertiesUtils global = new PropertiesUtils(MessageConfig.class.getClassLoader().getResourceAsStream("app.properties"));
		SendEmail sendEmail = new SendEmail(global.getKey("sendEmailAccount"), global.getKey("sendEmailPassword"),
				global.getKey("mailSmtpPort"), global.getKey("mailSmtpHost"));
		return sendEmail;
	}


}
