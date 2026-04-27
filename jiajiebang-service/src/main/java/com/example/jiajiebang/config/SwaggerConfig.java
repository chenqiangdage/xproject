package com.example.jiajiebang.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Swagger配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("家洁帮小程序 API")
                        .version("1.0.0")
                        .description("家洁帮小程序后端服务接口文档"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("请输入JWT Token，格式: Bearer <token>")))
                .security(Arrays.asList(
                        new SecurityRequirement().addList("bearer-jwt")));
    }
}
