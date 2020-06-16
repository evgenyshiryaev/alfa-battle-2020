package com.soypita.battle.testcases;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;

import static io.restassured.RestAssured.get;

@Slf4j
@ExtendWith(CustomArgumentProvider.class)
public class FirstTest {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5_000));
    }


    @Test
    public void shouldPassHealthCheck(String host) {
        log.info("Host is {}", host);
        get(host + "/actuator/health").then().statusCode(200);
    }

}
