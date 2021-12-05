package com.hl.common.swagger;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/12 20:49
 * @Version 1.0
 * @Description
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(myApiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hl.xinguanservice.controller"))
                .build();
    }

    private ApiInfo myApiInfo(){
        return new ApiInfoBuilder()
                .title("管理系统网站-核心API文档")
                .description("本文档描述了本项目微服务接口定义")
                .version("1.0")
                .contact(new Contact("黄亮", "http://baidu.com",
                        "2446738664@qq.com"))
                .build();
    }

    private List<ApiKey> securitySchemes() {
        ArrayList<ApiKey> apiKeyList = Lists.newArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}
