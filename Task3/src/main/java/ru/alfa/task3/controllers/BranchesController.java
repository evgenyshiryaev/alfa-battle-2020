package ru.alfa.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public Branches getBranch(@PathVariable Long id) {
        return branchesService.findById(id);
    }

    @GetMapping(value = "/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    public Branches getNearBranch(@RequestParam Double lat, @RequestParam Double lon) {
        return distanceService.getNearBranch(lat,lon);
    }
}
