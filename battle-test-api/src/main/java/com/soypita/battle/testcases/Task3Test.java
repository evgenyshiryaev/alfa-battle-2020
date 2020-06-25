package com.soypita.battle.testcases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.task3.Branches;
import com.soypita.battle.testcases.models.task3.ErrorResponse;


// Total tests = TODO
// Points per test = TODO
public class Task3Test extends BaseTest {

    private static final String ROOT_FOLDER = "task3";

    private static final String BRANCHES_API_PATH = "/branches";


    // 1
    // tests = 4, points = TODO

    @Test
    public void getByIdSampleFoundTest(String host) throws Exception {
        Branches actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/612")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Branches.class);

        Branches expected = readObject(ROOT_FOLDER + "/1/branches-612.json", Branches.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getByIdSampleNotFoundTest(String host) throws Exception {
        ErrorResponse actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/1")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);

        ErrorResponse expected = new ErrorResponse("branch not found");
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getById1FoundTest(String host) throws Exception {
        Branches actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/631")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Branches.class);

        Branches expected = readObject(ROOT_FOLDER + "/1/branches-631.json", Branches.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getById1NotFoundTest(String host) throws Exception {
        ErrorResponse actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/2020")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);

        ErrorResponse expected = new ErrorResponse("branch not found");
        Assertions.assertEquals(expected, actual);
    }


    // 2
    // tests = TODO, points = TODO

    // TODO


    // 3
    // tests = TODO, points = TODO

    // TODO

}
