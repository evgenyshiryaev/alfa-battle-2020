package com.soypita.battle.testcases;

import com.soypita.battle.testcases.models.task4.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class Task4Test extends BaseTest {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Set<String> VALID_LOANS_SET = Set.of("434224", "917105", "692147", "145020");
    private static Set<String> VALID_LOANS_10TH_SET = Set.of("442783", "146418", "692147", "553055");


    @Test
    public void successfullyPassHealthCheck(String host) {
        log.info("Run successfullyPassHealthCheck test for {}", host);
        getGiven(host)
                .when().get("/admin/health")
                .then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void successfullyLoadPerson(String host) throws InterruptedException {
        log.info("Run successfullyLoadPerson test for {}", host);
        getGiven(host).
                when().put("/loans/loadPersons")
                .then().assertThat().statusCode(HttpStatus.OK.value());

        Thread.sleep(1000 * 30);

        log.info("Request perform with response /getPerson/855406656");
        Person person = getGiven(host).
                when().get("/loans/getPerson/855406656")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Person.class);

        log.info("Request perform with response {}", person);

        assertNotNull(person, "Expect not null response");
        assertEquals("855406656", person.getDocid(), "Unexpected docId");
        assertEquals("1961-05-22", dateFormat.format(person.getBirthday()), "Unexpected birthday");
        assertEquals("Celina Jackson", person.getFio(), "Unexpected fio");
        assertEquals("F", person.getGender(), "Unexpected gender");
        assertTrue(new BigDecimal("69106.0").compareTo(person.getSalary()) == 0, "Unexpected salary");
    }

    @Test
    public void successfullyLoadLoans(String host) throws InterruptedException {
        log.info("Run successfullyLoadLoans test for {}", host);
        getGiven(host).
                when().put("/loans/loadLoans")
                .then().assertThat().statusCode(HttpStatus.OK.value());

        Thread.sleep(1000 * 30);

        log.info("Request perform with response /getLoan/692826");
        Loan loan = getGiven(host).
                when().get("/loans/getLoan/692826")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(Loan.class);

        log.info("Request perform with response {}", loan);

        assertNotNull(loan, "Expect not null response");
        assertEquals("692826", loan.getLoan(), "Unexpected loan");
        assertEquals("2017-01-16", dateFormat.format(loan.getStartdate()), "Unexpected startdate");
        assertEquals("027665876", loan.getDocument(), "Unexpected document");
        assertTrue(new BigDecimal("448900").compareTo(loan.getAmount()) == 0, "Unexpected amount");
        assertTrue(loan.getPeriod() == 48, "Unexpected period");
    }


    @Test
    public void successfullyClosedLoans(String host) throws InterruptedException {
        log.info("Run successfullyClosedLoans test for {}", host);

        List<Loan> loanList = getGiven(host).
                when().get("/loans/creditClosed")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", Loan.class);

        log.info("Request perform with response {}", loanList);

        assertNotNull(loanList, "Expect not null response");
        assertEquals(161, loanList.size(), "Response loans list size doesnt' match");
    }


    @Test
    public void successfullyCreditHistory(String host) throws InterruptedException {
        log.info("Run successfullyCreditHistory test for {}", host);

        PersonLoans loans = getGiven(host).
                when().get("/loans/creditHistory/737767072/")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(PersonLoans.class);

        log.info("Request perform with response {}", loans);

        assertNotNull(loans, "Expect not null response");
        assertTrue(loans.getCountLoan() == 4, "Response creditHistory countLoan size doesnt' match");
        assertTrue(new BigDecimal("1058400.0").compareTo(loans.getSumAmountLoans()) == 0, "Response creditHistory sumAmountLoans size doesnt' match");
        assertEquals(4, loans.getLoans().size(), "Response creditHistory loans size doesnt' match");
        loans.getLoans().stream().forEach(
                loan -> assertTrue(VALID_LOANS_SET.contains(loan.getLoan()), "Unexpected loan in loans block in creditHistory response")
        );
    }


    @Test
    public void personNotFound(String host) throws InterruptedException {
        log.info("Run personNotFound test for {}", host);

        ResponseStatus status = getGiven(host).
                when().get("/loans/getPerson/8554066563")
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ResponseStatus.class);
        assertNotNull(status, "Expect not null response");
        assertEquals("person not found", status.getStatus(), "Response getPerson status incorrect");
    }


    @Test
    public void loanNotFound(String host) throws InterruptedException {
        log.info("Run loanNotFound test for {}", host);

        ResponseStatus status = getGiven(host).
                when().get("/loans/getLoan/2312")
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ResponseStatus.class);
        assertNotNull(status, "Expect not null response");
        assertEquals("loan not found", status.getStatus(), "Response getLoan status incorrect");
    }


    @Test
    public void successfullyLoansSortByPersonBirthday(String host) throws InterruptedException {
        log.info("Run successfullyLoansSortByPersonBirthday test for {}", host);

        List<PersonWithLoans> personList = getGiven(host).
                when().get("/loans/loansSortByPersonBirthday")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", PersonWithLoans.class);

        log.info("Request perform with response {}", personList);

        assertNotNull(personList, "Expect not null response");
        assertEquals(100, personList.size(), "Response LoansSortByPersonBirthday list size doesnt' match");
        assertEquals("408828583", personList.get(9).getDocid(), "Response LoansSortByPersonBirthday document in 10th person doesnt' match");
        personList.get(9).getLoans().stream().forEach(
                loan -> assertTrue(VALID_LOANS_10TH_SET.contains(loan.getLoan()), "Response LoansSortByPersonBirthday loans in 10th person doesnt' match")
        );

    }

}
