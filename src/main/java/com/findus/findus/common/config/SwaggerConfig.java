package com.findus.findus.common.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
    Info info = new Info()
        .title("FindUs api docs")
        .version(springdocVersion)
        .description("findus api");

    return new OpenAPI()
        .components(new Components())
        .info(info);
  }
}
