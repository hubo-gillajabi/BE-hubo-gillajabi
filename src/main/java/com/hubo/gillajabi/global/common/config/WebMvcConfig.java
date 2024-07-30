package com.hubo.gillajabi.global.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    private static final String[] ALLOWED_PATHS = {
            "/swagger-ui/**",
            "/api/**",
            "/graphql",
            "/graphiql/**",
            "/v3/api-docs/**"
    };

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping(Arrays.toString(ALLOWED_PATHS))
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true)
                .allowedHeaders("Content-Type", "Authorization", "Cookie");
    }

//    @Bean
//    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
//        return CookieSameSiteSupplier.ofNone();
//    }
}
