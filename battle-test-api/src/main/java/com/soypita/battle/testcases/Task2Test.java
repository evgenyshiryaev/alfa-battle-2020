package com.soypita.battle.testcases;

import com.soypita.battle.testcases.models.task2.UserPaymentAnalytic;
import com.soypita.battle.testcases.models.task2.UserPaymentStats;
import com.soypita.battle.testcases.models.task2.UserTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class Task2Test extends BaseTest {

    private static Set<String> VALID_USER_ID_SET = Set.of("XAABAA", "XA3SZV", "XA1VWF", "XA1VWB", "XA1VWJ");
    private static Set<String> VALID_RECIPIENT_TEMPLATE_SET = Set.of("XA6BFO", "XA6F9K");
    private static Set<Integer> VALID_CATEGORY_TEMPLATE_SET = Set.of(1, 3);
    private static String USER_NOT_FOUND_RESPONSE = "user not found";

    @Test
    public void successfullyPassHealthCheck(String host) {
        log.info("Run successfullyPassHealthCheck test for {}", host);
        getGiven(host)
                .when().get("/admin/health")
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void successfullyGetPaymentAnalyticForAllUsersAndVerifyIt(String host) {
        log.info("Run successfullyGetPaymentAnalyticForAllUsersAndVerifyIt test for {}", host);

        // when
        var resp = getGiven(host)
                .when().get("/analytic")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", UserPaymentAnalytic.class);

        log.info("Request perform with response {}", resp);

        // then
        assertNotNull(resp, "Expect not null response");
        assertFalse(resp.isEmpty(), "Expect not empty payment analytic response");
        assertEquals(5, resp.size(), "Response analytic list size doesnt' match");
        resp.stream().forEach(
                analytic -> assertTrue(VALID_USER_ID_SET.contains(analytic.getUserId()), "Unexpected user id in analytic response")
        );
    }

    @Test
    public void successfullyGetPaymentAnalyticByUserId(String host) {
        log.info("Run successfullyGetPaymentAnalyticByUserId test for {}", host);

        // when
        var resp = getGiven(host)
                .when().get("/analytic/XAABAA")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(UserPaymentAnalytic.class);

        log.info("Request perform with response {}", resp);

        // then
        assertNotNull(resp, "Expect not null response");
        assertEquals("XAABAA", resp.getUserId(), "Unexpected user id in response");
        assertEquals(3, resp.getAnalyticInfo().size(), "Unexpected size of response");
        log.info("total sum {}, min {}, max {}, sum {}",
                resp.getTotalSum(),
                resp.getAnalyticInfo().get(1).getMin(),
                resp.getAnalyticInfo().get(1).getMax(),
                resp.getAnalyticInfo().get(1).getSum());
        assertTrue(new BigDecimal("1002161.49").compareTo(resp.getTotalSum()) == 0, "unexpected total sum result");
        assertNotNull(resp.getAnalyticInfo().getOrDefault(1, null));
        assertNotNull(resp.getAnalyticInfo().getOrDefault(2, null));
        assertNotNull(resp.getAnalyticInfo().getOrDefault(3, null));
        var userAnalytic = resp.getAnalyticInfo().get(1);
        assertTrue(new BigDecimal("63.17").compareTo(userAnalytic.getMin()) == 0, "unexpected min result");
        assertTrue(new BigDecimal("9996.61").compareTo(userAnalytic.getMax()) == 0, "unexpected max result");
        assertTrue(new BigDecimal("358920.72").compareTo(userAnalytic.getSum()) == 0, "unexpected category sum result");
    }

    @Test
    public void shouldReturnNotFoundCodeForGetPaymentAnalyticByUnexpectedUserID(String host) {
        log.info("Run shouldReturnNotFoundCodeForGetPaymentAnalyticByUnexpectedUserID test for {}", host);

        var resp = getGiven(host)
                .when().get("/analytic/UNEXPECTED")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().asString();

        assertTrue(resp.contains(USER_NOT_FOUND_RESPONSE));
    }

    @Test
    public void successfullyReturnUserPaymentStats(String host) {
        log.info("Run successfullyReturnUserPaymentStats test for {}", host);

        // when
        var resp = getGiven(host)
                .when().get("/analytic/XAABAA/stats")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(UserPaymentStats.class);

        log.info("Request perform with response {}", resp);

        // then
        assertNotNull(resp, "Expect not null response");
        assertEquals(2, resp.getRareCategoryId(), "Unexpected rareCategoryId value");
        assertEquals(1, resp.getMaxAmountCategoryId(), "Unexpected maxAmountCategoryId value");
        assertEquals(2, resp.getMinAmountCategoryId(), "Unexpected minAmountCategoryId value");
    }

    @Test
    public void shouldReturnNotFoundCodeForUserPaymentStatsByUnexpectedUserID(String host) {
        log.info("Run shouldReturnNotFoundCodeForUserPaymentStatsByUnexpectedUserID test for {}", host);

        var resp = getGiven(host)
                .when().get("/analytic/UNEXPECTED/stats")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().asString();

        assertTrue(resp.contains(USER_NOT_FOUND_RESPONSE));
    }

    @Test
    public void successfullyGetUserPaymentTemplate(String host) {
        log.info("Run successfullyGetUserPaymentTemplate test for {}", host);

        // when
        var resp = getGiven(host)
                .when().get("/analytic/XA1VWF/templates")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", UserTemplate.class);

        log.info("Request perform with response {}", resp);

        // then
        assertNotNull(resp, "Expect not null response");
        assertFalse(resp.isEmpty());
        assertEquals(2, resp.size(), "Unexpected template response size");
        resp.stream().forEach(
                template -> {
                    assertTrue(VALID_RECIPIENT_TEMPLATE_SET.contains(template.getRecipientId()), "Unexpected recipientId value");
                    assertTrue(VALID_CATEGORY_TEMPLATE_SET.contains(template.getCategoryId()), "Unexpected categoryId value");
                }
        );
    }

    @Test
    public void shouldReturnEmptyListForGetUserPaymentTemplateByUnexpectedUserID(String host) {
        log.info("Run shouldReturnEmptyListForGetUserPaymentTemplateByUnexpectedUserID test for {}", host);

        // when
        var resp = getGiven(host)
                .when().get("/analytic/UNEXPECTED/templates")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().asString();

        log.info("Request perform with response {}", resp);

        // then
        assertTrue(resp.contains(USER_NOT_FOUND_RESPONSE));
    }
}
