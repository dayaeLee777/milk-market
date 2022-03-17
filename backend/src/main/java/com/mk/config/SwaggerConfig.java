package com.mk.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mk.api.controller")).paths(PathSelectors.any()).build()
				.securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey()))
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("MilkMarket BACKEND APIs", "For API SERVER descriptions.", "v1.0", "Terms of service",
				new Contact("", "", ""), "License of API", "", Collections.emptyList());
	}



	private ApiKey apiKey() {
		return new ApiKey("AccessToken", "Authorization", "header");
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("AccessToken", authorizationScopes));
	}

	private SecurityContext securityContext() {
		return springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(defaultAuth())
				.build();
	}
}
