package com.infoway.banking.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Banking Services")
				.description("Documentação da API de acesso aos endpoints do sistema de serviços bancários.").version("1.0")
				.build();
	}
	
	@Bean
	public Docket api() {
		List<SecurityContext> securityContexts = new ArrayList<SecurityContext>();
		securityContexts.add(securityContext("/banco/**", "Banco", "acessa os endpoits de /banco"));
		securityContexts.add(securityContext("/conta/**", "Cliente", "acessa os endpoits de /conta"));
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.infoway.banking")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())
				.securityContexts(securityContexts)
				.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())));
	}
	
	private SecurityContext securityContext(String path, String scope, String description) {
	    return SecurityContext.builder()
	        .securityReferences(authScopes(scope, description))
	        .forPaths(PathSelectors.ant(path))
	        .build();
	}

	private List<SecurityReference> authScopes(String scope, String description) {
	    AuthorizationScope authorizationScope = new AuthorizationScope(scope, description);
	    AuthorizationScope[] authorizationScopes = {authorizationScope};
	    return Arrays.asList(new SecurityReference("Token Access", authorizationScopes));
	}
	
}
