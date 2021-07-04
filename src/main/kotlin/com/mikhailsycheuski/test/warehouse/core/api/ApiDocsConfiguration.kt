package com.mikhailsycheuski.test.warehouse.core.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class ApiDocsConfiguration {

  @Bean
  fun api(): Docket? {
    return Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(basePackage("com.mikhailsycheuski.test.warehouse"))
      .paths(PathSelectors.any())
      .build()
      .consumes(setOf(APPLICATION_JSON_VALUE))
      .produces(setOf(APPLICATION_JSON_VALUE))
      .apiInfo(apiInfo());
  }

  fun apiInfo(): ApiInfo =
    ApiInfoBuilder()
      .title("Warehouse service API")
      .description("Exposes REST end points to manage inventory and products")
      .build()
}