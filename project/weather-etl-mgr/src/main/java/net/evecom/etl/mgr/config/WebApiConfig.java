package net.evecom.etl.mgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebApiConfig {

	@Bean
	public Docket mgrApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("管理平台API").useDefaultResponseMessages(false)
				.apiInfo(new ApiInfoBuilder().version("1.0.0").title("管理平台API").description("管理平台API").build()).select()
				.apis(RequestHandlerSelectors.basePackage("net.evecom.etl.mgr.controller")).build();
	}

	@Bean
	public Docket mvcApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("基线功能API").useDefaultResponseMessages(false)
				.apiInfo(new ApiInfoBuilder().version("1.0.0").title("基线功能API").description("基线功能API").build()).select()
				.apis(RequestHandlerSelectors.basePackage("net.evecom.core.mvc.controller")).build();
	}

	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("长威科技WEBAPI")
				// 创建人
				.contact(new Contact("ZHENGC", "http://www.evecom.com.cn/", ""))
				// 版本号
				.version("1.0")
				// 描述
				.description("API 描述").build();
	}

}
