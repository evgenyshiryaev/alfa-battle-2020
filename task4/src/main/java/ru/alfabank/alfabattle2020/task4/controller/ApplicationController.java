package ru.alfabank.alfabattle2020.task4.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.alfabank.alfabattle2020.elasticredits.service.ApplicatioinServises;


@RestController
public class ApplicationController {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    ApplicatioinServises servises = new ApplicatioinServises();

    @PutMapping(path = "/loadPersons")
    public ResponseEntity<?> loadPerson() throws IOException {
        servises.setPeople();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/loadLoans")
    public ResponseEntity<?> loasdLoan() throws IOException {

        servises.setLoan();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/creditHistory/{docId}")
    public ResponseEntity<?> creditHistory(@PathVariable String docId) throws IOException {

        return ResponseEntity.ok(servises.personLoans(docId));
    }


    @GetMapping(path = "/creditClosed/")
    public ResponseEntity<?> creditClosed() throws IOException {

        return ResponseEntity.ok(servises.loansClosed());
    }

    @GetMapping(path = "/LoansSortByPersonBirthday/")
    public ResponseEntity<?> loansSortByPersonBirthday() throws IOException {

        return ResponseEntity.ok(servises.loansSortByPersonBirthday());
    }


}
