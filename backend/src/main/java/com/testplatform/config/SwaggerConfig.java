package com.testplatform.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // 文档标题
                        .title("Wiki系统API文档")
                        // 文档描述
                        .description("Wiki系统接口文档")
                        // 文档版本
                        .version("1.0.0")
                        // 联系人信息
                        .contact(new Contact()
                                .name("Admin")
                                .url("http://example.com")
                                .email("admin@example.com")))
                // 添加全局安全配置
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                // 配置安全方案
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("请在此输入JWT token，格式为Bearer {token}")));
    }
}