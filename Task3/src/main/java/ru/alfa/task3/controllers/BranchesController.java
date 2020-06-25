package ru.alfa.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ru.alfa.task3.dto.ErrorResponse;
import ru.alfa.task3.entity.Branches;
import ru.alfa.task3.services.BranchesService;
import ru.alfa.task3.services.DistanceService;

@RestController
public class BranchesController {
    private BranchesService branchesService;
    private DistanceService distanceService;

    @Autowired
    public BranchesController(BranchesService branchesService, DistanceService distanceService) {
        this.branchesService = branchesService;
        this.distanceService = distanceService;
    }


    @GetMapping(value = "/branches/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = Branches.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    public ResponseEntity<?> getBranch(@PathVariable Long id) {
        Branches branches = branchesService.findById(id);
        return branches != null
                ? ResponseEntity.ok(branches)
                : new ResponseEntity<>(new ErrorResponse("branch not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    public Branches getNearBranch(@RequestParam Double lat, @RequestParam Double lon) {
        return distanceService.getNearBranch(lat,lon);
    }
}
