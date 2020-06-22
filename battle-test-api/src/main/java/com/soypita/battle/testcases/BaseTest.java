package com.soypita.battle.testcases;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


@ExtendWith(CustomArgumentProvider.class)
public abstract class BaseTest {

    private static final int TIMEOUT = 5_000;


    @BeforeAll
    public static void beforeAll() {
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", TIMEOUT));
    }

    protected static RequestSpecification getGiven(String host) {
        return RestAssured.given().spec(getSpec(host));
    }


    private static RequestSpecification getSpec(String host) {
        return new RequestSpecBuilder()
                .setBaseUri(host)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

}
