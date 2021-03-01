package dev.gabrielcunha.config.documentation;

import java.util.Collections;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ConfigurationProperties(prefix="app")
public class SwaggerConfig {

	private String name;
	
	private String version;
	
	private String description;
	
	private String organization;
	
	private String contextPath;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build()
//					.pathMapping(contextPath)
					.useDefaultResponseMessages(false);
	}
	
	private ApiInfo apiInfo() {

		return new ApiInfo(
				name, 
				description, 
				version, 
				organization,
				new Contact("Desenvolvimento", "", "gabrielcunhadev@gmail.com"), 
				"", 
				"", 
				Collections.emptyList());			
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public String toString() {
		return "SwaggerConfig [name=" + name + ", version=" + version + ", description=" + description
				+ ", organization=" + organization + ", contextPath=" + contextPath + "]";
	}
	
}
