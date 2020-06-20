package com.soypita.battle.testcases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.Task1AtmResponse;


public class Task1Test extends BaseTest {

    // 1

    @Test
    public void getByIdFoundTest(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/352074")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                352074, "64.53249", "40.602063", "Архангельск", "проспект Московский, д. 35");
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getByIdNotFoundTest(String host) {
        getGiven(host)
                .when().get("/69")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }


    // 2

    // TODO


    // 3

    // TODO

}
