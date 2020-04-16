package com.acong.ssoserver;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**

 * Swagger2配置类
 * 在与spring boot 集成时，放在与Application.java同级的目录下
 * 通过@Configuration注解，让spring来加载该配置
 * 再通过@EnableSwagger2注解来启动Swagger2
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig  {
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authorization").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());

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
