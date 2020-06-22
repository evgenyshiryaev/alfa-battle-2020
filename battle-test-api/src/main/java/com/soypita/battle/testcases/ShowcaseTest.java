package com.soypita.battle.testcases;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class ShowcaseTest extends BaseTest {

    @Test
    public void shouldPassHealthCheck(String host) {
        getGiven(host)
                .when().get("/actuator/health")
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }

}
