package com.api.portofolio.config;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-admin")
//                .pathsToMatch("/admin/**")
//                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class))
//                .build();
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portofolio API")
                        .version("0.0.1")
                        .description("Portofolio API Backend Documentation"))
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                        .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

//    @Bean
//    public OpenApiCustomizer openApiCustomizer() {
//        return openApi -> {
//            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
//                operation.getRequestBody().setContent(new Content()
//                        .addMediaType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, new MediaType()
//                                .schema(new Schema<>().type("object").properties(Map.of(
//                                        "skillsReq", new Schema<>().$ref("#/components/schemas/SkillsReq"),
//                                        "icon", new Schema<>().type("string").format("binary")
//                                )))));
//            }));
//        };
//    }
}