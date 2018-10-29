package net.evecom.etl.processor.config;

import net.evecom.core.db.database.pool.DruidFilter;
import net.evecom.core.db.database.pool.DruidServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Druid监控web配置
 * @author： zhengc
 * @date： 2018年5月17日
 */
@Configuration
public class DBPoolConfig {
	private static final Logger log = LoggerFactory.getLogger(DBPoolConfig.class);

	/**
	 * 配置监控界面servlet
	 *
	 * @return
	 */
	@Bean
	public ServletRegistrationBean<DruidServlet> druidServlet() {
		log.info("rest druidServlet init");
		ServletRegistrationBean<DruidServlet> reg = new ServletRegistrationBean<DruidServlet>();
		reg.setServlet(new DruidServlet());
		reg.addUrlMappings("/druid/*");
		return reg;
	}

	/**
	 * 配置监控界面过滤器
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<DruidFilter> filterRegistrationBean() {
		log.info("rest filterRegistrationBean init");
		FilterRegistrationBean<DruidFilter> filterRegistrationBean = new FilterRegistrationBean<DruidFilter>();
		filterRegistrationBean.setFilter(new DruidFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

}
