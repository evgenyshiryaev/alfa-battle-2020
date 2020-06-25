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
import ru.alfa.task3.dto.BranchesWithPredicting;
import ru.alfa.task3.dto.DataTimeForModel;
import ru.alfa.task3.dto.ErrorResponse;
import ru.alfa.task3.entity.Branches;
import ru.alfa.task3.services.BranchesService;
import ru.alfa.task3.services.DataAnalystService;

@RestController
public class PredictController {

    private BranchesService branchesService;
    private DataAnalystService dataAnalystService;

    @Autowired
    public PredictController(BranchesService branchesService, DataAnalystService dataAnalystService) {
        this.branchesService = branchesService;
        this.dataAnalystService = dataAnalystService;
    }

    @GetMapping(value = "/branches/{id}/predict", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = BranchesWithPredicting.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    public ResponseEntity<?> getBranchesWithPredicting(@PathVariable Long id, @RequestParam Integer dayOgWeek, @RequestParam Integer hourOfDay) {
        Branches branches = branchesService.findById(id);
        if (branches == null) {
            return new ResponseEntity<>(new ErrorResponse("branch not found"), HttpStatus.NOT_FOUND);
        }

        BranchesWithPredicting branchesWithPredicting = new BranchesWithPredicting(branches);
        DataTimeForModel dataTimeForModel = new DataTimeForModel(id, dayOgWeek, hourOfDay);

        branchesWithPredicting.setPredicting(dataAnalystService.getModel().get(dataTimeForModel));
        branchesWithPredicting.setHourOfDay(hourOfDay);
        branchesWithPredicting.setDayOfWeek(dayOgWeek);

        return ResponseEntity.ok(branchesWithPredicting);
    }

}
