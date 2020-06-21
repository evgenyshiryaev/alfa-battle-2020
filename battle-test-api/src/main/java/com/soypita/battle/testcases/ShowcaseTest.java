package com.soypita.battle.testcases;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Slf4j
public class ShowcaseTest extends BaseTest {

    @Test
    public void shouldPassHealthCheck(String host) {
        log.info("Run showcase test for {}", host);
        getGiven(host)
                .when().get("/actuator/health")
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }
}
