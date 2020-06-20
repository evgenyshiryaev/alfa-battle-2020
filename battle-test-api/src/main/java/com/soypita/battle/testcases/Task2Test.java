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

    @Test
    public void successfullyPassHealthCheck(String host) {
        getGiven(host)
                .when().get("/admin/health")
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void successfullyGetPaymentAnalyticForAllUsersAndVerifyIt(String host) {
        // when
        var resp = getGiven(host)
                .when().get("/analytic")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", UserPaymentAnalytic.class);

        // then
        assertNotNull(resp);
        assertFalse(resp.isEmpty());
        assertEquals(5, resp.size());
        resp.stream().forEach(
                analytic -> assertTrue(VALID_USER_ID_SET.contains(analytic.getUserId()))
        );
    }

    @Test
    public void successfullyGetPaymentAnalyticByUserId(String host) {
        // when
        var resp = getGiven(host)
                .when().get("/analytic/XAABAA")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(UserPaymentAnalytic.class);
        // then
        assertNotNull(resp);
        assertEquals("XAABAA", resp.getUserId());
        assertEquals(3, resp.getAnalyticInfo().size());
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
        getGiven(host)
                .when().get("/analytic/UNEXPECTED")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void successfullyReturnUserPaymentStats(String host) {
        // when
        var resp = getGiven(host)
                .when().get("/analytic/XAABAA/stats")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(UserPaymentStats.class);

        // then
        assertNotNull(resp);
        assertEquals(2, resp.getRareCategoryId());
        assertEquals(1, resp.getMaxAmountCategoryId());
        assertEquals(2, resp.getMinAmountCategoryId());
    }

    @Test
    public void shouldReturnNotFoundCodeForUserPaymentStatsByUnexpectedUserID(String host) {
        getGiven(host)
                .when().get("/analytic/UNEXPECTED/stats")
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void successfullyGetUserPaymentTemplate(String host) {
        // when
        var resp = getGiven(host)
                .when().get("/analytic/XA1VWF/templates")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", UserTemplate.class);

        // then
        assertNotNull(resp);
        assertFalse(resp.isEmpty());
        assertEquals(2, resp.size());
        resp.stream().forEach(
                template -> {
                    assertTrue(VALID_RECIPIENT_TEMPLATE_SET.contains(template.getRecipientId()));
                    assertTrue(VALID_CATEGORY_TEMPLATE_SET.contains(template.getCategoryId()));
                }
        );
    }

    @Test
    public void shouldReturnEmptyListForGetUserPaymentTemplateByUnexpectedUserID(String host) {
        // when
        var resp = getGiven(host)
                .when().get("/analytic/UNEXPECTED/templates")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", UserTemplate.class);

        // then
        assertNotNull(resp);
        assertTrue(resp.isEmpty());
    }
}
