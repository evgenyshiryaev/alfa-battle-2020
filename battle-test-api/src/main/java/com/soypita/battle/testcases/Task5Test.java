package com.soypita.battle.testcases;

import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soypita.battle.testcases.models.task5.FinalPriceReceipt;
import com.soypita.battle.testcases.models.task5.PromoMatrix;
import com.soypita.battle.testcases.models.task5.ShoppingCart;


// Total tests = 20
// Points per test = 4
public class Task5Test extends BaseTest {

    private static final String ROOT_FOLDER = "task5";
    private static final String MATRIX_FILE_PATH = "/matrix.json";
    private static final String CART_FILE_PATH = "/cart.json";
    private static final String RECEIPT_FILE_PATH = "/receipt.json";

    private static final String MATRIX_API_PATH = "/promo";
    private static final String RECEIPT_API_PATH = "/receipt";


    private final ObjectMapper objectMapper = new ObjectMapper();


    // 1
    // tests = 3, points = 12

    @Test
    public void noPromo10Test(String host) throws Exception {
        doTest(host, "/1/0");
    }

    @Test
    public void noPromo11Test(String host) throws Exception {
        doTest(host, "/1/1");
    }

    @Test
    public void noPromo12Test(String host) throws Exception {
        doTest(host, "/1/2");
    }


    // 2
    // tests = 3, points = 12

    @Test
    public void loyaltyCard20Test(String host) throws Exception {
        doTest(host, "/2/0");
    }

    @Test
    public void loyaltyCard21Test(String host) throws Exception {
        doTest(host, "/2/1");
    }

    @Test
    public void loyaltyCard22Test(String host) throws Exception {
        doTest(host, "/2/2");
    }


    // 3
    // tests = 7, points = 28

    @Test
    public void nk30Test(String host) throws Exception {
        doTest(host, "/3/0");
    }

    @Test
    public void nk31Test(String host) throws Exception {
        doTest(host, "/3/1");
    }

    @Test
    public void nk32Test(String host) throws Exception {
        doTest(host, "/3/2");
    }

    @Test
    public void nk33Test(String host) throws Exception {
        doTest(host, "/3/3");
    }

    @Test
    public void nk34Test(String host) throws Exception {
        doTest(host, "/3/4");
    }

    @Test
    public void nk35Test(String host) throws Exception {
        doTest(host, "/3/5");
    }

    @Test
    public void nk36Test(String host) throws Exception {
        doTest(host, "/3/6");
    }


    // 4
    // tests = 7, points = 28

    @Test
    public void group40Test(String host) throws Exception {
        doTest(host, "/4/0");
    }

    @Test
    public void group41Test(String host) throws Exception {
        doTest(host, "/4/1");
    }

    @Test
    public void group42Test(String host) throws Exception {
        doTest(host, "/4/2");
    }

    @Test
    public void group43Test(String host) throws Exception {
        doTest(host, "/4/3");
    }

    @Test
    public void group44Test(String host) throws Exception {
        doTest(host, "/4/4");
    }

    @Test
    public void group45Test(String host) throws Exception {
        doTest(host, "/4/5");
    }

    @Test
    public void group46Test(String host) throws Exception {
        doTest(host, "/4/6");
    }


    // utils

    private void doTest(String host, String path) throws Exception {
        postMatrix(host, path);

        FinalPriceReceipt actual = postCart(host, path);

        FinalPriceReceipt expected = readObject(path + RECEIPT_FILE_PATH, FinalPriceReceipt.class);
        Assertions.assertEquals(expected, actual);
    }


    private void postMatrix(String host, String path) throws Exception {
        PromoMatrix matrix = readObject(path + MATRIX_FILE_PATH, PromoMatrix.class);
        getGiven(host)
                .body(matrix).post(MATRIX_API_PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }


    private FinalPriceReceipt postCart(String host, String path) throws Exception {
        ShoppingCart cart = readObject(path + CART_FILE_PATH, ShoppingCart.class);
        return getGiven(host)
                .body(cart).post(RECEIPT_API_PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(FinalPriceReceipt.class);
    }


    private <T> T readObject(String path, Class<T> clazz) throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(ROOT_FOLDER + path);
        return objectMapper.readValue(stream, clazz);
    }

}
