package ru.alfa.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alfa.task3.dto.BranchesWithPredicting;
import ru.alfa.task3.dto.DataTimeForModel;
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
    public BranchesWithPredicting getBranchesWithPredicting(@PathVariable Long id, @RequestParam Integer dayOgWeek, @RequestParam Integer hourOfDay) {

        BranchesWithPredicting branchesWithPredicting = new BranchesWithPredicting(branchesService.findById(id));
        DataTimeForModel dataTimeForModel = new DataTimeForModel(id, dayOgWeek, hourOfDay);

        branchesWithPredicting.setPredicting(dataAnalystService.getModel().get(dataTimeForModel));
        branchesWithPredicting.setHourOfDay(hourOfDay);
        branchesWithPredicting.setDayOfWeek(dayOgWeek);

        return branchesWithPredicting;
    }
}
