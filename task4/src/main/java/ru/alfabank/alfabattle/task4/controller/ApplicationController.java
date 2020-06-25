package ru.alfabank.alfabattle.task4.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.alfabank.alfabattle.task4.entity.ResponseStatus;
import ru.alfabank.alfabattle.task4.service.ApplicatioinServises;


@RestController
public class ApplicationController {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    ApplicatioinServises servises = new ApplicatioinServises();

    @GetMapping(path = "/admin/health")
    public ResponseEntity<?> health() throws IOException {
        return ResponseEntity.ok(ResponseStatus.builder().status("UP").build());
    }

    @PutMapping(path = "/loadPersons")
    public ResponseEntity<?> loadPerson() throws IOException {
        servises.setPeople();
        return ResponseEntity.ok(ResponseStatus.builder().status("OK").build());
    }

    @PutMapping(path = "/loadLoans")
    public ResponseEntity<?> loasdLoan() throws IOException {

        servises.setLoan();
        return ResponseEntity.ok(ResponseStatus.builder().status("OK").build());
    }

    @GetMapping(path = "/getPerson/{docId}")
    public ResponseEntity<?> getPerson(@PathVariable String docId) throws IOException {
        return servises.getPerson(docId);
    }

    @GetMapping(path = "/getLoan/{loan}")
    public ResponseEntity<?> руеLoan(@PathVariable String loan) throws IOException {
        return servises.getLoan(loan);
    }

    @GetMapping(path = "/creditHistory/{docId}")
    public ResponseEntity<?> creditHistory(@PathVariable String docId) throws IOException {

        return ResponseEntity.ok(servises.personLoans(docId));
    }


    @GetMapping(path = "/creditClosed")
    public ResponseEntity<?> creditClosed() throws IOException {

        return ResponseEntity.ok(servises.loansClosed());
    }

    @GetMapping(path = "/LoansSortByPersonBirthday")
    public ResponseEntity<?> loansSortByPersonBirthday() throws IOException {

        return ResponseEntity.ok(servises.loansSortByPersonBirthday());
    }


}
