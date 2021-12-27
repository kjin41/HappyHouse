package com.ssafy.hw2.swagger;
 
import org.springframework.context.annotation.Bean;
 
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
	 
//swagger 사용을 위해 선언한다.
@EnableSwagger2
public class SwaggerConfig {
@Bean
	public Docket api() {
	 final ApiInfo apiInfo = new ApiInfoBuilder()
	  .title("상품관리프로그램 API")
	  .description("<h3>워크샵에서 사용되는 RestApi에 대한 문서를 제공한다.</h3>")
	  .contact(new Contact("SSAFY", "https://hw2.ssafy.com", "admin@ssafy.com"))
	  .license("MIT License")
	  .version("1.0")
	  .build();
	 // Swagger 2.0 기반의 문서 작성
	return new Docket(DocumentationType.SWAGGER_2)  
	 // 문서에 대한 정보 설정
	 .apiInfo(apiInfo)   
	 // ApiSelectorBuilder를 반환하며 상세한 설정 처리
	 .select()       
	 // 대상으로하는 api 설정
	 .apis(RequestHandlerSelectors.basePackage("com.ssafy.hw2.controller"))
	 // controller에서 swagger를 지정할 대상 path 설정
	 .paths(PathSelectors.ant("/inoutapi/**")) 
	 // Docket 객체 생성 
	.build();
	}
}