package net.evecom.etl.processor.config;

import net.evecom.core.rbac.web.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebAppConfig {

	@Bean
	public FilterRegistrationBean<CustomFilter> filterRegist() {
		FilterRegistrationBean<CustomFilter> frBean = new FilterRegistrationBean<CustomFilter>();
		frBean.setFilter(new CustomFilter());
		frBean.addUrlPatterns("/*");
		return frBean;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("50MB");
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("50MB");
		return factory.createMultipartConfig();
	}


}
