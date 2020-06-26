package com.soypita.battle.testcases;

import com.soypita.battle.testcases.models.task3.BranchesWithDistance;
import com.soypita.battle.testcases.models.task3.BranchesWithPredicting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.task3.Branches;
import com.soypita.battle.testcases.models.task3.ErrorResponse;


// Total tests = 20
// Points per test = 4
public class Task3Test extends BaseTest {

    private static final String ROOT_FOLDER = "task3";

    private static final String BRANCHES_API_PATH = "/branches";


    // 1
    // tests = 4, points = 16

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
    // tests = 6, points = 24

    @Test
    public void getNearBranch1Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.773284&lon=37.624125")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance1.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void getNearBranch2Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.672391&lon=37.562423")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance2.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearBranch3Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.797142&lon=37.787642")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance3.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearBranch4Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.788631&lon=37.610488")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance4.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearBranch5Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.760112&lon=37.616238")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance5.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNearBranch6Test(String host) throws Exception {
        BranchesWithDistance actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "?lat=55.747938&lon=37.627654")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithDistance.class);

        BranchesWithDistance expected = readObject(ROOT_FOLDER + "/2/distance6.json", BranchesWithDistance.class);
        Assertions.assertEquals(expected, actual);
    }


    // 3
    // tests = 10, points = 40

    @Test
    public void getPredictingForBranchNotFoundTest(String host) throws Exception {
        ErrorResponse actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/123/predict?dayOfWeek=1&hourOfDay=14")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);

        ErrorResponse expected = new ErrorResponse("branch not found");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch1Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/612/predict?dayOfWeek=1&hourOfDay=14")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting1.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch2Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/631/predict?dayOfWeek=5&hourOfDay=16")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting2.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch3Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/627/predict?dayOfWeek=3&hourOfDay=11")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting3.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch4Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/612/predict?dayOfWeek=4&hourOfDay=17")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting4.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch5Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/631/predict?dayOfWeek=2&hourOfDay=16")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting5.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch6Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/627/predict?dayOfWeek=1&hourOfDay=12")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting6.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch7Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/631/predict?dayOfWeek=5&hourOfDay=16")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting7.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch8Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/612/predict?dayOfWeek=3&hourOfDay=13")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting8.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPredictingForBranch9Test(String host) throws Exception {
        BranchesWithPredicting actual = getGiven(host)
                .when().get(BRANCHES_API_PATH + "/627/predict?dayOfWeek=5&hourOfDay=11")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(BranchesWithPredicting.class);

        BranchesWithPredicting expected = readObject(ROOT_FOLDER + "/3/predicting9.json", BranchesWithPredicting.class);
        Assertions.assertEquals(expected, actual);
    }

}
