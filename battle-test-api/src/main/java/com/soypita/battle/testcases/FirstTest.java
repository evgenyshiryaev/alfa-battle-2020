package com.soypita.battle.testcases;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import static io.restassured.RestAssured.get;

@Slf4j
@ExtendWith(CustomArgumentProvider.class)
public class FirstTest {
    @Test
    public void shouldPassHealthCheck(String host) {
        log.info("Host is {}", host);
        get(host + "/actuator/health").then().statusCode(200);
    }
}
