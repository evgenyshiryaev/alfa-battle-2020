package com.x5.promo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableConfigurationProperties(ApplicationConfiguration.ReferenceDataProperties.class)
public class ApplicationConfiguration {

  @Data
  @Validated
  @ConfigurationProperties("app.reference")
  public static class ReferenceDataProperties {
    @NotNull
    Resource itemsPath;
    @NotNull
    Resource itemGroupsPath;
  }


  @EnableSwagger2WebMvc
  @Configuration
  public static class SwaggerConfiguration {
    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2) //
        .select() //
        .apis(RequestHandlerSelectors.basePackage("com.x5.promo.controllers")) //
        .paths(PathSelectors.any()) //
        .build() //
        .useDefaultResponseMessages(false) //
        .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
      return new ApiInfo( //
        "На промоигле", //
        "Задача для Alfa Battle от X5 Retail Group", //
        "1.0.0", //
        null, //
        new Contact("X5 Retail Group", "https://x5.ru/", null), //
        null, //
        null, //
        List.of());
    }
  }

}
