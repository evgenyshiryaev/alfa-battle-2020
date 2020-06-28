package ru.alfabank.alfabattle.task4.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.alfabank.alfabattle.task4.entity.ResponseStatus;
import ru.alfabank.alfabattle.task4.service.ApplicatioinServises;


@RestController
@RequestMapping("/loans")
public class ApplicationController {
    @Autowired
    ApplicatioinServises servises;

    @PutMapping(path = "/loadPersons")
    public ResponseEntity<?> loadPerson() throws IOException {
        servises.setPeople();
        return ResponseEntity.ok(ResponseStatus.builder().status("OK").build());
    }

    @PutMapping(path = "/loadLoans")
    public ResponseEntity<?> loadLoan() throws IOException {

        servises.setLoan();
        return ResponseEntity.ok(ResponseStatus.builder().status("OK").build());
    }

    @GetMapping(path = "/getPerson/{docId}")
    public ResponseEntity<?> getPerson(@PathVariable String docId) throws IOException {
        return servises.getPerson(docId);
    }

    @GetMapping(path = "/getLoan/{loan}")
    public ResponseEntity<?> getLoan(@PathVariable String loan) throws IOException {
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

    @GetMapping(path = "/loansSortByPersonBirthday")
    public ResponseEntity<?> loansSortByPersonBirthday() throws IOException {

        return ResponseEntity.ok(servises.loansSortByPersonBirthday());
    }
}
