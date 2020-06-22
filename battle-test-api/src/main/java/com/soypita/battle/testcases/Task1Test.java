package com.soypita.battle.testcases;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.Task1AtmResponse;


// Total tests = 20
// Points per test = 4
public class Task1Test extends BaseTest {

    // 1
    // tests = 6, points = 24

    @Test
    public void getByIdSampleFoundTest(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/153463")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                153463, "55.6610213", "37.6309405", "Москва", "Старокаширское ш., 4, корп. 10", false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getByIdSampleNotFoundTest(String host) {
        getGiven(host)
                .when().get("/1")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    public void getById1FoundTest(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/352074")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                352074, "64.53249", "40.602063", "Архангельск", "проспект Московский, д. 35", false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getById1NotFoundTest(String host) {
        getGiven(host)
                .when().get("/69")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    public void getById2FoundTest(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/220588")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                220588, "59.903088", "29.768237", "Ломоносов", "ул. Михайловская,  д. 40/7,  литер А", true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getById2NotFoundTest(String host) {
        getGiven(host)
                .when().get("/666")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }


    // 2
    // tests = 6, points = 24

    @Test
    public void getNearestSample1Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=55.66&longitude=37.63")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                153463, "55.6610213", "37.6309405", "Москва", "Старокаширское ш., 4, корп. 10", false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestSample2Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=55.66&longitude=37.63&payments=true")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                210612, "55.66442", "37.628051", "Москва", "Каширское шоссе, д. 14", true);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getNearest1Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=70&longitude=60")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                156116, "76.101696", "61.040667", "Балашиха", "ул. Поповка, влд., 5", false);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearest2Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=70&longitude=60&payments=true")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                503580, "59.7016128", "56.7082509", "Соликамск", "Северная ул., 46", true);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getNearest3Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=40.1234&longitude=44.4321")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                222343, "44.173951", "43.444679", "Георгиевск", "ул. Октябрьская, 148", true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearest4Test(String host) {
        Task1AtmResponse actual = getGiven(host)
                .when().get("/nearest?latitude=40.1234&longitude=44.4321&payments=true")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse.class);

        Task1AtmResponse expected = new Task1AtmResponse(
                222343, "44.173951", "43.444679", "Георгиевск", "ул. Октябрьская, 148", true);
        Assertions.assertEquals(expected, actual);
    }


    // 3
    // tests = 8, points = 32

    @Test
    public void getNearestWithAfikSample1Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=55.66&longitude=37.63&alfik=300000")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(153463, "55.6610213", "37.6309405", "Москва", "Старокаширское ш., 4, корп. 10", false));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestWithAfikSample2Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=55.66&longitude=37.63&alfik=400000")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(153463, "55.6610213", "37.6309405", "Москва", "Старокаширское ш., 4, корп. 10", false),
                new Task1AtmResponse(153465, "55.6602801", "37.633823", "Москва", "Каширское ш., 18", false));
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getNearestWithAfik1Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=439501")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(504265, "64.52422", "40.601758", "Архангельск", "Ленинградский пр-кт, 38", true));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestWithAfik2Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=439502")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(504265, "64.52422", "40.601758", "Архангельск", "Ленинградский пр-кт, 38", true),
                new Task1AtmResponse(352074, "64.53249", "40.602063", "Архангельск", "проспект Московский, д. 35", false));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestWithAfik3Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=64.5&longitude=40.6&alfik=2200000")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(504265, "64.52422", "40.601758", "Архангельск", "Ленинградский пр-кт, 38", true),
                new Task1AtmResponse(352074, "64.53249", "40.602063", "Архангельск", "проспект Московский, д. 35", false),
                new Task1AtmResponse(501212, "64.5437283", "40.5678794", "Архангельск", "ул. Тимме, 4", true),
                new Task1AtmResponse(755070, "64.536527", "40.557936", "Архангельск", "пр-кт Обводный канал, 9, корп. 3", false),
                new Task1AtmResponse(221558, "64.546803", "40.557164", "Архангельск", "Воскресенская ул., 108", true));
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getNearestWithAfik4Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=51&longitude=81&alfik=439501")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(502457, "51.5181636", "81.2115087", "Рубцовск", "пр-кт Ленина, 58", true));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestWithAfik5Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=51&longitude=81&alfik=1000000")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(502457, "51.5181636", "81.2115087", "Рубцовск", "пр-кт Ленина, 58", true),
                new Task1AtmResponse(351894, "54.367677", "81.902674", "Ордынское", "пр-кт Революции, 20", false),
                new Task1AtmResponse(502350, "53.251703", "83.686278", "Барнаул", "ул. Белинского, 12", true));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearestWithAfik6Test(String host) {
        List<Task1AtmResponse> actual = Arrays.asList(getGiven(host)
                .when().get("/nearest-with-alfik?latitude=45&longitude=70&alfik=990000")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Task1AtmResponse[].class));

        List<Task1AtmResponse> expected = Arrays.asList(
                new Task1AtmResponse(195512, "54.954512", "72.630785", "Марьяновка", "ул. Южная, 1", false),
                new Task1AtmResponse(503842, "54.9501", "73.0281", "Лузино", "ул. Майорова, 18", true));
        Assertions.assertEquals(expected, actual);
    }

}
