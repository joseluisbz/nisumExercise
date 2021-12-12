package org.bz.nisum.ms.app.usuarios;

import org.bz.nisum.ms.app.usuarios.controllers.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackageClasses = UserController.class)
@Configuration
public class SpringFoxConfiguration {
	   private static final String SWAGGER_API_VERSION="1.0";
	   private static final String LICENSE_TEXT ="License";
	   private static final String title ="User API";
	   private static final  String description ="Restful APIs for users";

	   private ApiInfo apiInfo(){
	       return  new ApiInfoBuilder()
	               .title(title)
	               .description(description)
	               .license(LICENSE_TEXT)
	               .version(SWAGGER_API_VERSION)
	               .build();
	   }

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host("http://localhost:8001/v2/api-docs")
				.apiInfo(apiInfo())
				.pathMapping("/")
				.select()
				.build();
		
				/*
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.bz.nisum.ms.app.usuarios.controllers"))
				.paths(PathSelectors.any())
				.build();
				*/
		}
}
