package com.hubo.gillajabi.global.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile({"local", "dev"})
@OpenAPIDefinition(
        info = @Info(
                title = "Hubo API 명세서",
                description = "Hubo API 명세서를 통해 제공되는 모든 엔드포인트에 대한 상세 설명 및 인증 방법을 포함합니다.",
                version = "v0.0.1"
        ),
        security = {
                @SecurityRequirement(name = "JWT Auth"),
        },
        servers = {
                @Server(url = "http://localhost:8080", description = "로컬 서버")
        }
)
@SecurityScheme(
        name = "JWT Auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }
}
