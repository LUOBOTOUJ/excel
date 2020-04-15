package com.acong.ssoserver;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**

 * Swagger2配置类
 * 在与spring boot 集成时，放在与Application.java同级的目录下
 * 通过@Configuration注解，让spring来加载该配置
 * 再通过@EnableSwagger2注解来启动Swagger2
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.acong.ssoserver"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("意历维系统Restful API")
                .description("意历维系统Restful API")
                .termsOfServiceUrl("http://127.0.0.1:8081/")
                .contact("jyt")
                .version("1.0")
                .build();
    }

}
