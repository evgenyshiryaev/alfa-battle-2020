package com.soypita.battle.testcases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.task1.AtmResponse;
import com.soypita.battle.testcases.models.task1.ErrorResponse;


// Total tests = 20
// Points per test = 4
public class Task1Test extends BaseTest {

    private static final String ROOT_FOLDER = "task1";

    private static final ErrorResponse NOT_FOUND_RESPONSE = new ErrorResponse("atm not found");

    private static final String API_PATH = "/atms";


    // 1
    // tests = 6, points = 24

    @Test
    public void getByIdSampleFoundTest(String host) throws Exception {
        do200Test(host, "/153463", "/1/atm-153463.json", AtmResponse.class);
    }

    @Test
    public void getByIdSampleNotFoundTest(String host) {
        do404AtmTest(host, "/1");
    }


    @Test
    public void getById1FoundTest(String host) throws Exception {
        do200Test(host, "/352074", "/1/atm-352074.json", AtmResponse.class);
    }

    @Test
    public void getById1NotFoundTest(String host) {
        do404AtmTest(host, "/69");
    }


    @Test
    public void getById2FoundTest(String host) throws Exception {
        do200Test(host, "/220588", "/1/atm-220588.json", AtmResponse.class);
    }

    @Test
    public void getById2NotFoundTest(String host) {
        do404AtmTest(host, "/666");
    }


    // 2
    // tests = 6, points = 24

    @Test
    public void getNearestSample1Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=55.66&longitude=37.63",
                "/2/atm-153463.json", AtmResponse.class);
    }

    @Test
    public void getNearestSample2Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=55.66&longitude=37.63&payments=true",
                "/2/atm-210612.json", AtmResponse.class);
    }


    @Test
    public void getNearest1Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=70&longitude=60",
                "/2/atm-156116.json", AtmResponse.class);
    }

    @Test
    public void getNearest2Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=70&longitude=60&payments=true",
                "/2/atm-503580.json", AtmResponse.class);
    }


    @Test
    public void getNearest3Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=40.1234&longitude=44.4321",
                "/2/atm-222343.json", AtmResponse.class);
    }

    @Test
    public void getNearest4Test(String host) throws Exception {
        do200Test(host, "/nearest?latitude=40.1234&longitude=44.4321&payments=true",
                "/2/atm-222343.json", AtmResponse.class);
    }


    // 3
    // tests = 8, points = 32

    @Test
    public void getNearestWithAfikSample1Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=55.66&longitude=37.63&alfik=300000",
                "/3/atms-sample1.json", AtmResponse[].class);
    }

    @Test
    public void getNearestWithAfikSample2Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=55.66&longitude=37.63&alfik=400000",
                "/3/atms-sample2.json", AtmResponse[].class);
    }


    @Test
    public void getNearestWithAfik1Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=439501",
                "/3/atms-1.json", AtmResponse[].class);
    }

    @Test
    public void getNearestWithAfik2Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=439502",
                "/3/atms-2.json", AtmResponse[].class);
    }

    @Test
    public void getNearestWithAfik3Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=2200000",
                "/3/atms-3.json", AtmResponse[].class);
    }


    @Test
    public void getNearestWithAfik4Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=51&longitude=81&alfik=439501",
                "/3/atms-4.json", AtmResponse[].class);
    }

    @Test
    public void getNearestWithAfik5Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=51&longitude=81&alfik=1000000",
                "/3/atms-5.json", AtmResponse[].class);
    }

    @Test
    public void getNearestWithAfik6Test(String host) throws Exception {
        do200Test(host, "/nearest-with-alfik?latitude=45&longitude=70&alfik=990000",
                "/3/atms-6.json", AtmResponse[].class);
    }


    private <T> void do200Test(String host, String path, String expectedPath, Class<T> clazz) throws Exception {
        T actual = getGiven(host)
                .when().get(API_PATH + path)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(clazz);

        T expected = readObject(ROOT_FOLDER + expectedPath, clazz);
        if (!clazz.isArray()) {
            Assertions.assertEquals(expected, actual);
        } else {
            Assertions.assertArrayEquals((Object[])expected, (Object[])actual);
        }
    }


    private void do404AtmTest(String host, String path) {
        ErrorResponse actual = getGiven(host)
                .when().get(API_PATH + path)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);

        Assertions.assertEquals(NOT_FOUND_RESPONSE, actual);
    }

}
