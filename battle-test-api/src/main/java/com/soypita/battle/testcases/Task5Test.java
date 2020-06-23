package com.soypita.battle.testcases;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.soypita.battle.testcases.models.task1.AtmResponse;
import com.soypita.battle.testcases.models.task5.FinalPricePosition;
import com.soypita.battle.testcases.models.task5.FinalPriceReceipt;
import com.soypita.battle.testcases.models.task5.ItemPosition;
import com.soypita.battle.testcases.models.task5.PromoMatrix;
import com.soypita.battle.testcases.models.task5.ShoppingCart;

//Total tests = TODO
//Points per test = TODO
public class Task5Test extends BaseTest {

    private static final String MATRIX_PATH = "/promo";
    private static final String RECEIPT_PATH = "/receipt";


    // 1
    // tests = TODO, points = TODO

    @Test
    public void noPromo10Test(String host) {
        PromoMatrix matrix = new PromoMatrix();
        postMatrix(host, matrix);

        ShoppingCart cart = new ShoppingCart(1, false, Arrays.asList(
                new ItemPosition("3432166", 1)));
        FinalPriceReceipt actual = postCart(host, cart);

        FinalPriceReceipt expected = new FinalPriceReceipt(
                new BigDecimal("141.99"), new BigDecimal("0.00"), Arrays.asList(
                        new FinalPricePosition("3432166", "ЛУК.Жид.АНТИФ.G11 Green нез.1кг",
                                new BigDecimal("141.99"), new BigDecimal("141.99"))));
        Assertions.assertEquals(expected, actual);
   }

    // TODO


    // utils

    private void postMatrix(String host, PromoMatrix matrix) {
        getGiven(host)
                .body(matrix).post(MATRIX_PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }


    private FinalPriceReceipt postCart(String host, ShoppingCart cart) {
        return getGiven(host)
                .body(cart).post(RECEIPT_PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(FinalPriceReceipt.class);
    }

}
